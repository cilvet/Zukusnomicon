package cilveti.inigo.cbmobile2;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.couchbase.lite.CouchbaseLiteException;
import com.couchbase.lite.DataSource;
import com.couchbase.lite.Database;
import com.couchbase.lite.DatabaseConfiguration;
import com.couchbase.lite.Document;
import com.couchbase.lite.Expression;
import com.couchbase.lite.FullTextExpression;
import com.couchbase.lite.FullTextIndex;
import com.couchbase.lite.FullTextIndexItem;
import com.couchbase.lite.IndexBuilder;
import com.couchbase.lite.Meta;
import com.couchbase.lite.MutableDocument;
import com.couchbase.lite.Query;
import com.couchbase.lite.QueryBuilder;
import com.couchbase.lite.Result;
import com.couchbase.lite.ResultSet;
import com.couchbase.lite.SelectResult;
import com.google.gson.Gson;
import com.mancj.materialsearchbar.MaterialSearchBar;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cilveti.inigo.cbmobile2.models.Conjuro;
import cilveti.inigo.cbmobile2.models.ConjuroContainer;
import cilveti.inigo.cbmobile2.models.SearchResult;

public class ContainerActivity extends AppCompatActivity implements MainProcess {

    private Database database;
    private static String databaseName = "mydb";
    private FullTextIndex index;
    private static final String indexName = "mindex";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_container);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED ||ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED  ) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
        }else{
            abrirFragmento();
            abrirDatabase();
        }

    }

    private void abrirFragmento(){
        // Create fragment and give it an argument specifying the article it should show
        MainSearchFragment newFragment = new MainSearchFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        // Replace whatever is in the fragment_container view with this fragment,
        // and add the transaction to the back stack so the user can navigate back
        transaction.replace(R.id.fragment_container, newFragment);
        // Commit the transaction
        transaction.commit();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    abrirFragmento();
                    abrirDatabase();
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.

                } else {
                    Toast.makeText(this, "pur que no me kieres D:", Toast.LENGTH_LONG).show();
                    finish();
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }
    
    private void abrirDatabase(){
        database = getDatabase();

        if(database!=null){
            index = IndexBuilder.fullTextIndex(FullTextIndexItem.property("nombre"),
                    //FullTextIndexItem.property("descripcion"),
                    FullTextIndexItem.property("conjuroRaw")).setLanguage("es").ignoreAccents(true);
            try {
                database.createIndex(indexName, index);
            } catch (CouchbaseLiteException e) {
                e.printStackTrace();
            }
        }
    }

    private Database getDatabase(){

        Database database = null;

        if(!Database.exists(databaseName, new File(this.getFilesDir().getAbsolutePath()))){
            return getPreBuiltDatabase();
        }

        DatabaseConfiguration config = new DatabaseConfiguration(this.getApplicationContext());
        try {
            database = new Database(databaseName, config);
        } catch (CouchbaseLiteException e) {
            e.printStackTrace();
        }
        return database;
    }


    private Database getPreBuiltDatabase(){
        Database database = null;

        try{

            String path =  "mydb.cblite2.zip";
            String targetPath = this.getFilesDir().getAbsolutePath() + "/";
            if (path != null){
                Decompress.unzipFromAssets(this, path, targetPath);
            }
            DatabaseConfiguration config = new DatabaseConfiguration(this.getApplicationContext());
            database = new Database(databaseName, config);

        }catch (Exception e){
            e.printStackTrace();
        }
        return database;
    }

    private void cargarDB(){
        ConjuroContainer conjuroContainer = loadJSONFromAsset(this);


        // Get the database (and create it if it doesn’t exist).
        DatabaseConfiguration config = new DatabaseConfiguration(getApplicationContext());
        Database database = null;
        try {
            database = new Database("mydb", config);
        } catch (CouchbaseLiteException e) {
            e.printStackTrace();
        }


        for(Conjuro conjuro : conjuroContainer.getConjuros()){

            Map<String, Object> properties = conjuro.getPropertiesForUpdate();

            MutableDocument mutableDoc = new MutableDocument(properties);
            try {
                database.save(mutableDoc);
            } catch (CouchbaseLiteException e) {
                e.printStackTrace();
            }

        }
    }

    public ConjuroContainer loadJSONFromAsset(Context context) {
        String json = null;
        ConjuroContainer conjuroContainer = null;
        try {
            InputStream is = context.getAssets().open("conjuros.json");

            int size = is.available();

            byte[] buffer = new byte[size];

            is.read(buffer);

            is.close();

            json = new String(buffer, "UTF-8");

            conjuroContainer = new Gson().fromJson(json, ConjuroContainer.class);


        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return conjuroContainer;

    }

    @Override
    public Conjuro getConjuro(String id) {
        return Conjuro.documentMapToConjuro(database.getDocument(id).toMap());
    }

    @Override
    public List<SearchResult> getResults(String query) {
        List<SearchResult> searchResults = new ArrayList<>();

        FullTextExpression expression = FullTextExpression.index(indexName);
        Query searchQuery = QueryBuilder
                .select(SelectResult.expression(Meta.id),
                        SelectResult.expression(Expression.property("nombre")),
                        SelectResult.expression(Expression.property("descripcion")))
                .from(DataSource.database(database))
                .where(expression.match(query))
                .limit(Expression.intValue(100));
        ResultSet results = null;
        try {
            results = searchQuery.execute();
            List<Result> resultList = results.allResults();
            for(Result result: resultList){
                List<String> keys = result.getKeys();
                searchResults.add(new SearchResult(result.toMap()));
            }
        } catch (CouchbaseLiteException e) {
            e.printStackTrace();
        }

        return searchResults;
    }

    @Override
    public void openConjuro(String id) {
        // Create fragment and give it an argument specifying the article it should show
        ConjuroFragment newFragment = ConjuroFragment.newInstance(id);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        // Replace whatever is in the fragment_container view with this fragment,
        // and add the transaction to the back stack so the user can navigate back
        transaction.replace(R.id.fragment_container, newFragment);
        transaction.setCustomAnimations(R.anim.fade_in_left, R.anim.fade_in_right);
        transaction.addToBackStack(null);
        // Commit the transaction
        transaction.commit();
    }

    @Override
    public void openSearch() {
        onBackPressed();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}

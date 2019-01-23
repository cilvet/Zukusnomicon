package cilveti.inigo.cbmobile2.ui.activities;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.couchbase.lite.CouchbaseLiteException;
import com.couchbase.lite.Database;
import com.couchbase.lite.DatabaseOptions;
import com.couchbase.lite.Document;
import com.couchbase.lite.Manager;
import com.couchbase.lite.ManagerOptions;
import com.couchbase.lite.UnsavedRevision;
import com.couchbase.lite.android.AndroidContext;
import com.couchbase.lite.auth.Authenticator;
import com.couchbase.lite.auth.AuthenticatorFactory;
import com.couchbase.lite.replicator.RemoteRequestResponseException;
import com.couchbase.lite.replicator.Replication;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cilveti.inigo.cbmobile2.R;
import cilveti.inigo.cbmobile2.business.interfaces.MainProcess;
import cilveti.inigo.cbmobile2.data.CouchbaseManager;
import cilveti.inigo.cbmobile2.data.DataFetcher;
import cilveti.inigo.cbmobile2.data.LocalDataFetcher;
import cilveti.inigo.cbmobile2.models.Conjuro;
import cilveti.inigo.cbmobile2.models.ConjuroContainer;
import cilveti.inigo.cbmobile2.ui.fragments.ConjuroFragment;
import cilveti.inigo.cbmobile2.ui.fragments.MainSearchFragment;
import cilveti.inigo.cbmobile2.utils.Decompress;
import cilveti.inigo.cbmobile2.utils.file_utils;

public class ContainerActivity extends AppCompatActivity implements MainProcess {

    private Database database;
    private static String databaseName = "mydb";
    private static final String indexName = "mindex";
    private CouchbaseManager manager;


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
            init();
        }

    }

    private void init(){
        abrirFragmento();
        try {
            this.database = openDatabase(databaseName);
            manager = new CouchbaseManager(database, database);
            setUpReplciation();


        } catch (IOException e) {
            e.printStackTrace();
        } catch (CouchbaseLiteException e) {
            e.printStackTrace();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    private Replication puller;
    private static String replicationUrl ="https://c9611163-45f9-427d-8b47-a23170b5abbf-bluemix.cloudant.com/conjuros";
    private static String user = "ontoodyingsturadshonertl";
    private static String apikey = "ae6a52a586e128feca26059833887f4317b2c827";


    private void setUpReplciation(){

        Replication.ChangeListener changeListener = new Replication.ChangeListener() {
            @Override
            public void changed(Replication.ChangeEvent event) {
                if (event.getError() != null) {
                    Throwable throwable = event.getError();
                    if (throwable instanceof RemoteRequestResponseException) {
                        if (((RemoteRequestResponseException) throwable).getCode() == 401) {
                            Log.i("error login", "errrrrooooooorrr 401");
                        }
                    }
                }
            }
        };

        URL url = null;
        try {
            url = new URL(this.replicationUrl);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        puller = database.createPushReplication(url);
        puller.addChangeListener(changeListener);

        Authenticator authenticator = AuthenticatorFactory.createBasicAuthenticator(user, apikey);
        puller.setAuthenticator(authenticator);
        puller.setContinuous(true);
        puller.start();
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
                    init();

                } else {
                    Toast.makeText(this, "ZUKUS TE ODIA", Toast.LENGTH_LONG).show();
                    finish();

                }
                return;
            }

        }
    }


    private Database openDatabase(String dbName) throws IOException, CouchbaseLiteException, NullPointerException {

        String dbname = dbName;
        DatabaseOptions options = new DatabaseOptions();

        // if the database doesn't exist, don't create it and throw an exception instead
        options.setCreate(false);
        //options.setEncryptionKey(key);
        Manager manager = null;
        ManagerOptions managerOptions = new ManagerOptions();
        managerOptions.setExecutorThreadPoolSize(5);

        manager = new Manager(new AndroidContext(getApplicationContext()), managerOptions);
        Database mydatabase = manager.openDatabase(dbname, options);

        if(mydatabase==null){
            return getPreBuiltDatabase(dbName);
        }

        return mydatabase;
    }

    private Database getPreBuiltDatabase(String dbName){
        Database database = null;

        try{

            String path =  "mydb.cblite2.zip";
            String targetPath = this.getFilesDir().getAbsolutePath() + "/";
            if (path != null){
                Decompress.unzipFromAssets(this, path, targetPath);
            }

            return openDatabase(dbName);

        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    private void cargarDB() throws JSONException, IOException, CouchbaseLiteException {

        JSONObject conjuroContainer = loadJSONFromAsset(this);
        JSONArray array = (JSONArray)conjuroContainer.get("conjuros");
        List<Conjuro> conjuros = new ArrayList<>();
        Gson mygson = new Gson();
        for(int i = 0; i < array.length(); i++){
            conjuros.add(mygson.fromJson(array.get(i).toString(), Conjuro.class));
        }


        DatabaseOptions options = new DatabaseOptions();

        // if the database doesn't exist, don't create it and throw an exception instead
        options.setCreate(true);
        //options.setEncryptionKey(key);
        Manager manager = null;
        ManagerOptions managerOptions = new ManagerOptions();
        managerOptions.setExecutorThreadPoolSize(5);

        manager = new Manager(new AndroidContext(getApplicationContext()), managerOptions);
        Database mydatabase = manager.openDatabase(databaseName, options);
        this.database = mydatabase;

        for(Conjuro conjuro : conjuros){

            final Map<String, Object> properties = conjuro.getPropertiesForUpdate();

            final Document newSpell = database.createDocument();

            newSpell.update(new Document.DocumentUpdater() {
                @Override
                public boolean update(UnsavedRevision newRevision) {
                    newRevision.setProperties(properties);
                    return true;
                }
            });

        }

    }

    public JSONObject loadJSONFromAsset(Context context) {
        String json = null;
        ConjuroContainer conjuroContainer = null;
        JSONObject object = null;

        try {
            InputStream is = context.getAssets().open("results.json");

            int size = is.available();

            byte[] buffer = new byte[size];

            is.read(buffer);

            is.close();

            json = new String(buffer, "UTF-8");
            object = new JSONObject(json);

            //conjuroContainer = new Gson().fromJson(json, ConjuroContainer.class);


        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return object;

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
    public void copyDatabase(){
        file_utils.copyDatabase(databaseName, this, database);
    }

    @Override
    public DataFetcher getLocalDataFetcher() {
        return new LocalDataFetcher(manager);
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

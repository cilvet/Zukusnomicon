package cilveti.inigo.cbmobile2.ui.activities;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
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
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cilveti.inigo.cbmobile2.R;
import cilveti.inigo.cbmobile2.business.interfaces.MainActivity;
import cilveti.inigo.cbmobile2.business.interfaces.MainProcess;
import cilveti.inigo.cbmobile2.constants.Constants;
import cilveti.inigo.cbmobile2.data.CouchbaseManager;
import cilveti.inigo.cbmobile2.data.DataFetcher;
import cilveti.inigo.cbmobile2.data.LocalDataFetcher;
import cilveti.inigo.cbmobile2.models.Conjuro;
import cilveti.inigo.cbmobile2.models.ConjuroContainer;
import cilveti.inigo.cbmobile2.ui.fragments.ConjuroFragment;
import cilveti.inigo.cbmobile2.ui.fragments.MainSearchFragment;
import cilveti.inigo.cbmobile2.utils.Decompress;
import cilveti.inigo.cbmobile2.utils.file_utils;

public class ContainerActivity extends AppCompatActivity implements MainActivity {


    MainProcess mainProcess;

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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==Constants.RESULT_CODE_NUEVO_CONJURO){
            try{
                Conjuro conjuro = new Gson().fromJson(data.getStringExtra("conjuro"), Conjuro.class);
                mainProcess.getLocalDataFetcher().putConjuro(conjuro);

            }catch (Exception ex){
                ex.printStackTrace();
            }

        }
    }

    private void init(){
        abrirFragmento();
        this.mainProcess = (MainProcess) getApplication();
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




    @Override
    public void openConjuro(String id) {

        Intent intent = new Intent(this, ConjuroActivity.class);
        intent.putExtra("id", id);
        startActivity(intent);

//
//        // Create fragment and give it an argument specifying the article it should show
//        ConjuroFragment newFragment = ConjuroFragment.newInstance(id);
//        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//        // Replace whatever is in the fragment_container view with this fragment,
//        // and add the transaction to the back stack so the user can navigate back
//        transaction.add(R.id.fragment_container, newFragment);
//        transaction.show( newFragment);
//        transaction.setCustomAnimations(R.anim.fade_in_left, R.anim.fade_in_right);
//        transaction.addToBackStack(null);
//        // Commit the transaction
//        transaction.commit();
    }

    @Override
    public MainProcess getMainprocess() {
        return mainProcess;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
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

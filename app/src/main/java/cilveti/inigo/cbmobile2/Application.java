package cilveti.inigo.cbmobile2;

import android.content.Context;

import com.couchbase.lite.CouchbaseLiteException;
import com.couchbase.lite.Database;
import com.couchbase.lite.DatabaseOptions;
import com.couchbase.lite.Document;
import com.couchbase.lite.Manager;
import com.couchbase.lite.ManagerOptions;
import com.couchbase.lite.UnsavedRevision;
import com.couchbase.lite.android.AndroidContext;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cilveti.inigo.cbmobile2.business.interfaces.MainProcess;
import cilveti.inigo.cbmobile2.data.CouchbaseManager;
import cilveti.inigo.cbmobile2.data.DataFetcher;
import cilveti.inigo.cbmobile2.data.LocalDataFetcher;
import cilveti.inigo.cbmobile2.models.Conjuro;
import cilveti.inigo.cbmobile2.models.ConjuroContainer;
import cilveti.inigo.cbmobile2.utils.Decompress;
import cilveti.inigo.cbmobile2.utils.file_utils;

public class Application extends android.app.Application  implements MainProcess {


    private Database database;
    private static String databaseName = "mydb";
    private static final String indexName = "mindex";
    private CouchbaseManager manager;

    @Override
    public void onCreate() {
        super.onCreate();
        init();
    }

    @Override
    public void copyDatabase(){
        file_utils.copyDatabase(databaseName, this, database);
    }

    @Override
    public DataFetcher getLocalDataFetcher() {
        return new LocalDataFetcher(manager);
    }

    public void init(){
        try {
            this.database = openDatabase(databaseName);
            manager = new CouchbaseManager(database, database);
            manager.setUpReplciation();
            manager.restartReplication();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (CouchbaseLiteException e) {
            e.printStackTrace();
        } catch (Exception e){
            e.printStackTrace();
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
}

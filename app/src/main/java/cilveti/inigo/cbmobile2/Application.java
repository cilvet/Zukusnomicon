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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cilveti.inigo.cbmobile2.business.interfaces.MainProcess;
import cilveti.inigo.cbmobile2.data.CouchbaseManager;
import cilveti.inigo.cbmobile2.data.DataFetcher;
import cilveti.inigo.cbmobile2.data.LocalDataFetcher;
import cilveti.inigo.cbmobile2.models.Conjuro;
import cilveti.inigo.cbmobile2.models.ConjuroContainer;
import cilveti.inigo.cbmobile2.models.ConjuroV2;
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
//            try {
//                mydatabase = cargarDB();
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
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

    private Database cargarDB() throws JSONException, IOException, CouchbaseLiteException {

        JSONObject conjuroContainer = loadJSONFromAsset(this);
        JSONArray array = (JSONArray)conjuroContainer.get("conjuros");
        List<Conjuro> conjuros = new ArrayList<>();
        List<ConjuroV2> conjurosV2 = new ArrayList<>();

        Gson mygson = new Gson();

        List<String> escuelas = new ArrayList<>();
        List<String> subescuelas = new ArrayList<>();
        List<String> descriptores = new ArrayList<>();
        List<String> componentes = new ArrayList<>();
        List<String> tiradasSalvacion = new ArrayList<>();
        List<String> resistenciasConjuros = new ArrayList<>();
        List<String> areas = new ArrayList<>();
        List<String> clases = new ArrayList<>();
        List<String> nivelesNumericos = new ArrayList<>();
        List<String> duraciones = new ArrayList<>();


        for(int i = 0; i < array.length(); i++){
            ConjuroV2 miConjurito = new ConjuroV2(mygson.fromJson(array.get(i).toString(), Conjuro.class));
            conjurosV2.add(miConjurito);

            if(!escuelas.contains(miConjurito.getEscuela()))escuelas.add(miConjurito.getEscuela());
            if(!subescuelas.contains(miConjurito.getSubEscuela()))subescuelas.add(miConjurito.getSubEscuela());
            if(!tiradasSalvacion.contains(miConjurito.getTiradaSalvacion()))tiradasSalvacion.add(miConjurito.getTiradaSalvacion());
            if(!resistenciasConjuros.contains(miConjurito.getResistenciaConjuros()))resistenciasConjuros.add(miConjurito.getResistenciaConjuros());
            if(!areas.contains(miConjurito.getArea()))areas.add(miConjurito.getArea());

            for(String string: miConjurito.getDescriptores())if(!descriptores.contains(string))descriptores.add(string);
            for(String string: miConjurito.getComponentes())if(!componentes.contains(string))componentes.add(string);
            for(String string: miConjurito.getClases())if(!clases.contains(string))clases.add(string);
            for(String string: miConjurito.getNivelesNumeros())if(!nivelesNumericos.contains(string))nivelesNumericos.add(string);
            for(String string: miConjurito.getDuracion())if(!duraciones.contains(string))duraciones.add(string);

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

        final Map<String, Object> statsDoc = new HashMap<>();
        statsDoc.put("escuelas", escuelas);
        statsDoc.put("subescuelas", subescuelas);
        statsDoc.put("descriptores", descriptores);
        statsDoc.put("componentes", componentes);
        statsDoc.put("tiradasSalvacion", tiradasSalvacion);
        statsDoc.put("resistenciasConjuros", resistenciasConjuros);
        statsDoc.put("nivelesNumericos", nivelesNumericos);
        statsDoc.put("areas", areas);
        statsDoc.put("clases", clases);
        statsDoc.put("duraciones", duraciones);

        final Document statsDocument = database.getDocument("statsDoc");

        statsDocument.update(new Document.DocumentUpdater() {
            @Override
            public boolean update(UnsavedRevision newRevision) {
                newRevision.setProperties(statsDoc);
                return true;
            }
        });

        for(ConjuroV2 conjuro : conjurosV2){

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
        return mydatabase;

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

package cilveti.inigo.cbmobile2.data;

import android.util.Log;

import com.couchbase.lite.CouchbaseLiteException;
import com.couchbase.lite.Database;
import com.couchbase.lite.Document;
import com.couchbase.lite.Emitter;
import com.couchbase.lite.Mapper;
import com.couchbase.lite.UnsavedRevision;
import com.couchbase.lite.View;
import com.couchbase.lite.auth.Authenticator;
import com.couchbase.lite.auth.AuthenticatorFactory;
import com.couchbase.lite.replicator.RemoteRequestResponseException;
import com.couchbase.lite.replicator.Replication;


import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

import cilveti.inigo.cbmobile2.models.SearchResult;
import cilveti.inigo.cbmobile2.utils.Functions;
import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;

import static cilveti.inigo.cbmobile2.constants.Constants.INDEX_CURRENTLY_UPDATING;
import static cilveti.inigo.cbmobile2.constants.Constants.INDEX_FAILED_TO_UPDATE;
import static cilveti.inigo.cbmobile2.constants.Constants.INDEX_UPDATED;
import static cilveti.inigo.cbmobile2.constants.Constants.INDEX_UP_TO_DATE;


/**
 * Created by inigo on 14/02/2018.
 * Clase que se ocupa de efectuar y recibir todos los cambios o información en un documento
 */

public class CouchbaseManager {

    private Database database;
    private Database searchDatabase;

    private boolean bulkWorkGoing = false;

    private View generalView;
    private View latestView;
    private View filteredView;



    private Replication puller;
    private Replication pusher;
    private static String replicationUrl ="https://c9611163-45f9-427d-8b47-a23170b5abbf-bluemix.cloudant.com/conjuros";
    private static String user = "ontoodyingsturadshonertl";
    private static String apikey = "ae6a52a586e128feca26059833887f4317b2c827";


    public CouchbaseManager(final Database database, Database searchDatabase) {
        this.database = database;
        this.searchDatabase = searchDatabase;
        setUpViews();
        
    }


    public void setUpReplciation(){

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
        puller = database.createPullReplication(url);
        pusher = database.createPushReplication(url);
        puller.addChangeListener(changeListener);

        Authenticator authenticator = AuthenticatorFactory.createBasicAuthenticator(user, apikey);

        puller.setAuthenticator(authenticator);
        pusher.setAuthenticator(authenticator);

        puller.setContinuous(true);
        pusher.setContinuous(true);

    }

    public void restartReplication(){
        if(puller!=null && pusher!=null){
            pusher.restart();
            puller.restart();
        }
    }

    public boolean isPushPending(Document  docId){
        if(pusher!=null){
            return pusher.isDocumentPending(docId);
        }
        return false;
    }

    boolean indexing = false;

    public Integer updateGeneral(){

        if(indexing)return INDEX_CURRENTLY_UPDATING;

        if(generalView!=null && generalView.isStale()){
            indexing = true;
            try {
                generalView.updateIndexAlone();
                indexing = false;
                return INDEX_UPDATED;
            } catch (CouchbaseLiteException e) {
                e.printStackTrace();
                indexing = false;
                return INDEX_FAILED_TO_UPDATE;
            }
        }
        return INDEX_UP_TO_DATE;
    }

    private static final String VIEW_TITLE = "title";
    private static final String VIEW_LATEST = "latest";
    private static final String VIEW_DESCRIPTION = "description";
    private static final String TYPE = "type";
    private static final String SPELL = "conjuro";
    private static final String NAME = "nombre";
    private static final String NOMBRE_ORIGINAL = "manual"; //todo: cambiar esto en el json
    private static final String DESCRIPTION = "descripcion";
    private static final String TIME = "time";

    private void setUpViews(){

        generalView = database.getView(VIEW_TITLE);

        if (generalView.getMap() == null) {
            generalView.setMap(new Mapper() {
                @Override
                public void map(Map<String, Object> document, Emitter emitter) {

                    String spanishName = ((String) document.get(NAME));
                    String originalName = ((String) document.get(NOMBRE_ORIGINAL));

                    if(spanishName == null)spanishName= "";
                    if(originalName == null)originalName= "";

                    spanishName = spanishName.trim().replaceAll(" +", " ");
                    originalName = originalName.trim().replaceAll(" +", " ");

                    SearchResult resultobject = new SearchResult(originalName, spanishName, (String )document.get("_id"), (ArrayList<String>) document.get(DESCRIPTION));

                    emitter.emit(spanishName, resultobject);
                    emitter.emit(originalName, resultobject);

                    List<String> allNames = new ArrayList<>();
                    allNames.addAll(Arrays.asList(spanishName.split(" ")));
                    allNames.addAll(Arrays.asList(originalName.split(" ")));

                    int lastindex = 0;
                    int spaces = allNames.size() - 2;

                    for(int i = 0; i< spaces; i++){
                        Integer index = spanishName.indexOf(" ", lastindex);
                        lastindex = index + 1;
                        if(index!=null && index!=-1){
                            allNames.add(spanishName.substring(index + 1));
                        }
                    }

                    for(int i = 0; i< allNames.size(); i++){
                        emitter.emit(allNames.get(i), resultobject);
                    }

                }
            },"4");
        }

        latestView = database.getView(VIEW_LATEST);

        if (latestView.getMap() == null) {
            latestView.setMap(new Mapper() {
                @Override
                public void map(Map<String, Object> document, Emitter emitter) {

                    String spanishName = ((String) document.get(NAME));
                    String originalName = ((String) document.get(NOMBRE_ORIGINAL));
                    String time = ((String) document.get(TIME));

                    if(spanishName == null)spanishName= "";
                    if(originalName == null)originalName= "";
                    if(time==null)return;

                    SearchResult resultobject = new SearchResult(originalName, spanishName, (String )document.get("_id"), (ArrayList<String>) document.get(DESCRIPTION));

                    emitter.emit(time, resultobject);

                }
            },"1");
        }

//        if (filteredView.getMap() == null) {
//            filteredView.setMap(new Mapper() {
//                @Override
//                public void map(Map<String, Object> document, Emitter emitter) {
//
//
//                    String spanishName = ((String) document.get(NAME));
//                    String originalName = ((String) document.get(NOMBRE_ORIGINAL));
//                    String time = ((String) document.get(TIME));
//
//                    if(spanishName == null)spanishName= "";
//                    if(originalName == null)originalName= "";
//                    if(time==null)return;
//
//                    SearchResult resultobject = new SearchResult(originalName, spanishName, (String )document.get("_id"), (ArrayList<String>) document.get(DESCRIPTION));
//
//                    emitter.emit(time, resultobject);
//
//                }
//            },"1");
//        }

    }
    
    public Observable<Document> getDocument(final String docId){
        return Observable.fromCallable(new Callable<Document>() {
            @Override
            public Document call() throws Exception {
                return database.getDocument(docId);
            }
        });
    }

    public void upsertDocument(final Map<String, Object> properties){
        Document doc = database.createDocument();
        try {
            Map<String, Object> updateProperties = doc.update(new Document.DocumentUpdater() {
                @Override
                public boolean update(UnsavedRevision newRevision) {
                    newRevision.setProperties(properties);
                    return true;
                }
            }).getProperties();
            if(updateProperties!=null);

        } catch (CouchbaseLiteException e) {
            e.printStackTrace();
        }
    }

    public void upsertDocumentWithTime(final Map<String, Object> properties){
        properties.put("time", Functions.getStringInstant());
        Document doc = database.createDocument();
        try {
            Map<String, Object> updateProperties = doc.update(new Document.DocumentUpdater() {
                @Override
                public boolean update(UnsavedRevision newRevision) {
                    newRevision.setProperties(properties);
                    return true;
                }
            }).getProperties();
            if(updateProperties!=null);

        } catch (CouchbaseLiteException e) {
            e.printStackTrace();
        }
    }

    public Observable<Document> getDocumentLive(final String docId){

        final PublishSubject<Document> subject = PublishSubject.create();


        database.getDocument(docId).addChangeListener(new Document.ChangeListener() {
            @Override
            public void changed(Document.ChangeEvent event) {
                subject.onNext(event.getSource());
            }
        });

        return subject;
    }

    public Document getDocumentSynchronously(final String docId){
        return database.getDocument(docId);
    }

    public View getGeneralView(){
        return generalView;
    }

    public View getSearchGeneralView(){

        //return searchDatabase.getView(VIEW_TITLE);
        return generalView;
    }


    public View getLatestView(){

        //return searchDatabase.getView(VIEW_TITLE);
        return latestView;
    }



    public Database getDatabase(){
        return database;
    }

    public Observable<Boolean> updateDocumentObservableV1(final Map<String, Object> properties, final String id, final String rev){

        return Observable.fromCallable(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                return updateDocument(properties, id, rev);
            }
        });
    }

    /**
     *
     *
     * @return true if the document was updated successfully
     */
    public boolean updateDocument(final Map<String, Object> properties, String id, String rev){
        final Document doc = database.getExistingDocument(id);
        if(doc == null || !doc.getCurrentRevisionId().equals(rev)) return false;

        try {
            Map<String, Object> updateProperties = doc.update(new Document.DocumentUpdater() {
                 @Override
                 public boolean update(UnsavedRevision newRevision) {
                     newRevision.setProperties(properties);
                     return true;
                 }
             }).getProperties();
            if(updateProperties!=null) return true;
            
        } catch (CouchbaseLiteException e) {
            e.printStackTrace();
            return false;
        }
        return false;
    }
    
}

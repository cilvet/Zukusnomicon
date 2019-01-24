package cilveti.inigo.cbmobile2.data;

import com.couchbase.lite.Document;
import com.couchbase.lite.LiveQuery;
import com.couchbase.lite.Query;
import com.couchbase.lite.QueryEnumerator;
import com.couchbase.lite.QueryRow;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

import cilveti.inigo.cbmobile2.models.Conjuro;
import cilveti.inigo.cbmobile2.models.SearchResult;
import cilveti.inigo.cbmobile2.utils.Functions;
import io.reactivex.Observable;
import io.reactivex.functions.Function;

import static cilveti.inigo.cbmobile2.utils.Functions.getUniqueResults;

public class LocalDataFetcher implements DataFetcher {

    private CouchbaseManager manager;

    public LocalDataFetcher(CouchbaseManager manager) {
        this.manager = manager;
    }

    @Override
    public Observable<List<SearchResult>> getTitleSearchResults(String mQuery) {
        final Query query = manager.getSearchGeneralView().createQuery().toLiveQuery();
        query.setIndexUpdateMode(Query.IndexUpdateMode.BEFORE);
        query.setStartKey(mQuery);
        query.setEndKey(mQuery + "\u02ad");
        query.setLimit(500);

        Observable<List<SearchResult>> livequeryObservable = Functions.observeLiveQuery(query.toLiveQuery()).map(new Function<LiveQuery.ChangeEvent, List<SearchResult>>() {
            @Override
            public List<SearchResult> apply(LiveQuery.ChangeEvent changeEvent) throws Exception {
                List<SearchResult> searchResults = new ArrayList<>();
                if(changeEvent.getRows()!=null){
                    for(QueryRow row: changeEvent.getRows()){
                        searchResults.add(new SearchResult((Map<String, Object>) row.getValue()));
                    }
                }
                return searchResults;
            }
        });

        return Observable.fromCallable(new Callable<List<SearchResult>>() {
            @Override
            public List<SearchResult> call() throws Exception {
                QueryEnumerator enumerator = query.run();
                List<SearchResult> searchResults = new ArrayList<>();

                for(QueryRow row: enumerator){
                    searchResults.add(new SearchResult((Map<String, Object>) row.getValue()));
                }
                return searchResults;
            }
        }).mergeWith(livequeryObservable).map(new Function<List<SearchResult>, List<SearchResult>>() {
            @Override
            public List<SearchResult> apply(List<SearchResult> results) throws Exception {
                return getUniqueResults(results);
            }
        });

    }

    @Override
    public Observable<List<SearchResult>> getLatestResults() {
        final Query query = manager.getLatestView().createQuery().toLiveQuery();
        query.setIndexUpdateMode(Query.IndexUpdateMode.BEFORE);
        query.setDescending(true);
        query.setLimit(500);

        Observable<List<SearchResult>> livequeryObservable = Functions.observeLiveQuery(query.toLiveQuery()).map(new Function<LiveQuery.ChangeEvent, List<SearchResult>>() {
            @Override
            public List<SearchResult> apply(LiveQuery.ChangeEvent changeEvent) throws Exception {
                List<SearchResult> searchResults = new ArrayList<>();
                if(changeEvent.getRows()!=null){
                    for(QueryRow row: changeEvent.getRows()){
                        searchResults.add(new SearchResult((Map<String, Object>) row.getValue()));
                    }
                }
                return searchResults;
            }
        });

        return Observable.fromCallable(new Callable<List<SearchResult>>() {
            @Override
            public List<SearchResult> call() throws Exception {
                QueryEnumerator enumerator = query.run();
                List<SearchResult> searchResults = new ArrayList<>();

                for(QueryRow row: enumerator){
                    searchResults.add(new SearchResult((Map<String, Object>) row.getValue()));
                }
                return searchResults;
            }
        }).mergeWith(livequeryObservable).map(new Function<List<SearchResult>, List<SearchResult>>() {
            @Override
            public List<SearchResult> apply(List<SearchResult> results) throws Exception {
                return getUniqueResults(results);
            }
        });

    }

    @Override
    public Observable<Conjuro> getConjuro(final String id) {
        return manager.getDocument(id)
                .mergeWith(manager.getDocumentLive(id))
                .map(new Function<Document, Conjuro>() {
                    @Override
                    public Conjuro apply(Document document) throws Exception {
                        Conjuro conjuro =  Conjuro.documentMapToConjuro(document.getProperties());
                        conjuro.setChecked(!manager.isPushPending(document));
                        return conjuro;
                    }
                });
    }

    @Override
    public void putConjuro(Conjuro conjuro) {
        manager.upsertDocumentWithTime(conjuro.getPropertiesForUpdate());
    }
}

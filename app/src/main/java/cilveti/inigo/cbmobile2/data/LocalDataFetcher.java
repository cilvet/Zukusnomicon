package cilveti.inigo.cbmobile2.data;

import com.couchbase.lite.Document;
import com.couchbase.lite.Query;
import com.couchbase.lite.QueryEnumerator;
import com.couchbase.lite.QueryRow;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

import cilveti.inigo.cbmobile2.models.Conjuro;
import cilveti.inigo.cbmobile2.models.SearchResult;
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

        return Observable.fromCallable(new Callable<List<SearchResult>>() {
            @Override
            public List<SearchResult> call() throws Exception {
                List<SearchResult> searchResults = new ArrayList<>();
                QueryEnumerator enumerator = query.run();
                for(QueryRow row: enumerator){
                    Map<String, Object> map = (Map<String, Object>) row.getValue();
                    searchResults.add(new SearchResult(map));
                }
                return getUniqueResults(searchResults);
            }
        });
    }

    @Override
    public Observable<Conjuro> getConjuro(String id) {
        return manager.getDocument(id)
                .map(new Function<Document, Conjuro>() {
                    @Override
                    public Conjuro apply(Document document) throws Exception {
                        return Conjuro.documentMapToConjuro(document.getProperties());
                    }
                });
    }
}

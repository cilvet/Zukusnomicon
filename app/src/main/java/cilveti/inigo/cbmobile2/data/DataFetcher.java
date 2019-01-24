package cilveti.inigo.cbmobile2.data;


import java.util.List;

import cilveti.inigo.cbmobile2.models.Conjuro;
import cilveti.inigo.cbmobile2.models.SearchResult;
import io.reactivex.Observable;

public interface DataFetcher {

    Observable<List<SearchResult>> getTitleSearchResults(String query);
    Observable<List<SearchResult>> getLatestResults();
    Observable<Conjuro> getConjuro(String id);
    void putConjuro(Conjuro conjuro);

}

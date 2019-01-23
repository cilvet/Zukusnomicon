package cilveti.inigo.cbmobile2.data;


import java.util.List;

import cilveti.inigo.cbmobile2.models.Conjuro;
import cilveti.inigo.cbmobile2.models.SearchResult;
import io.reactivex.Observable;

public interface DataFetcher {

    public Observable<List<SearchResult>> getTitleSearchResults(String query);
    public Observable<Conjuro> getConjuro(String id);

}

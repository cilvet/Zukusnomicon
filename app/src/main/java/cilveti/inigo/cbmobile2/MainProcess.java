package cilveti.inigo.cbmobile2;

import java.util.List;

import cilveti.inigo.cbmobile2.models.Conjuro;
import cilveti.inigo.cbmobile2.models.SearchResult;

public interface MainProcess {
    Conjuro getConjuro(String id);
    List<SearchResult> getResults(String query);
    void openConjuro(String id);
    void openSearch();
}

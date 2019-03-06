package cilveti.inigo.cbmobile2.business.interfaces;

import java.util.List;

import cilveti.inigo.cbmobile2.data.DataFetcher;
import cilveti.inigo.cbmobile2.models.Conjuro;
import cilveti.inigo.cbmobile2.models.SearchResult;

public interface MainProcess {
    void copyDatabase();
    DataFetcher getLocalDataFetcher();
}

package cilveti.inigo.cbmobile2.business.interfaces;

import java.util.List;

import cilveti.inigo.cbmobile2.models.SearchResult;

public interface MainSearchContract {
    interface View {
        void showResults(List<SearchResult> results);
    }

    interface Presenter{
        void search(String query);
        void getLatest();
    }
}

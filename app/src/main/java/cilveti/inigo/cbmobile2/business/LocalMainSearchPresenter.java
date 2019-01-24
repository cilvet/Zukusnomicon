package cilveti.inigo.cbmobile2.business;

import java.util.List;

import cilveti.inigo.cbmobile2.business.interfaces.MainSearchContract;
import cilveti.inigo.cbmobile2.data.DataFetcher;
import cilveti.inigo.cbmobile2.models.SearchResult;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class LocalMainSearchPresenter implements MainSearchContract.Presenter {

    DataFetcher fetcher;
    MainSearchContract.View view;

    Disposable disposable;

    public LocalMainSearchPresenter(DataFetcher fetcher, MainSearchContract.View view) {
        this.fetcher = fetcher;
        this.view = view;
    }

    @Override
    public void search(String query) {

        if(disposable!=null)disposable.dispose();

        disposable = fetcher.getTitleSearchResults(query)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<SearchResult>>() {
                    @Override
                    public void accept(List<SearchResult> searchResults) throws Exception {
                        view.showResults(searchResults);
                    }
                });
    }

    @Override
    public void getLatest() {
        if(disposable!=null)disposable.dispose();

        disposable = fetcher.getLatestResults()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<SearchResult>>() {
                    @Override
                    public void accept(List<SearchResult> searchResults) throws Exception {
                        view.showResults(searchResults);
                    }
                });
    }
}

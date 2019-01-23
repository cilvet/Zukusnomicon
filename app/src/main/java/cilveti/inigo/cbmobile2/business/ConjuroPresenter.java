package cilveti.inigo.cbmobile2.business;

import cilveti.inigo.cbmobile2.business.interfaces.ConjuroContract;
import cilveti.inigo.cbmobile2.data.DataFetcher;
import cilveti.inigo.cbmobile2.models.Conjuro;
import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class ConjuroPresenter implements ConjuroContract.Presenter {

    DataFetcher fetcher;
    ConjuroContract.View view;
    private Disposable disposable;

    public ConjuroPresenter(DataFetcher fetcher, ConjuroContract.View view) {
        this.fetcher = fetcher;
        this.view = view;
    }

    @Override
    public void getConjuro(String id) {

        if(disposable!=null)disposable.dispose();

        disposable = fetcher.getConjuro(id)
                .observeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Conjuro>() {
                    @Override
                    public void accept(Conjuro conjuro) throws Exception {
                        view.showConjuro(conjuro);
                    }
                });
    }
}

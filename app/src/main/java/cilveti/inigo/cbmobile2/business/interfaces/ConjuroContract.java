package cilveti.inigo.cbmobile2.business.interfaces;
import cilveti.inigo.cbmobile2.models.Conjuro;
import io.reactivex.Observable;

public interface ConjuroContract {
    interface View{
        void showConjuro(Conjuro conjuro);
    }

    interface Presenter{
        void getConjuro(String id);
    }
}

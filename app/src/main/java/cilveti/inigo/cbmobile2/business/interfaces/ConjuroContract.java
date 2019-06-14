package cilveti.inigo.cbmobile2.business.interfaces;
import cilveti.inigo.cbmobile2.models.Conjuro;
import cilveti.inigo.cbmobile2.models.ConjuroV2;
import io.reactivex.Observable;

public interface ConjuroContract {
    interface View{
        void showConjuro(ConjuroV2 conjuro);
    }

    interface Presenter{
        void getConjuro(String id);
    }
}

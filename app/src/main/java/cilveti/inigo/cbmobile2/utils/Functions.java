package cilveti.inigo.cbmobile2.utils;

import android.content.Context;
import android.support.annotation.NonNull;

import com.couchbase.lite.LiveQuery;

import java.util.ArrayList;
import java.util.List;

import cilveti.inigo.cbmobile2.models.SearchResult;
import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;

public class Functions {
    /**
     * Obtener la altura de la status bar.
     *
     * @param context Contexto utilizado para acceder a recursos.
     * @return La altura de la status bar.
     */
    public static int getStatusBarHeight(Context context) {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    public static Observable<LiveQuery.ChangeEvent> observeLiveQuery(LiveQuery liveQuery){

        final PublishSubject<LiveQuery.ChangeEvent> subject = PublishSubject.create();
        liveQuery.addChangeListener(new LiveQuery.ChangeListener() {
            @Override
            public void changed(LiveQuery.ChangeEvent event) {
                subject.onNext(event);
            }
        });
        liveQuery.start();
        return subject;
    }

    /**
     *
     * @param originalResults una lista de searchresult
     * @return devuelve la lista con los resultados "duplicados" (es decir, que tengan el mismo id) eliminados
     */
    public static List<SearchResult> getUniqueResults(List<SearchResult> originalResults){
        List<String> ids = new ArrayList<>();
        List<SearchResult> finalResults = new ArrayList<>();
        for(SearchResult result: originalResults){
            if(!ids.contains(result.getId())){
                finalResults.add(result);
                ids.add(result.getId());
            }
        }
        return finalResults;
    }

    public static long getInstant(){
        return System.currentTimeMillis();
    }

    @NonNull
    public static String getStringInstant(){
        return String.valueOf(getInstant());
    }
}

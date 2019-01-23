package cilveti.inigo.cbmobile2.utils;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import cilveti.inigo.cbmobile2.models.SearchResult;

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
}

package cilveti.inigo.cbmobile2;

import android.content.Context;

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

}

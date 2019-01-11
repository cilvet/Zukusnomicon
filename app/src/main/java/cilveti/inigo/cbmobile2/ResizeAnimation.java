package cilveti.inigo.cbmobile2;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;

/**
 * Clase con una animación de redimensión.
 *
 * @author Javier Torné.
 */
public class ResizeAnimation extends Animation {

    private int targetHeight;
    private View view;
    private int startHeight;
    private int type;

    public ResizeAnimation(View view, int targetHeight, int startHeight, int type) {
        this.view = view;
        this.targetHeight = targetHeight;
        this.startHeight = startHeight;
        this.type = type;
    }

    @Override
    protected void applyTransformation(float interpolatedTime, Transformation t) {
        int newHeight;

        if (type == Constants.ANIMATION_TYPE_ASCENDENT)
            newHeight = (int) (startHeight + targetHeight * interpolatedTime);
        else
            newHeight = (int) (startHeight + (targetHeight - startHeight) * interpolatedTime);

        view.getLayoutParams().height = newHeight;
        view.requestLayout();
    }

    @Override
    public void initialize(int width, int height, int parentWidth, int parentHeight) {
        super.initialize(width, height, parentWidth, parentHeight);
    }

    @Override
    public boolean willChangeBounds() {
        return true;
    }

}

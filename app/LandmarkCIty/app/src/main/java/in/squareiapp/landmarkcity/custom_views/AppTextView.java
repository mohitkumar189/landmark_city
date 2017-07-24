package in.squareiapp.landmarkcity.custom_views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;

import in.squareiapp.landmarkcity.R;

/**
 * Created by mohit kumar on 7/24/2017.
 */

public class AppTextView extends android.support.v7.widget.AppCompatTextView {

    private final String FONT = "fonts/avanti_regular.ttf";

    public AppTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs);
    }

    public AppTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);

    }

    public AppTextView(Context context) {
        super(context);
        init(null);
    }

    private void init(AttributeSet attrs) {
        if (attrs!=null) {
            TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.AppTextView);
            String fontName = a.getString(R.styleable.AppTextView_avanti);
            if (fontName!=null) {
                Typeface myTypeface = Typeface.createFromAsset(getContext().getAssets(), FONT);
                setTypeface(myTypeface);
            }
            a.recycle();
        }
    }
}

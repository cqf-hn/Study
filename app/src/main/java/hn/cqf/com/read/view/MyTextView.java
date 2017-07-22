package hn.cqf.com.read.view;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * @author cqf
 * @time 2016/12/30 0030  上午 11:28
 * @desc ${TODD}
 */
public class MyTextView extends TextView {

    public MyTextView(Context context) {
        this(context,null);
    }

    public MyTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        Typeface tf =Typeface.createFromAsset(context.getAssets(),"fonts/PMingLiU.ttf");
        super.setTypeface(tf);
    }
}

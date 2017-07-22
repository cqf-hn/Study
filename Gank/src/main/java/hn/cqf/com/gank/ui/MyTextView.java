package hn.cqf.com.gank.ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import hn.cqf.com.gank.R;

public class MyTextView extends View {
    //文字画笔
    TextPaint mTextPaint;
    Paint circleP;

    String content = "";
    int mCount = 0;
    int mCurrentIndex = 0;
    //文字的间距
    int pading = 10;
    //内圆的直径
    int inner;


    OnClickListener mListener;
    private float mTextSize;

    public MyTextView(Context context) {
        super(context);
    }

    public MyTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        //获取到xml定义的属性
        TypedArray arry = context.obtainStyledAttributes(attrs, R.styleable.MyTextView);
        int innerColor = arry.getColor(R.styleable.MyTextView_innerColor, Color.BLUE);
        mTextSize = arry.getDimension(R.styleable.MyTextView_myTextSize, 20);


        mTextPaint = new TextPaint();
        //抗锯齿
        mTextPaint.setFlags(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setTextSize(mTextSize);
        mTextPaint.setColor(Color.WHITE);

        //内圈画笔
        circleP = new Paint();
        circleP.setFlags(Paint.ANTI_ALIAS_FLAG);
        circleP.setColor(innerColor);


        measureText();

        //使用完成后一定要回收
        arry.recycle();
    }

    private void measureText() {
        content = mCurrentIndex + "/" + mCount;
        //文字的宽度
        int length = content.length();
        float scale = 4 *1.0f / length;
        switch (length) {
            case 4:
                mTextPaint.setTextSize(mTextSize * scale);
                break;
            case 5:
                mTextPaint.setTextSize(mTextSize * scale);
                break;
            case 6:
                mTextPaint.setTextSize(mTextSize * scale);
                break;
            case 7:
                mTextPaint.setTextSize(mTextSize * scale);
                break;
        }
        float text_Width = mTextPaint.measureText(content);

        //内圆圈的直径
        inner = (int) text_Width + 2 * pading;
    }

    public void setCount(int count) {
        mCount = count;
        measureText();
        setMeasuredDimension(inner, inner);
        invalidate();
    }

    public void setCurrentIndex(int currentIndex) {
        mCurrentIndex = currentIndex;
        measureText();
        setMeasuredDimension(inner, inner);
        invalidate();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //文字的宽度+内圆的边距*2+画笔的宽度*2
        setMeasuredDimension(inner, inner);
    }

    public void setListener(OnClickListener listener) {
        this.mListener = listener;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //canvas.drawColor(Color.RED);
        canvas.drawCircle(inner / 2, inner / 2, inner / 2, circleP);

        float y = (canvas.getHeight() / 2);
        float de = mTextPaint.descent();//+
        float a = mTextPaint.ascent();//-

        Log.i("it520", "de = " + de + " a = " + a);

        //x 左边距
        //y 顶部到baseLine的距离
        canvas.drawText(content, pading, inner / 2 - ((de + a) / 2), mTextPaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                setAlpha(0.3f);
                break;
            case MotionEvent.ACTION_UP:
                setAlpha(1.0f);
                if (mListener != null) {
                    mListener.onClick(this);
                }
                break;
        }
        return true;
    }
}

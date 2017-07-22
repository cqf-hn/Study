package hn.cqf.com.gank.ui.network;

import android.support.v4.view.ViewCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import butterknife.BindView;
import hn.cqf.com.gank.R;
import hn.cqf.com.gank.base.BaseActivity;
import hn.cqf.com.gank.bean.DataBean;
import hn.cqf.com.gank.cons.Constant;

/**
 * @author cqf
 * @time 2017/1/26 0026  上午 12:25
 * @desc ${TODD}
 */
public class NetWorkActivity extends BaseActivity implements View.OnTouchListener {

    @BindView(R.id.web)
    WebView mWeb;
    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    float OldX1, OldY1, OldX2, OldY2, NewX1, NewY1, NewX2, NewY2;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;


    @Override
    protected int setContentViewByResId() {
        return R.layout.activity_network;
    }

    @Override
    protected void setupWindowAnimations() {
        getWindow().getEnterTransition().setDuration(getResources().getInteger(
                R.integer.animation_activity_duration));
    }

    @Override
    protected void initView() {
        DataBean data = (DataBean) getIntent().getSerializableExtra(
                Constant.PASS_DATABEAN_TO_NETWORK);
        ViewCompat.setTransitionName(mToolbarTitle, getString(R.string.share_ele_title));
        mToolbarTitle.setText(data.getDesc());

        //支持javascript
        mWeb.getSettings().setJavaScriptEnabled(true);
        // 设置可以支持缩放
        mWeb.getSettings().setSupportZoom(true);
        // 设置出现缩放工具
        mWeb.getSettings().setBuiltInZoomControls(true);
        // 禁止显示缩放条
        mWeb.getSettings().setDisplayZoomControls(false);
        //扩大比例的缩放
        mWeb.getSettings().setUseWideViewPort(true);
        //自适应屏幕
        mWeb.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        mWeb.getSettings().setLoadWithOverviewMode(true);
        // 是否支持脚本
        mWeb.getSettings().setJavaScriptEnabled(true);
        //mWeb.setOnTouchListener(this);
        mWeb.loadUrl(data.getUrl());
        //处理url重定向不要抛到系统浏览器
        mWeb.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                view.loadUrl(request.getUrl().getPath());
                return true;
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                Log.i("it520", "onReceivedError");
                super.onReceivedError(view, request, error);

            }

        });
    }

    @Override
    protected Toolbar getSpecialActionBar() {
        return mToolbar;
    }

    @Override
    protected void initEvent() {
        mWeb.setOnTouchListener(this);
    }


    //用以仿uc随手势放大页面,未成功
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_POINTER_2_DOWN:
                if (event.getPointerCount() == 2) {
                    for (int i = 0; i < event.getPointerCount(); i++) {
                        if (i == 0) {
                            OldX1 = event.getX(i);
                            OldY1 = event.getY(i);
                        } else if (i == 1) {
                            OldX2 = event.getX(i);
                            OldY2 = event.getY(i);
                        }
                    }
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if (event.getPointerCount() == 2) {
                    for (int i = 0; i < event.getPointerCount(); i++) {
                        if (i == 0) {
                            NewX1 = event.getX(i);
                            NewY1 = event.getY(i);
                        } else if (i == 1) {
                            NewX2 = event.getX(i);
                            NewY2 = event.getY(i);
                        }
                    }
                    float disOld = (float) Math.sqrt((Math.pow(OldX2 - OldX1, 2) + Math.pow(
                            OldY2 - OldY1, 2)));
                    float disNew = (float) Math.sqrt((Math.pow(NewX2 - NewX1, 2) + Math.pow(
                            NewY2 - NewY1, 2)));
                    Log.d("onTouch", "disOld=" + disOld + "|disNew=" + disNew);
                    if (disOld - disNew >= 25) {
                        // 缩小
                        mWeb.zoomOut();

                    } else if (disNew - disOld >= 25) {
                        // 放大
                        mWeb.zoomIn();
                    }
                    OldX1 = NewX1;
                    OldX2 = NewX2;
                    OldY1 = NewY1;
                    OldY2 = NewY2;
                }
        }
        return false;
    }

    @Override
    public void onBackPressed() {
        //如果webviwe能够回退到上一个页面
        if (mWeb.canGoBack()) {
            mWeb.goBack();
            return;
        }
        super.onBackPressed();
    }

    @Override
    protected void onStop() {
        if (null != mWeb) {
            mWeb.destroy();
        }
        super.onStop();
    }

}

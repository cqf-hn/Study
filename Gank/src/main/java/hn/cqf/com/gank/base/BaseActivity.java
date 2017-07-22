package hn.cqf.com.gank.base;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import hn.cqf.com.gank.R;
import hn.cqf.com.swipebacklayout.app.SwipeBackActivity;


/**
 * @author cqf
 * @time 2017/1/26 0026  下午 12:16
 * @desc ${TODD}
 */
public abstract class BaseActivity<T extends BasePresenter> extends SwipeBackActivity {

    protected T mPresenter;
    protected Unbinder mUnbinder;
    private long mLastBackTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(setContentViewByResId());
        mUnbinder = ButterKnife.bind(this);
        setSwipeBackEnable(isSwipeBack());
        setupWindowAnimations();
        initPresenter();
        initView();
        initActionBar();
        initEvent();
        initData();
    }

    protected boolean isSwipeBack() {
        return true;
    }

    /**
     * 元素共享时使用到的方法
     * Activity设置跳转动画
     */
    protected void setupWindowAnimations() {
        //default empty implements
    }

    protected void initPresenter() {
        //default empty implements
    }

    protected abstract int setContentViewByResId();

    protected abstract void initView();

    /**
     * 子类设置ActionBar，父类完成ActionBar的统一操作
     */
    private void initActionBar() {
        setSupportActionBar(getSpecialActionBar());
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);

            if(isTaskRoot()) {
                actionBar.setHomeAsUpIndicator(R.drawable.img_menu);
            } else {
                actionBar.setHomeAsUpIndicator(R.drawable.ic_back);
            }
        }
    }

    protected Toolbar getSpecialActionBar() {
        return null;
    }

    protected abstract void initEvent();

    protected void initData() {
        //default empty implements
    }


    @Override
    public void onBackPressed() {
        if(isTaskRoot()) {
            long now = System.currentTimeMillis();
            if(isMenuOpen()) {
                return;
            }

            if (now - mLastBackTime < 2000) {
                finish();
            } else {
                Toast.makeText(this, "再次点击退出当前应用", Toast.LENGTH_SHORT).show();
            }
            mLastBackTime = now;
        } else {
            super.onBackPressed();
//            finish();
//            if(Build.VERSION.SDK_INT > 5) {
//                overridePendingTransition(R.anim.close_enter_anim,R.anim.close_exit_anim);
//            }

        }
        //super.onBackPressed();
    }

    /**
     * 由启动页面判断菜单是否打开,打开则,关闭菜单然后返回
     * 不打开则任何事都不做
     */
    protected boolean isMenuOpen() {
        return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return true;
    }




    @Override
    public void onDestroy() {
        super.onDestroy();
        if(mUnbinder!=null) {
            mUnbinder.unbind();
        }
        if (mPresenter != null) {
            mPresenter.detach();
        }
    }
}

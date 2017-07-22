package hn.cqf.com.read.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import hn.cqf.com.read.ui.main.MainActivity;

/**
 * @author cqf
 * @time 2016/12/30 0030  下午 4:59
 * @desc ${TODD}
 */
public abstract class MenuBaseFragment extends Fragment {

    protected GestureDetector mGestureDetector;
    protected MainActivity mActivity;
    protected View mView;
    protected float mDx;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(null == mView) {
            mView = specialView(inflater,container);
        }
        initView();
        initEvent();
        return mView;
    }




    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mActivity = (MainActivity) getActivity();
        init();
    }

    private void init() {
        mGestureDetector = new GestureDetector(mActivity, new MyOnGestureListener());
    }




    /**
     * 获取子类提供的View
     * @return
     * @param inflater
     * @param container
     */
    protected abstract View specialView(LayoutInflater inflater, ViewGroup container);

    /**
     * 初始化View
     */
    protected abstract void initView();

    /**
     * 初始化事件
     */
    protected abstract void initEvent();

    /**
     * 对应不同的菜单,进行相对于的手势操作
     * @return
     */
    protected abstract boolean isLeft();


    /**
     * 对手势操作的简单封装
     */
    protected class MyOnGestureListener extends GestureDetector.SimpleOnGestureListener {

        //滑动时候的回调
        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2,
                                float distanceX, float distanceY) {
            if(isLeft()) {
                mDx += distanceX;
            }else{
                mDx -= distanceX;
            }
            if(mDx >= 200) {
                mActivity.showContent();
            }
            return true;
        }

        //快速滑动时候的回调
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
                               float velocityY) {
            if(velocityX>1000 && !isLeft()) {
                mActivity.showContent();
            }
            if(velocityX<-1000 && isLeft()) {
                mActivity.showContent();
            }
            return true;
        }
    }
}



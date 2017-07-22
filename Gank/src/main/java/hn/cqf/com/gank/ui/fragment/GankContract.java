package hn.cqf.com.gank.ui.fragment;

import android.app.Activity;
import android.support.annotation.Nullable;

import java.io.Serializable;
import java.util.List;

import hn.cqf.com.gank.base.BaseActivity;
import hn.cqf.com.gank.base.BasePresenter;
import hn.cqf.com.gank.base.BaseView;
import hn.cqf.com.gank.bean.DataBean;

/**
 * @author cqf
 * @time 2017/2/14 0014  下午 10:14
 * @desc ${TODD}
 */
public interface GankContract {
    interface View extends BaseView {
        void setAdapterData(List<DataBean> dataBeen);

        void addData(List<DataBean> dataBeen);
    }

    /**
     * 处理与用户交互的负责逻辑
     */
    interface Presenter extends BasePresenter<View> {
        void startActivity(@Nullable Serializable data, Activity activity,
                           Class<? extends BaseActivity> clz, @Nullable android.view.View v);

        void onPullUpRefresh();
    }
}

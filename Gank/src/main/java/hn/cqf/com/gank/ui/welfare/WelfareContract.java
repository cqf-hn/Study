package hn.cqf.com.gank.ui.welfare;

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
 * @time 2017/1/29 0029  下午 4:21
 * @desc ${TODD}
 */
public interface WelfareContract {

    interface View extends BaseView {
        void setAdapterData(List<DataBean> dataBeen);

        void addData(List<DataBean> dataBeen);
    }

    /**
     * 处理与用户交互的负责逻辑
     */
    interface Presenter extends BasePresenter<WelfareContract.View> {
        void startActivity(@Nullable Serializable data, int position, Activity activity,
                           Class<? extends BaseActivity> clz, @Nullable android.view.View v);

        void onPullUpRefresh();
    }
}
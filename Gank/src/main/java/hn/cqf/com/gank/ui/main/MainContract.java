package hn.cqf.com.gank.ui.main;

import android.app.Activity;
import android.support.annotation.Nullable;

import java.io.Serializable;
import java.util.List;

import hn.cqf.com.gank.base.BaseActivity;
import hn.cqf.com.gank.base.BasePresenter;
import hn.cqf.com.gank.base.BaseView;
import hn.cqf.com.gank.bean.DataBean;
import hn.cqf.com.pickerview.bean.TimeInfo;

/**
 * @author cqf
 * @time 2017/1/17 0017  下午 5:01
 * @desc ${TODD}
 */
public interface MainContract {
    /**
     * 负责绘制UI元素
     */
    interface View extends BaseView {
        /**
         * 更新头部图片
         *
         * @param url 图片url
         */
        void updateHeaderImage(String url);

        void updateHeaderBackground(int rgb);

        void updateUserName(String name);

        /**
         * 设置用户Icon
         * @param url
         */
        void updateUserIcon(String url);
        /**
         * 更新头部标题
         *
         * @param title 标题字符串
         */
        void updateTitle(String title);

        void setTimeRange(List<TimeInfo> timeInfos);

        void setAdapterData(List<DataBean> dataBeen);
    }

    /**
     * 处理与用户交互的负责逻辑
     */
    interface Presenter extends BasePresenter<MainContract.View>{
        void onTimeSelect(TimeInfo info);
        void onWelfareSelect();//福利
        void onGankSelect();//干货订阅
        void login();//登录
        void reward();//奖赏
        void talking();//交流
        void startActivity(@Nullable Serializable data, Activity activity,
                           Class<? extends BaseActivity> clz,@Nullable android.view.View v);
    }
}

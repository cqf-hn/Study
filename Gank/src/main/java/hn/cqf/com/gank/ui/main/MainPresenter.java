package hn.cqf.com.gank.ui.main;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.view.View;

import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import hn.cqf.com.gank.R;
import hn.cqf.com.gank.api.APIManager;
import hn.cqf.com.gank.base.BaseActivity;
import hn.cqf.com.gank.bean.DataBean;
import hn.cqf.com.gank.bean.DayDataInfo;
import hn.cqf.com.gank.cons.Constant;
import hn.cqf.com.gank.utils.StringsToTimeInfosMapper;
import hn.cqf.com.gank.utils.TransitionHelper;
import hn.cqf.com.pickerview.bean.TimeInfo;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * @author cqf
 * @time 2017/1/18 0018  下午 12:26
 * @desc ${TODD}
 */
public class MainPresenter implements MainContract.Presenter {
    private MainContract.View mView;
    Subscription mSubscription;
    Subscriber mSubscriber = new Subscriber<List<DataBean>>() {
        @Override
        public void onSubscribe(Subscription s) {
            mSubscription = s;
            s.request(Long.MAX_VALUE);
        }

        @Override
        public void onNext(List<DataBean> dataBeen) {
            mView.setAdapterData(dataBeen);
        }

        @Override
        public void onError(Throwable t) {
            mView.showError(t.toString(), t);
        }

        @Override
        public void onComplete() {

        }
    };

    @Override
    public void attachView(MainContract.View view) {
        this.mView = view;
    }

    @Override
    public void start() {
        APIManager.getApiService().getHistory()
                .subscribeOn(Schedulers.io())
                .map(StringsToTimeInfosMapper.getInstance())
                .flatMap(new Function<List<TimeInfo>, Publisher<DayDataInfo>>() {
                    @Override
                    public Publisher<DayDataInfo> apply(List<TimeInfo> timeInfos) throws Exception {
                        TimeInfo info = timeInfos.get(0);
                        mView.setTimeRange(timeInfos);
                        return APIManager.getApiService().getDayData(info.getYear(),
                                info.getMonth(), info.getDay());
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Function<DayDataInfo, List<DataBean>>() {
                    @Override
                    public List<DataBean> apply(DayDataInfo dayDataInfo) throws Exception {
                        if (dayDataInfo.get福利() != null) {
                            DataBean bean = dayDataInfo.get福利().get(0);
                            mView.updateHeaderImage(bean.getUrl());
                            mView.updateTitle(bean.getWho());
                            mView.updateUserName(bean.getWho());
                            //仅仅是调用而已  最终icon用updateHeaderImage压缩过的
                            mView.updateUserIcon(bean.getUrl());
                        }
                        List<DataBean> datas = new ArrayList<>();
                        if (dayDataInfo.getAndroid() != null) {
                            addDataBean(datas, dayDataInfo.getAndroid());
                        }
                        if (dayDataInfo.getIOS() != null) {
                            addDataBean(datas, dayDataInfo.getIOS());
                        }
                        if (dayDataInfo.getApp() != null) {
                            addDataBean(datas, dayDataInfo.getApp());
                        }
                        if (dayDataInfo.get拓展资源() != null) {
                            addDataBean(datas, dayDataInfo.get拓展资源());
                        }
                        if (dayDataInfo.get瞎推荐() != null) {
                            addDataBean(datas, dayDataInfo.get瞎推荐());
                        }
                        if (dayDataInfo.get休息视频() != null) {
                            addDataBean(datas, dayDataInfo.get休息视频());
                        }
                        return datas;
                    }
                })
                .subscribe(mSubscriber);
    }

    private void addDataBean(List<DataBean> datas, List<DataBean> itemDatas) {
        String type = itemDatas.get(0).getType();
        DataBean dataBean = new DataBean();
        dataBean.setType(type);

        if (type.equals("休息视频")) {
            datas.addAll(0, itemDatas);
            datas.add(0, dataBean);
        } else {
            datas.add(dataBean);
            datas.addAll(itemDatas);
        }
    }

    @Override
    public void onTimeSelect(TimeInfo info) {
        APIManager.getApiService().getDayData(info.getYear(),
                info.getMonth(), info.getDay())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Function<DayDataInfo, List<DataBean>>() {
                    @Override
                    public List<DataBean> apply(DayDataInfo dayDataInfo) throws Exception {
                        if (dayDataInfo.get福利() != null) {
                            DataBean bean = dayDataInfo.get福利().get(0);
                            mView.updateHeaderImage(bean.getUrl());
                            mView.updateTitle(bean.getWho());
                            mView.updateUserName(bean.getWho());
                            //仅仅是调用而已  最终icon用updateHeaderImage压缩过的
                            mView.updateUserIcon(bean.getUrl());
                        }
                        List<DataBean> datas = new ArrayList<>();
                        if (dayDataInfo.getAndroid() != null) {
                            addDataBean(datas, dayDataInfo.getAndroid());
                        }
                        if (dayDataInfo.getIOS() != null) {
                            addDataBean(datas, dayDataInfo.getIOS());
                        }
                        if (dayDataInfo.getApp() != null) {
                            addDataBean(datas, dayDataInfo.getApp());
                        }
                        if (dayDataInfo.get拓展资源() != null) {
                            addDataBean(datas, dayDataInfo.get拓展资源());
                        }
                        if (dayDataInfo.get瞎推荐() != null) {
                            addDataBean(datas, dayDataInfo.get瞎推荐());
                        }
                        if (dayDataInfo.get休息视频() != null) {
                            addDataBean(datas, dayDataInfo.get休息视频());
                        }
                        return datas;
                    }
                })
                .subscribe(mSubscriber);
    }

    @Override
    public void onWelfareSelect() {

    }

    @Override
    public void onGankSelect() {

    }

    @Override
    public void login() {

    }

    @Override
    public void reward() {

    }

    @Override
    public void talking() {

    }

    @Override
    public void startActivity(@Nullable Serializable data, Activity activity,
                              Class<? extends BaseActivity> clz, @Nullable View v) {
        Intent intent = new Intent(activity, clz);
        if (data != null) {
            DataBean bean = (DataBean) data;
            intent.putExtra(Constant.PASS_DATABEAN_TO_NETWORK, bean);
        }
        /*
         * grideView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
         *     @Override
         *     public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
         *         ActivityOptionsCompat  aop=ActivityOptionsCompat.makeSceneTransitionAnimation(MainActivity.this,
         *                 view.findViewById(R.id.imageView),"123");
         *         Intent   intent=new Intent(getApplicationContext(),SecondActivity.class);
         *        ActivityCompat.startActivity(MainActivity.this,intent,aop.toBundle());
         *     }
         * });
         * */
        if (v != null) {
            Pair<View, String>[] pairs = TransitionHelper.createSafeTransitionParticipants(activity, false,
                    new Pair<>(v, activity.getString(R.string.share_ele_title)));
            startActivity(intent, pairs, activity);
        } else {
            activity.startActivity(intent);
            if (Build.VERSION.SDK_INT > 5) {
                activity.overridePendingTransition(R.anim.open_enter_anim, R.anim.open_exit_anim);
            }
        }

    }

    private void startActivity(Intent intent, Pair<View, String>[] pairs, Activity activity) {

        ActivityOptionsCompat transitionActivityOptions = ActivityOptionsCompat.makeSceneTransitionAnimation
                (activity, pairs);
        ActivityCompat.startActivity(activity, intent, transitionActivityOptions.toBundle());
    }

    @Override
    public void detach() {
        mView = null;
        if (mSubscription != null) {
            mSubscription.cancel();
        }
    }
}

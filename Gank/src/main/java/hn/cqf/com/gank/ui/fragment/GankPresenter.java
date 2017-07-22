package hn.cqf.com.gank.ui.fragment;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.util.Log;
import android.view.View;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.io.Serializable;
import java.util.List;

import hn.cqf.com.gank.R;
import hn.cqf.com.gank.api.APIManager;
import hn.cqf.com.gank.base.BaseActivity;
import hn.cqf.com.gank.bean.DataBean;
import hn.cqf.com.gank.cons.Constant;
import hn.cqf.com.gank.utils.TransitionHelper;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @author cqf
 * @time 2017/2/14 0014  下午 10:12
 * @desc ${TODD}
 */
public class GankPresenter  implements GankContract.Presenter {
    private GankContract.View mView;
    private int mPage = 1;
    private boolean isRefresh;//如果正在下拉刷新则防止再刷新,(没啥用)
    private String type;

    public int getPage() {
        return mPage;
    }

    public void setPage(int page) {
        mPage = page;
    }

    Subscription mSubscription;
    Subscriber mSubscriber = new Subscriber<List<DataBean>>() {
        @Override
        public void onSubscribe(Subscription s) {
            mSubscription = s;
            s.request(Long.MAX_VALUE);
        }

        @Override
        public void onNext(List<DataBean> dataBeen) {
            Log.v("shan", "" + dataBeen);
            if (mPage == 1) {
                mView.setAdapterData(dataBeen);
            } else {
                mView.addData(dataBeen);
                isRefresh = false;
            }
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
    public void attachView(GankContract.View view) {
        this.mView = view;
    }

    public void attachViewAndSetType(GankContract.View view, String type) {
        attachView(view);
        this.type = type;
    }

    @Override
    public void detach() {
        mView = null;
        if (mSubscription != null) {
            mSubscription.cancel();
        }
    }

    @Override
    public void start() {
        if (mPage != 1) {
            mPage = 1;
        }
        APIManager.getApiService().getTypeData(type,
                Constant.GET_TYPE_DATA_COUNT, mPage)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(mSubscriber);
    }

    @Override
    public void startActivity(@Nullable Serializable data, Activity activity,
                              Class<? extends BaseActivity> clz, @Nullable View v) {
        Intent intent = new Intent(activity, clz);
        if (data != null) {
            //待修改
            intent.putExtra(Constant.PASS_DATABEAN_TO_NETWORK, data);

        }
        if (v != null) {
            //待修改
            Pair<View, String>[] pairs = TransitionHelper.createSafeTransitionParticipants(activity, false,
                    new Pair<>(v, activity.getString(R.string.share_ele_title)));
            startActivity(intent, pairs, activity);
        }
        /*else {
            activity.startActivity(intent);
            if (Build.VERSION.SDK_INT > 5) {
                activity.overridePendingTransition(R.anim.open_enter_anim, R.anim.open_exit_anim);
            }
        }*/
    }

    @Override
    public void onPullUpRefresh() {
        if (isRefresh) {
            return;
        }
        mPage++;
        APIManager.getApiService().getTypeData(type,
                Constant.GET_TYPE_DATA_COUNT, mPage)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(mSubscriber);
    }

    private void startActivity(Intent intent, Pair<View, String>[] pairs, Activity activity) {

        ActivityOptionsCompat transitionActivityOptions = ActivityOptionsCompat.makeSceneTransitionAnimation
                (activity, pairs);
        ActivityCompat.startActivity(activity, intent, transitionActivityOptions.toBundle());
    }

    public void setRefresh(boolean refresh) {
        isRefresh = refresh;
    }
}

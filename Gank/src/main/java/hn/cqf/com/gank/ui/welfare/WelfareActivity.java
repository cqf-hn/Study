package hn.cqf.com.gank.ui.welfare;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.transition.ChangeBounds;
import android.transition.ChangeImageTransform;
import android.transition.TransitionSet;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import hn.cqf.com.gank.R;
import hn.cqf.com.gank.adapter.LoadMoreWrapper;
import hn.cqf.com.gank.adapter.PictureAdapter;
import hn.cqf.com.gank.base.BaseActivity;
import hn.cqf.com.gank.bean.DataBean;
import hn.cqf.com.gank.cons.Constant;

/**
 * @author cqf
 * @time 2017/1/29 0029  下午 4:19
 * @desc 福利
 */
public class WelfareActivity extends BaseActivity<WelfarePresenter>
        implements WelfareContract.View, SwipeRefreshLayout.OnRefreshListener,
        LoadMoreWrapper.OnLoadMoreListener {
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout mSwipeRefreshLayout;
    private PictureAdapter mAdapter;
    private StaggeredGridLayoutManager mLayoutManager;
    private LoadMoreWrapper mLoadMoreWrapper;

    @Override
    protected int setContentViewByResId() {
        return R.layout.activity_welfare;
    }

    @Override
    protected void setupWindowAnimations() {
        TransitionSet mtransitionset = new TransitionSet();
        mtransitionset.addTransition(new ChangeBounds());
        mtransitionset.addTransition(new ChangeImageTransform());
        //  mtransitionset.addTransition(new Fade());
        mtransitionset.setDuration(R.integer.animation_activity_duration);
        getWindow().setEnterTransition(mtransitionset);
        getWindow().setExitTransition(mtransitionset);
        getWindow().setSharedElementEnterTransition(mtransitionset);
        getWindow().setSharedElementExitTransition(mtransitionset);
        getWindow().setAllowEnterTransitionOverlap(false);
    }

    @Override
    protected void initPresenter() {
        mPresenter = new WelfarePresenter();
        mPresenter.attachView(this);
    }

    @Override
    protected void initView() {
        mAdapter = new PictureAdapter(this);
        mAdapter.setHasStableIds(true);
        mLoadMoreWrapper = new LoadMoreWrapper(mAdapter);
        mLoadMoreWrapper.setLoadMoreView(R.layout.default_loading);
        mLoadMoreWrapper.setOnLoadMoreListener(this);
        mLayoutManager = new StaggeredGridLayoutManager(2,
                StaggeredGridLayoutManager.VERTICAL);
        mLayoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_NONE);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setPadding(0, 0, 0, 0);
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                mLayoutManager.invalidateSpanAssignments();
            }
        });
        //设置自定义分割线
        //mRecyclerView.addItemDecoration(new DividerGridItemDecoration(this));
        mRecyclerView.setAdapter(mLoadMoreWrapper);
    }

    @Override
    protected Toolbar getSpecialActionBar() {
        mToolbar.setTitle(Constant.GANK_TYPE_WELFARE);
        return mToolbar;
    }

    @Override
    protected void initEvent() {
        mSwipeRefreshLayout.setOnRefreshListener(this);
    }

    @Override
    protected void initData() {
        mSwipeRefreshLayout.setRefreshing(true);
        mPresenter.start();
    }

    @Override
    public void showError(String msg, Throwable e) {
        mSwipeRefreshLayout.setRefreshing(false);
        Toast.makeText(WelfareActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onRefresh() {
        // 刷新重新请求数据
        mSwipeRefreshLayout.setRefreshing(true);
        mPresenter.start();
    }

    @Override
    public void setAdapterData(List<DataBean> dataBeen) {
        mSwipeRefreshLayout.setRefreshing(false);
        //mAdapter.setDataAndRefresh(dataBeen);
        mAdapter.setDataAndRefresh((ArrayList<DataBean>) dataBeen);
        mLoadMoreWrapper.notifyItemRangeChanged(0, dataBeen.size());
    }

    @Override
    public void addData(List<DataBean> dataBeen) {
        int lastCount = mAdapter.getItemCount();
        mAdapter.addData(dataBeen);
        mLoadMoreWrapper.notifyItemRangeInserted(lastCount, dataBeen.size());
    }

    @Override
    public void onLoadMoreRequested() {
        mPresenter.onPullUpRefresh();
    }
}

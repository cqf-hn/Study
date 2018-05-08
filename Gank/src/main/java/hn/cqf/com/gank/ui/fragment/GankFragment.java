package hn.cqf.com.gank.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import hn.cqf.com.gank.R;
import hn.cqf.com.gank.adapter.GankListAdapter;
import hn.cqf.com.gank.adapter.LoadMoreWrapper;
import hn.cqf.com.gank.base.BaseActivity;
import hn.cqf.com.gank.bean.DataBean;
import hn.cqf.com.gank.cons.Constant;

import static hn.cqf.com.gank.R.id.recycler_view;
import static hn.cqf.com.gank.R.id.swipe_refresh_layout;

/**
 * @author cqf
 * @time 2017/2/6 0006  下午 10:12
 * @desc 干货订阅:ViewPager的item(Fragment)
 */
public class GankFragment extends Fragment implements GankContract.View,
        LoadMoreWrapper.OnLoadMoreListener,
        SwipeRefreshLayout.OnRefreshListener {

    @BindView(recycler_view)
    RecyclerView mRecyclerView;
    @BindView(swipe_refresh_layout)
    SwipeRefreshLayout mSwipeRefreshLayout;
    private String mTitle;

    private GankListAdapter mAdapter;
    private LoadMoreWrapper mLoadMoreWrapper;
    private LinearLayoutManager mLayoutManager;
    private GankPresenter mPresenter;
    private Bundle savedState;
    private static String mSimpleName;
    private static String mKeyPage;
    private int mPage;


    public static GankFragment newInstance(String title) {
        Bundle args = new Bundle();
        args.putString(Constant.ARGS_GANK_FRAGMENT, title);
        GankFragment fragment = new GankFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mTitle = getArguments().getString(Constant.ARGS_GANK_FRAGMENT);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        //创建View,初始化View
        View view = initView(inflater, container);
        initPresenter();
        initEvent();
        if (!restoreStateFromArguments()) {
            // First Time running, Initialize something here
            mPresenter.setPage(mPage);
        }

        return view;
    }

    private boolean restoreStateFromArguments() {

        Bundle b = getArguments();
        savedState = b.getBundle(mSimpleName);
        if (savedState != null) {
            restoreState();
            return true;
        }
        return false;
    }

    // 取出数据
    private void restoreState() {
        if (savedState != null) {
            setAdapterData((List<DataBean>) savedState.getSerializable(mTitle));
            mPage = savedState.getInt(mKeyPage);
            //比如
            //tv1.setText(savedState.getString(“text”));
        }
    }

    private void initPresenter() {
        mPresenter = new GankPresenter();
        mPresenter.attachViewAndSetType(this,mTitle);
    }

    @NonNull
    private View initView(LayoutInflater inflater, @Nullable ViewGroup container) {
        View view = inflater.inflate(R.layout.fragment_gank, container, false);
        mRecyclerView = (RecyclerView) view.findViewById(recycler_view);
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(swipe_refresh_layout);
        mAdapter = new GankListAdapter((BaseActivity) getActivity());
        mLoadMoreWrapper = new LoadMoreWrapper(mAdapter);
        mLoadMoreWrapper.setLoadMoreView(R.layout.default_loading);
        mLoadMoreWrapper.setOnLoadMoreListener(this);
        mLayoutManager = new LinearLayoutManager(view.getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mLoadMoreWrapper);
        return view;
    }


    private void initEvent() {
        mSwipeRefreshLayout.setOnRefreshListener(this);
    }



    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //获取数据
        if(mPage == 0) {
            initData();
        }
    }

    private void initData() {
        mSwipeRefreshLayout.setRefreshing(true);
        mPresenter.start();
    }


    @Override
    public void onLoadMoreRequested() {
        mPresenter.onPullUpRefresh();
    }

    @Override
    public void setAdapterData(List<DataBean> dataBeen) {
        mSwipeRefreshLayout.setRefreshing(false);
        //mAdapter.setDataAndRefresh(dataBeen);
        mAdapter.setDataAndRefresh((ArrayList<DataBean>) dataBeen);
        mLoadMoreWrapper.notifyDataSetChanged();
        mLoadMoreWrapper.notifyItemRangeChanged(0,dataBeen.size());
    }

    @Override
    public void addData(List<DataBean> dataBeen) {
        int lastCount = mAdapter.getItemCount();
        mAdapter.addData(dataBeen);
        mLoadMoreWrapper.notifyItemRangeInserted(lastCount,dataBeen.size());
    }

    @Override
    public void showError(String msg, Throwable e) {
        mSwipeRefreshLayout.setRefreshing(false);
        Toast.makeText(getActivity(), msg, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onRefresh() {
        // 刷新重新请求数据
        mSwipeRefreshLayout.setRefreshing(true);
        mPresenter.start();
    }

    // onSaveInstanceState 可能不会被调用 在Fragment中
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        saveStateToArguments(outState);
    }


    private void saveStateToArguments(@Nullable Bundle outState) {
        Bundle b = outState;
        if(getView() != null) {
            savedState = saveState();
        }
        if (savedState != null) {
            if(b == null) {
                b = getArguments();//Arguments 与 Fragment共存
            }
            mSimpleName = this.getClass().getSimpleName();
            b.putBundle(mSimpleName, savedState);
        }
    }

    // 保存数据到Bundle中
    private Bundle saveState() {
        Bundle state = new Bundle();
        state.putSerializable(mTitle,(ArrayList<DataBean>) mAdapter.getData());
        mKeyPage = mPresenter.getClass().getSimpleName() + mTitle;
        state.putInt(mKeyPage,mPresenter.getPage());
        return state;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        saveStateToArguments(null);
    }

}

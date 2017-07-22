package hn.cqf.com.gank.ui.main;


import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.transition.ChangeBounds;
import android.transition.ChangeImageTransform;
import android.transition.TransitionSet;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;

import java.util.Date;
import java.util.List;

import butterknife.BindView;
import hn.cqf.com.gank.R;
import hn.cqf.com.gank.adapter.HomeAdapter;
import hn.cqf.com.gank.base.BaseActivity;
import hn.cqf.com.gank.base.BaseRecyclerAdapter;
import hn.cqf.com.gank.bean.DataBean;
import hn.cqf.com.gank.ui.gank.GankActivity;
import hn.cqf.com.gank.ui.network.NetWorkActivity;
import hn.cqf.com.gank.ui.welfare.WelfareActivity;
import hn.cqf.com.gank.utils.BitmapUtils;
import hn.cqf.com.gank.utils.MyTimeUtils;
import hn.cqf.com.pickerview.TimePickerView;
import hn.cqf.com.pickerview.bean.TimeInfo;


public class MainActivity extends BaseActivity<MainPresenter>
        implements NavigationView.OnNavigationItemSelectedListener,
        View.OnClickListener, SwipeRefreshLayout.OnRefreshListener,
        TimePickerView.OnTimeSelectListener, MainContract.View,
        BaseRecyclerAdapter.OnItemClickListener {

    @BindView(R.id.scroll_lly)
    LinearLayout mScrollLly;
    @BindView(R.id.scroll_image)
    ImageView mScrollImage;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout mCollapsingToolbar;
    @BindView(R.id.app_bar)
    AppBarLayout mAppBar;
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @BindView(R.id.fab)
    FloatingActionButton mFab;
    @BindView(R.id.left_menu)
    NavigationView mLeftMenu;
    @BindView(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;


    RelativeLayout mLeftMenuHeadRly;
    ImageView mUserIcon;
    TextView mUserNameTv;

    private TimePickerView pvTime;
    private List<TimeInfo> mTimeInfos;
    private HomeAdapter mAdapter;


    @Override
    protected int setContentViewByResId() {
        return R.layout.activity_main;
    }

    @Override
    protected void setupWindowAnimations() {
        /*TestActivityTransice的例子*/
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


        /*======Material-Animations-CN-master的例子======*/
        // 侧滑动画
        // Slide transition = new Slide();
        // transition.setSlideEdge(Gravity.LEFT);
        // transition.setDuration(getResources().getInteger(R.integer.animation_activity_duration));
        // 爆炸效果的动画
        // Explode transition = new Explode();
        // transition.setDuration(getResources().getInteger(R.integer.anim_duration_long));
        // 渐变动画
        /*Fade transition = new Fade();
        transition.setDuration(getResources().getInteger(R.integer.animation_activity_duration));
        // 这两个方法在 TransitionActivity1 详解
        getWindow().setReenterTransition(transition);
        getWindow().setExitTransition(transition);*/
        /*======Material-Animations-CN-master的例子======*/
    }

    @Override
    protected boolean isSwipeBack() {
        return false;
    }

    @Override
    protected void initPresenter() {
        mPresenter = new MainPresenter();
        mPresenter.attachView(this);
    }

    @Override
    protected void initView() {
        mLeftMenu.setCheckedItem(R.id.nav_welfare);//设置默认选中

        mAdapter = new HomeAdapter();
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mAdapter);

        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);

        /*======初始化PickerView======*/
        pvTime = new TimePickerView(this, TimePickerView.Type.YEAR_MONTH_DAY);
        /*======侧滑栏的头部======*/
        mLeftMenuHeadRly = (RelativeLayout) mLeftMenu.getHeaderView(0);
        mUserNameTv = (TextView) mLeftMenuHeadRly.findViewById(R.id.username_tv);
        mUserIcon = (ImageView) mLeftMenuHeadRly.findViewById(R.id.user_icon);

        //控制时间范围
        //Calendar calendar = Calendar.getInstance();
        //pvTime.setRange(calendar.get(Calendar.YEAR) - 20,
        // calendar.get(Calendar.YEAR));//要在setTime 之前才有效果哦
    }

    @Override
    protected Toolbar getSpecialActionBar() {
        return mToolbar;
    }

    @Override
    protected void initEvent() {
        mLeftMenu.setNavigationItemSelectedListener(this);
        mFab.setOnClickListener(this);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        pvTime.setOnTimeSelectListener(this);
        mAdapter.setItemClickListener(this);
    }

    @Override
    protected void initData() {
        mSwipeRefreshLayout.setRefreshing(true);
        mPresenter.start();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fab:
                if (null == mTimeInfos) {
                    Snackbar.make(view, "数据还未获取到,请稍等", Snackbar.LENGTH_SHORT).
                            setAction("确定", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {

                                }
                            }).show();
                } else {
                    pvTime.show();
                }
                break;
        }
    }

    @Override
    public void onRefresh() {
        mPresenter.start();
    }

    @Override
    public void onTimeSelect(Date date) {
        mSwipeRefreshLayout.setRefreshing(true);
        mPresenter.onTimeSelect(MyTimeUtils.DateToTimeInfo(date));
    }

    @Override
    public void updateHeaderImage(String url) {
        Glide.with(this).load(url).asBitmap().placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher).into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(Bitmap resource,
                                        GlideAnimation<? super Bitmap> glideAnimation) {
                //获取图片有活力的颜色
                Palette.Builder builder = new Palette.Builder(resource);
                builder.generate(new Palette.PaletteAsyncListener() {
                    @Override
                    public void onGenerated(Palette palette) {
                        //获取样板
                       /* Vibrant(充满活力的)
                        * Vibrant dark(充满活力的黑)
                        * Vibrant light(充满活力的亮)
                        * Muted(柔和的)
                        * Muted dark(柔和的黑)
                        * Muted lighr(柔和的亮)*/
                        Palette.Swatch vibrant = palette.getVibrantSwatch();
                        if (vibrant != null) {
                            updateHeaderBackground(vibrant.getRgb());
                            return;
                        }
                        vibrant = palette.getLightVibrantSwatch();
                        if (vibrant != null) {
                            updateHeaderBackground(vibrant.getRgb());
                            return;
                        }
                        vibrant = palette.getLightMutedSwatch();
                        if (vibrant != null) {
                            updateHeaderBackground(vibrant.getRgb());
                        }
                    }
                });
                //设置图片
                int sampleSize = resource.getWidth() / mScrollLly.getWidth();
                Bitmap bitmap = BitmapUtils.compressBySampleSize(resource, sampleSize, false);
                mScrollImage.setImageBitmap(bitmap);
            }
        });
    }

    @Override
    public void updateHeaderBackground(int rgb) {
        //if (android.os.Build.VERSION.SDK_INT >= 21) {
        //Window window = getWindow();
        //设置状态栏的颜色
        //window.setStatusBarColor(rgb);
        //设置导航栏的颜色
        //window.setNavigationBarColor(rgb);
        //}
        mLeftMenuHeadRly.setBackgroundColor(rgb);
        mCollapsingToolbar.setBackgroundColor(rgb);

        //mScrollLly.setBackgroundColor(rgb);
        //mAppBar.setBackgroundColor(rgb);
        //mToolbar.setBackgroundDrawable(new ColorDrawable(rgb));
        //mCollapsingToolbar.setExpandedTitleColor(rgb);
        //mCollapsingToolbar.setCollapsedTitleTextColor(rgb);
    }

    @Override
    public void updateUserName(String name) {
        mUserNameTv.setText(name);
    }

    @Override
    public void updateUserIcon(String url) {
        Glide.with(this).load(url).asBitmap().centerCrop().placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher).into(mUserIcon);
    }

    @Override
    public void updateTitle(String title) {
        mCollapsingToolbar.setTitle(title);
    }

    @Override
    public void setTimeRange(List<TimeInfo> timeInfos) {
        mTimeInfos = timeInfos;
        TimeInfo info = timeInfos.get(0);
        pvTime.setRange(true, timeInfos.get(timeInfos.size() - 1), info);
        pvTime.setTime(new Date(MyTimeUtils.DateToMillis(
                info.getYear(), info.getMonth(), info.getDay())));
        pvTime.setCyclic(false);
        pvTime.setCancelable(true);
    }

    @Override
    public void setAdapterData(List<DataBean> dataBeen) {
        mSwipeRefreshLayout.setRefreshing(false);
        mAdapter.setDataAndRefresh(dataBeen);
    }

    @Override
    public void showError(String msg, Throwable e) {
        mSwipeRefreshLayout.setRefreshing(false);
        Toast.makeText(MainActivity.this, msg, Toast.LENGTH_LONG).show();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return true;
    }

    @Override
    protected boolean isMenuOpen() {
        return mDrawerLayout.isDrawerOpen(Gravity.LEFT);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                break;
            case R.id.setting:
                Toast.makeText(MainActivity.this, "setting", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
        return true;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_welfare:
                mPresenter.startActivity(null, this, WelfareActivity.class, null);
                break;
            case R.id.nav_gank:
                mPresenter.startActivity(null, this, GankActivity.class, null);
                break;
        }
        mDrawerLayout.closeDrawers();
        return true;
    }

    @Override
    public void onClick(View v, int position) {
        DataBean bean = mAdapter.getItem(position);
        mPresenter.startActivity(bean, this, NetWorkActivity.class, v);
        //        if(Build.VERSION.SDK_INT > 5) {
        //            overridePendingTransition(R.anim.open_enter_anim,R.anim.open_exit_anim);
        //        }
    }
}

package hn.cqf.com.gank.ui.gank;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import hn.cqf.com.gank.R;
import hn.cqf.com.gank.adapter.GankPagerAdapter;
import hn.cqf.com.gank.base.BaseActivity;
import hn.cqf.com.gank.bean.FragmentInfo;
import hn.cqf.com.gank.ui.fragment.GankFragment;

/**
 * @author cqf
 * @time 2017/2/9 0009  下午 5:51
 * @desc 干货定制
 */
public class GankActivity extends BaseActivity {
    @BindView(R.id.tab_layout)
    TabLayout mTabLayout;
    @BindView(R.id.view_pager)
    ViewPager mViewPager;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    private GankPagerAdapter mAdapter;

    @Override
    protected int setContentViewByResId() {
        return R.layout.activity_gank;
    }

    @Override
    protected void initView() {
        mAdapter = new GankPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
    }

    @Override
    protected Toolbar getSpecialActionBar() {
        mToolbar.setTitle("干货订阅");
        return mToolbar;
    }

    @Override
    protected void initEvent() {
        mViewPager.addOnPageChangeListener(
                new TabLayout.TabLayoutOnPageChangeListener(mTabLayout));
    }

    @Override
    protected void initData() {
        //Android | iOS | 休息视频 | 拓展资源 | 前端 | all
        String[] strings = getResources().getStringArray(R.array.gank_types);
        List<FragmentInfo> infos = new ArrayList<>();
        for (int i = 0; i < strings.length; i++) {
            FragmentInfo info = new FragmentInfo();
            info.setTitle(strings[i]);
            GankFragment fragment = GankFragment.newInstance(strings[i]);
            info.setFragment(fragment);
            infos.add(info);
        }
        mAdapter.setFragments(infos);
    }

}

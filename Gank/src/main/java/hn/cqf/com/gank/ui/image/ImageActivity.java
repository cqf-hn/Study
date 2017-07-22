package hn.cqf.com.gank.ui.image;


import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;

import java.util.ArrayList;

import butterknife.BindView;
import hn.cqf.com.gank.R;
import hn.cqf.com.gank.adapter.ImagePagerAdapter;
import hn.cqf.com.gank.base.BaseActivity;
import hn.cqf.com.gank.bean.DataBean;
import hn.cqf.com.gank.cons.Constant;
import hn.cqf.com.gank.ui.HackyViewPager;
import hn.cqf.com.gank.ui.MyTextView;

/**
 * @author cqf
 * @time 2017/2/5 0005  下午 4:12
 * @desc 图片详情:图片切换;手势操作图片
 */
public class ImageActivity extends BaseActivity implements ViewPager.OnPageChangeListener {
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.view_pager)
    HackyViewPager mViewPager;
    @BindView(R.id.my_text_view)
    MyTextView mMyTextView;
    private ImagePagerAdapter mAdapter;

    @Override
    protected int setContentViewByResId() {
        return R.layout.activity_image;
    }

    @Override
    protected void setupWindowAnimations() {
        getWindow().getEnterTransition().setDuration(getResources().getInteger(
                R.integer.animation_activity_duration));
    }

    @Override
    protected void initView() {
        int position = getIntent().getIntExtra(Constant.PASS_POSITION_TO_IMAGE, 0);
        ArrayList<DataBean> datas = (ArrayList<DataBean>) getIntent()
                .getSerializableExtra(Constant.PASS_DATABEANS_TO_IMAGE);

        mAdapter = new ImagePagerAdapter(datas);
        mAdapter.setData(datas);
        mViewPager.setAdapter(mAdapter);
        mViewPager.setCurrentItem(position, false);
        mMyTextView.setCount(datas.size());
        mMyTextView.setCurrentIndex(position + 1);
    }

    @Override
    protected Toolbar getSpecialActionBar() {
        return mToolbar;
    }

    @Override
    protected void initEvent() {
        mViewPager.addOnPageChangeListener(this);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        mMyTextView.setCurrentIndex(position + 1);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}

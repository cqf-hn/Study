package hn.cqf.com.gank.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

import hn.cqf.com.gank.bean.FragmentInfo;

/**
 * @author cqf
 * @time 2017/2/9 0009  下午 11:17
 * @desc 干货定制:ViewPager的Adapter
 */
public class GankPagerAdapter extends FragmentStatePagerAdapter {
    private List<FragmentInfo> mFragments;

    public GankPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position).getFragment();
    }

    @Override
    public int getCount() {
        return mFragments == null ? 0 : mFragments.size();
    }

    public void setFragments(List<FragmentInfo> fragments) {
        this.mFragments = fragments;
        notifyDataSetChanged();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mFragments.get(position).getTitle();
    }
}

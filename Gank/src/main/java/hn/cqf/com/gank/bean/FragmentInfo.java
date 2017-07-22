package hn.cqf.com.gank.bean;

import android.support.v4.app.Fragment;

/**
 * @author cqf
 * @time 2017/2/9 0009  下午 11:22
 * @desc ${TODD}
 */
public class FragmentInfo {
    private Fragment mFragment;
    private String mTitle;

    public Fragment getFragment() {
        return mFragment;
    }

    public void setFragment(Fragment fragment) {
        mFragment = fragment;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }
}

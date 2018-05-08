package hn.cqf.com.gank.adapter;

import android.view.ViewGroup;

import hn.cqf.com.gank.adapter.wrapper.GankListViewWrapper;
import hn.cqf.com.gank.base.BaseActivity;
import hn.cqf.com.gank.base.BaseRecyclerAdapter;
import hn.cqf.com.gank.base.BaseRecyclerViewWrapper;
import hn.cqf.com.gank.bean.DataBean;

/**
 * @author cqf
 * @time 2017/2/10 0010  上午 10:25
 * @desc 干货订阅的ViewPager的item(Fragment):RecyclerView的Adapter
 */
public class GankListAdapter extends BaseRecyclerAdapter<DataBean> {

    public GankListAdapter(BaseActivity activity) {
        super(activity);
    }

    @Override
    protected BaseRecyclerViewWrapper createViewWrapper(BaseActivity _activity, ViewGroup parent, int type) {
        return new GankListViewWrapper(_activity,parent);
    }
}

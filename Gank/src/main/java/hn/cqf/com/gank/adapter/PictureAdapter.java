package hn.cqf.com.gank.adapter;

import android.view.ViewGroup;

import hn.cqf.com.gank.adapter.wrapper.PictureViewWrapper;
import hn.cqf.com.gank.base.BaseActivity;
import hn.cqf.com.gank.base.BaseRecyclerAdapter;
import hn.cqf.com.gank.base.BaseRecyclerViewWrapper;
import hn.cqf.com.gank.bean.DataBean;

/**
 * @author cqf
 * @time 2017/1/29 0029  下午 9:14
 * @desc 福利:RecyclerView(瀑布流)的Adapter
 */
public class PictureAdapter extends BaseRecyclerAdapter<DataBean> {

    public PictureAdapter(BaseActivity activity) {
        super(activity);
    }

    @Override
    protected BaseRecyclerViewWrapper createViewWrapper(BaseActivity _activity, ViewGroup parent, int type) {
        return new PictureViewWrapper(_activity,parent);
    }
}

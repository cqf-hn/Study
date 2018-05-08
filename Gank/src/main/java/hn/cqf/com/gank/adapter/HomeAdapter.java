package hn.cqf.com.gank.adapter;

import android.text.TextUtils;
import android.view.ViewGroup;

import hn.cqf.com.gank.adapter.wrapper.HomeCategoryWrapper;
import hn.cqf.com.gank.adapter.wrapper.HomeWrapper;
import hn.cqf.com.gank.base.BaseActivity;
import hn.cqf.com.gank.base.BaseRecyclerAdapter;
import hn.cqf.com.gank.base.BaseRecyclerViewWrapper;
import hn.cqf.com.gank.bean.DataBean;

/**
 * @author cqf
 * @time 2017/1/8 0008  上午 10:42
 * @desc 主页RecyclerView的Adapter
 */
public class HomeAdapter extends BaseRecyclerAdapter<DataBean> {

    public static final int ITEM_LIST_TYPE = 1;
    public static final int ITEM_CATEGORY_TYPE = 2;

    public HomeAdapter(BaseActivity activity) {
        super(activity);
    }


    @Override
    protected BaseRecyclerViewWrapper createViewWrapper(BaseActivity _activity, ViewGroup parent, int type) {
        if(type == ITEM_LIST_TYPE) {
            return new HomeWrapper(_activity,parent);
        } else {
            return new HomeCategoryWrapper(_activity,parent);
        }
    }

    /**
     * 根据不同的数据返回不同的Type,决定Type获取不同的layout和ViewHolder
     *
     * @param position
     * @return
     */
    @Override
    public int getItemViewType(int position) {
        DataBean bean = getItem(position);
        if (bean.isUsed() || !TextUtils.isEmpty(bean.getDesc())) {
            return ITEM_LIST_TYPE;
        }
        return ITEM_CATEGORY_TYPE;
    }
}

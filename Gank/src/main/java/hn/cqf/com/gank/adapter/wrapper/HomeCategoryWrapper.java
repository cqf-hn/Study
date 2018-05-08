package hn.cqf.com.gank.adapter.wrapper;

import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.BindView;
import hn.cqf.com.gank.R;
import hn.cqf.com.gank.base.BaseActivity;
import hn.cqf.com.gank.base.BaseRecyclerViewWrapper;
import hn.cqf.com.gank.bean.DataBean;

/**
 * @author cqf
 * @time 2018/5/8 0008  上午 11:23
 * @desc ${TODD}
 */
public class HomeCategoryWrapper extends BaseRecyclerViewWrapper<DataBean> {

    @BindView(R.id.category)
    TextView mCategory;

    public HomeCategoryWrapper(BaseActivity activity) {
        super(activity);
    }

    public HomeCategoryWrapper(BaseActivity activity, ViewGroup parent) {
        super(activity, parent);
    }

    @Override
    protected int onItemLayoutId() {
        return R.layout.item_category_home;
    }

    @Override
    public void bindData(int position) {
        DataBean data = getItem(position);
        mCategory.setText(data.getType());
    }
}

package hn.cqf.com.gank.adapter;

import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.BindView;
import hn.cqf.com.gank.R;
import hn.cqf.com.gank.base.BaseRecyclerAdapter;
import hn.cqf.com.gank.base.BaseRecyclerViewHolder;
import hn.cqf.com.gank.bean.DataBean;
import hn.cqf.com.gank.utils.StringStyles;

/**
 * @author cqf
 * @time 2017/1/8 0008  上午 10:42
 * @desc 主页RecyclerView的Adapter
 */
public class HomeAdapter extends BaseRecyclerAdapter<DataBean> {

    public static final int ITEM_LIST_TYPE = 1;
    public static final int ITEM_CATEGORY_TYPE = 2;


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

    @Override
    protected int getItemLayoutResId(int type) {
        if (type == ITEM_LIST_TYPE) {
            return R.layout.item_list_home;
        }
        return R.layout.item_category_home;
    }

    @Override
    public void onBindViewHolder(BaseRecyclerViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        if (getItemViewType(position) == HomeAdapter.ITEM_LIST_TYPE) {
            showItemAnim(holder.itemView, position);
        }
    }

    @Override
    protected BaseRecyclerViewHolder createViewHolder(View itemView, int type) {
        if (type == ITEM_LIST_TYPE) {
            return new HomeListViewHolder(itemView);
        }
        return new HomeCategoryViewHolder(itemView);
    }

    @Override
    protected boolean isSetClickListener(int viewType) {
        if (viewType == ITEM_CATEGORY_TYPE){
            return false;
        } else {
            return true;
        }
    }

    public class HomeListViewHolder extends BaseRecyclerViewHolder {
        @BindView(R.id.title)
        TextView mTitle;
        @BindView(R.id.gank_layout)
        LinearLayout mGankLayout;

        public HomeListViewHolder(View itemView) {
            super(itemView);
        }

        //填充数据
        @Override
        public void bindData(int position) {
            DataBean data = getItem(position);
            if (data.isUsed()) {
                //能够存储String的样式(类似于StringBuilder)
                SpannableStringBuilder builder = new SpannableStringBuilder(data.getDesc())
                        .append(
                                //设置字体以及样式->返回SpannableString(类似于String)
                                StringStyles.format(mGankLayout.getContext(), " (via. " +
                                        data.getWho() +
                                        ")", R.style.ViaTextAppearance));
                CharSequence gankText = builder.subSequence(0, builder.length());
                mTitle.setText(gankText);
            } else {
                mGankLayout.setVisibility(View.GONE);
            }

        }
    }

    public class HomeCategoryViewHolder extends BaseRecyclerViewHolder {
        @BindView(R.id.category)
        TextView mCategory;

        public HomeCategoryViewHolder(View itemView) {
            super(itemView);
        }

        //填充数据
        @Override
        public void bindData(int position) {
            DataBean data = getItem(position);
            mCategory.setText(data.getType());
        }
    }
}

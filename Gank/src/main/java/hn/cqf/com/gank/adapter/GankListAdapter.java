package hn.cqf.com.gank.adapter;

import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import butterknife.BindView;
import hn.cqf.com.gank.R;
import hn.cqf.com.gank.base.BaseRecyclerAdapter;
import hn.cqf.com.gank.base.BaseRecyclerViewHolder;
import hn.cqf.com.gank.bean.DataBean;

/**
 * @author cqf
 * @time 2017/2/10 0010  上午 10:25
 * @desc 干货订阅的ViewPager的item(Fragment):RecyclerView的Adapter
 */
public class GankListAdapter extends BaseRecyclerAdapter<DataBean> {

    @Override
    protected int getItemLayoutResId(int viewType) {
        return R.layout.item_list_gank;
    }

    @Override
    protected BaseRecyclerViewHolder createViewHolder(View itemView, int type) {
        return new GankListViewHolder(itemView);
    }

    public void setData(List<DataBean> data) {
        if (mData != null) {
            mData.clear();
            mData.addAll(data);
        } else {
            this.mData = data;
        }
    }

    public void addData(List<DataBean> dataBeen) {
        mData.addAll(dataBeen);
    }

    public List<DataBean> getData() {
        return mData;
    }

    public class GankListViewHolder extends BaseRecyclerViewHolder {

        @BindView(R.id.item_gank_iv)
        ImageView mItemGankIv;
        @BindView(R.id.item_gank_type_tv)
        TextView mItemGankTypeTv;
        @BindView(R.id.item_gank_time_tv)
        TextView mItemGankTimeTv;
        @BindView(R.id.item_gank_desc_tv)
        TextView mItemGankDescTv;
        @BindView(R.id.card_view)
        CardView mCardView;
        @BindView(R.id.item_gank_who_tv)
        TextView mItemGankWhoTv;

        public GankListViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void bindData(int position) {
            DataBean dataBean = mData.get(position);
            if (dataBean.getImages() != null) {
                mItemGankIv.setVisibility(View.VISIBLE);
                Glide.with(mItemGankIv.getContext())
                        .load(dataBean.getImages().get(0))
                        .into(mItemGankIv);
            } else {
                mItemGankIv.setVisibility(View.GONE);
            }
            mItemGankTypeTv.setText(dataBean.getType());
            String time = dataBean.getPublishedAt();
            mItemGankTimeTv.setText(time.substring(0, time.indexOf("T")));
            mItemGankDescTv.setText(dataBean.getDesc());
            mItemGankWhoTv.setText(dataBean.getWho());
        }
    }
}

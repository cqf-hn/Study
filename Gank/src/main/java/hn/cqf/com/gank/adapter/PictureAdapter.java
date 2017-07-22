package hn.cqf.com.gank.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.view.View;
import android.widget.FrameLayout;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import hn.cqf.com.gank.R;
import hn.cqf.com.gank.base.BaseRecyclerAdapter;
import hn.cqf.com.gank.base.BaseRecyclerViewHolder;
import hn.cqf.com.gank.bean.DataBean;
import hn.cqf.com.gank.ui.ScaleImageView;

/**
 * @author cqf
 * @time 2017/1/29 0029  下午 9:14
 * @desc 福利:RecyclerView(瀑布流)的Adapter
 */
public class PictureAdapter extends BaseRecyclerAdapter<DataBean> {

    private int mWidthDp;
    private FrameLayout.LayoutParams mParams;

    @Override
    protected int getItemLayoutResId(int viewType) {
        return R.layout.item_picture_view;
    }

    @Override
    protected BaseRecyclerViewHolder createViewHolder(View itemView, int type) {

        return new PictureViewHolder(itemView);
    }

    public void setData(List<DataBean> data) {
        if (mData != null) {
            mData.clear();
            mData.addAll(data);
        } else {
            this.mData = data;
        }
    }

    public ArrayList<DataBean> getData() {
        return (ArrayList<DataBean>) mData;
    }

    public void addData(List<DataBean> dataBeen) {
        //int lastCount = getItemCount();
        mData.addAll(dataBeen);
        //notifyItemRangeInserted(lastCount,dataBeen.size());
    }

    @Override
    public void onBindViewHolder(BaseRecyclerViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
    }

    public class PictureViewHolder extends BaseRecyclerViewHolder {
        @BindView(R.id.picture_iv)
        ScaleImageView mPictureIv;
        @BindView(R.id.picture_cv)
        CardView mPictureCv;
        private Context mContext;

        public PictureViewHolder(View itemView) {
            super(itemView);
            mContext = itemView.getContext();
        }

        @Override
        public void bindData(int position) {
            DataBean data = getItem(position);
            if (data == null) {
                return;
            }
            String url = data.getUrl();
            if (TextUtils.isEmpty(url)) {
                return;
            }
            // 加载图片
            mPictureIv.setInitSize(data.getWidth(),data.getHeight());
            Glide.with(mContext)
                    .load(url)
                    .into(mPictureIv);
        }
    }


}

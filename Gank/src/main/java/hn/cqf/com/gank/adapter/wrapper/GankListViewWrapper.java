package hn.cqf.com.gank.adapter.wrapper;

import android.content.Intent;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v7.widget.CardView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import butterknife.BindView;
import hn.cqf.com.gank.R;
import hn.cqf.com.gank.base.BaseActivity;
import hn.cqf.com.gank.base.BaseRecyclerViewWrapper;
import hn.cqf.com.gank.bean.DataBean;
import hn.cqf.com.gank.cons.Constant;
import hn.cqf.com.gank.ui.network.NetWorkActivity;
import hn.cqf.com.gank.utils.TransitionHelper;

/**
 * @author cqf
 * @time 2018/5/8 0008  上午 11:29
 * @desc ${TODD}
 */
public class GankListViewWrapper extends BaseRecyclerViewWrapper<DataBean> {

    @BindView(R.id.item_gank_iv)
    ImageView mItemGankIv;
    @BindView(R.id.item_gank_type_tv)
    TextView mItemGankTypeTv;
    @BindView(R.id.item_gank_who_tv)
    TextView mItemGankWhoTv;
    @BindView(R.id.item_gank_time_tv)
    TextView mItemGankTimeTv;
    @BindView(R.id.item_gank_desc_tv)
    TextView mItemGankDescTv;
    @BindView(R.id.card_view)
    CardView mCardView;
    private DataBean mBean;

    public GankListViewWrapper(BaseActivity activity) {
        super(activity);
    }

    public GankListViewWrapper(BaseActivity activity, ViewGroup parent) {
        super(activity, parent);
    }

    @Override
    protected int onItemLayoutId() {
        return R.layout.item_list_gank;
    }

    @Override
    public void bindData(int position) {
        mBean = mData.get(position);
        if (mBean.getImages() != null) {
            mItemGankIv.setVisibility(View.VISIBLE);
            Glide.with(mItemGankIv.getContext())
                    .load(mBean.getImages().get(0))
                    .into(mItemGankIv);
        } else {
            mItemGankIv.setVisibility(View.GONE);
        }
        mItemGankTypeTv.setText(mBean.getType());
        String time = mBean.getPublishedAt();
        mItemGankTimeTv.setText(time.substring(0, time.indexOf("T")));
        mItemGankDescTv.setText(mBean.getDesc());
        mItemGankWhoTv.setText(mBean.getWho());
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(_activity, NetWorkActivity.class);
        if (mBean != null) {
            //待修改
            intent.putExtra(Constant.PASS_DATABEAN_TO_NETWORK, mBean);

        }
        if (v != null) {
            //待修改
            Pair<View, String>[] pairs = TransitionHelper.createSafeTransitionParticipants(_activity, false,
                    new Pair<>(v, _activity.getString(R.string.share_ele_title)));
            ActivityOptionsCompat transitionActivityOptions = ActivityOptionsCompat.makeSceneTransitionAnimation
                    (_activity, pairs);
            ActivityCompat.startActivity(_activity, intent, transitionActivityOptions.toBundle());
        }
    }
}

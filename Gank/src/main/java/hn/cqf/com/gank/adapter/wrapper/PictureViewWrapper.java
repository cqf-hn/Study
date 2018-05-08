package hn.cqf.com.gank.adapter.wrapper;

import android.content.Intent;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;

import java.io.Serializable;

import butterknife.BindView;
import hn.cqf.com.gank.R;
import hn.cqf.com.gank.base.BaseActivity;
import hn.cqf.com.gank.base.BaseRecyclerViewWrapper;
import hn.cqf.com.gank.bean.DataBean;
import hn.cqf.com.gank.cons.Constant;
import hn.cqf.com.gank.ui.ScaleImageView;
import hn.cqf.com.gank.ui.image.ImageActivity;
import hn.cqf.com.gank.utils.TransitionHelper;

/**
 * @author cqf
 * @time 2018/5/8 0008  上午 11:31
 * @desc ${TODD}
 */
public class PictureViewWrapper extends BaseRecyclerViewWrapper<DataBean> {

    @BindView(R.id.picture_iv)
    ScaleImageView mPictureIv;
    @BindView(R.id.picture_cv)
    CardView mPictureCv;
    private DataBean mData;

    public PictureViewWrapper(BaseActivity activity) {
        super(activity);
    }

    public PictureViewWrapper(BaseActivity activity, ViewGroup parent) {
        super(activity, parent);
    }

    @Override
    protected int onItemLayoutId() {
        return R.layout.item_picture_view;
    }

    @Override
    public void bindData(int position) {
        mData = getItem(position);
        if (mData == null) {
            return;
        }
        String url = mData.getUrl();
        if (TextUtils.isEmpty(url)) {
            return;
        }
        // 加载图片
        mPictureIv.setInitSize(mData.getWidth(), mData.getHeight());
        Glide.with(_activity)
                .load(url)
                .into(mPictureIv);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(_activity, ImageActivity.class);
        if (mData != null) {
            //待修改
            intent.putExtra(Constant.PASS_DATABEANS_TO_IMAGE, (Serializable) getData());
            intent.putExtra(Constant.PASS_POSITION_TO_IMAGE,position);
        }
        if (v != null) {
            //待修改
            Pair<View, String>[] pairs = TransitionHelper.createSafeTransitionParticipants(_activity, false,
                    new Pair<>(v, _activity.getString(R.string.share_ele_image)));
            ActivityOptionsCompat transitionActivityOptions = ActivityOptionsCompat.makeSceneTransitionAnimation
                    (_activity, pairs);
            ActivityCompat.startActivity(_activity, intent, transitionActivityOptions.toBundle());
        }
    }
}

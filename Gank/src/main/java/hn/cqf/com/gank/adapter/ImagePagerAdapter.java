package hn.cqf.com.gank.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import hn.cqf.com.gank.R;
import hn.cqf.com.gank.bean.DataBean;
import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * @author cqf
 * @time 2017/2/6 0006  下午 10:09
 * @desc 图片详情:ViewPager的Adapter
 */
public class ImagePagerAdapter extends PagerAdapter {

    private PhotoView mPictureIv;
    private ArrayList<DataBean> mData;

    public ImagePagerAdapter(ArrayList<DataBean> data) {
        mData = data;
    }

    public void setData(ArrayList<DataBean> data) {
        mData = data;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mData == null ? 0 : mData.size();
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Context context = container.getContext();
        DataBean dataBean = mData.get(position);
        View view = LayoutInflater.from(context)
                .inflate(R.layout.item_image_view, container, false);
        mPictureIv = (PhotoView)view.findViewById(R.id.picture_iv);
        ViewCompat.setTransitionName(mPictureIv, context.getString(R.string.share_ele_image));
        Glide.with(context).load(dataBean.getUrl()).into(mPictureIv);
        mPictureIv.setZoomable(true);
        mPictureIv.setOnViewTapListener(new PhotoViewAttacher.OnViewTapListener() {
            @Override
            public void onViewTap(View view, float x, float y) {

            }
        });

        container.addView(view, ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        return view;
    }

    //    protected void hideOrShowToolbar() {
//        mAppBar.animate()
//                .translationY(mIsHidden ? 0 : -mAppBar.getHeight())
//                .setInterpolator(new DecelerateInterpolator(2))
//                .start();
//        mIsHidden = !mIsHidden;
//    }
}

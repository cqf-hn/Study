package hn.cqf.com.gank.adapter.wrapper;

import android.content.Intent;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.text.SpannableStringBuilder;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.BindView;
import hn.cqf.com.gank.R;
import hn.cqf.com.gank.base.BaseActivity;
import hn.cqf.com.gank.base.BaseRecyclerViewWrapper;
import hn.cqf.com.gank.bean.DataBean;
import hn.cqf.com.gank.cons.Constant;
import hn.cqf.com.gank.ui.network.NetWorkActivity;
import hn.cqf.com.gank.utils.StringStyles;
import hn.cqf.com.gank.utils.TransitionHelper;

/**
 * @author cqf
 * @time 2018/5/8 0008  上午 11:23
 * @desc ${TODD}
 */
public class HomeWrapper extends BaseRecyclerViewWrapper<DataBean> {

    @BindView(R.id.title)
    TextView mTitle;
    @BindView(R.id.gank_layout)
    LinearLayout mGankLayout;
    private DataBean mData;

    public HomeWrapper(BaseActivity activity) {
        super(activity);
    }

    public HomeWrapper(BaseActivity activity, ViewGroup parent) {
        super(activity, parent);
    }

    @Override
    protected int onItemLayoutId() {
        return R.layout.item_list_home;
    }

    @Override
    public void bindData(int position) {
        mData = getItem(position);
        if (mData.isUsed()) {
            //能够存储String的样式(类似于StringBuilder)
            SpannableStringBuilder builder = new SpannableStringBuilder(mData.getDesc())
                    .append(
                            //设置字体以及样式->返回SpannableString(类似于String)
                            StringStyles.format(mGankLayout.getContext(), " (via. " +
                                    mData.getWho() +
                                    ")", R.style.ViaTextAppearance));
            CharSequence gankText = builder.subSequence(0, builder.length());
            mTitle.setText(gankText);
        } else {
            mGankLayout.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(_activity, NetWorkActivity.class);
        if (mData != null) {
            intent.putExtra(Constant.PASS_DATABEAN_TO_NETWORK, mData);
        }
        /*
         * grideView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
         *     @Override
         *     public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
         *         ActivityOptionsCompat  aop=ActivityOptionsCompat.makeSceneTransitionAnimation(MainActivity.this,
         *                 view.findViewById(R.id.imageView),"123");
         *         Intent   intent=new Intent(getApplicationContext(),SecondActivity.class);
         *        ActivityCompat.startActivity(MainActivity.this,intent,aop.toBundle());
         *     }
         * });
         * */
        if (v != null) {
            Pair<View, String>[] pairs = TransitionHelper.createSafeTransitionParticipants(_activity, false,
                    new Pair<>(v, _activity.getString(R.string.share_ele_title)));
            ActivityOptionsCompat transitionActivityOptions = ActivityOptionsCompat.makeSceneTransitionAnimation
                    (_activity, pairs);
            ActivityCompat.startActivity(_activity, intent, transitionActivityOptions.toBundle());
        } else {
            _activity.startActivity(intent);
            if (Build.VERSION.SDK_INT > 5) {
                _activity.overridePendingTransition(R.anim.open_enter_anim, R.anim.open_exit_anim);
            }
        }
    }
}

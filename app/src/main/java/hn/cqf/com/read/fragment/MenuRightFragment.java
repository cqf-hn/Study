package hn.cqf.com.read.fragment;

import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import hn.cqf.com.read.R;
import hn.cqf.com.read.base.MenuBaseFragment;
import hn.cqf.com.read.view.MyTextView;

/**
 * @author cqf
 * @time 2016/12/29 0029  下午 3:31
 * @desc ${TODD}
 */
public class MenuRightFragment extends MenuBaseFragment implements View.OnClickListener, View.OnTouchListener {

    ImageButton mTitleBarLeftIb;
    MyTextView mTitleBarTitleTv;
    ImageButton mTitleBarRightIb;
    MyTextView mTitleBarRightTv;
    MyTextView mTitleBarLineTv;
    RelativeLayout mTitleBarRl;
    ImageView mAvaterIv;
    MyTextView mNameTv;
    LinearLayout mUserLl;
    MyTextView mNotificationTv;
    MyTextView mFavoritesTv;
    MyTextView mDownloadTv;
    MyTextView mNoteTv;
    MyTextView mMsgCountTv;
    MyTextView mVersionTv;
    ImageView mDxSpace;

    @Override
    protected View specialView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.fragment_right_menu, container, false);
    }

    @Override
    protected void initView() {
        mTitleBarLeftIb = (ImageButton) mView.findViewById(R.id.title_bar_left_ib);
        mTitleBarTitleTv = (MyTextView) mView.findViewById(R.id.title_bar_title_tv);
        mTitleBarRightIb = (ImageButton) mView.findViewById(R.id.title_bar_right_ib);
        mTitleBarRightTv = (MyTextView) mView.findViewById(R.id.title_bar_right_tv);
        mTitleBarLineTv = (MyTextView) mView.findViewById(R.id.title_bar_line_tv);
        mTitleBarRl = (RelativeLayout) mView.findViewById(R.id.title_bar_rl);
        mAvaterIv = (ImageView) mView.findViewById(R.id.avater_iv);
        mNameTv = (MyTextView) mView.findViewById(R.id.name_tv);
        mUserLl = (LinearLayout) mView.findViewById(R.id.user_ll);
        mNotificationTv = (MyTextView) mView.findViewById(R.id.notification_tv);
        mFavoritesTv = (MyTextView) mView.findViewById(R.id.favorites_tv);
        mDownloadTv = (MyTextView) mView.findViewById(R.id.download_tv);
        mNoteTv = (MyTextView) mView.findViewById(R.id.note_tv);
        mMsgCountTv = (MyTextView) mView.findViewById(R.id.msg_count_tv);
        mVersionTv = (MyTextView) mView.findViewById(R.id.version_tv);
        mDxSpace = (ImageView) mView.findViewById(R.id.dxspace);

    }

    @Override
    protected void initEvent() {
        mView.setOnTouchListener(this);
        mNotificationTv.setOnClickListener(this);
    }

    @Override
    protected boolean isLeft() {
        return false;
    }


    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mDx = 0;
                break;
        }
        mGestureDetector.onTouchEvent(event);
        return true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.notification_tv:
            Toast.makeText(v.getContext(), "消息", Toast.LENGTH_SHORT).show();
            break;
        }
    }



}

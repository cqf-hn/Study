package hn.cqf.com.read.fragment;

import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import hn.cqf.com.read.R;
import hn.cqf.com.read.base.MenuBaseFragment;
import hn.cqf.com.read.view.MyTextView;

/**
 * @author cqf
 * @time 2016/12/29 0029  下午 3:30
 * @desc ${TODD}
 */
public class MenuLeftFragment extends MenuBaseFragment implements View.OnClickListener, View.OnTouchListener {

    ImageButton mTitleBarLeftIb;
    MyTextView mTitleBarTitleTv;
    ImageButton mTitleBarRightIb;
    MyTextView mTitleBarRightTv;
    MyTextView mTitleBarLineTv;
    RelativeLayout mTitleBarRl;
    ImageView mLogoIv;
    RelativeLayout mTitleLogoRl;
    MyTextView mHomePageTv;
    MyTextView mWordsTv;
    MyTextView mVoiceTv;
    MyTextView mVideoTv;
    MyTextView mDiscussTv;
    MyTextView mCalendarTv;
    LinearLayout mColumnLl;
    ProgressBar mPb;
    MyTextView mVoiceTitleTv;
    TextView mVoiceNameTv;
    ImageButton mMediaControllerPlayPause;
    RelativeLayout mVoiceControllerRl;




    @Override
    protected View specialView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.fragment_left_menu, container, false);
    }

    @Override
    protected void initView() {
        mTitleBarLeftIb = (ImageButton) mView.findViewById(R.id.title_bar_left_ib);
        mTitleBarTitleTv = (MyTextView) mView.findViewById(R.id.title_bar_title_tv);
        mTitleBarRightIb = (ImageButton) mView.findViewById(R.id.title_bar_right_ib);
        mTitleBarRightTv = (MyTextView) mView.findViewById(R.id.title_bar_right_tv);
        mTitleBarLineTv = (MyTextView) mView.findViewById(R.id.title_bar_line_tv);
        mTitleBarRl = (RelativeLayout) mView.findViewById(R.id.title_bar_rl);
        mLogoIv = (ImageView) mView.findViewById(R.id.logo_iv);
        mTitleLogoRl = (RelativeLayout) mView.findViewById(R.id.title_logo_rl);
        mHomePageTv = (MyTextView) mView.findViewById(R.id.home_page_tv);
        mWordsTv = (MyTextView) mView.findViewById(R.id.words_tv);
        mVoiceTv = (MyTextView) mView.findViewById(R.id.voice_tv);
        mVideoTv = (MyTextView) mView.findViewById(R.id.video_tv);
        mDiscussTv = (MyTextView) mView.findViewById(R.id.discuss_tv);
        mCalendarTv = (MyTextView) mView.findViewById(R.id.calendar_tv);
        mColumnLl = (LinearLayout) mView.findViewById(R.id.column_ll);
        mPb = (ProgressBar) mView.findViewById(R.id.pb);
        mVoiceTitleTv = (MyTextView) mView.findViewById(R.id.voice_title_tv);
        mVoiceNameTv = (TextView) mView.findViewById(R.id.voice_name_tv);
        mMediaControllerPlayPause = (ImageButton) mView.findViewById(R.id.mediacontroller_play_pause);
        mVoiceControllerRl = (RelativeLayout) mView.findViewById(R.id.voice_controller_rl);
    }

    @Override
    protected void initEvent() {
        mHomePageTv.setOnClickListener(this);
        mView.setOnTouchListener(this);
    }

    @Override
    protected boolean isLeft() {
        return true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.home_page_tv:
                Toast.makeText(v.getContext(), "首页", Toast.LENGTH_SHORT).show();
        }
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

}

package hn.cqf.com.pickerview;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

import hn.cqf.com.pickerview.bean.TimeInfo;
import hn.cqf.com.pickerview.view.BasePickerView;
import hn.cqf.com.pickerview.view.WheelTime;

/**
 * 时间选择器
 * Created by Sai on 15/11/22.
 */
public class TimePickerView extends BasePickerView implements View.OnClickListener {
    public enum Type {
        ALL, YEAR_MONTH_DAY, HOURS_MINS, MONTH_DAY_HOUR_MIN, YEAR_MONTH
    }// 四种选择模式，年月日时分，年月日，时分，月日时分

    WheelTime wheelTime;
    private View btnSubmit, btnCancel;
    private TextView tvTitle;
    private static final String TAG_SUBMIT = "submit";
    private static final String TAG_CANCEL = "cancel";
    private OnTimeSelectListener timeSelectListener;

    public TimePickerView(Context context, Type type) {
        super(context);

        LayoutInflater.from(context).inflate(R.layout.pickerview_time, contentContainer);
        // -----确定和取消按钮
        btnSubmit = findViewById(R.id.btnSubmit);
        btnSubmit.setTag(TAG_SUBMIT);
        btnCancel = findViewById(R.id.btnCancel);
        btnCancel.setTag(TAG_CANCEL);
        btnSubmit.setOnClickListener(this);
        btnCancel.setOnClickListener(this);
        //顶部标题
        tvTitle = (TextView) findViewById(R.id.tvTitle);
        // ----时间转轮
        final View timepickerview = findViewById(R.id.timepicker);
        wheelTime = new WheelTime(timepickerview, type);

        //默认选中当前时间
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int hours = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        wheelTime.setPicker(year, month, day, hours, minute);

    }

    /**
     * 设置可以选择的时间范围
     * 要在setTime之前调用才有效果
     *
     * @param startYear 开始年份
     * @param endYear   结束年份
     */
    public void setRange(int startYear, int endYear) {
        wheelTime.setStartYear(startYear);
        wheelTime.setEndYear(endYear);
    }

    /*======修改:改变可选择的时间范围(年/月/日)======*/

    /**
     * 设置可以选择的时间范围
     *
     * @param isChange  true:才能设定年月日
     *                  否则与setRange(int startYear, int endYear)没有区别
     * @param startTime 开始的年月日
     * @param endTime   结束的年月日
     */
    public void setRange(boolean isChange, TimeInfo startTime, TimeInfo endTime) {
        Log.v("shan", "startTime:" + startTime);
        Log.v("shan", "endTime:" + endTime);

        wheelTime.setStartYear(startTime.getYear());
        wheelTime.setStartMonth(startTime.getMonth());
        wheelTime.setStartDay(startTime.getDay());

        wheelTime.setEndYear(endTime.getYear());
        wheelTime.setEndMonth(endTime.getMonth());
        wheelTime.setEndDay(endTime.getDay());
        //true 才能设定年月日
        wheelTime.setChange(isChange);
        //wheelTime.setStartYear(startYear);
        //wheelTime.setEndYear(endYear);
    }
    /*======修改:改变可选择的时间范围(年/月/日)======*/


    /**
     * 设置选中时间
     *
     * @param date 时间
     */
    public void setTime(Date date) {
        Calendar calendar = Calendar.getInstance();
        if (date == null)
            calendar.setTimeInMillis(System.currentTimeMillis());
        else
            calendar.setTime(date);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int hours = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        Log.v("shan", "year:" + year + "month:" + month + "day:" + day);
        wheelTime.setPicker(year, month, day, hours, minute);
    }

    //    /**
    //     * 指定选中的时间，显示选择器
    //     *
    //     * @param date
    //     */
    //    public void show(Date date) {
    //        Calendar calendar = Calendar.getInstance();
    //        if (date == null)
    //            calendar.setTimeInMillis(System.currentTimeMillis());
    //        else
    //            calendar.setTime(date);
    //        int year = calendar.get(Calendar.YEAR);
    //        int month = calendar.get(Calendar.MONTH);
    //        int day = calendar.get(Calendar.DAY_OF_MONTH);
    //        int hours = calendar.get(Calendar.HOUR_OF_DAY);
    //        int minute = calendar.get(Calendar.MINUTE);
    //        wheelTime.setPicker(year, month, day, hours, minute);
    //        show();
    //    }

    /**
     * 设置是否循环滚动
     *
     * @param cyclic 是否循环
     */
    public void setCyclic(boolean cyclic) {
        wheelTime.setCyclic(cyclic);
    }

    @Override
    public void onClick(View v) {
        String tag = (String) v.getTag();
        if (tag.equals(TAG_CANCEL)) {
            dismiss();
            return;
        } else {
            if (timeSelectListener != null) {
                try {
                    Date date = WheelTime.dateFormat.parse(wheelTime.getTime());
                    timeSelectListener.onTimeSelect(date);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
            dismiss();
            return;
        }
    }

    public interface OnTimeSelectListener {
        void onTimeSelect(Date date);
    }

    public void setOnTimeSelectListener(OnTimeSelectListener timeSelectListener) {
        this.timeSelectListener = timeSelectListener;
    }

    public void setTitle(String title) {
        tvTitle.setText(title);
    }
}

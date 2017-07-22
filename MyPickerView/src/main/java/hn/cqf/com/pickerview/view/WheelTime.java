package hn.cqf.com.pickerview.view;

import android.content.Context;
import android.view.View;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;

import hn.cqf.com.pickerview.R;
import hn.cqf.com.pickerview.TimePickerView;
import hn.cqf.com.pickerview.adapter.NumericWheelAdapter;
import hn.cqf.com.pickerview.lib.WheelView;
import hn.cqf.com.pickerview.listener.OnItemSelectedListener;


public class WheelTime {
    public static DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    private View view;
    private WheelView wv_year;
    private WheelView wv_month;
    private WheelView wv_day;
    private WheelView wv_hours;
    private WheelView wv_mins;

    private TimePickerView.Type type;
    public static final int DEFULT_START_YEAR = 1990;
    public static final int DEFULT_END_YEAR = 2100;
    private int startYear = DEFULT_START_YEAR;
    private int endYear = DEFULT_END_YEAR;

    /*======修改======*/
    private int startMonth;
    private int startDay;
    private int endMonth;
    private int endDay;
    //只有true时候才能设定年月日的范围(以下翻译均来自有道...)
    private boolean isChange;//Only when true to set the range of (date) (month) (year)
    private int mCurrentYear;
    private int mCurrentMonth;
    private int mMinMonthItem = 1;//可变:用以当年份改变的时候,限制月的范围
    private int mMaxMonthItem = 12;//Variable:For when a change in that year,
                                            // limit the month
    private int mMinDayItem = 1;//可变:用以当年份或者月份改变的时候,限制月的范围
    private int mMaxDayItem = 30;//Variable:  for in those days a month or change of time,
                                            // limit the day

    public void setMinMonthItem(int minMonthItem) {
        mMinMonthItem = minMonthItem;
    }

    public void setMaxMonthItem(int maxMonthItem) {
        mMaxMonthItem = maxMonthItem;
    }

    public void setMaxDayItem(int maxDayItem) {
        mMaxDayItem = maxDayItem;
    }

    public void setMinDayItem(int minDayItem) {
        mMinDayItem = minDayItem;
    }
    /*======修改======*/


    public WheelTime(View view) {
        super();
        this.view = view;
        type = TimePickerView.Type.ALL;
        setView(view);
    }

    public WheelTime(View view, TimePickerView.Type type) {
        super();
        this.view = view;
        this.type = type;
        setView(view);
    }

    public void setPicker(int year, int month, int day) {
        this.setPicker(year, month, day, 0, 0);
    }

    public void setPicker(int year, int month, int day, int h, int m) {
        // 添加大小月月份并将其转换为list,方便之后的判断
        String[] months_big = {"1", "3", "5", "7", "8", "10", "12"};
        String[] months_little = {"4", "6", "9", "11"};

        final List<String> list_big = Arrays.asList(months_big);
        final List<String> list_little = Arrays.asList(months_little);

        Context context = view.getContext();
        // 年
        wv_year = (WheelView) view.findViewById(R.id.year);
        wv_year.setAdapter(new NumericWheelAdapter(startYear, endYear));// 设置"年"的显示数据
        wv_year.setLabel(context.getString(R.string.pickerview_year));// 添加文字
        wv_year.setCurrentItem(year - startYear);// 初始化时显示的数据
        mCurrentYear = year;

        // 月
        wv_month = (WheelView) view.findViewById(R.id.month);
        setMonthRange(year);
        wv_month.setLabel(context.getString(R.string.pickerview_month));
        if (isChange && year == startYear) {
            wv_month.setCurrentItem(month - startMonth + 1);
        } else {
            wv_month.setCurrentItem(month);
        }
        mCurrentMonth = month + 1;

        // 日
        wv_day = (WheelView) view.findViewById(R.id.day);
        // 判断大小月及是否闰年,用来确定"日"的数据
        if (list_big.contains(String.valueOf(month + 1))) {
            setDayRange(year, month + 1, 31);
        } else if (list_little.contains(String.valueOf(month + 1))) {
            setDayRange(year, month + 1, 30);
        } else {
            // 闰年
            if ((year % 4 == 0 && year % 100 != 0) || year % 400 == 0) {
                setDayRange(year, month + 1, 29);
            } else {
                setDayRange(year, month + 1, 28);
            }
        }
        wv_day.setLabel(context.getString(R.string.pickerview_day));
        if (isChange && year == startYear && month == startMonth) {
            wv_day.setCurrentItem(day - startDay);
        } else {
            wv_day.setCurrentItem(day - 1);
        }


        wv_hours = (WheelView) view.findViewById(R.id.hour);
        wv_hours.setAdapter(new NumericWheelAdapter(0, 23));
        wv_hours.setLabel(context.getString(R.string.pickerview_hours));// 添加文字
        wv_hours.setCurrentItem(h);

        wv_mins = (WheelView) view.findViewById(R.id.min);
        wv_mins.setAdapter(new NumericWheelAdapter(0, 59));
        wv_mins.setLabel(context.getString(R.string.pickerview_minutes));// 添加文字
        wv_mins.setCurrentItem(m);

        // 添加"年"监听
        OnItemSelectedListener wheelListener_year = new OnItemSelectedListener() {
            @Override
            public void onItemSelected(int index) {
                mCurrentYear = index + startYear;


                //setting month range
                setMonthRange(mCurrentYear);
                //让month的数据不要越界
                if (wv_month.getCurrentItem() >= mMaxMonthItem - mMinMonthItem) {
                    wv_month.setCurrentItem(mMaxMonthItem - mMinMonthItem);
                }
                //此处(mMinMonthItem - mMinMonthItem)写法只为好理解,用zero的效果一致
                if (wv_month.getCurrentItem() <= mMinMonthItem - mMinMonthItem) {
                    wv_month.setCurrentItem(mMinMonthItem - mMinMonthItem);
                }

                // 判断大小月及是否闰年,用来确定"日"的数据
                if (list_big.contains(String.valueOf(mCurrentMonth))) {
                    setDayRange(mCurrentYear, mCurrentMonth, 31);
                } else if (list_little.contains(String.valueOf(mCurrentMonth))) {
                    setDayRange(mCurrentYear, mCurrentMonth, 30);
                } else {
                    if ((mCurrentYear % 4 == 0 && mCurrentYear % 100 != 0)
                            || mCurrentYear % 400 == 0) {
                        setDayRange(mCurrentYear, mCurrentMonth, 29);
                    } else {
                        setDayRange(mCurrentYear, mCurrentMonth, 28);
                    }
                }


                //让day的数据不要越界
                if (wv_day.getCurrentItem() >= mMaxDayItem - mMinDayItem) {
                    wv_day.setCurrentItem(mMaxDayItem - mMinDayItem);
                }
                if (wv_day.getCurrentItem() <= mMinDayItem - mMinDayItem) {
                    wv_day.setCurrentItem(mMinDayItem - mMinDayItem);
                }

                //Log.v("shan", "year" + wv_month.getCurrentItem() + "---" + wv_day.getCurrentItem());
            }
        };
        // 添加"月"监听
        OnItemSelectedListener wheelListener_month = new OnItemSelectedListener() {
            @Override
            public void onItemSelected(int index) {
                if (isChange && mCurrentYear == startYear) {
                    mCurrentMonth = startMonth + index;
                } else {
                    mCurrentMonth = index + 1;
                }
                // 判断大小月及是否闰年,用来确定"日"的数据
                if (list_big.contains(String.valueOf(mCurrentMonth))) {
                    setDayRange(mCurrentYear, mCurrentMonth, 31);
                } else if (list_little.contains(String.valueOf(mCurrentMonth))) {
                    setDayRange(mCurrentYear, mCurrentMonth, 30);
                } else {
                    if (((mCurrentYear) % 4 == 0 && (mCurrentYear) % 100 != 0)
                            || (mCurrentYear) % 400 == 0) {
                        setDayRange(mCurrentYear, mCurrentMonth, 29);
                    } else {
                        setDayRange(mCurrentYear, mCurrentMonth, 28);
                    }
                }
                //设定day的数据不越界
                if (wv_day.getCurrentItem() >= mMaxDayItem - mMinDayItem) {
                    wv_day.setCurrentItem(mMaxDayItem - mMinDayItem);
                }
                if (wv_day.getCurrentItem() <= mMinDayItem - mMinDayItem) {
                    wv_day.setCurrentItem(mMinDayItem - mMinDayItem);
                }


            }
        };
        wv_year.setOnItemSelectedListener(wheelListener_year);
        wv_month.setOnItemSelectedListener(wheelListener_month);

        // 根据屏幕密度来指定选择器字体的大小(不同屏幕可能不同)
        int textSize = 6;
        switch (type) {
            case ALL:
                textSize = textSize * 3;
                break;
            case YEAR_MONTH_DAY:
                textSize = textSize * 4;
                wv_hours.setVisibility(View.GONE);
                wv_mins.setVisibility(View.GONE);
                break;
            case HOURS_MINS:
                textSize = textSize * 4;
                wv_year.setVisibility(View.GONE);
                wv_month.setVisibility(View.GONE);
                wv_day.setVisibility(View.GONE);
                break;
            case MONTH_DAY_HOUR_MIN:
                textSize = textSize * 3;
                wv_year.setVisibility(View.GONE);
                break;
            case YEAR_MONTH:
                textSize = textSize * 4;
                wv_day.setVisibility(View.GONE);
                wv_hours.setVisibility(View.GONE);
                wv_mins.setVisibility(View.GONE);
        }
        wv_day.setTextSize(textSize);
        wv_month.setTextSize(textSize);
        wv_year.setTextSize(textSize);
        wv_hours.setTextSize(textSize);
        wv_mins.setTextSize(textSize);

    }

    /**
     * 设定天的范围
     *
     * @param year
     * @param month
     * @param maxDay
     */
    private void setDayRange(int year, int month, int maxDay) {
        if (isChange && startYear == year && startMonth == month) {
            wv_day.setAdapter(new NumericWheelAdapter(startDay, maxDay));
            setMinDayItem(startDay);
            setMaxDayItem(maxDay);
        } else if (isChange && endYear == year && endMonth == month) {
            wv_day.setAdapter(new NumericWheelAdapter(1, endDay));
            setMinDayItem(1);
            setMaxDayItem(endDay);
        } else {
            wv_day.setAdapter(new NumericWheelAdapter(1, maxDay));
            setMinDayItem(1);
            setMaxDayItem(maxDay);
        }
    }

    /**
     * 设定月的范围
     *
     * @param year
     */
    private void setMonthRange(int year) {
        if (isChange && startYear == year) {
            wv_month.setAdapter(new NumericWheelAdapter(startMonth, 12));
            setMinMonthItem(startMonth);
            setMaxMonthItem(12);
        } else if (isChange && endYear == year) {
            wv_month.setAdapter(new NumericWheelAdapter(1, endMonth));
            setMinMonthItem(1);
            setMaxMonthItem(endMonth);
        } else {
            wv_month.setAdapter(new NumericWheelAdapter(1, 12));
            setMinMonthItem(1);
            setMaxMonthItem(12);
        }
    }

    /**
     * 设置是否循环滚动
     *
     * @param cyclic
     */
    public void setCyclic(boolean cyclic) {
        wv_year.setCyclic(cyclic);
        wv_month.setCyclic(cyclic);
        wv_day.setCyclic(cyclic);
        wv_hours.setCyclic(cyclic);
        wv_mins.setCyclic(cyclic);
    }

    public String getTime() {
        StringBuffer sb = new StringBuffer();
        sb.append((wv_year.getCurrentItem() + startYear)).append("-")
                .append((wv_month.getCurrentItem() + mMinMonthItem)).append("-")
                .append((wv_day.getCurrentItem() + mMinDayItem)).append(" ")
                .append(wv_hours.getCurrentItem()).append(":")
                .append(wv_mins.getCurrentItem());
        return sb.toString();
    }

    public View getView() {
        return view;
    }

    public void setView(View view) {
        this.view = view;
    }

    public int getStartYear() {
        return startYear;
    }

    public void setStartYear(int startYear) {
        this.startYear = startYear;
    }

    public int getEndYear() {
        return endYear;
    }

    public void setEndYear(int endYear) {
        this.endYear = endYear;
    }

    public int getStartMonth() {
        return startMonth;
    }

    public void setStartMonth(int startMonth) {
        this.startMonth = startMonth;
    }

    public int getStartDay() {
        return startDay;
    }

    public void setStartDay(int startDay) {
        this.startDay = startDay;
    }

    public int getEndMonth() {
        return endMonth;
    }

    public void setEndMonth(int endMonth) {
        this.endMonth = endMonth;
    }

    public int getEndDay() {
        return endDay;
    }

    public void setEndDay(int endDay) {
        this.endDay = endDay;
    }

    public boolean isChange() {
        return isChange;
    }

    public void setChange(boolean change) {
        isChange = change;
    }
}

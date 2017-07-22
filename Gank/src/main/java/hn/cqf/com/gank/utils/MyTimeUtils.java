package hn.cqf.com.gank.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import hn.cqf.com.pickerview.bean.TimeInfo;

/**
 * @author cqf
 * @time 2017/1/20 0020  下午 10:45
 * @desc ${TODD}
 */
public class MyTimeUtils {
    public static long DateToMillis(int year, int month, int day) {
        StringBuilder builder = new StringBuilder();
        builder.append(year);
        if (month < 10) {
            builder.append("0");
        }
        builder.append(month);
        if (day < 10) {
            builder.append("0");
        }
        builder.append(day);
        String str = builder.toString();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        try {
            return sdf.parse(str).getTime();//毫秒
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static TimeInfo DateToTimeInfo(Date date) {
        String time = date == null ? " " : new SimpleDateFormat("yyyy/MM/dd").format(date);
        String[] split = time.split("/");
        TimeInfo info = null;
        if (split.length != 0) {
            info = new TimeInfo();
            info.setYear(Integer.parseInt(split[0]));
            info.setMonth(Integer.parseInt(split[1]));
            info.setDay(Integer.parseInt(split[2]));
        }
        return info;
    }


}

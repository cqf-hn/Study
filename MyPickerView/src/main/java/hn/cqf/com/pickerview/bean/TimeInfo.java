package hn.cqf.com.pickerview.bean;

/**
 * @author cqf
 * @time 2017/1/13 0013  上午 9:40
 * @desc 保存时间
 */
public class TimeInfo {
    public TimeInfo() {
    }

    public TimeInfo(int year, int month, int day) {
        this.year = year;
        this.month = month;
        this.day = day;
    }

    private int year;
    private int month;
    private int day;

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    @Override
    public String toString() {
        return "TimeInfo{" +
                "year=" + year +
                ", month=" + month +
                ", day=" + day +
                '}';
    }
}

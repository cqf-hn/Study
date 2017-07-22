package hn.cqf.com.gank.bean;

import java.util.List;

/**
 * @author cqf
 * @time 2017/1/11 0011  下午 10:32
 * @desc ${TODD}
 */
public class DayDataInfo {

    /**
     * _id : 56cc6d23421aa95caa707bba
     * createdAt : 2015-08-06T02:05:32.826Z
     * desc : 一个查看设备规格的库，并且可以计算哪一年被定为“高端”机
     * publishedAt : 2015-08-06T04:16:55.575Z
     * type : Android
     * url : https://github.com/facebook/device-year-class
     * used : true
     * who : 有时放纵
     */

    private List<DataBean> Android;
    /**
     * _id : 56cc6d1d421aa95caa70777e
     * createdAt : 2015-08-06T01:55:36.30Z
     * desc : iOS 核心动画高级技巧
     * publishedAt : 2015-08-06T04:16:55.592Z
     * type : iOS
     * url : http://zsisme.gitbooks.io/ios-/content/
     * used : true
     * who : Andrew Liu
     */

    private List<DataBean> iOS;
    /**
     * _id : 56cc6d23421aa95caa707c2b
     * createdAt : 2015-08-06T03:55:07.175Z
     * desc : 重温字幕版倒鸭子~~~
     * publishedAt : 2015-08-06T04:16:55.578Z
     * type : 休息视频
     * url : http://video.weibo.com/show?fid=1034:0c79a69b1bafe17df62e750391d92118
     * used : true
     * who : 代码家
     */

    private List<DataBean> 休息视频;
    /**
     * _id : 56cc6d1d421aa95caa707781
     * createdAt : 2015-08-06T00:53:43.851Z
     * desc : node express 源码接卸
     * publishedAt : 2015-08-06T04:16:55.601Z
     * type : 拓展资源
     * url : https://gist.github.com/dlutwuwei/3faf88d535ac81c4e263
     * used : true
     * who : YJX
     */

    private List<DataBean> 拓展资源;
    /**
     * _id : 56cc6d23421aa95caa707c6f
     * createdAt : 2015-08-06T01:33:55.463Z
     * desc : 8.6
     * publishedAt : 2015-08-06T04:16:55.601Z
     * type : 福利
     * url : http://ww4.sinaimg.cn/large/7a8aed7bgw1eusn3niy2bj20hs0qo0wb.jpg
     * used : true
     * who : 张涵宇
     */

    private List<DataBean> 福利;
    private List<DataBean> App;

    private List<DataBean> 瞎推荐;


    public List<DataBean> getAndroid() {
        return Android;
    }

    public void setAndroid(List<DataBean> Android) {
        this.Android = Android;
    }

    public List<DataBean> getIOS() {
        return iOS;
    }

    public void setIOS(List<DataBean> iOS) {
        this.iOS = iOS;
    }

    public List<DataBean> get休息视频() {
        return 休息视频;
    }

    public void set休息视频(List<DataBean> 休息视频) {
        this.休息视频 = 休息视频;
    }

    public List<DataBean> get拓展资源() {
        return 拓展资源;
    }

    public void set拓展资源(List<DataBean> 拓展资源) {
        this.拓展资源 = 拓展资源;
    }

    public List<DataBean> get福利() {
        return 福利;
    }

    public void set福利(List<DataBean> 福利) {
        this.福利 = 福利;
    }

    public List<DataBean> getApp() {
        return App;
    }

    public void setApp(List<DataBean> app) {
        App = app;
    }

    public List<DataBean> get瞎推荐() {
        return 瞎推荐;
    }

    public void set瞎推荐(List<DataBean> 瞎推荐) {
        this.瞎推荐 = 瞎推荐;
    }

    @Override
    public String toString() {
        return "DayDataInfo{" +
                "Android=" + Android +
                ", iOS=" + iOS +
                ", 休息视频=" + 休息视频 +
                ", 拓展资源=" + 拓展资源 +
                ", 福利=" + 福利 +
                ", App=" + App +
                ", 瞎推荐=" + 瞎推荐 +
                '}';
    }
}

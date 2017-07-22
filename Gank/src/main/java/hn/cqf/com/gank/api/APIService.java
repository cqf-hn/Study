package hn.cqf.com.gank.api;

import java.util.List;

import hn.cqf.com.gank.bean.DataBean;
import hn.cqf.com.gank.bean.DayDataInfo;
import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Path;


/**
 * @desc 创建业务请求接口
 * Retrofit的用法参考-->Retrofit2 完全解析 探索与OkHttp之间的关系
 * http://blog.csdn.net/lmj623565791/article/details/51304204
 */
public interface APIService {

    //http://gank.io/api/day/history 获取时间段
    @GET("day/history")
    Flowable<List<String>> getHistory();

    //http://gank.io/api/day/2015/08/06 获取2015.08.06的数据
    @GET("day/{year}/{month}/{day}")
    Flowable<DayDataInfo> getDayData(
            @Path("year") int year,
            @Path("month") int month,
            @Path("day") int day);

    //http://gank.io/api/data/福利/10/1
    //数据类型： 福利 | Android | iOS | 休息视频 | 拓展资源 | 前端 | all
    @GET("data/{type}/{count}/{page}")
    Flowable<List<DataBean>> getTypeData(
            @Path("type") String type,
            @Path("count") int count,
            @Path("page") int page
    );

}

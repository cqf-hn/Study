package hn.cqf.com.gank.api;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import hn.cqf.com.gank.convert.ResponseConvertFactory;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;


/**
 * @author cqf
 * @time 2017/1/10 下午 8:56
 * @desc ${TODD}
 */
public class APIManager {
    private Retrofit mRetrofitGank;
    private static APIManager mManager = new APIManager();


    public static APIManager getInstance() {
        return mManager;
    }

    private APIManager() {
        // 实例化Retrofit
        //"error": false,
        //"results": []
        OkHttpClient okHttpClient = new OkHttpClient();
        mRetrofitGank = new Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl("http://gank.io/api/")
                //将Json结果并解析成DAO
                .addConverterFactory(ResponseConvertFactory.create())
                //.addConverterFactory(GsonConverterFactory.create())
                //Service接口默认返回Call<T> 要想返回Observable<T>,需得加上这一句
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    public static APIService getApiService() {
        return getInstance().mRetrofitGank.create(APIService.class);
    }

}

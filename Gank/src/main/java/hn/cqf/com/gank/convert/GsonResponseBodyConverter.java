package hn.cqf.com.gank.convert;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;

import okhttp3.ResponseBody;
import retrofit2.Converter;

public class GsonResponseBodyConverter<T> implements Converter<ResponseBody, T> {
    private final Gson gson;
    private final TypeAdapter<T> adapter;

    GsonResponseBodyConverter(Gson gson, TypeAdapter<T> adapter) {
        this.gson = gson;
        this.adapter = adapter;
    }

    /**
     * 获取我们想要的数据转换成Reader,之后与源码结果一致
     */
    @Override
    public T convert(ResponseBody value) throws IOException {
        try {
            //GankHttpResult result = JSON.parseObject(value.string(), GankHttpResult.class);
            //GankHttpResult result = gson.fromJson(value.string(), GankHttpResult.class);
            //Log.v("shan",""+result.getResults());

            JSONObject object = new JSONObject(value.string());

            ByteArrayInputStream inputStream ;
            if (object.toString().contains("results\":[")) {//原谅我,fastJson和gson都试过了,都不行,只能写死了
                JSONArray results = object.getJSONArray("results");
                inputStream = new ByteArrayInputStream(results.toString().getBytes());
            } else if (object.toString().contains("results\":{")) {

                JSONObject results = object.getJSONObject("results");
                //将字符串转换成输入流
                inputStream = new ByteArrayInputStream(results.toString().getBytes());
            } else {
                //随便抛个异常的,请自动忽略
                throw new JSONException("Port Exception");
            }
            //字节流转换成字符流
            Reader reader = new InputStreamReader(inputStream);

            //这个也不清楚是做什么的,只是仿照源代码,想要什么直接给什么
            JsonReader jsonReader = gson.newJsonReader(reader);
            //返回APIService接口中每个请求所定义的返回结果
            return adapter.read(jsonReader);
        } catch (JSONException e) {
            e.printStackTrace();
        } finally {
            value.close();
        }
        return null;
    }
}
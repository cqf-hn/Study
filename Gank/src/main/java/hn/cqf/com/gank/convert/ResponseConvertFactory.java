package hn.cqf.com.gank.convert;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;


/**
 * @author cqf
 * @time 2017/1/10 0010  下午 11:41
 * @desc ${TODD}
 */
public class ResponseConvertFactory extends Converter.Factory {
    public static ResponseConvertFactory create() {
        return create(new Gson());
    }

    public static ResponseConvertFactory create(Gson gson) {
        return new ResponseConvertFactory(gson);
    }
    private final Gson gson;

    private ResponseConvertFactory(Gson gson) {
        if (gson == null) throw new NullPointerException("gson == null");
        this.gson = gson;
    }
    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations,
                                                            Retrofit retrofit) {
        TypeAdapter<?> adapter = gson.getAdapter(TypeToken.get(type));
        //GsonResponseBodyConverter // 处理ResponseBody
        return new GsonResponseBodyConverter<>(gson, adapter);
    }
    @Override
    public Converter<?, RequestBody> requestBodyConverter(Type type,
                                                          Annotation[] parameterAnnotations, Annotation[] methodAnnotations, Retrofit retrofit) {
        TypeAdapter<?> adapter = gson.getAdapter(TypeToken.get(type));
        //GsonResponseBodyConverter // 处理ResponseBody
        return new GsonRequestBodyConverter<>(gson, adapter);
    }

}

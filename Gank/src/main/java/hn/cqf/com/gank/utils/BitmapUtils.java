package hn.cqf.com.gank.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

/**
 * @author cqf
 * @time 2017/1/24 0024  上午 11:35
 * @desc ${TODD}
 */
public class BitmapUtils {

    public static Bitmap getScaledBitmap(String path, int targetWidth, int targetHeight) {
        //边界压缩后再加载
        BitmapFactory.Options options = new BitmapFactory.Options();
        //加载图片时不会讲整张图片加载到内存,只会读取图片的尺寸信息而已,读取的信息被放到Options中
        options.inJustDecodeBounds = true;
        Bitmap bitmap = BitmapFactory.decodeFile(path, options);

        //拿到尺寸信息之后,计算边界压缩的比例
        int outHeight = options.outHeight;
        int outWidth = options.outWidth;
        //用原图的尺寸除以目标尺寸,得到比例       宽高比例取里面最大的比例作为压缩比例
        int heightScale = outHeight / targetHeight;
        int widthScale = outWidth / targetWidth;
        int maxScale = Math.max(heightScale, widthScale);

        //        registerReceiver()
        //                unregisterReceiver();
        //边界压缩后,还需要将图片加载到内存里,生成bitmap
        options.inJustDecodeBounds = false;
        //修改它的色彩配置
        options.inPreferredConfig = Bitmap.Config.ARGB_4444;
        //        options.inPreferredConfig = Bitmap.Config.RGB_565;
        bitmap = BitmapFactory.decodeFile(path, options);
        
        return bitmap;
    }

    /**
     * 计算采样大小
     *
     * @param options   选项
     * @param maxWidth  最大宽度
     * @param maxHeight 最大高度
     * @return 采样大小
     */
    private static int calculateInSampleSize(BitmapFactory.Options options, int maxWidth, int maxHeight) {
        if (maxWidth == 0 || maxHeight == 0) return 1;
        int height = options.outHeight;
        int width = options.outWidth;
        int inSampleSize = 1;
        while ((height >>= 1) >= maxHeight && (width >>= 1) >= maxWidth) {
            inSampleSize <<= 1;
        }
        return inSampleSize;
    }

    /**
     * 获取bitmap
     *
     * @param is 输入流
     * @return bitmap
     */
    public static Bitmap getBitmap(InputStream is) {
        if (is == null) return null;
        return BitmapFactory.decodeStream(is);
    }

    /**
     * 获取bitmap
     *
     * @param is        输入流
     * @param maxWidth  最大宽度
     * @param maxHeight 最大高度
     * @return bitmap
     */
    public static Bitmap getBitmap(InputStream is, int maxWidth, int maxHeight) {
        if (is == null) return null;
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(is, null, options);
        options.inSampleSize = calculateInSampleSize(options, maxWidth, maxHeight);
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeStream(is, null, options);
    }

    public static int getBitmapSampleSizeHeight(String path, int targetWidth) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, options);
        int outHeight = options.outHeight;
        int outWidth = options.outWidth;
        int scale = outWidth / targetWidth;
        return outHeight / scale;
    }

    public static int getBitmapSampleSizeWidth(String path, int targetHeight) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, options);
        int outHeight = options.outHeight;
        int outWidth = options.outWidth;
        int scale = outHeight / targetHeight;
        return outWidth / scale;
    }

    /**
     * 按采样大小压缩
     *
     * @param src        源图片
     * @param sampleSize 采样率大小
     * @return 按采样率压缩后的图片
     */
    public static Bitmap compressBySampleSize(Bitmap src, int sampleSize) {
        return compressBySampleSize(src, sampleSize, false);
    }

    /**
     * 按采样大小压缩
     *
     * @param src        源图片
     * @param sampleSize 采样率大小
     * @param recycle    是否回收
     * @return 按采样率压缩后的图片
     */
    public static Bitmap compressBySampleSize(Bitmap src, int sampleSize, boolean recycle) {
        if (isEmptyBitmap(src))
            return null;
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = sampleSize;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        src.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] bytes = baos.toByteArray();
        if (recycle && !src.isRecycled())
            src.recycle();
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length, options);
    }

    private static boolean isEmptyBitmap(Bitmap src) {
        return src == null || src.getWidth() == 0 || src.getHeight() == 0;
    }
}

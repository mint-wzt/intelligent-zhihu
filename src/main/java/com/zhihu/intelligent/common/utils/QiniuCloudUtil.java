package com.zhihu.intelligent.common.utils;


import com.qiniu.util.Auth;
import com.qiniu.util.Base64;
import com.qiniu.util.StringMap;
import com.qiniu.util.UrlSafeBase64;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


/**
 * 七牛云上传文件工具类
 */
public class QiniuCloudUtil {

    // 设置需要操作的账号的AK和SK
    private static final String ACCESS_KEY = "jZ1lkSDQAgv_OrsfS_Ym5arBtg-6pT7ntY7tO-SE";
    private static final String SECRET_KEY = "xdkagMXD4ZOe_HHR22gQDtH97lj_LKazb81vEe8z";

    // 要上传的空间
    private static final String bucket = "wangzhitong";

    // 密钥
    private static final Auth auth = Auth.create(ACCESS_KEY, SECRET_KEY);

    private static final String DOMAIN = "http://q2ayj5xzi.bkt.clouddn.com/";

//    private static final String style = "自定义的图片样式";

    public static String getUpToken() {
        return auth.uploadToken(bucket, null, 3600, new StringMap().put("insertOnly", 1));
    }

    //base64方式上传
    public static String put64image(byte[] base64, String key) throws Exception {
        String file64 = Base64.encodeToString(base64, 0);
        Integer len = base64.length;

        //华北空间使用 upload-z1.qiniu.com，华南空间使用 upload-z2.qiniu.com，北美空间使用 upload-na0.qiniu.com
        String url = "http://upload-z2.qiniu.com/putb64/" + len + "/key/" + UrlSafeBase64.encodeToString(key);

        RequestBody rb = RequestBody.create(null, file64);
        Request request = new Request.Builder()
                .url(url)
                .addHeader("Content-Type", "application/octet-stream")
                .addHeader("Authorization", "UpToken " + getUpToken())
                .post(rb).build();
        //System.out.println(request.headers());
        OkHttpClient client = new OkHttpClient();
        okhttp3.Response response = client.newCall(request).execute();
        System.out.println(response);
        //如果不需要添加图片样式，使用以下方式
        return DOMAIN + key;
//        return DOMAIN + key + "?" + style;
    }

    public static String uploadImg(MultipartFile image, String fileName) {
        try {
            byte[] bytes = image.getBytes();
            try {
                //使用base64方式上传到七牛云
                return QiniuCloudUtil.put64image(bytes, fileName);
            } catch (Exception e) {
                return null;
            }
        } catch (IOException e) {
            return null;
        }
    }
}

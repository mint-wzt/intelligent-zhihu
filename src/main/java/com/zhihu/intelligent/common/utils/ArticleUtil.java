package com.zhihu.intelligent.common.utils;


/**
 * 文章工具类
 */
public class ArticleUtil {

    /**
     * 限制上传文件为图片格式
     *
     * @param type
     * @return
     */
    public static boolean isImage(String type) {
        if (type != null) { // 判断文件类型是否为空
            if (".GIF".equals(type.toUpperCase()) || ".PNG".equals(type.toUpperCase()) || ".JPG".equals(type.toUpperCase()) || ".JPEG".equals(type.toUpperCase())) {
                return true;
            }
            return false;
        } else {
            return false;
        }
    }
}

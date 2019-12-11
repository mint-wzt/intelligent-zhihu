package com.zhihu.intelligent.system.exception;

public class ImageUploadFailedException extends RuntimeException {
    public ImageUploadFailedException(){}

    public ImageUploadFailedException(String message){
        super(message);
    }
}

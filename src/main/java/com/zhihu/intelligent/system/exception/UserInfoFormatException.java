package com.zhihu.intelligent.system.exception;

public class UserInfoFormatException extends RuntimeException {
    public UserInfoFormatException(){}

    public UserInfoFormatException(String message){
        super(message);
    }
}

package com.zhihu.intelligent.system.exception;

public class DeleteFailedException extends RuntimeException {

    public DeleteFailedException(){}

    public DeleteFailedException(String message){
        super(message);
    }

}

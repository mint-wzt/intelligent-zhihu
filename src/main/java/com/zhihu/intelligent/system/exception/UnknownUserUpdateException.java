package com.zhihu.intelligent.system.exception;

public class UnknownUserUpdateException extends RuntimeException {
    public UnknownUserUpdateException() {
    }

    public UnknownUserUpdateException(String message) {
        super(message);
    }
}

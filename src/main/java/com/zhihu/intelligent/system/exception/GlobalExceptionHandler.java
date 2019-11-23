package com.zhihu.intelligent.system.exception;

import com.alibaba.fastjson.JSON;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(value = UserNameAlreadyExistException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleUserNameAlreadyExistException(UserNameAlreadyExistException e) {
        return JSON.toJSONString(new GlobalResponse(HttpStatus.BAD_REQUEST.value(), e.getMessage()));
    }

    @ExceptionHandler(value = SignatureException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleSignatureException(SignatureException e) {
        return JSON.toJSONString(new GlobalResponse(HttpStatus.UNAUTHORIZED.value(),e.getMessage()));
    }

    @ExceptionHandler(value = UnknownUserUpdateException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleUnkownUserUpdateException(UnknownUserUpdateException e){
        return JSON.toJSONString(new GlobalResponse(HttpStatus.BAD_REQUEST.value(),e.getMessage()));
    }

    @ExceptionHandler(value = ImageUploadFailedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleImageUploadFailedException(ImageUploadFailedException e){
        return JSON.toJSONString(new GlobalResponse(HttpStatus.BAD_REQUEST.value(),e.getMessage()));
    }
}

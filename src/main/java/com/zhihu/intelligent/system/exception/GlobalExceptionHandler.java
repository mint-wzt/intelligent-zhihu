package com.zhihu.intelligent.system.exception;

import io.jsonwebtoken.security.SignatureException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(value = UserNameAlreadyExistException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public GlobalResponse handleUserNameAlreadyExistException(UserNameAlreadyExistException e) {
        return new GlobalResponse(HttpStatus.BAD_REQUEST.value(), e.getMessage());
    }

    @ExceptionHandler(value = SignatureException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public GlobalResponse handleSignatureException(SignatureException e) {
        return new GlobalResponse(HttpStatus.UNAUTHORIZED.value(),e.getMessage());
    }

    @ExceptionHandler(value = ImageUploadFailedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public GlobalResponse handleImageUploadFailedException(ImageUploadFailedException e){
        return new GlobalResponse(HttpStatus.BAD_REQUEST.value(),e.getMessage());
    }

    @ExceptionHandler(value = DeleteFailedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public GlobalResponse handleDeleteFailedException(DeleteFailedException e){
        return new GlobalResponse(HttpStatus.BAD_REQUEST.value(),e.getMessage());
    }

    @ExceptionHandler(value = UserInfoFormatException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public GlobalResponse handleUserInfoEmptyException(UserInfoFormatException e){
        return new GlobalResponse(1001,e.getMessage());
    }

    @ExceptionHandler(value = ImageFormatException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public GlobalResponse handleImageFormatException(ImageFormatException e){
        return new GlobalResponse(1002,e.getMessage());
    }
}

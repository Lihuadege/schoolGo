package com.li.schoolGo.config;

import com.li.schoolGo.util.GlobalException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(GlobalException.class)
    public Object handleException(){
        return null;
    }

}

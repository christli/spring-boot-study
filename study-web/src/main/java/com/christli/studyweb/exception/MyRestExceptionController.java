package com.christli.studyweb.exception;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 基于@ControllerAdvice注解的全局异常统一处理只能针对于Controller层的异常
 * 为了和Controller 区分 ，我们可以指定 annotations = RestController.class，那么在Controller中抛出的异常 这里就不会被捕捉
 */
@RestControllerAdvice(annotations = RestController.class)
public class MyRestExceptionController {

    /**
     * 处理所有的Controller层面的异常
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public final Map handleAllExceptions(Exception ex, WebRequest request) {
        Map<String, Object> map = new HashMap<>();
        map.put("status", -1);
        map.put("msg", ex.getLocalizedMessage());

        return map;
    }

    /**
     * 处理所有的Controller层面的异常
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public final Map handleValidExceptions(MethodArgumentNotValidException ex, WebRequest request) {
        Map<String, Object> map = new HashMap<>();
        map.put("status", -1);
        // 获取异常信息
        BindingResult exceptions = ex.getBindingResult();
        // 判断异常中是否有错误信息，如果存在就使用异常中的消息，否则使用默认消息
        if (exceptions.hasErrors()) {
            List<ObjectError> errors = exceptions.getAllErrors();
            if (!errors.isEmpty()) {
                // 这里列出了全部错误参数，按正常逻辑，只需要第一条错误即可
                FieldError fieldError = (FieldError) errors.get(0);
                map.put("msg", fieldError.getDefaultMessage());
            }
        } else {
            map.put("msg", ex.getLocalizedMessage());
        }

        return map;
    }
}

package com.leyou.common.advice;

import com.leyou.common.exception.LyExcetion;
import com.leyou.common.vo.ExceptionResult;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**通用异常处理拦截器
 * 利用aop的思想 当出现异常的时 我们拦截下来 进行增强
 */
@ControllerAdvice //默认情况下 会拦截所有加了@Controller的类
public class CommonExceptionHandler {

    /**
     * 声明要处理的异常类型
     * @param e
     * @return
     */
    @ExceptionHandler(LyExcetion.class)
    public ResponseEntity<ExceptionResult> handleException(LyExcetion e){
        //我们不能直接的返回e.getMessage() 这样返回的参数太少了 我们需要定义一个异常返回对象 ExceptionResult
        ExceptionResult result=new ExceptionResult(e.getExceptionEnum());
        return ResponseEntity.status(e.getExceptionEnum().getStauts()).body(result);
    }

}

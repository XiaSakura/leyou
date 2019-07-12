package com.leyou.common.exception;

import com.leyou.common.enums.ExceptionEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 自定义异常
 * 因为RuntimeException不能存放状态码
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class LyExcetion extends RuntimeException{
    private ExceptionEnum exceptionEnum;
}

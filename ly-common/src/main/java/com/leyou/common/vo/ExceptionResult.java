package com.leyou.common.vo;

import com.leyou.common.enums.ExceptionEnum;
import com.leyou.common.exception.LyExcetion;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 用于返回页面的 类似pageResult
 * 异常返回对象
 */
@Data
@AllArgsConstructor
public class ExceptionResult {
    private int status;
    private String message;
    private Long timestamp;

    public ExceptionResult(){

    }

    public ExceptionResult(ExceptionEnum e){
        this.status= e.getStauts();
        this.message=e.getMsg();
        this.timestamp=System.currentTimeMillis();
    }
}

package com.xinghen.udf.core.except;

import com.xinghen.udf.core.RestResponse;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 错误信息
 */
@Data
public class ErrorResult implements Serializable {

    /**
     * 异常时间
     */
    @ApiModelProperty(value = "异常时间", required = true, dataType = "date")
    private Date date;

    /**
     * 异常类名
     */
    @ApiModelProperty(value = "异常类名", required = true, dataType = "string")
    private String type;

    /**
     * 异常信息
     */
    @ApiModelProperty(value = "异常信息", required = true, dataType = "string")
    private String message;

    /**
     * 异常堆栈
     */
    @ApiModelProperty(value = "异常堆栈", required = true, dataType = "string")
    private String stackTrace;

    /**
     * 子异常
     */
    @ApiModelProperty(value = "子异常", required = true, dataType = "object")
    private RestResponse<String> child;
}

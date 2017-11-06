package com.xinghen.udf.core;

import com.xinghen.udf.core.except.ErrorResult;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.io.Serializable;
import java.util.UUID;

/**
 * rest响应对象
 */
@ApiModel(description = "响应消息体")
@Data
public class RestResponse<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 响应ID
     */
    @ApiModelProperty(value = "响应ID", required = true, dataType = "string")
    private String id = UUID.randomUUID().toString();

    /**
     * 状态码（业务定义）
     */
    @ApiModelProperty(value = "状态码", required = true, dataType = "string")
    private String code = Integer.toString(HttpStatus.OK.value());

    /**
     * 状态码描述（业务定义）
     */
    @ApiModelProperty(value = "状态码描述", required = true, dataType = "string")
    private String message = HttpStatus.OK.getReasonPhrase();

    /**
     * 结果集
     */
    @ApiModelProperty(value = "结果集", required = true, dataType = "object")
    private T result;

    /**
     * 错误
     */
    @ApiModelProperty(value = "错误", required = true, dataType = "object")
    private ErrorResult error;

    public RestResponse() {
    }

    public RestResponse(T result) {
        this.result = result;
    }

    public RestResponse(HttpStatus httpStatus, ErrorResult error) {
        this.code = Integer.toString(httpStatus.value());
        this.message = httpStatus.getReasonPhrase();
        this.error = error;
    }

    public RestResponse(String code, String message, ErrorResult error) {
        this.code = code;
        this.message = message;
        this.error = error;
    }

    public RestResponse(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public RestResponse(String code, String message, T result) {
        this.code = code;
        this.message = message;
        this.result = result;
    }
}

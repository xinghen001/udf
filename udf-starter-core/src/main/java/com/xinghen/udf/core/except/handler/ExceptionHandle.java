package com.xinghen.udf.core.except.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xinghen.udf.core.ApplicationConfig;
import com.xinghen.udf.core.RestResponse;
import com.xinghen.udf.core.except.ErrorResult;
import com.xinghen.udf.core.except.ParameterValidException;
import com.xinghen.udf.core.except.PermissionException;
import com.xinghen.udf.core.except.SystemRuntimeException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.security.auth.message.AuthException;
import java.io.IOException;
import java.util.Date;

@ControllerAdvice
@Slf4j
public class ExceptionHandle extends ResponseEntityExceptionHandler {

    @Autowired
    private ApplicationConfig applicationConfig;

    @Autowired
    private ObjectMapper objectMapper;

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> exception(Exception ex, WebRequest request) {
        ResponseEntity<Object> objectResponseEntity = this.handleException(ex, request);
        return this.handleExceptionInternal(ex, null, objectResponseEntity.getHeaders(), objectResponseEntity.getStatusCode(), request);
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {
        HttpStatus localHttpStatus = status;
        ErrorResult errorResult = buildError(applicationConfig, ex);
        if (ex instanceof PermissionException) { //权限异常
            localHttpStatus = HttpStatus.FORBIDDEN;
        } else if (ex instanceof AuthException) { //认证异常
            localHttpStatus = HttpStatus.UNAUTHORIZED;
        } else if (ex instanceof ParameterValidException) { //参数校验异常
            localHttpStatus = HttpStatus.BAD_REQUEST;
        } else if (ex instanceof RestClientResponseException) { //rest请求异常
            try {
                RestClientResponseException restClientResponseException = (RestClientResponseException) ex;
                String data = restClientResponseException.getResponseBodyAsString();
                if (StringUtils.isNotBlank(data)) {
                    RestResponse<String> child = objectMapper.readValue(data, objectMapper.getTypeFactory().constructParametricType(RestResponse.class, String.class));
                    errorResult.setChild(child);
                }
            } catch (IOException e) {
                throw new SystemRuntimeException(e);
            }
        }
        log.error(ex.getClass().getName(), ex);
        return super.handleExceptionInternal(ex, new RestResponse<>(localHttpStatus, errorResult), headers, localHttpStatus, request);
    }

    /**
     * 描述 : 构造错误响应对象
     *
     * @param applicationConfig 系统配置
     * @param throwable         异常
     * @return 错误响应对象
     */
    public static ErrorResult buildError(ApplicationConfig applicationConfig, Throwable throwable) {
        ErrorResult error = new ErrorResult();
        error.setType(throwable.getClass().getName());
        error.setMessage(ExceptionUtils.getMessage(throwable));
        if (applicationConfig.isOutputExceptionStackTrace()) {
            error.setStackTrace(ExceptionUtils.getStackTrace(throwable));
        }
        error.setDate(new Date());
        return error;
    }

}

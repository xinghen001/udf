package com.xinghen.udf.core.except.handler;

import com.xinghen.udf.core.ApplicationConfig;
import com.xinghen.udf.core.RestResponse;
import com.xinghen.udf.core.except.ErrorResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.AbstractErrorController;
import org.springframework.boot.autoconfigure.web.ErrorAttributes;
import org.springframework.boot.autoconfigure.web.ErrorProperties;
import org.springframework.boot.autoconfigure.web.ErrorViewResolver;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;
import java.util.Map;

@ApiIgnore
@Controller
@RequestMapping("${server.error.page:${error.path:/error}}")
@Slf4j
public class ExceptionController extends AbstractErrorController {

    private static final String KEY_EXCEPTION = "exception";

    private static final String KEY_MESSAGE = "message";

    private final ErrorProperties errorProperties;

    @Autowired
    private ApplicationConfig applicationConfig;

    public ExceptionController(ErrorAttributes errorAttributes, ErrorProperties errorProperties) {
        super(errorAttributes);
        this.errorProperties = errorProperties;
    }

    public ExceptionController(ErrorAttributes errorAttributes, ErrorProperties errorProperties, List<ErrorViewResolver> errorViewResolvers) {
        super(errorAttributes, errorViewResolvers);
        this.errorProperties = errorProperties;
    }

    @Override
    public String getErrorPath() {
        return this.errorProperties.getPath();
    }

    @RequestMapping(produces = "text/html")
    public ModelAndView errorHtml(HttpServletRequest request, HttpServletResponse response) {
        HttpStatus status = getStatus(request);
        Map<String, Object> map = getErrorAttributes(request, isIncludeStackTrace(request, MediaType.TEXT_HTML));
        RestResponse<String> restResponse = this.getRestResponse(request, status, map);
        map.put("restResponse", restResponse);
        map.put(KEY_EXCEPTION, restResponse.getError().getType());
        map.put(KEY_MESSAGE, restResponse.getError().getMessage());
        response.setStatus(status.value());
        ModelAndView modelAndView = resolveErrorView(request, response, status, map);
        return modelAndView == null ? new ModelAndView("error", map) : modelAndView;
    }

    @RequestMapping
    @ResponseBody
    public ResponseEntity<RestResponse<String>> error(HttpServletRequest request) {
        Map<String, Object> body = getErrorAttributes(request, isIncludeStackTrace(request, MediaType.ALL));
        HttpStatus status = getStatus(request);
        return new ResponseEntity<>(getRestResponse(request, status, body), status);
    }

    protected RestResponse<String> getRestResponse(HttpServletRequest request, HttpStatus status, Map<String, Object> body) {
        log.info(body.toString());
        ErrorResult errorResult = new ErrorResult();
        if (status == HttpStatus.NOT_FOUND) { //404处理
            errorResult.setType(NoHandlerFoundException.class.getName());
            errorResult.setMessage(body.get("path").toString());
        } else { //非404处理
            Object object = request.getAttribute("javax.servlet.error.exception");
            if (object != null && object instanceof Exception) { //上下文中能拿到异常的情况
                Exception exception = (Exception) object;
                //ZuulException异常特殊处理,去除ZuulException的包裹 (不用instanceof的原因是不想因为这里的判断而引入zuul的依赖在core包中)
                if (exception.getClass().getName().equals("com.netflix.zuul.exception.ZuulException")) { //NOSONAR
                    errorResult = ExceptionHandle.buildError(applicationConfig, exception.getCause());
                } else {
                    errorResult = ExceptionHandle.buildError(applicationConfig, exception);
                }
            } else { //上下文中拿不到异常的情况
                errorResult.setType(body.containsKey(KEY_EXCEPTION) ? body.get(KEY_EXCEPTION).toString() : "unknow exception");
                errorResult.setMessage(body.containsKey(KEY_MESSAGE) ? body.get(KEY_MESSAGE).toString() : "unknow message");
            }
        }
        errorResult.setDate(new Date());
        return new RestResponse<>(status, errorResult);
    }

    protected boolean isIncludeStackTrace(HttpServletRequest request, MediaType textHtml) {
        ErrorProperties.IncludeStacktrace include = getErrorProperties().getIncludeStacktrace();
        return include == ErrorProperties.IncludeStacktrace.ALWAYS || include == ErrorProperties.IncludeStacktrace.ON_TRACE_PARAM && getTraceParameter(request);
    }

    protected ErrorProperties getErrorProperties() {
        return this.errorProperties;
    }
}

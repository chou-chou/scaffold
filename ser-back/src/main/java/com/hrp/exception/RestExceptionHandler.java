package com.hrp.exception;

import com.hrp.entity.enums.ResponseErrorEnum;
import com.hrp.entity.vo.ResponseResult;
import com.hrp.utils.RestResultGenerator;
import freemarker.core.UnexpectedTypeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

/**
 * RestExceptionHandler
 * RestController全局异常处理器
 *
 * @author KVLT
 * @date 2017-06-20.
 */
@ControllerAdvice(annotations = RestController.class)
public class RestExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(RestExceptionHandler.class);

    /**
     * 统一的rest接口异常处理器
     *
     * @param e
     * @param <T>
     * @return
     */
    @ExceptionHandler
    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    private <T> ResponseResult<T> globalExceptionHandler(Exception e) {

        logger.error("--------->接口调用异常!", e);
        return RestResultGenerator.genErrorResult(ResponseErrorEnum.INTERNAL_SERVER_ERROR);
    }

    /**
     * bean校验未通过异常
     *
     * @param e
     * @param <T>
     * @return
     */
    @ExceptionHandler(UnexpectedTypeException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    private <T> ResponseResult<T> illegalParamsExceptionHandler(UnexpectedTypeException e) {

        logger.error("--------->请求参数不合法!", e);
        return RestResultGenerator.genErrorResult(ResponseErrorEnum.ILLEGAL_PARAMS);
    }

}

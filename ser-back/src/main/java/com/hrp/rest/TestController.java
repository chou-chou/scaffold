package com.hrp.rest;

import com.hrp.utils.ErrorType;
import com.hrp.utils.RestUrl;
import com.wordnik.swagger.annotations.*;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * TestController
 *
 * @author KVLT
 * @date 2017-03-15.
 */
@Api(value = "test", description = "api测试", produces = MediaType.APPLICATION_JSON_VALUE)
@Controller
public class TestController {

    @ApiOperation(value = "获得商品信息", notes = "获取商品信息(用于数据同步)", httpMethod = "POST", produces=MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses(value = {@ApiResponse(code = 200, message = "商品信息"),
            @ApiResponse(code = 201, message = ErrorType.errorCheckToken + "(token验证失败)",  response=String.class),
            @ApiResponse(code = 202, message = ErrorType.error500 + "(系统错误)",response = String.class)})
    @RequestMapping(value = RestUrl.getProduct, method = RequestMethod.POST)
    @ResponseBody
    public String getProduct(@ApiParam(value = "json参数",  required = true) String someone)throws Exception {
        return "你好，" + someone;
    }
}

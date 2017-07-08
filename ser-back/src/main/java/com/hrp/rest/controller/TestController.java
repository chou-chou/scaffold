package com.hrp.rest.controller;

import com.hrp.utils.ErrorType;
import com.hrp.utils.RestUrl;
import io.swagger.annotations.*;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

/**
 * TestController
 *
 * @author KVLT
 * @date 2017-03-15.
 */
@Api(value = "/r/test", description = "api测试", produces = MediaType.APPLICATION_JSON_VALUE)
@RestController
@RequestMapping("/r/test")
public class TestController {

    @RequestMapping(value = RestUrl.test, method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "测试", httpMethod = "GET", notes = "测试API")
    public String greeting(@ApiParam(required = true, name = "name", value = "名称") @RequestParam(value = "name") String name) {
        return "欢迎， " + name;
    }

    @ApiOperation(value = "获得商品信息", notes = "获取商品信息(用于数据同步)", httpMethod = "POST", produces=MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses(value = {@ApiResponse(code = 200, message = "商品信息"),
            @ApiResponse(code = 201, message = ErrorType.errorCheckToken + "(token验证失败)",  response=String.class),
            @ApiResponse(code = 202, message = ErrorType.error500 + "(系统错误)",response = String.class)})
    @RequestMapping(value = RestUrl.getProduct, method = RequestMethod.POST)
    @ResponseBody
    public String getProduct(@ApiParam(name = "name", value = "参数", required = true) /*@RequestParam(value = "name")*/ String name) {
        return "你好，" + name;
    }
}

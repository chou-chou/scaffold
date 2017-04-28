package com.hrp.controller;

import com.hrp.service.RoleService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;

/**
 * RoleController
 *
 * @author KVLT
 * @date 2017-03-23.
 */
@Controller
@RequestMapping(value = "/role")
public class RoleController extends BaseController {

    @Resource(name = "roleService")
    private RoleService roleService;

    /**
     * 角色列表
     */
    @RequestMapping(method = RequestMethod.GET, value = "/list")
    private String list() {
        return "base/auth/role_list";
    }

}

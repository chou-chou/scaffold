package com.hrp.utils;

import com.hrp.service.AuthorityService;
import com.hrp.service.UserService;

/**
 * ServiceHelper
 * 获取Spring容器中的service bean
 * @author KVLT
 * @date 2017-03-15.
 */
public class ServiceHelper {

    public static Object getService(String serviceName){
        //WebApplicationContextUtils.
        return Constant.WEB_APP_CONTEXT.getBean(serviceName);
    }

    public static UserService getUserService(){
        return (UserService) getService("userService");
    }

    /**
     * 获取项目验证服务类
     * @return
     */
    public static AuthorityService getSysAuthorityService() {
        return (AuthorityService) getService("authorityService");
    }

}

package com.hrp.manager;

import com.hrp.shiro.session.ShiroSessionRepository;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * CustomSessionManager
 * 用户Session 手动管理
 * @author KVLT
 * @date 2017-03-28.
 */
public class CustomSessionManager {

    @Autowired
    ShiroSessionRepository shiroSessionRepository;
}

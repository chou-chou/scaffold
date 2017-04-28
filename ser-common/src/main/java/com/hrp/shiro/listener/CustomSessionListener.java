package com.hrp.shiro.listener;

import com.hrp.shiro.session.ShiroSessionRepository;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.SessionListener;

/**
 * CustomSessionListener
 * shiro 会话监听
 * @author KVLT
 * @date 2017-03-28.
 */
public class CustomSessionListener implements SessionListener {

    private ShiroSessionRepository shiroSessionRepository;

    /**
     * 一个回话的生命周期开始
     */
    public void onStart(Session session) {
        //TODO
        System.out.println("on start");
    }
    /**
     * 一个会话的生命周期结束
     */
    public void onStop(Session session) {
        //TODO
        System.out.println("on stop");
    }

    public void onExpiration(Session session) {
        shiroSessionRepository.deleteSession(session.getId());
    }

    public ShiroSessionRepository getShiroSessionRepository() {
        return shiroSessionRepository;
    }

    public void setShiroSessionRepository(ShiroSessionRepository shiroSessionRepository) {
        this.shiroSessionRepository = shiroSessionRepository;
    }

}

package com.hrp.oauth.service;

import com.hrp.oauth.entity.OAuthUser;

/**
 * OAuthUserService
 *
 * @author KVLT
 * @date 2017-03-24.
 */
public interface OAuthUserService {

    /**
     * 根据第三方登录类型和授权ID 找到授权用户
     * @param authType
     * @param oAuthId
     * @return
     */
    public OAuthUser findByOAuthTypeAndOAuthId(String authType, String oAuthId) throws Exception;

}

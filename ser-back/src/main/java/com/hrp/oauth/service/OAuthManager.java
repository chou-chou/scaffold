package com.hrp.oauth.service;

import com.hrp.oauth.common.CustomOAuthService;

import java.util.List;

/**
 * OAuthServices
 *
 * @author KVLT
 * @date 2017-03-24.
 */
public class OAuthManager {

    private List<CustomOAuthService> customOAuthServices;

    public CustomOAuthService getOAuthService(String type) {
        CustomOAuthService oAuthService = null;
        for (CustomOAuthService customOAuthService : customOAuthServices) {
            if (customOAuthService.getOAuthType().equals(type)) {
                oAuthService = customOAuthService;
                break;
            }
        }
        return oAuthService;
    }

    public List<CustomOAuthService> getAllOAuthServices() {
        return customOAuthServices;
    }

}

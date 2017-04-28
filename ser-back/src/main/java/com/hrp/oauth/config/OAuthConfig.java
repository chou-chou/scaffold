package com.hrp.oauth.config;

import com.hrp.entity.enums.EnumOAuthType;
import com.hrp.oauth.common.CustomOAuthService;
import com.hrp.oauth.platform.github.GithubApi;
import org.scribe.builder.ServiceBuilder;

/**
 * OAuthConfig
 *
 * @author KVLT
 * @date 2017-03-24.
 */
public class OAuthConfig {

    String callback_url;
    String key;
    String secret;
    String state;

    public OAuthConfig(String callback_url, String key, String secret, String state) {
        this.callback_url = callback_url;
        this.key = key;
        this.secret = secret;
        this.state = state;
    }

    public GithubApi githubApi(){
        return new GithubApi(state);
    }

    public CustomOAuthService getXXXOAuthService() {
        return (CustomOAuthService) new ServiceBuilder()
                .provider(githubApi())
                .apiKey(key)
                .apiSecret(secret)
                .callback(String.format(callback_url, EnumOAuthType.GITHUB.getCode()))
                .build();
    }
}

package com.hrp.oauth.common;

import com.hrp.oauth.entity.OAuthUser;
import org.scribe.model.Token;
import org.scribe.oauth.OAuthService;

/**
 * CustomOAuthService
 *
 * @author KVLT
 * @date 2017-03-24.
 */
public interface CustomOAuthService extends OAuthService {

    String getOAuthType();
    String getAuthorizationUrl();
    OAuthUser getOAuthUser(Token accessToken);
    String getBtnClass();

}

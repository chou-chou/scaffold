package com.hrp.oauth.common;

import com.hrp.oauth.entity.OAuthUser;
import org.scribe.model.OAuthRequest;
import org.scribe.model.Token;
import org.scribe.model.Verifier;
import org.scribe.oauth.OAuthService;

/**
 * OAuthServiceDecorator
 *
 * @author KVLT
 * @date 2017-03-24.
 */
public abstract class OAuthServiceDecorator implements OAuthService {

    private final OAuthService oAuthService;
    private final String oAuthType;
    private final String authorizationUrl;

    public OAuthServiceDecorator(OAuthService oAuthService, String type) {
        super();
        this.oAuthService = oAuthService;
        this.oAuthType = type;
        this.authorizationUrl = oAuthService.getAuthorizationUrl(null);
    }

    public String getoAuthType() {
        return oAuthType;
    }

    public String getAuthorizationUrl(){
        return authorizationUrl;
    }

    public abstract OAuthUser getOAuthUser(Token accessToken);


    public Token getRequestToken() {
        return null;
    }

    public Token getAccessToken(Token token, Verifier verifier) {
        return null;
    }

    public void signRequest(Token token, OAuthRequest oAuthRequest) {

    }

    public String getVersion() {
        return null;
    }

    public String getAuthorizationUrl(Token token) {
        return null;
    }

}

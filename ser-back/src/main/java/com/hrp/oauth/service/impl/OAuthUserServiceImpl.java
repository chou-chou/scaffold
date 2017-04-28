package com.hrp.oauth.service.impl;

import com.hrp.dao.BaseDao;
import com.hrp.oauth.entity.OAuthUser;
import com.hrp.oauth.service.OAuthUserService;
import com.hrp.utils.PageData;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * OAuthUserServiceImpl
 *
 * @author KVLT
 * @date 2017-03-24.
 */
@Service("oAuthUserService")
public class OAuthUserServiceImpl implements OAuthUserService {

    @Resource(name = "baseDao")
    private BaseDao baseDao;

    public OAuthUser findByOAuthTypeAndOAuthId(String authType, String oAuthId) throws Exception {
        PageData pd = new PageData();
        pd.put("authType", authType);
        pd.put("oAuthId", oAuthId);

        pd = (PageData) this.baseDao.findForObject("", pd);
        OAuthUser oAuthUser = pd.map2Bean(OAuthUser.class);

        return oAuthUser;
    }
}

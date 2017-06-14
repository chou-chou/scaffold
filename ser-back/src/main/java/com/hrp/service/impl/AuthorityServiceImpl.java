package com.hrp.service.impl;

import com.hrp.dao.BaseDao;
import com.hrp.entity.system.Authority;
import com.hrp.service.AuthorityService;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * AuthorityServiceImpl
 *
 * @author KVLT
 * @date 2017-04-17.
 */
@Service("authorityService")
@CacheConfig(cacheNames = "sysAuthorityCache")
public class AuthorityServiceImpl implements AuthorityService {

    @Resource
    private BaseDao baseDao;

    @Override
    @Cacheable(cacheNames = "sysAuthorityCache")
    public List<Authority> listAll() throws Exception {
        return (List<Authority>) baseDao.findForList("AuthorityMapper.listAll", null);
    }
}

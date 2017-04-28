package com.hrp.service;

import com.hrp.entity.system.Authority;

import java.util.List;

/**
 * AuthorityServiceImpl
 *
 * @author KVLT
 * @date 2017-04-17.
 */
public interface AuthorityService {

    /**
     * 获取所有验证实体
     * @return
     */
    public List<Authority> listAll() throws Exception;

}

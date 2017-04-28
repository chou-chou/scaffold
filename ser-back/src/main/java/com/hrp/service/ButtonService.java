package com.hrp.service;

import java.util.Set;

/**
 * ButtonService
 * 按钮操作权限接口类
 * @author KVLT
 * @date 2017-03-24.
 */
public interface ButtonService {

    public Set<String> getButtonsByUsername(String username);

}

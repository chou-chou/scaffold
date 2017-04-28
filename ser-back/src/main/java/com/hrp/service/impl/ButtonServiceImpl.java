package com.hrp.service.impl;

import com.hrp.dao.BaseDao;
import com.hrp.service.ButtonService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Set;

/**
 * ButtonServiceImpl
 * 按钮操作权限接口类
 * @author KVLT
 * @date 2017-03-24.
 */
@Service("buttonService")
public class ButtonServiceImpl implements ButtonService {

    @Resource
    private BaseDao baseDao;

    public Set<String> getButtonsByUsername(String username) {
        return null;
    }
}

package com.hrp.service.impl;

import com.hrp.dao.BaseDao;
import com.hrp.entity.system.Button;
import com.hrp.service.ButtonService;
import com.hrp.utils.PageData;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

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

    public List<Button> getButtonList(PageData pd) throws Exception {
        return (List<Button>) baseDao.findForList("ButtonMapper.getButtonList", pd);
    }

    @Override
    public Boolean saveButton(Button button) throws Exception {
        Integer result = (Integer) baseDao.save("ButtonMapper.saveButton", button);
        return (result > 0) ? true : false;
    }

    @Override
    public Boolean deleteButton(PageData pd) throws Exception {
        Integer result = (Integer) baseDao.delete("ButtonMapper.deleteButton", pd);
        return (result > 0) ? true : false;
    }

    @Override
    public Boolean updateButton(Button button) throws Exception {
        Integer result = (Integer) baseDao.update("ButtonMapper.updateButton", button);
        return (result > 0) ? true : false;
    }
}

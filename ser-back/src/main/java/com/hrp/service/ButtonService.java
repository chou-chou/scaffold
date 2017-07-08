package com.hrp.service;

import com.hrp.entity.system.Button;
import com.hrp.utils.PageData;

import java.util.List;

/**
 * ButtonService
 * 按钮操作权限接口类
 * @author KVLT
 * @date 2017-03-24.
 */
public interface ButtonService {

    public List<Button> getButtonList(PageData pd) throws Exception;

    public Button getButtonInfo(Integer id) throws Exception;

    public Boolean saveButton(Button button) throws Exception;

    public Boolean deleteButton(PageData pd) throws Exception;

    public Boolean updateButton(Button button) throws Exception;

}

package com.hrp.service;

import com.hrp.entity.system.Dictionary;
import com.hrp.entity.system.TreeNode;
import com.hrp.utils.PageData;

import java.util.List;

/**
 * DictionaryService
 * 字典接口类
 * @author KVLT
 * @date 2017-03-24.
 */
public interface DictionaryService {

    /**
     * 根据检索条件检索出字典列表（包括所有子节点）  以树形结点数据返回
     * @param pd
     * @return
     * @throws Exception
     */
    public List<TreeNode> selectDictionaryCascade(PageData pd) throws Exception;

    /**
     * 根据检索条件检索出字典列表（不包括父子节点）
     * @param pd
     * @return
     * @throws Exception
     */
    public List<Dictionary> listAllDictionary(PageData pd) throws Exception;

    /**
     * 根据字典id获取实体
     * @param pd
     * @return
     */
    public Dictionary getByDicId(PageData pd) throws Exception;

    /**
     * 根据SUP_CODE查询父字典
     * @param pd
     * @return
     * @throws Exception
     */
    public Dictionary getBySupCode(PageData pd) throws Exception;

    /**
     * 根据SUP_CODE查询父字典
     * @param supCode
     * @return
     * @throws Exception
     */
    public Dictionary getBySupCode(String supCode) throws Exception;

    /**
     * 获取子字典数据
     * @param pd
     * @return
     * @throws Exception
     */
    public List<Dictionary> getSubDictionaryes(PageData pd) throws Exception;

    /**
     * 新增字典
     * @param pd
     * @return
     * @throws Exception
     */
    public Boolean saveDictionary(PageData pd) throws Exception;

    /**
     * 新增字典
     * @param dic
     * @return
     * @throws Exception
     */
    public Object saveDictionary(Dictionary dic) throws Exception;

    /**
     * 编辑字典
     * @param pd
     * @return
     * @throws Exception
     */
    public Boolean updateDictionary(PageData pd) throws Exception;


    /**
     * 支持批量删除
     * @param pd
     * @return
     * @throws Exception
     */
    public Boolean deleteByIds(PageData pd) throws Exception;

}

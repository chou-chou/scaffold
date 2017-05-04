package com.hrp.service.impl;

import com.hrp.dao.BaseDao;
import com.hrp.entity.system.Dictionary;
import com.hrp.entity.system.TreeNode;
import com.hrp.service.DictionaryService;
import com.hrp.utils.PageData;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * DictionaryServiceImpl
 *
 * @author KVLT
 * @date 2017-03-24.
 */
@Service("dictionaryService")
public class DictionaryServiceImpl implements DictionaryService {

    @Resource
    private BaseDao baseDao;

    @Override
    public List<TreeNode> selectDictionaryCascade(PageData pd) throws Exception {
        return (List<TreeNode>) baseDao.findForList("DictionaryMapper.selectDictionaryCascade", pd);
    }

    @Override
    public List<Dictionary> listAllDictionary(PageData pd) throws Exception {
        return (List<Dictionary>) baseDao.findForList("DictionaryMapper.listAllDictionary", pd);
    }

    @Override
    public Dictionary getByDicId(PageData pd) throws Exception {
        return (Dictionary) baseDao.findForObject("DictionaryMapper.getByDicId", pd);
    }

    @Override
    public Boolean saveDictionary(PageData pd) throws Exception {
        Integer result = (Integer) baseDao.save("DictionaryMapper.saveDictionaryMap", pd);
        return (result > 0) ? true : false;
    }

    @Override
    public Boolean updateDictionary(PageData pd) throws Exception {
        Integer result = (Integer) baseDao.update("DictionaryMapper.updateDictionary", pd);
        return (result > 0) ? true : false;
    }

    @Override
    public Boolean deleteByIds(PageData pd) throws Exception {
        Integer result = (Integer) baseDao.delete("DictionaryMapper.deleteDictionary", pd);
        return (result > 0) ? true : false;
    }

    @Override
    public Dictionary getBySupCode(PageData pd) throws Exception {
        return (Dictionary) baseDao.findForObject("DictionaryMapper.getBySupCode", pd);
    }

    @Override
    public Dictionary getBySupCode(String supCode) throws Exception {
        return (Dictionary) baseDao.findForObject("DictionaryMapper.getBySupCode", supCode);
    }

    @Override
    public List<Dictionary> getSubDictionaryes(PageData pd) throws Exception {
        return (List<Dictionary>) baseDao.findForList("DictionaryMapper.getSubDictionarys", pd);
    }

    @Override
    public Object saveDictionary(Dictionary dic) throws Exception {
        return baseDao.save("DictionaryMapper.saveDictionaryBean", dic);
    }
}

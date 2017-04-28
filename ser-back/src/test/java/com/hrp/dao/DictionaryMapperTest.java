package com.hrp.dao;

import com.hrp.entity.system.Dictionary;
import com.hrp.entity.system.TreeNode;
import com.hrp.utils.PageData;
import org.junit.Test;
import org.springframework.test.annotation.Rollback;

import java.util.List;

/**
 * DictionaryMapperTest
 *
 * @author KVLT
 * @date 2017-03-15.
 */
public class DictionaryMapperTest extends BaseDaoTest {

    @Test
    public void testDictionary() throws Exception{
        PageData pd = new PageData();
        Dictionary da = new Dictionary();
       //ENTRY_CODE , ENTRY_VALUE , SUP_ID , sequence , REMARK
        da.setEntryCode("01");
        da.setEntryValue("性别");
        da.setSupCode("");
        da.setSequence(1);
        da.setRemark("性别");
        dao.save("DictionaryMapper.saveDictionaryvity",da);
        dao.findForList("DictionaryMapper.selectDictionary", pd);
    }

    @Test
    public void selectDictionaryCascade() throws Exception {
        PageData pd = new PageData();

        pd.put("dicInfo", "CV0100.02");
        List<TreeNode> dicList = (List<TreeNode>) dao.findForList("DictionaryMapper.selectDictionaryCascade", pd);
        for (TreeNode dic : dicList) {
            logger.info(dic.toString());
        }
    }

    @Test
    public void listAllDictionary() throws Exception {
        PageData pd = new PageData();
        List<Dictionary> dicList = (List<Dictionary>) dao.findForList("DictionaryMapper.listAllDictionary", pd);
        for (Dictionary dictionary : dicList) {
            logger.info(dictionary.toString());
        }
    }

    @Test
    public void getByDicId() throws Exception {
        PageData pd = new PageData();
        pd.put("dicId", "1");

        Dictionary dic = (Dictionary) dao.findForObject("DictionaryMapper.getByDicId", pd);
        logger.info(dic.toString());
    }

    @Test
    @Rollback(false)
    public void saveDictionary() throws Exception {
        PageData pd = new PageData();
        pd.put("entryCode", "test");
        pd.put("entryValue", "测试");

        dao.save("DictionaryMapper.saveDictionary", pd);
    }

    @Test
    @Rollback(false)
    public void updateDictionary() throws Exception {
        PageData pd = new PageData();
        pd.put("dicId", "11");
        pd.put("entryCode", "test");
        pd.put("entryValue", "测试11");

        Object result = dao.update("DictionaryMapper.updateDictionary", pd);
        logger.info(result.toString());
    }

}

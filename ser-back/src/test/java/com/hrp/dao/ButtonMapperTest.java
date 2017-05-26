package com.hrp.dao;

import com.hrp.entity.system.Button;
import org.junit.Test;

import java.util.List;

/**
 * ButtonMapperTest
 *
 * @author KVLT
 * @date 2017-03-15.
 */
public class ButtonMapperTest extends BaseDaoTest {

    @Test
    public void testButton() throws Exception{
        Button bt = new Button();
        bt.setBtnTitle("查询");
        bt.setDisabled(false);
        bt.setMenuId(1);
        bt.setRemark("功能查询");
        Object obj = dao.save("ButtonMapper.saveButtonvity", bt);
        logger.info("    SQL影响行数 ：" + obj + "\t 新增ID ： " + bt.getBtnId());
    }

    @Test
    public void getButtonList() throws Exception {
        List<Button> btnList = (List<Button>) dao.findForList("ButtonMapper.getButtonList", pd);
        logger.info("获取按钮个数：\t" + btnList.size());
        for (Button button : btnList) {
            logger.info(button.toString());
        }
    }

}

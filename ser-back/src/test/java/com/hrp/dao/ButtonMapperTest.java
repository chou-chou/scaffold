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
        bt.setEnabled(false);
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

    /**
     * 根据用户ID获取用户按钮信息
     *
     * @throws Exception
     */
    @Test
    public void getButtonByUserId() throws Exception {
        pd.put("userId", "067448C76BC748BAA9EC01BBCB75631D");
        List<Button> btnList = (List<Button>) dao.findForList("ButtonMapper.getButtonByUserId", pd);
        logger.info("获取按钮个数：\t" + btnList.size());
        for (Button button : btnList) {
            logger.info(button.toString());
        }
    }

    @Test
    public void getButtonInfo() throws Exception {
        Button btn = (Button) dao.findForObject("ButtonMapper.getButtonInfo", 12);
        logger.info(btn.toString());
    }

}

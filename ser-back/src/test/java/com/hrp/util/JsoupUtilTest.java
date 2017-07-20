package com.hrp.util;

import com.hrp.entity.system.Button;
import com.hrp.utils.JsoupUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.List;

/**
 * JsoupUtilTest
 *
 * @author KVLT
 * @date 2017-05-19.
 */
public class JsoupUtilTest {

    protected final static Logger logger = LoggerFactory.getLogger(JsoupUtilTest.class);

    private static final String filePath = "E:\\WorkSpaces\\IdeaWpaces\\SER\\ser-back\\src\\main\\webapp\\WEB-INF\\views\\back\\system\\user\\user_list.jsp";
    private static final String ENCODE = "UTF-8";

    private static final String[] elementFields = new String[] {
            "[inCtrl]"
    };

    @Test
    public void parse() throws Exception {
        JsoupUtil ju = JsoupUtil.newInstance();

        List<Button> btnList = ju.parseBtnByFile(filePath, elementFields);
        for (Button button : btnList) {
            logger.info(" --> " + button.toString());
        }
    }

    @Test
    public void par() throws Exception {
        File input = new File(filePath);  // 读取

        Document doc = Jsoup.parse(input, ENCODE);  // 解析

        Elements elements = new Elements();
        Elements pElements = new Elements();
        for (String elementField : elementFields) {
            Elements es = doc.select(elementField);

            for (Element e : es) {
                Element p = e.parent();
                pElements.add(p);
                logger.info(" ----- " + p.attr("name"));
            }

            logger.info("-------------------------" + elementField + "\t" + es.size());
            elements.addAll(es); // 选择页面元素
        }

        for (Element ele : elements) {
            logger.info("\n --- " + "id:\t" + ele.id()
                    + "\n --- tag:\t" + ele.tagName()
                    + "\n --- class:\t" + ele.className()
                    + "\n --- title:\t" + ele.attr("title")
                    + "\n --- text:\t" + ele.text());
        }
    }
}

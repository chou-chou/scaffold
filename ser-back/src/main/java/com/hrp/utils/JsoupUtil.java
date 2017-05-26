package com.hrp.utils;

import com.google.common.collect.Lists;
import com.hrp.entity.system.Button;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.List;

/**
 * JsoupUtil
 *
 * @author KVLT
 * @date 2017-05-19.
 */
public class JsoupUtil {

    private static final Logger logger = LoggerFactory.getLogger(JsoupUtil.class);

    private static final String ENCODE = "UTF-8";

    /**
     * 静态内部类实现单例
     */
    private static class JsoupUtilHolder {
        public static JsoupUtil instance = new JsoupUtil();
    }

    private JsoupUtil() {}

    public static JsoupUtil newInstance() {
        return JsoupUtilHolder.instance;
    }

    /**
     * 读取本地文件，解析页面元素button的id
     * @param filePath
     * @return
     * @throws Exception
     */
    public List<Button> parseBtnByFile(String filePath, String... elementFields) throws Exception {
        List<Button> idList = Lists.newArrayList();
        File input = new File(filePath);  // 读取

        Document doc = Jsoup.parse(input, ENCODE);  // 解析

        Elements elements = new Elements();
        for (String elementField : elementFields) {
            Elements es = doc.select(elementField);
            logger.info(elementField + "\t" + es.size());
            elements.addAll(es); // 选择页面元素
        }

        for (Element ele : elements) {
            logger.info("\n --- " + "id:\t" + ele.id()
                    + "\n --- tag:\t" + ele.tagName()
                    + "\n --- class:\t" + ele.className()
                    + "\n --- title:\t" + ele.attr("title")
                    + "\n --- text:\t" + ele.text());
            Button btn = new Button();
            btn.setBtnId(ele.id());
            btn.setBtnTag(ele.tagName());
            btn.setBtnClass(ele.className());
            btn.setBtnType(ele.attr("type"));
            btn.setBtnTitle(ele.attr("title"));
            btn.setBtnText(ele.text());

            idList.add(btn);
        }

        return idList;
    }
}

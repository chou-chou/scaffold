package com.hrp.utils;

import com.google.common.collect.Lists;
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
    public List<String> parseBtnByFile(String filePath, String... elementFields) throws Exception {
        List<String> idList = Lists.newArrayList();
        File input = new File(filePath);  // 读取

        Document doc = Jsoup.parse(input, ENCODE);  // 解析

        Elements ids = new Elements();
        for (String elementField : elementFields) {
            ids.addAll(doc.select(elementField)); // 选择页面元素
        }

        for (Element btn : ids) {
            if (null != btn.id() && !"".equals(btn.id())) {
                idList.add(btn.id());
                logger.info(btn.id());
            }
        }

        return idList;
    }
}

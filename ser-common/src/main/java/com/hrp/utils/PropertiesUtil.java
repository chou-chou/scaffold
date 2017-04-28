package com.hrp.utils;

import java.io.InputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * PropertiesUtil
 *
 * @author KVLT
 * @date 2017-03-23.
 */
public class PropertiesUtil {

    static private Properties props;

    static{
        System.out.println(PropertiesUtil.class.getClass().getResource("/").getPath() + "../classes/config/system.properties");
        readProperties(PropertiesUtil.class.getClass().getResource("/").getPath() + "../classes/config/system.properties");
    }

    /**
     * 获取某个属性
     */
    public String getProperty(String key){
        return props.getProperty(key);
    }

    /**
     * 获取所有属性，返回一个map,不常用
     * 可以试试props.putAll(t)
     */
    public Map getAllProperty(){
        Map map=new HashMap();
        Enumeration enu = props.propertyNames();
        while (enu.hasMoreElements()) {
            String key = (String) enu.nextElement();
            String value = props.getProperty(key);
            map.put(key, value);
        }
        return map;
    }

    /**
     * 在控制台上打印出所有属性，调试时用。
     */
    public void printProperties(){
        props.list(System.out);
    }

    private static void readProperties(String fileName) {
        try {
            props = new Properties();
            InputStream fis = PropertiesUtil.class.getClassLoader().getResourceAsStream(fileName);
            props.load(fis);
            fis.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getValue(String key) {
        try {
            String returnStr = "";
            returnStr = props.getProperty(key);
            return returnStr;
        } catch (Exception e) {
            return "";
        }
    }

    public static void main(String[] args) {
        String str = PropertiesUtil.getValue("page.login");
        System.out.println(str);
    }

}

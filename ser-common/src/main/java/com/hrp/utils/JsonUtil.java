package com.hrp.utils;

import com.hrp.utils.lang.StringUtil;
import net.sf.json.JSON;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.xml.XMLSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.util.*;

/**
 * JsonUtil
 * JSON数据处理工具类
 * @author KVLT
 * @date 2017-03-14.
 */
public class JsonUtil {

    private static final Logger log = LoggerFactory.getLogger(JsonUtil.class);
    private static XMLSerializer xmlserializer = new XMLSerializer();

    private static final Map PRIMITIVE_MAP;
    private static final Set NUMBER_MAP;

    static {
        PRIMITIVE_MAP = new HashMap();
        PRIMITIVE_MAP.put(Float.TYPE, java.lang.Float.class);
        PRIMITIVE_MAP.put(Double.TYPE, java.lang.Double.class);
        PRIMITIVE_MAP.put(Integer.TYPE, java.lang.Integer.class);
        PRIMITIVE_MAP.put(Long.TYPE, java.lang.Long.class);
        PRIMITIVE_MAP.put(Byte.TYPE, java.lang.Byte.class);
        PRIMITIVE_MAP.put(Short.TYPE, java.lang.Short.class);
        PRIMITIVE_MAP.put(Character.TYPE, java.lang.Character.class);
        PRIMITIVE_MAP.put(Boolean.TYPE, java.lang.Boolean.class);
        NUMBER_MAP = new HashSet(PRIMITIVE_MAP.values());
        NUMBER_MAP.add(java.math.BigDecimal.class);
        NUMBER_MAP.add(java.math.BigInteger.class);
    }

    /**
     * 实体类型的对象
     */
    public static final int OBJECT_TYPE_BEAN = 1;

    /**
     * 集合类型对象
     */
    public static final int OBJECT_TYPE_LIST = 2;

    /**
     * 方法名称：writeJsonToResponse
     * 内容摘要：向客户端写入JSON字符串流
     * @param response  HTTP响应
     * @param object    待写入的对象
     * @param objType   待写入的对象类型
     * @return
     */
    public static boolean writeJsonToResponse(HttpServletResponse response, Object object, int objType)
    {
        boolean flag = true;

        if(object == null)
        {
            return false;
        }

        //response.setContentType("text/xml; charset=UTF-8");
        response.setContentType("application/json; charset=UTF-8");
        try
        {
            JSON json = null;

            if (objType == OBJECT_TYPE_BEAN)
            {
                json = JSONObject.fromObject(object);
            }
            else if (objType == OBJECT_TYPE_LIST)
            {
                json = JSONArray.fromObject(object);
            }
            else
            {
                return false;
            }

            String responseText = json.toString();
            response.getWriter().write(responseText);
        }
        catch (IOException e)
        {
            flag = false;
            e.printStackTrace();
        }
        return flag;
    }

    /**
     * 方法名称：parseJsonObjectToBean
     * 内容摘要：将一个JSON对象转换成指定类型的Bean
     * @param json  任意实体，包括Json格式字符串
     * @param clazz 需要转换的bean的Class
     * @return
     */
    public static Object parseJsonObjectToBean(Object json, Class clazz)
    {
        Object bean = null;
        try
        {
            JSONObject jsonObject = JSONObject.fromObject(json);

            bean = JSONObject.toBean(jsonObject, clazz);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return bean;
    }

    /**
     * 方法名称：parseJsonArrayToBean
     * 内容摘要：将一个JSON对象转换成指定类型的Bean集合
     * @param json  任意实体，包括Json格式字符串
     * @param clazz 需要转换的bean的Class
     * @return
     */
    public static List<Object> parseJsonArrayToBean(Object json, Class clazz)
    {
        List<Object> list = new LinkedList<Object>();
        try
        {
            JSONArray jsonArray = JSONArray.fromObject(json);

            Object[] beans = jsonArray.toArray();

            Object bean = null;
            for(int i=0 ; i< beans.length; i++)
            {
                bean = parseJsonObjectToBean(beans[i], clazz);
                list.add(bean);
            }

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return list;
    }

    /**
     * 方法名称：getJsonString
     * 内容摘要：将对象转换为JSON字符串
     * @param object
     * @param objType
     * @return
     */
    public static String getJsonString(Object object, int objType)
    {
        JSON json = null;
        try
        {
            json = null;

            if (objType == OBJECT_TYPE_BEAN)
            {
                json = JSONObject.fromObject(object);
            }
            else if (objType == OBJECT_TYPE_LIST)
            {
                json = JSONArray.fromObject(object);
            }
            else
            {
                return "待写入实体的对象类型不正确";
            }
        }
        catch (Exception e)
        {
            return "转换JSON字符串出错";
        }

        return json.toString();
    }

    public static Class getClass(String className)
            throws ClassNotFoundException {
        if (className.indexOf('.') == -1)
            if (className.equalsIgnoreCase("date"))
                className = (new StringBuilder()).append("java.util.").append(
                        StringUtil.capitalize(className)).toString();
            else
                className = (new StringBuilder()).append("java.lang.").append(
                        StringUtil.capitalize(className)).toString();
        Class clazz = Class.forName(className);
        return clazz;
    }

    public static Object getValue(String type, Object rawValue)
            throws SecurityException, IllegalArgumentException,
            NoSuchMethodException, InstantiationException,
            IllegalAccessException, InvocationTargetException,
            ClassNotFoundException, ParseException {
        return getValue(getClass(type), rawValue);
    }

    public static Object getValue(Class type, Object rawValue)
            throws SecurityException, IllegalArgumentException,
            NoSuchMethodException, InstantiationException,
            IllegalAccessException, InvocationTargetException, ParseException {
        int flag = 0;
        Object value;
        if (java.util.Date.class.equals(type) || java.util.Date.class.equals(type.getSuperclass())) {
            value = getDiversedDateValue(rawValue, type);
            flag = 1;
        } else if (java.lang.String.class.equals(type)) {
            value = getString(rawValue);
            flag = 2;
        } else if (type.isPrimitive()) {
            value = getNumberValue(rawValue, getWrappedClass(type));
            flag = 3;
        } else if (NUMBER_MAP.contains(type)) {
            value = getNumberValue(rawValue, type);
            flag = 4;
        } else {
            value = rawValue;
        }
        log.debug("Set [{},{}] with value {}", new Object[] { type,
                Integer.valueOf(flag), value });
        return value;
    }

    private static Object getDiversedDateValue(Object raw, Class clazz)
            throws SecurityException, NoSuchMethodException,
            IllegalArgumentException, InstantiationException,
            IllegalAccessException, InvocationTargetException, ParseException {
        Object tmp;
        if (raw.toString().indexOf(':') > 0)
            tmp = DateUtil.parse(raw.toString(), DateUtil.formatStr_yyyyMMddHHmmss);
        else
            tmp = DateUtil.parse(raw.toString(), DateUtil.formatStr_yyyyMMdd);
        long time = ((Date) tmp).getTime();
        Constructor constructor = clazz
                .getConstructor(new Class[] { Long.TYPE });
        Object value = constructor.newInstance(new Object[] { Long
                .valueOf(time) });
        return value;
    }

    private static Class getWrappedClass(Class clazz) {
        Class wrappedClass = (Class) PRIMITIVE_MAP.get(clazz);
        log
                .debug("original class [{}], wrapped with [{}]", clazz,
                        wrappedClass);
        return wrappedClass;
    }

    private static Object getNumberValue(Object raw, Class clazz)
            throws SecurityException, NoSuchMethodException,
            IllegalArgumentException, InstantiationException,
            IllegalAccessException, InvocationTargetException {
        String tmp = getString(raw);
        if (tmp.length() == 0) {
            return null;
        } else {
            Constructor constructor = clazz.getConstructor(new Class[] { java.lang.String.class });
            Object value = constructor.newInstance(new Object[] { tmp });
            return value;
        }
    }

    private static String getString(Object o) {
        return String.valueOf(o).trim();
    }

    /***
     * List 转为 JSON
     * @param list
     * @return
     */
    public static <T> String list2Json(List<T> list) {
        if(null != list && list.size() > 0){
            JSONArray jsonArray = JSONArray.fromObject(list);
            return jsonArray.toString();
        }
        return "";
    }

    /**
     * JSON 转换为 List
     * @param jsonStr
     * @param objectClass
     * @param <T>
     * @return
     */
    public static <T> List<T> json2List(String jsonStr, Class<T> objectClass){
        if (StringUtil.isNotBlank(jsonStr)) {
            JSONArray jsonArray = JSONArray.fromObject(jsonStr);
            List<T> list = (List<T>) JSONArray.toCollection(jsonArray, objectClass);
            return list;
        }
        return null;
    }

    /***
     * Object 转为  JSON
     * @param object
     * @return
     */
    public static String object2Json(Object object) {
        if(null != object){
            JSONArray jsonArray = JSONArray.fromObject(object);
            return jsonArray.toString();
        }
        return "";
    }

    /***
     *
     * JSON 转 Object
     *
     * @param jsonStr
     *         [{"age":12,"createTime":null,"id":"","name":"wxw","registerTime":null,"sex":1}]
     * @param objectClass
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T> T json2Ojbect(String jsonStr,  Class<T> objectClass){
        if(null != jsonStr){
            String leftStr = jsonStr.substring(0,2);
            String rightStr = jsonStr.substring(jsonStr.length()-2,jsonStr.length());
            if(leftStr.equals("[{")){
                jsonStr = jsonStr.substring(1,jsonStr.length());
            }
            if(rightStr.equals("}]")){
                jsonStr = jsonStr.substring(0,jsonStr.length()-1);
            }
            JSONObject jsonStu = JSONObject.fromObject(jsonStr);
            return (T) JSONObject.toBean(jsonStu,objectClass);
        }
        return null;
    }

    /***
     * JsonArray 转为 JSON
     * @param jsonArray
     * @return
     */
    public static String jsonArrayToJSONString(JSONArray jsonArray) {
        if(null != jsonArray){
            return jsonArray.toString();
        }
        return "";
    }

    /***
     * JsonObject 转为  JSON
     * @param jsonObject
     * @return
     */
    public static String jsonObjectToJSONString(JSONObject jsonObject) {
        if(null != jsonObject){
            return jsonObject.toString();
        }
        return "";
    }

    /***
     * 将Object转换为JsonObject
     * @param object
     * @return
     */
    public static JSONObject object2JsonObject(Object object) {
        if(null != object){
            return JSONObject.fromObject(object);
        }
        return null;
    }

    /***
     * XML 转为 JSON
     * @param xmlString
     *             XML字符串  例如：
     *                     <?xml version='1.0' encoding='utf-8'?><cities><province name='北京'><item>东城区</item><item>西城区</item><item>崇文区</item><item>宣武区</item><item>朝阳区</item><item>丰台区</item><item>石景山区</item><item>海淀区</item><item>门头沟区</item><item>房山区</item><item>通州区</item><item>顺义区</item><item>昌平区</item><item>大兴区</item><item>怀柔区</item><item>平谷区</item><item>密云县</item><item>延庆县</item></province></cities>
     * @return
     *
     */
    public static String xml2json(String xmlString){
        if(StringUtil.isNotBlank(xmlString)){
            try {
                return xmlserializer.read(xmlString).toString();
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
        return null;
    }

    /***
     * JSON 转为      XML
     * @param jsonStr
     *             XML字符串  例如：
     *                     [{'province':{'@name':'北京','item':['东城区','西城区','崇文区','宣武区','朝阳区','丰台区','石景山区','海淀区','门头沟区','房山区','通州区','顺义区','昌平区','大兴区','怀柔区','平谷区','密云县','延庆县']}}]
     *                  或者：
     *                  {'province':{'@name':'北京','item':['东城区','西城区','崇文区','宣武区','朝阳区','丰台区','石景山区','海淀区','门头沟区','房山区','通州区','顺义区','昌平区','大兴区','怀柔区','平谷区','密云县','延庆县']}}
     * @return
     *
     */
    public static String json2xml(String jsonStr){
        if(StringUtil.isNotBlank(jsonStr)){
            try {
                if(jsonStr.contains("[{") && jsonStr.contains("}]")){
                    JSONArray jobj = JSONArray.fromObject(jsonStr);
                    return xmlserializer.write(jobj);
                }
                JSONObject jobj = JSONObject.fromObject(jsonStr);
                return xmlserializer.write(jobj);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
        return null;
    }

    /***
     * XML/JSON 互转
     *
     * @param sourceFilePath
     *                 要解析的文件路径
     * @param directFilePath
     *              生成文件存放的路径
     * @param flag
     *             true:JSON 转为 XML
     *             false:XML转为 JSON
     * @return
     */
    public static String xml2JsonOrjson2Xml(String sourceFilePath,String directFilePath,boolean flag){
        if(StringUtil.isBlank(sourceFilePath) || StringUtil.isBlank(directFilePath)){
            return null;
        }
        FileInputStream in =null;
        BufferedReader br = null;
        FileWriter fw = null;
        String rs = null;
        try{
            File jsonFile = new File(sourceFilePath);
            in = new FileInputStream(jsonFile);
            StringBuffer sbuf = new StringBuffer();
            br = new BufferedReader(new InputStreamReader(in));
            String temp =null;

            while((temp=br.readLine())!=null){
                sbuf.append(temp);
            }
            if(flag){
                rs  = json2xml(sbuf.toString());
            }else{
                rs  = xml2json(sbuf.toString());
            }
            File test = new File(directFilePath);
            if(!test.exists()){
                test.createNewFile();
            }
            fw = new FileWriter(test);
            fw.write(rs);
        }catch (Exception e) {
            e.printStackTrace();
        }finally{
            try {
                fw.close();
                br.close();
                in.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return rs;
    }

    public static void main(String[] args)
    {
        String json = "[]";

    }
}

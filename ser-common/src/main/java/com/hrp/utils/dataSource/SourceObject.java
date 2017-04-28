package com.hrp.utils.dataSource;

import com.hrp.utils.lang.StringUtil;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * SourceObject
 *
 * @author KVLT
 * @date 2017-03-14.
 */
public class SourceObject implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 6038947396704594858L;

    private final static String TARGET_PACKAGE = "com.fbm.entity";

    public String[] getTargetObjectNames() {
        Class<? extends SourceObject> c = this.getClass();
        ConvertObject convertObject = null;
        convertObject = c.getAnnotation(ConvertObject.class);
        if (convertObject == null) {
            System.err.println("没有配置需要转换的目标对象!");
            return null;
        }
        String names = convertObject.name();
        if (names == null || "".equals(names)) {
            return null;
        }

        String[] targetObjectNames = names.split(",");
        return targetObjectNames;
    }

    public TargetObject getTargetObject() {
        return getTargetObject(null);
    }

    public TargetObject getTargetObject(String targetObjectName) {
        TargetObject targetObject = null;
        Class<? extends SourceObject> c = this.getClass();
        ConvertObject convertObject = null;
        convertObject = c.getAnnotation(ConvertObject.class);
        if (convertObject == null) {
            System.err.println("没有配置需要转换的目标对象!");
            return null;
        }
        String names = convertObject.name();
        if (names == null || "".equals(names)) {
            return null;
        }
        String[] targetObjectNames = names.split(",");
        if (targetObjectNames == null || targetObjectNames.length == 0) {
            return null;
        }
        if (targetObjectName == null) {
            targetObjectName = targetObjectNames[0];
        }
        Class<? extends TargetObject> targetObjectC = null;
        try {
            targetObjectC = (Class<? extends TargetObject>) Class.forName(TARGET_PACKAGE + "." + targetObjectName);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        if (targetObjectC == null) {
            System.err.println("配置需要转换的目标对象不存在：" + targetObjectName + "!");
            return null;
        }

        try {
            targetObject = targetObjectC.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("配置需要转换的目标对象不存在：" + targetObjectName + "!");
            return null;
        }
        convert(null, this, targetObject);
        return targetObject;
    }

    public void convert(Map<String, String> sourceMap, SourceObject sourceObject, TargetObject targetObject) {
        if(sourceMap==null) {
            sourceMap = new HashMap<String, String>();
        }
        String scName = sourceObject.getClass().getName();
        if(sourceMap.get(scName)!=null) {
            return;
        }
        sourceMap.put(scName, "");

        String targetObjectName = targetObject.getClass().getSimpleName();

        Class<?> supperC = sourceObject.getClass();
        while(true) {
            if(supperC.getSimpleName().equals(SourceObject.class.getSimpleName())||supperC.getSimpleName().equals(Object.class.getSimpleName())) {
                break;
            }
            for(Field field : supperC.getDeclaredFields()) {
                field.setAccessible(true);
                Object sourceValue = null;
                try {
                    sourceValue = field.get(sourceObject);
                } catch(Exception e) {
                    e.printStackTrace();
                }
                if(sourceValue==null) {
                    continue;
                }
                ConvertAttribute converAttribute = field.getAnnotation(ConvertAttribute.class);
                if(converAttribute!=null) {
                    String type = converAttribute.type();

                    String name = converAttribute.name();
                    if(name.indexOf(".")>=0) {
                        if(name.startsWith(targetObjectName+".")) {
                            name =  name.replace(targetObjectName+".", "");
                        } else {
                            continue;
                        }
                    }
                    Method m = null;
                    try {
                        Object value = null;
                        value = sourceValue;
                        if("class".equals(type)) {
                            String targetName = converAttribute.target();
                            if(value instanceof List) {
                                List<SourceObject> soList = (List<SourceObject>) value;
                                List<TargetObject> toList = new ArrayList<TargetObject>();
                                for(int i=0;i<soList.size();i++) {
                                    toList.add(soList.get(i).getTargetObject(targetName));
                                }
                                value = toList;
                                m = targetObject.getClass().getMethod("set"+StringUtil.getSetMethod(name), List.class);
                            } else {
                                SourceObject so = (SourceObject) sourceValue;
                                value = so.getTargetObject(targetName);
                                m = targetObject.getClass().getMethod("set"+StringUtil.getSetMethod(name), value.getClass());
                            }
                        } else if("field".equals(type)) {
                            value = sourceValue;
                            m = targetObject.getClass().getMethod("set"+ StringUtil.getSetMethod(name), value.getClass());
                        }
                        m.invoke(targetObject, value);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }


                } else {
                    if(sourceValue instanceof SourceObject) {
                        convert(sourceMap, (SourceObject) sourceValue, targetObject);
                    } else if(sourceValue instanceof List) {
                        List<SourceObject> ls = (List<SourceObject>) sourceValue;
                        if(ls.size()>0) {
                            convert(sourceMap, ls.get(0), targetObject);
                        }
                    }
                }

            }

            supperC = supperC.getSuperclass();

        }
    }
}

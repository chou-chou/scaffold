package com.hrp.utils;

import com.hrp.utils.lang.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * ReflectUtil
 * 反射工具
 * @author KVLT
 * @date 2017-03-14.
 */
public class ReflectUtil {

    private static final Logger logger = LoggerFactory.getLogger(ReflectUtil.class);

    /**
     * 缓存方法
     */
    private static final Map<Class<?>, Method[]> METHODS_CACHEMAP = new HashMap<Class<?>, Method[]>();

    /**
     * 反射 取值、设值,合并两个对象(Field same only )
     *
     * @param fromobj
     * @param toobj
     */
    public static <T> void copyProperties(T fromobj, T toobj, String... fieldspec) {
        for (String filename : fieldspec) {
            Object val = ReflectUtil.invokeGetterMethod(fromobj, filename);
            ReflectUtil.invokeSetterMethod(toobj, filename, val);
        }
    }

    /**
     * 调用Getter方法
     *
     * @param obj
     *            对象
     * @param propertyName
     *            属性名
     * @return
     */
    public static Object invokeGetterMethod(Object obj, String propertyName) {
        String getterMethodName = "get" + StringUtil.capitalize(propertyName);
        return invokeMethod(obj, getterMethodName, null, null);
    }

    /**
     * 调用Setter方法,不指定参数的类型
     *
     * @param obj
     * @param propertyName
     * @param value
     */
    public static void invokeSetterMethod(Object obj, String propertyName, Object value) {
        invokeSetterMethod(obj, propertyName, value, null);
    }

    /**
     * 调用Setter方法,指定参数的类型
     *
     * @param obj
     * @param propertyName  字段名
     * @param value
     * @param propertyType
     *            为空，则取value的Class
     */
    public static void invokeSetterMethod(Object obj, String propertyName,
                                          Object value, Class<?> propertyType) {
        value = handleValueType(obj,propertyName,value);
        propertyType = propertyType != null ? propertyType : value.getClass();
        String setterMethodName = "set" + StringUtil.capitalize(propertyName);
        invokeMethod(obj, setterMethodName, new Class<?>[] { propertyType },
                new Object[] { value });
    }

    private static Object handleValueType(Object obj, String propertyName,
                                          Object value){
        String getterMethodName = "get" + StringUtil.capitalize(propertyName);
        Class<?> argsType = value.getClass();;
        Class<?> returnType = obtainAccessibleMethod(obj, getterMethodName).getReturnType();
        if(argsType == returnType){
            return value;
        }

        if (returnType == Boolean.class) {
            String temp = value.toString();
            value = (StringUtil.isNotBlank(temp) && Long.valueOf(temp) > 0) ? true : false;
        } else if (returnType == Long.class) {
            value = Long.valueOf(value.toString());
        }else if(returnType == Date.class){
            try {
                value = DateUtil.parse(value.toString(), DateUtil.formatStr_yyyyMMddHHmmss);
            } catch (ParseException e) {
                logger.error("类型转型Timpestap-->Date时，发生错误! " + e.getMessage() + "("+value.toString()+")");
            }
        } else if (returnType == Short.class) {
            value = Short.valueOf(value.toString());
        } else if (returnType == BigDecimal.class) {
            value = BigDecimal.valueOf(Long.valueOf(value.toString()));
        } else if (returnType == BigInteger.class) {
            value = BigInteger.valueOf(Long.valueOf(value.toString()));
        } else if(returnType == String.class){
            value = String.valueOf(value);
        }else if(returnType == Integer.class){
            value = Integer.valueOf(value.toString());
        }
        return value;
    }

    /**
     * 直接调用对象方法，忽视private/protected修饰符
     *
     * @param obj
     * @param methodName
     * @param parameterTypes
     * @param params
     * @return
     */
    public static Object invokeMethod(final Object obj, final String methodName, final Class<?>[] parameterTypes, final Object[] params) {
        Method method = obtainAccessibleMethod(obj, methodName, parameterTypes);
        if (method == null) { throw new IllegalArgumentException(
                "Devkit: Could not find method [" + methodName
                        + "] on target [" + obj + "]."); }
        try {
            return method.invoke(obj, params);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 循环向上转型，获取对象的DeclaredMethod,并强制设置为可访问 如向上转型到Object仍无法找到，返回null
     *
     * 用于方法需要被多次调用的情况，先使用本函数先取得Method,然后调用Method.invoke(Object obj,Object...
     * args)
     *
     * @param obj
     * @param methodName
     * @param parameterTypes
     * @return
     */
    public static Method obtainAccessibleMethod(final Object obj,
                                                final String methodName, final Class<?>... parameterTypes) {
        Class<?> superClass = obj.getClass();
        Class<Object> objClass = Object.class;
        for (; superClass != objClass; superClass = superClass.getSuperclass()) {
            Method method = null;
            try {
                method = superClass.getDeclaredMethod(methodName,
                        parameterTypes);
                method.setAccessible(true);
                return method;
            } catch (NoSuchMethodException e) {
                // Method不在当前类定义，向上转型
            } catch (SecurityException e) {
                // Method不在当前类定义，向上转型
            }
        }
        return null;
    }

    /**
     * 不能确定方法是否包含参数时，通过方法名匹配获得方法
     *
     * @param obj
     * @param methodName
     * @return
     */
    public static Method obtainMethod(final Object obj, final String methodName) {
        Class<?> clazz = obj.getClass();
        Method[] methods = METHODS_CACHEMAP.get(clazz);
        if (methods == null) { // 尚未缓存
            methods = clazz.getDeclaredMethods();
            METHODS_CACHEMAP.put(clazz, methods);
        }
        for (Method method : methods) {
            if (method.getName().equals(methodName))
                return method;
        }
        return null;

    }

    /**
     * 直接读取对象属性值 忽视private/protected修饰符，不经过getter函数
     *
     * @param obj
     * @param fieldName
     * @return
     */
    public static Object obtainFieldValue(final Object obj,
                                          final String fieldName) {
        Field field = obtainAccessibleField(obj, fieldName);
        if (field == null) { throw new IllegalArgumentException(
                "Devkit: could not find field [" + fieldName + "] on target ["
                        + obj + "]"); }
        Object retval = null;
        try {
            retval = field.get(obj);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return retval;

    }

    /**
     * 直接设置对象属性值 忽视private/protected修饰符，不经过setter函数
     *
     * @param obj
     * @param fieldName
     * @param value
     */
    public static void setFieldValue(final Object obj, final String fieldName,
                                     final Object value) {
        Field field = obtainAccessibleField(obj, fieldName);
        if (field == null) { throw new IllegalArgumentException(
                "Devkit: could not find field [" + fieldName + "] on target ["
                        + obj + "]"); }
        try {
            field.set(obj, value);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    /**
     * 循环向上转型，获取对象的DeclaredField,并强制设为可访问 如向上转型Object仍无法找到，返回null
     *
     * @param obj
     * @param fieldName
     * @return
     */
    public static Field obtainAccessibleField(final Object obj, final String fieldName) {
        Class<?> superClass = obj.getClass();
        Class<Object> objClass = Object.class;
        for (; superClass != objClass; superClass = superClass.getSuperclass()) {
            try {
                Field field = superClass.getDeclaredField(fieldName);
                field.setAccessible(true);
                return field;
            } catch (NoSuchFieldException e) {
                // Field不在当前类定义，向上转型
            } catch (SecurityException e) {
                // Field不在当前类定义，向上转型
            }
        }
        return null;
    }

    /**
     * 获取obj对象fieldName的Field
     * @param obj
     * @param fieldName
     * @return
     */
    public static Field getFieldByFieldName(Object obj, String fieldName) {
        for (Class<?> superClass = obj.getClass(); superClass != Object.class; superClass = superClass
                .getSuperclass()) {
            try {
                return superClass.getDeclaredField(fieldName);
            } catch (NoSuchFieldException e) {
            }
        }
        return null;
    }

    /**
     * 获取obj对象fieldName的属性值
     * @param obj
     * @param fieldName
     * @return
     * @throws SecurityException
     * @throws NoSuchFieldException
     * @throws IllegalArgumentException
     * @throws IllegalAccessException
     */
    public static Object getValueByFieldName(Object obj, String fieldName)
            throws SecurityException, NoSuchFieldException,
            IllegalArgumentException, IllegalAccessException {
        Field field = getFieldByFieldName(obj, fieldName);
        Object value = null;
        if(field!=null){
            if (field.isAccessible()) {
                value = field.get(obj);
            } else {
                field.setAccessible(true);
                value = field.get(obj);
                field.setAccessible(false);
            }
        }
        return value;
    }

    /**
     * 设置obj对象fieldName的属性值
     * @param obj
     * @param fieldName
     * @param value
     * @throws SecurityException
     * @throws NoSuchFieldException
     * @throws IllegalArgumentException
     * @throws IllegalAccessException
     */
    public static void setValueByFieldName(Object obj, String fieldName,
                                           Object value) throws SecurityException, NoSuchFieldException,
            IllegalArgumentException, IllegalAccessException {
        Field field = obj.getClass().getDeclaredField(fieldName);
        if (field.isAccessible()) {
            field.set(obj, value);
        } else {
            field.setAccessible(true);
            field.set(obj, value);
            field.setAccessible(false);
        }
    }

    public static void main(String[] args) throws Exception {

    }
}

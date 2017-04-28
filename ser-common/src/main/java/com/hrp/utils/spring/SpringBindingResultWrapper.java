package com.hrp.utils.spring;

import java.util.List;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

/**
 * SpringBindingResultWrapper
 * 后台校验的处理类
 * @author KVLT
 * @date 2017-03-26.
 */
public class SpringBindingResultWrapper {
    /**
     * 后台错误处理的方法
     */
    public static String warpErrors(final BindingResult errors) {
        // 再次确认错误
        if (errors.hasErrors()) {
            String errorMsg = "";
            // 获取错误集合
            List<FieldError> fieldErrors = errors.getFieldErrors();
            // 获取有多少个错误
            int length = fieldErrors.size();
            // 循环错误，倒着取值
            for (int i = length - 1; i >= 0; i--) {
                // 获取一个错误
                FieldError error = fieldErrors.get(i);
                // 判断是否有汉字，有才拼接，避免抛出的异常
                if (gbk(error.getDefaultMessage() + "")) {
                    if (i != 0) {
                        // 不是左后一个错误，需要拼接换行符
                        errorMsg = errorMsg + error.getDefaultMessage() + "<br />&nbsp;&nbsp;&nbsp;";
                    } else {
                        // 如果是最后一个错误就不用拼接换行符
                        errorMsg = errorMsg + error.getDefaultMessage();
                    }
                }
            }
            return errorMsg;
        } else {
            return "";
        }
    }

    /**
     * 判断字符串是否有汉字
     */
    @SuppressWarnings("finally")
    private static boolean gbk(final String str) {
        boolean flag = false;
        try {
            char[] chars = str.toCharArray();
            for (int i = 0; i < chars.length; i++) {
                // gbk编码
                byte[] bytes = ("" + chars[i]).getBytes("GBK");
                if (bytes.length == 2) {
                    int[] ints = new int[2];
                    ints[0] = bytes[0] & 0xff;
                    ints[1] = bytes[1] & 0xff;
                    if (ints[0] >= 0x81 && ints[0] <= 0xFE && ints[1] >= 0x40 && ints[1] <= 0xFE) {
                        flag = true;
                        // 有汉字就跳出
                        break;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return flag;
        }
    }
}

package com.hrp.utils.sql;

import com.hrp.utils.lang.StringUtil;

/**
 * SqlUtil4MS
 * mybatis中like查询时，需用该方法处理字符，防止sql注入
 * sql server 2008测试有效
 * @author KVLT
 * @date 2017-04-26.
 */
public class SqlUtil4MS {

    private static final String H = "#";
    private static final String S = "$";

    /**
     * mapper.xml中的取值方式为#{}时
     * @param str like的查询条件
     * @return
     */
    public static String likeEscapeH(String str) {
        return likeEscapeZ(str, H, true, true);
    }

    /**
     * mapper.xml中的取值方式为${}时
     * @param str like的查询条件
     * @return
     */
    public static String likeEscapeS(String str) {
        return likeEscapeZ(str, S, true, true);
    }

    /**
     * @param str   like的查询条件
     * @param type  mapper.xml中的取值方式，只能“#”或“$”
     * @param start 字符串前部是否拼接“%”
     * @param end   字符串尾部是否拼接“%”
     * @return
     */
    public static String likeEscapeZ(String str, String type, boolean start, boolean end) {
        if (StringUtil.isBlank(str)) {
            return null;
        }
        StringBuffer buffer = new StringBuffer();
        // 拼接顺序不能改变
        if (S.equals(type)) {
            buffer.append(" '");
        }
        if (start) {
            buffer.append("%");
        }
        int len = str.length();
        //注意："]"不能处理
        for (int i = 0; i < len; i++) {
            char c = str.charAt(i);
            switch (c) {
                case '\'':
                    if (S.equals(type)) {
                        buffer.append("''");// 单引号替换成两个单引号
                    } else {
                        buffer.append(c);
                    }
                    break;
                case '[':
                    buffer.append("[[]");
                    break;
                case '_':
                    buffer.append("[_]");
                    break;
                case '%':
                    buffer.append("[%]");
                    break;
                case '^':
                    buffer.append("[^]");
                    break;
                case '!':
                    buffer.append("[!]");
                    break;
                default:
                    buffer.append(c);
            }
        }

        if (end) {
            buffer.append("%");
        }
        if (S.equals(type)) {
            buffer.append("' ");
        }
        return buffer.toString();
    }

    public static void main(String[] args) {
        String str = "aaaa]p'100%_a[[][][]][[][]]df[]dfaf]";
        System.out.println("result#: " + likeEscapeH(str));
        System.out.println("result$: " + likeEscapeS(str));
    }

}

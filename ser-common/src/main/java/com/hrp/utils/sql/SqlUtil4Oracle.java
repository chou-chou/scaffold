package com.hrp.utils.sql;

import com.hrp.utils.lang.StringUtil;

/**
 * SqlUtil4Oracle
 * mybatis中like查询用$取值时，需用该方法处理字符，防止sql注入
 * oracle 适用
 * @author KVLT
 * @date 2017-04-26.
 */
public class SqlUtil4Oracle {
    /**
     * mapper.xml中的取值方式为${}时
     *
     * @param str
     *            like的查询条件
     * @return
     */
    public static String likeEscape(String str) {
        return likeEscape(str, true, true);
    }

    /**
     * @param str
     *            like的查询条件
     * @param start
     *            字符串前部是否拼接“%”
     * @param end
     *            字符串尾部是否拼接“%”
     * @return
     */
    public static String likeEscape(String str, boolean start, boolean end) {
        if (StringUtil.isBlank(str)) {
            return null;
        }
        StringBuilder buf = new StringBuilder();
        // 拼接顺序不能改变
        buf.append(" '");
        if (start) {
            buf.append("%");
        }
        boolean flag = false;
        int len = str.length();
        for (int i = 0; i < len; i++) {
            char c = str.charAt(i);
            switch (c) {
                case '\'':
                    buf.append("''");// 单引号替换成两个单引号
                    break;
                case '_':
                    buf.append("\\_");
                    flag = true;
                    break;
                case '%':
                    buf.append("\\%");
                    flag = true;
                    break;
                default:
                    buf.append(c);
            }
        }

        if (end) {
            buf.append("%");
        }
        buf.append("' ");
        if (flag) {
            buf.append("escape '\\' ");
        }
        return buf.toString();
    }

    public static void main(String[] args) {
        String str = "aaaa]p'100%_a[[][][]][[][]]df[]dfaf]!bbb^";
        System.out.println("result$: " + likeEscape(str));
    }

}

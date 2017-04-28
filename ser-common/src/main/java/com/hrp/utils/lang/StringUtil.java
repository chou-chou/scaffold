package com.hrp.utils.lang;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang3.StringEscapeUtils;

import java.beans.XMLDecoder;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * StringUtil
 *
 * @author KVLT
 * @date 2017-03-14.
 */
public class StringUtil extends org.apache.commons.lang.StringUtils {

    private StringBuffer sb = null;
    private String sysLineSeparator = null;

    private static Pattern numericPattern = Pattern.compile("^[0-9\\-]+$");
    private static Pattern numericStringPattern = Pattern
            .compile("^[0-9\\-\\-]+$");
    private static Pattern floatNumericPattern = Pattern
            .compile("^[0-9\\-\\.]+$");
    private static Pattern abcPattern = Pattern.compile("^[a-z|A-Z]+$");
    public static final String splitStrPattern = ",|，|;|；|、|\\.|。|-|_|\\(|\\)|\\[|\\]|\\{|\\}|\\\\|/| |　|\"";

    public StringUtil() {
        if (sb != null) {
            sb = null;
        } else {
            sb = new StringBuffer();
        }
        sysLineSeparator = System.getProperty("line.separator");
    }

    /**
     * 输入指定字串返回非空字符
     *
     * @param str
     *            指定字串
     * @return 返回类型 String 返回非空字符
     *
     */
    public static String requote(String str) {
        if (str == null)
            str = "";
        if ("null".equalsIgnoreCase(str))
            str = "";
        return str;
    }

    public static String objectToString(Object objectStr) {
        if (objectStr == null)
            objectStr = "";
        if ("null".equalsIgnoreCase(String.valueOf(objectStr)))
            objectStr = "";
        return String.valueOf(objectStr);
    }

    public static String objectToString(Object objectStr, String e) {
        if (objectStr == null)
            objectStr = e;
        if ("null".equalsIgnoreCase(String.valueOf(objectStr)))
            objectStr = e;
        return String.valueOf(objectStr);
    }

    /**
     * 不区分大小写对比两个字符串
     * @param srcStr
     * @param destSrc
     * @return
     */
    public static boolean equalsIgnoreCase(String srcStr, String destSrc) {
        if (isNotEmpty(srcStr) && isNotEmpty(destSrc)) {
            if (srcStr.equalsIgnoreCase(destSrc))   return true;
            else    return false;
        } else {
            return false;
        }
    }

    /**
     * 非空返回false
     * @param str
     * @return
     */
    public static boolean isBlankOrNull(String str) {
        return "".equals(requote(str)) ? true : false;
    }
    public static boolean isNotBlank(String str) {
        return !isBlank(str);
    }

    /**
     * 对象为null
     * @param obj
     * @return
     */
    public static boolean isNull(Object obj) {
        return null == obj ? true : false;
    }

    /**
     * 非""返回TRUE
     * @param str
     * @return
     */
    public static boolean isBlank(String str) {
        int strLen;
        if ((str == null) || ((strLen = str.length()) == 0))
            return true;
        for (int i = 0; i < strLen; i++)
            if (!Character.isWhitespace(str.charAt(i)))
                return false;
        return true;
    }

    /**
     * 字符str拼接num次
     * @param str
     * @param num
     * @return
     */
    public static String getRepeatedStr(String str, int num){
        String strs="";
        if(num==0)
            return strs;
        for(int i=0;i<num;i++){
            strs+=str;
        }
        return strs;
    }

    /**
     * GBK编码的字符串转为ISO885_1
     * @param str   GBK编码的字符
     * @return      返回类型 String 转化后的ISO885_1字符串
     * @throws Exception
     */
    public static String GBKToISO(String str) throws Exception {
        if (str != null) {
            return new String(str.getBytes("GBK"), "ISO8859_1");
        } else {
            return "";
        }
    }

    /**
     * ISO8859_1编码的字符串转为GBK
     * @param str   GBK编码的字符
     * @return      返回类型 String 转化后的GBK字符串
     * @throws Exception
     */
    public static String ISOToGBK(String str) throws Exception {
        if (str != null) {
            return new String(str.getBytes("ISO8859_1"), "GBK");
        } else {
            return "";
        }
    }

    /**
     * GB编码的字符串转为UTF8
     * @param src   GB编码的字符
     * @return      返回类型 String 转化后的UTF8字符串
     */
    public static String gbToUtf8(String src) {
        byte[] b = src.getBytes();
        char[] c = new char[b.length];
        for (int i = 0; i < b.length; i++) {
            c[i] = (char) (b[i] & 0x00FF);
        }
        return new String(c);
    }

    /**
     * 将指定对象转化为非空String，如果为空返回“0”
     * @param obj   指定对象
     * @return      返回类型 String 非空String
     */
    public static String null2Zero(Object obj) {

        if ((obj == null) || (obj.equals("")) || (obj.equals("null"))) {
            return "0";
        } else {
            return obj.toString().trim();
        }
    }

    /**
     * 将指定对象转化为非空String，如果为空返回“”
     *
     * @param obj
     *            指定对象
     * @return 返回类型 String 非空String
     *
     */
    public static String null2Str(Object obj) {
        if ((obj == null) || (obj.equals("null")) || (obj.equals(""))) {
            return "";
        } else {
            return obj.toString().trim();
        }
    }

    /**
     * 将一个字符串按给定分割字符分割成数组
     *
     * @param source
     *            字符串
     * @param useChar
     *            分割字符
     * @return 返回类型 String[] 数组字符串
     *
     */
    public static String[] split(String source, char useChar) {
        List list = new ArrayList();
        String sub;
        String[] result;

        if (source.charAt(0) == useChar)
            source = source.substring(1, source.length());
        if (source.charAt(source.length() - 1) == useChar)
            source = source.substring(0, source.length() - 1);

        int start = 0;
        int end = source.indexOf(useChar);
        while (end > 0) {
            sub = source.substring(start, end);
            list.add(sub);
            start = end + 1;
            end = source.indexOf(useChar, start);
        }

        sub = source.substring(start, source.length());
        list.add(sub);

        result = new String[list.size()];

        Iterator iter = list.iterator();
        int i = 0;
        while (iter.hasNext()) {
            result[i++] = (String) iter.next();
        }
        return result;
    }

    /**
     * 将一个字符串按给定分割字符分割成List
     *
     * @param source
     *            字符串
     * @param useChar
     *            分割字符
     * @return 返回类型 List 分割成的List
     *
     */
    public static List splitList(String source, char useChar) {
        List list = new ArrayList();
        String sub;

        if (source.charAt(0) == useChar)
            source = source.substring(1, source.length());
        if (source.charAt(source.length() - 1) == useChar)
            source = source.substring(0, source.length() - 1);

        int start = 0;
        int end = source.indexOf(useChar);
        while (end > 0) {
            sub = source.substring(start, end);
            list.add(sub);
            start = end + 1;
            end = source.indexOf(useChar, start);
        }

        sub = source.substring(start, source.length());
        list.add(sub);

        return list;
    }

    /**
     * 判断给定子字符串<code>subStr</code>是否在大字符�?<code>str</code>
     *
     * @param subStr
     *            子字符串
     * @param str
     *            大字符串
     * @return 返回类型 如果存在则返回true,否则返回false
     */
    public static boolean isIn(String subStr, String str) {
        if (subStr == null || str == null) {
            return false;
        }
        if (str.indexOf(subStr) == -1) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * 将数组中的每一项用“，”连接起来
     * @param str   数组
     * @return 返回类型 String 字符串
     */
    public static String join(String[] str) {
        return join(str, ",");
    }

    /**
     * 将数组中的每一项用指定字符串连接起来
     * @param str
     *            数组
     * @param join
     *            指定字符串
     * @return 返回类型 String 字符串
     */
    public static String join(String[] str, String join) {
        if (str == null || join == null) {
            return "";
        }
        String rtnStr = "";
        for (int i = 0; i < str.length; i++) {
            rtnStr += join + str[i];
        }
        if (rtnStr.indexOf(join) != -1) {
            rtnStr = rtnStr.substring(join.length());
        }
        return rtnStr;
    }

    /**
     * 将set集合中的每一项用指定字符串连接起来
     * @param set   set集合
     * @param join  连接字符串
     * @return
     */
    public static String join(Set<String> set, String join) {
        String[] str = set.toArray(new String[0]);
        return join(str, join);
    }

    public static String join(List list, String join) {
        Object[] str = list.toArray(new Object[0]);

        if (str == null || join == null) {
            return "";
        }
        String rtnStr = "";
        for (int i = 0; i < str.length; i++) {
            rtnStr += join + str[i].toString();
        }
        if (rtnStr.indexOf(join) != -1) {
            rtnStr = rtnStr.substring(join.length());
        }
        return rtnStr;
    }

    /**
     * 字符串替换
     *
     * @param line
     *            源字符串
     * @param oldString
     *            将要被替换掉的子字符串
     * @param newString
     *            将要用来替换旧子字符串的字符串
     * @return 返回类型 String 返回替换后的结果字符串
     *
     */
    public static final String replace(String line, String oldString,
                                       String newString) {
        if (line == null) {
            return null;
        }
        int i = 0;
        if ((i = line.indexOf(oldString, i)) >= 0) {
            char[] line2 = line.toCharArray();
            char[] newString2 = newString.toCharArray();
            int oLength = oldString.length();
            StringBuffer buf = new StringBuffer(line2.length);
            buf.append(line2, 0, i).append(newString2);
            i += oLength;
            int j = i;
            while ((i = line.indexOf(oldString, i)) > 0) {
                buf.append(line2, j, i - j).append(newString2);
                i += oLength;
                j = i;
            }
            buf.append(line2, j, line2.length - j);
            return buf.toString();
        }
        return line;
    }

    /**
     * 字符串替换，忽略大小写
     *
     * @param line
     *            源字符串
     * @param oldString
     *            将要被替换掉的子字符串
     * @param newString
     *            将要用来替换旧子字符串的字符串
     * @return 返回类型 String 返回替换后的结果字符串
     *
     */
    public static final String replaceIgnoreCase(String line, String oldString,
                                                 String newString) {
        if (line == null) {
            return null;
        }
        String lcLine = line.toLowerCase();
        String lcOldString = oldString.toLowerCase();
        int i = 0;
        if ((i = lcLine.indexOf(lcOldString, i)) >= 0) {
            char[] line2 = line.toCharArray();
            char[] newString2 = newString.toCharArray();
            int oLength = oldString.length();
            StringBuffer buf = new StringBuffer(line2.length);
            buf.append(line2, 0, i).append(newString2);
            i += oLength;
            int j = i;
            while ((i = lcLine.indexOf(lcOldString, i)) > 0) {
                buf.append(line2, j, i - j).append(newString2);
                i += oLength;
                j = i;
            }
            buf.append(line2, j, line2.length - j);
            return buf.toString();
        }
        return line;
    }

    /**
     * 字符串替换，并且统计替换的个数
     *
     * @param line
     *            源字符串
     * @param oldString
     *            将要被替换掉的子字符串
     * @param newString
     *            将要用来替换旧子字符串的字符串
     * @param count
     *            统计替换的个数
     * @return 返回类型 String 返回替换后的结果字符串
     *
     */
    public static final String replace(String line, String oldString,
                                       String newString, int[] count) {
        if (line == null) {
            return null;
        }
        int i = 0;
        if ((i = line.indexOf(oldString, i)) >= 0) {
            int counter = 0;
            counter++;
            char[] line2 = line.toCharArray();
            char[] newString2 = newString.toCharArray();
            int oLength = oldString.length();
            StringBuffer buf = new StringBuffer(line2.length);
            buf.append(line2, 0, i).append(newString2);
            i += oLength;
            int j = i;
            while ((i = line.indexOf(oldString, i)) > 0) {
                counter++;
                buf.append(line2, j, i - j).append(newString2);
                i += oLength;
                j = i;
            }
            buf.append(line2, j, line2.length - j);
            count[0] = counter;
            return buf.toString();
        }
        return line;
    }

    /**
     * 将数字型日期转换为带间隔符的字符串型
     *
     * @param date
     *            指定日期
     * @param interval
     *            间隔符
     * @return 返回类型 String 带间隔符的字符串型
     *
     */
    public static final String int2Date(Integer date, String interval) {
        String line = String.valueOf(date);
        if (line.length() != 8) {
            return null;
        } else {
            StringBuffer buf = new StringBuffer(10);
            buf.append(line.substring(0, 4));
            buf.append(interval);
            buf.append(line.subSequence(4, 6));
            buf.append(interval);
            buf.append(line.substring(6, 8));
            return buf.toString();
        }

    }

    /**
     * 将数字型日期转换为带间隔符的字符串型
     *
     * @param date
     *            指定日期
     * @param interval
     *            间隔符
     * @return 返回类型 String 带间隔符的字符串型
     *
     */
    public static final String long2Date(Long date, String interval) {
        String line = String.valueOf(date);
        if (line.length() != 8) {
            return null;
        } else {
            StringBuffer buf = new StringBuffer(10);
            buf.append(line.substring(0, 4));
            buf.append(interval);
            buf.append(line.subSequence(4, 6));
            buf.append(interval);
            buf.append(line.substring(6, 8));
            return buf.toString();
        }

    }

    /**
     * 查一个字符串是否全部为空
     *
     * @param input
     *            字符串
     * @return 返回类型 boolean 如果字符串为空格则返回false,否则返回strTemp.length()!
     *
     */
    public static boolean checkDataValid(String input) {
        String strTemp = new String(input);
        if (strTemp == null || strTemp.length() == 0) {
            return false;
        }
        strTemp = strTemp.trim();
        return strTemp.length() != 0;
    }

    /**
     *
     * 替换输入字符串中的非法字符串
     *
     * @param input
     *            是要替换的字符串
     * @return 返回类型 String 如果字符串为空则返回input,否则返回buf.toString()替换后的字符串
     *
     */
    public static String escapeHTML(String input) {

        if (input == null || input.length() == 0) {
            return input;
        }
        input = input.trim();
        StringBuffer buf = new StringBuffer();
        char ch = ' ';
        for (int i = 0; i < input.length(); i++) {
            ch = input.charAt(i);
            if (ch == '<') {
                buf.append("&lt;");
            } else if (ch == '>') {
                buf.append("&gt;");
            } else if (ch == '\n') {
                buf.append("<br/>");
            } else if (ch == ' ') {
                buf.append("&nbsp;");
            } else if (ch == '\'') {
                buf.append("''");
            } else {
                buf.append(ch);
            }
        } // end for loop
        return buf.toString();
    }

    /**
     * 转换个包含有HTML标志(ie, &lt;b&gt;,&lt;table&gt;, etc)的字符串，将里面�?'><'转换�?'&lt''
     * and '&gt;'
     *
     * @param input
     *            篇用于转换的text
     * @return 返回类型 String 转换后的字符串
     *
     */
    public static final String escapeHTMLTags(String input) {
        if (input == null || input.length() == 0) {
            return input;
        }
        StringBuffer buf = new StringBuffer(input.length());
        char ch = ' ';
        for (int i = 0; i < input.length(); i++) {
            ch = input.charAt(i);
            if (ch == '<') {
                buf.append("&lt;");
            } else if (ch == '>') {
                buf.append("&gt;");
            } else {
                buf.append(ch);
            }
        }
        return buf.toString();
    }

    /**
     * 追加字符串到字符串缓冲器
     *
     * @param str
     *            要追加的字符串
     *
     */
    public void append(String str) {
        sb.append(str);
    }

    /**
     * 追加字符串到字符串缓冲器 并追加一个缺省的换行符\n
     *
     * @param str
     *            要追加的字符串
     *
     */
    public void appendln(String str) {
        appendln(str, false);
    }

    /**
     * 追加字符串到字符串缓冲器 并根据<code>isSysLineSeparator</code>不同追加缺省的换行符\n或系统换行符
     *
     * @param str
     *            要追加的字符串
     * @param useSysLineSeparator
     *            是否追加系统换行符表
     *
     */
    public void appendln(String str, boolean useSysLineSeparator) {
        if (useSysLineSeparator) {
            sb.append(str);
            sb.append(this.sysLineSeparator);
        } else {
            sb.append(str);
            sb.append("\n");
        }
    }

    /**
     * 转换字符串缓冲器中的数据为字符串表示
     *
     * @return 返回类型 String 表示字符串缓冲器中的数据的字符串
     *
     */
    public String toStr() {
        return sb.toString();
    }

    /**
     *
     * 把一篇文档分割成小写的，以 , .\r\n:/\+为分割符的数组
     *
     * @param text
     *            要被分割成数组的文档
     * @return 返回类型 String[] 返回分割成的数组
     *
     */
    public static final String[] toLowerCaseWordArray(String text) {
        if (text == null || text.length() == 0) {
            return new String[0];
        }
        StringTokenizer tokens = new StringTokenizer(text, " ,\r\n.:/\\+");
        String[] words = new String[tokens.countTokens()];
        for (int i = 0; i < words.length; i++) {
            words[i] = tokens.nextToken().toLowerCase();
        }
        return words;
    }

    /**
     * 将对象数组转为字符串数组
     *
     * @param objs
     *            对象数组
     * @return 返回类型 String[] 字符串数组
     *
     */
    public static String[] objectArrayToStringArray(Object[] objs) {
        if (objs == null)
            return null;
        String[] s = new String[objs.length];
        System.arraycopy(objs, 0, s, 0, s.length);
        return s;
    }

    /**
     * 字符串加密
     *
     * @param str
     *            字符串
     * @return 返回类型 String 加密后字符串
     *
     */
    public static final String encrypt(String str) {

        String string_in = "YN8K1JOZVURB3MDETS5GPL27AXWIHQ94C6F0#$_@!~`%^&*()-+=[]{}'|?;:/,<>.\"\\ ";
        String string_out = " @!~`%^&*()-+=[]{}'|?;:/,<>._$#ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789\"\\";
        String outpass = "";
        try {
            if (str != null) {
                int offset = 0;
                Calendar calendar = Calendar.getInstance();
                int ss = calendar.get(Calendar.SECOND);
                offset = ss % 68;
                if (offset > 0)
                    offset = offset - 1;
                outpass = string_in.substring(offset, offset + 1);
                string_in = string_in + string_in;
                string_in = string_in.substring(offset, offset + 69);
                outpass = outpass + translate(str, string_in, string_out);
                outpass = strToAscStr(outpass,"-");
                return outpass;
            } else {
                return "";
            }
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * 简单加解密
     * @Title:
     * @Description: TODO
     * @param @param str
     * @param @return
     * @return 返回类型
     * @throws
     */
    public static final String simpleEncrypt1(String str){
        String in = strToAscStr(str,"-");
        return in;
    }

    public static final String simpleDisencrypt(String str){
        String out = ascToStr(str,"-");
        return out;
    }

    /**
     * 字符串解密
     *
     * @param str
     *            字符串
     * @return 返回类型 String 解密后字符串
     *
     */
    public static final String disencrypt(String str) {
        str = ascToStr(str,"-");
        String string_in = "YN8K1JOZVURB3MDETS5GPL27AXWIHQ94C6F0#$_@!~`%^&*()-+=[]{}'|?;:/,<>.\"\\ ";
        String string_out = " @!~`%^&*()-+=[]{}'|?;:/,<>._$#ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789\"\\";
        try {
            int offset = 0;
            char c = str.charAt(0);
            offset = string_in.indexOf(c);
            string_in = string_in + string_in;
            string_in = string_in.substring(offset, offset + 69);
            String s = str.substring(1);
            s = s.toUpperCase();
            String inpass = translate(s, string_out, string_in);
            return inpass;
        } catch (Exception e) {
            return "";
        }
    }

    /**
     *
     * 取得str字符串转换为大写字母再取出里面的每一个字符，看这个字符在string_in中出现的位置，再取出string_out字符串在这个位置上的每个字符。
     *
     * @param str
     * @param string_in
     * @param string_out
     * @return 返回类型 String 返回转换后的字符串
     */
    private static final String translate(String str, String string_in,
                                          String string_out) {

        String s = str.toUpperCase();
        char[] outc = new char[s.length()];
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            int j = string_in.indexOf(c);
            outc[i] = string_out.charAt(j);

        }
        String outs = new String(outc);

        return outs;
    }

    /**
     * 对字符串进行编码,目的是解决数据库中文问题
     *
     * @param inputStr
     *            指定字符串
     * @return 返回类型 String 返回编码后的字符串
     *
     */
    public static String encode(String inputStr) {

        String tempStr = "";
        try {
            tempStr = new String(inputStr.getBytes("ISO-8859-1"));
        } catch (Exception ex) {
            System.out.print("encode() error: " + ex.toString());
        }
        return tempStr;
    }

    /**
     * 将指定url用GBK编码
     *
     * @param url
     *            指定url
     * @return 返回类型 String 编码后url
     *
     */
    public static String urlEncode(String url) {
        if (url == null || "".equals(url))
            return "";
        try {
            return java.net.URLEncoder.encode(url, "GBK");
        } catch (UnsupportedEncodingException e) {
            return "";
        }
    }

    /**
     * 将指定url用GBK解码
     *
     * @param encodedUrl
     *            指定url
     * @return 返回类型 String 解码后url
     *
     */
    public static String urlDecode(String encodedUrl) {
        if (encodedUrl == null || "".equals(encodedUrl))
            return "";
        try {
            return java.net.URLDecoder.decode(encodedUrl, "GBK");
        } catch (UnsupportedEncodingException e) {
            return "";
        }
    }

    /**
     * 字符串是否为空
     *
     * @param str
     *            字符串
     * @return 返回类型 boolean 为空则为true，否则为false
     *
     */
    public static boolean isEmpty(String str) {
        if (str == null || "".equals(str) ||"".equals(str.trim())||str.equals("null")||str.equals("undefined"))
            return true;
        return false;
    }




    /**
     * 将浮点型小数转换为百分数，小数点后第三位四舍五入
     *
     * @param f
     *            浮点型小数
     * @return 返回类型 String 浮点型小数转换为百分数，小数点后第三位四舍五入后的字符串
     *
     */
    public static String getRoundPercent(double f) {
        DecimalFormat df = new DecimalFormat("#####.00");

        return df.format(f);
    }

    /**
     * 金额数字转换
     *
     * @param f
     *            浮点型小数
     * @return 返回类型 String 浮点型小数转换为百分数，小数点后第三位四舍五入后的字符串
     *
     */
    public static String formartDouble(double f) {
        DecimalFormat df = new DecimalFormat("#,##0.0#");
        return df.format(f);
    }

    /**
     * 字符串金额数字转换
     *
     * @param str
     *            浮点型小数
     * @return 返回类型 String 浮点型小数转换为百分数，小数点后第三位四舍五入后的字符串
     *
     */
    public static Double parseDouble(String str) {
        try {
            DecimalFormat df = new DecimalFormat("#,##0.0#");
            return Double.valueOf(String.valueOf(df.parseObject(str)));
        } catch (Exception e) {
            return new Double(0.0);
        }
    }

    /**
     * 将浮点型小数转换为百分数，小数点后第三位四舍五入
     *
     * @param f
     *            浮点型小数
     * @return 返回类型 double 浮点型小数转换为百分数，小数点后第三位四舍五入后的浮点型小数
     *
     */
    public static double getDoubledigit(double f) {
        DecimalFormat df = new DecimalFormat("#####.00");
        return Double.parseDouble(df.format(f));
    }

    /**
     * 将浮点型小数转换为整数
     *
     * @param f
     *            浮点型小数
     * @return 返回类型 int 转换后的整数
     *
     */

    public static int getIntRoundPercent(double f) {

        DecimalFormat df = new DecimalFormat("#####");
        Double.parseDouble(df.format(f));

        return Integer.parseInt(df.format(f));
    }

    /**
     * 将浮点型小数转换为百分数
     *
     * @param f
     *            浮点型小数
     * @return 返回类型 int 转换后的点型小数
     *
     */
    public static int getRoundPercent(float f) {
        double r = f * 100;
        String round = String.valueOf(r);
        if (round.indexOf(".") > 0) {
            round = round.substring(0, round.indexOf("."));
            int intValue = Integer.parseInt(round);
            if (r - intValue >= 0.5)
                intValue += 1;
            round = String.valueOf(intValue);
        }
        return Integer.parseInt(round);
    }

    /**
     * 得到YYYY-12200203型字串 YYYY：2009
     *
     * @return 返回类型 String YYYY-12200203型字串
     *
     */
    public static String getYYYYString() {
        Calendar now = Calendar.getInstance();
        String number = "" + now.get(Calendar.YEAR) + "-"
                + now.getTimeInMillis();
        return number;
    }

    /**
     * 将指定日期去掉“_”
     *
     * @param data
     *            指定日期
     * @return 返回类型 String 去掉“_”后的日期
     *
     */
    public static String removeUnderScores(String data) {
        String temp = null;
        StringBuffer out = new StringBuffer();
        temp = data;

        StringTokenizer st = new StringTokenizer(temp, "_");
        while (st.hasMoreTokens()) {
            String element = (String) st.nextElement();
            out.append(capitalize(element));
        }
        return out.toString();
    }

    /**
     * 将指定字符串大写
     *
     * @param data
     *            指定字符串
     * @return 返回类型 String 返回新字串
     *
     */
    public static String capitalize(String data) {
        StringBuffer sbuf = new StringBuffer(data.length());
        sbuf.append(data.substring(0, 1).toUpperCase()).append(
                data.substring(1));
        return sbuf.toString();
    }

    /**
     * 将指定字符串的第pos个字符大写
     *
     * @param data
     *            指定字符串
     * @param pos
     *            第pos个字符
     * @return 返回类型 String 返回改变后的字符串
     *
     */
    public static String capitalize(String data, int pos) {
        StringBuffer buf = new StringBuffer(data.length());
        buf.append(data.substring(0, pos - 1));
        buf.append(data.substring(pos - 1, pos).toUpperCase());
        buf.append(data.substring(pos, data.length()));
        return buf.toString();
    }

    /**
     * 将指定字符串的第pos个字符小写
     *
     * @param data
     *            指定字符串
     * @param pos
     *            第pos个字符
     * @return 返回类型 String 返回改变后的字符窜
     *
     */
    public static String unCapitalize(String data, int pos) {
        StringBuffer buf = new StringBuffer(data.length());
        buf.append(data.substring(0, pos - 1));
        buf.append(data.substring(pos - 1, pos).toLowerCase());
        buf.append(data.substring(pos, data.length()));
        return buf.toString();
    }

    /**
     * 将一个字符串按给定分割字符串分割成数组
     *
     * @param text
     *            字符串
     * @param separator
     *            分割字符串
     * @return 返回类型 String[] 数组字符串
     *
     */
    public static String[] split(String text, String separator) {
        StringTokenizer st = new StringTokenizer(text, separator);
        String[] values = new String[st.countTokens()];
        int pos = 0;
        while (st.hasMoreTokens()) {
            values[pos++] = st.nextToken();
        }
        return values;
    }

    /**
     * 将字符串中所有的tag全部替换成为指定的info
     *
     * @param source
     *            原来的字符串
     * @param info
     *            替换tag的字符串
     * @param startTag
     *            被替换字符串起始点
     * @param endTag
     *            被替换字符串结束点
     * @return 返回类型 String 替换后的字符串
     *
     */
    public static String replaceAll(String source, String info,
                                    String startTag, String endTag) {
        if ((source == null) || (source.length() == 0)) {
            return "";
        }
        if ((info == null) || (startTag == null) || (startTag.length() == 0)
                || (endTag == null) || (endTag.length() == 0)) {
            return source;
        }
        int sIndex = source.indexOf(startTag);
        int eIndex = source.indexOf(endTag);
        boolean valid = (sIndex >= 0 && eIndex >= 0);
        if (!valid) {
            return source;
        } else {
            if (sIndex > eIndex) {
                eIndex = source.indexOf(endTag, sIndex);
            }
        }
        StringBuffer ret = new StringBuffer();
        int start = 0;
        while (valid) {
            info = source.substring(sIndex + 1, eIndex).trim();
            ret.append(source.substring(start, sIndex + 1)).append(info)
                    .append(endTag);
            start = eIndex + 1;
            sIndex = source.indexOf(startTag, start);
            eIndex = source.indexOf(endTag, start);
            valid = (sIndex >= 0 && eIndex >= 0 && eIndex > sIndex);
        }
        ret.append(source.substring(start));
        return ret.toString();
    }

    /**
     * 将输入字符中的SQL保留字进行替换，目前只替换英文半角的单引号'
     * 单引号替换方法：一个单引号换成连续的两个单引号，例如'ABC'D替换成''ABC''D
     *
     * @param s
     *            源字符串
     * @return 返回类型 String 替换后字符串
     *
     */
    public static String getSQLencode(String s) {
        if ((s == null) || (s.length() == 0))
            return "";
        StringBuffer sb = new StringBuffer();
        char c;
        for (int i = 0; i < s.length(); i++) {
            c = s.charAt(i);
            switch (c) {
                case '\'':
                    sb.append("''");
                    break;
                default:
                    sb.append(c);
            }
        }
        return sb.toString();
    }

    /**
     * 将输入字符中的格式化成precision指定的程度,截掉的部分用“..”补齐
     *
     * @param s
     *            被格式化字符串
     * @param precision
     *            格式化长度
     * @return 返回类型 String 格式化后字符串
     *
     */
    public static String getFormatString(String s, int precision) {
        String retValue = "";
        if ((s == null) || (s.length() == 0))
            retValue = "";
        if (s.length() <= precision)
            retValue = s;
        if (s.length() == precision + 1)
            retValue = s;
        if (s.length() > precision + 1)
            retValue = s.substring(0, precision - 1) + "..";
        return retValue;
    }



    /**
     * 生成树形CODE码
     *
     * @param title
     *            头
     * @param tail
     *            尾
     * @param tailLength
     *            长度
     * @return 返回类型 String 返回生成的code码
     *
     */
    public static String ensureLengthWith0(String title, String tail,
                                           int tailLength) {
        int len = tail.length();

        if (len == tailLength)
            return title.concat(tail);
        if (len > tailLength)
            return title.concat(tail.substring(0, tailLength));

        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < tailLength - len; i++)
            sb.append('0');

        return title.concat(sb.toString().concat(tail));
    }

    public static final String filterHalfWord(String sourceStr)
            throws UnsupportedEncodingException {
        String result = "";
        char[] cc = sourceStr.toCharArray();
        for (int i = 0; i < cc.length; i++) {
            byte[] c = String.valueOf(cc[i]).getBytes("UTF-8");
            String hex = encodeHex(c);

            if (!"0".equals(hex.trim())) {
                result += String.valueOf(cc[i]);
            }
        }
        return result;
    }

    public static final String encodeHex(byte[] bytes) {
        StringBuffer buff = new StringBuffer(bytes.length * 2);
        String b;
        for (int i = 0; i < bytes.length; i++) {
            b = Integer.toHexString(bytes[i]);
            buff.append(b.length() > 2 ? b.substring(6, 8) : b);
            buff.append(" ");
        }
        return buff.toString();
    }

    public static String getRandom18Str() {
        String random18Str = "";
        random18Str = (new Date().getTime() + "" + Math
                .floor(Math.random() * 100000));
        random18Str = random18Str.substring(0, random18Str.indexOf("."));
        if (null != random18Str && 18 == random18Str.length()) {
            return random18Str;
        } else {
            return getRandom18Str();
        }
    }

    public static void main(String[] args) {
        System.out.println(encrypt("111111"));
        System.out.println(disencrypt("5d-3f-3f-3f-3f-3f-3f"));
        System.out.println(equalsIgnoreCase("dest", "DEst"));

    }

    /**
     * 字符转ASC
     *
     * @param str
     * @param splitStr
     * @return
     */
    public static String strToAscStr(String str,String splitStr) {
        String retStr = "";
        if(null==str){
            return "";
        }
        byte[] byteArray = str.getBytes();
        for(int i=0;i<byteArray.length;i++){
            retStr+=Integer.toHexString(byteArray[i])+splitStr;
        }
        if(retStr.length()>splitStr.length()){
            return retStr.substring(0,retStr.length()-splitStr.length());
        }else{
            return retStr;
        }
    }

    /**
     * ASC转字符
     *
     * @param str
     * @param split
     * @return
     */
    public static String ascToStr(String str, String split) {
        String retStr = "";
        if(null==str){
            return retStr;
        }
        String[] strArray = str.split(split);
        if(null!=strArray&&0<strArray.length){
            for(int i=0;i<strArray.length;i++){
                retStr += (char)Integer.parseInt(strArray[i], 16);
            }
        }
        return retStr;
    }

    /**
     * 取得指定子串在字符串中出现的次数。
     * <p/>
     * <p>
     * 如果字符串为<code>null</code>或空，则返回<code>0</code>。
     * <pre>
     * StringUtil.countMatches(null, *)       = 0
     * StringUtil.countMatches("", *)         = 0
     * StringUtil.countMatches("abba", null)  = 0
     * StringUtil.countMatches("abba", "")    = 0
     * StringUtil.countMatches("abba", "a")   = 2
     * StringUtil.countMatches("abba", "ab")  = 1
     * StringUtil.countMatches("abba", "xxx") = 0
     * </pre>
     * </p>
     * @param str    要扫描的字符串
     * @param subStr 子字符串
     * @return 子串在字符串中出现的次数，如果字符串为<code>null</code>或空，则返回<code>0</code>
     */
    public static int countMatches(String str, String subStr) {
        if ((str == null) || (str.length() == 0) || (subStr == null) || (subStr.length() == 0)) {
            return 0;
        }

        int count = 0;
        int index = 0;

        while ((index = str.indexOf(subStr, index)) != -1) {
            count++;
            index += subStr.length();
        }

        return count;
    }

    /**
     * 去掉字符串中的html源码。<br>
     * @param con  内容
     * @return 去掉后的内容
     */

    public static String clearHTML(String con) {
        String content = con;
        if(con!=null){
            content=con.replaceAll("</?[^>]+>","");//剔出了<html>的标签
            content=content.replace("&nbsp;","");
            content=content.replace(".","");
            content=content.replace("\"","‘");
            content=content.replace("'","‘");
        }
        return content;
    }

    /**
     * 获取字段名对应的setter方法
     * @param name
     * @return
     */
    public static String getSetMethod(String name) {
        if (name == null || name.length() == 0)	return "";

        return name.substring(0, 1).toUpperCase() + name.substring(1, name.length());
    }

    /**
     * 判断是否数字表示
     *
     * @param src
     *            源字符串
     * @return 是否数字的标志
     */
    public static boolean isNumeric(String src) {
        boolean return_value = false;
        if (src != null && src.length() > 0) {
            Matcher m = numericPattern.matcher(src);
            if (m.find()) {
                return_value = true;
            }
        }
        return return_value;
    }

    /**
     * 判断是否数字表示
     *
     * @param src
     *            源字符串
     * @return 是否数字的标志
     */
    public static boolean isNumericString(String src) {
        boolean return_value = false;
        if (src != null && src.length() > 0) {
            Matcher m = numericStringPattern.matcher(src);
            if (m.find()) {
                return_value = true;
            }
        }
        return return_value;
    }

    /**
     * 判断是否纯字母组合
     *
     * @param src
     *            源字符串
     * @return 是否纯字母组合的标志
     */
    public static boolean isABC(String src) {
        boolean return_value = false;
        if (src != null && src.length() > 0) {
            Matcher m = abcPattern.matcher(src);
            if (m.find()) {
                return_value = true;
            }
        }
        return return_value;
    }

    /**
     * 判断是否浮点数字表示
     *
     * @param src
     *            源字符串
     * @return 是否数字的标志
     */
    public static boolean isFloatNumeric(String src) {
        boolean return_value = false;
        if (src != null && src.length() > 0) {
            Matcher m = floatNumericPattern.matcher(src);
            if (m.find()) {
                return_value = true;
            }
        }
        return return_value;
    }

    /**
     * 把string array or list用给定的符号symbol连接成一个字符串
     *
     * @param array
     * @param symbol
     * @return
     */
    public static String joinString(List array, String symbol) {
        String result = "";
        if (array != null) {
            for (int i = 0; i < array.size(); i++) {
                String temp = array.get(i).toString();
                if (temp != null && temp.trim().length() > 0)
                    result += (temp + symbol);
            }
            if (result.length() > 1)
                result = result.substring(0, result.length() - 1);
        }
        return result;
    }

    public static String subStringNotEncode(String subject, int size) {
        if (subject != null && subject.length() > size) {
            subject = subject.substring(0, size) + "...";
        }
        return subject;
    }

    /**
     * 截取字符串　超出的字符用symbol代替 　　
     *
     * @param len
     *            　字符串长度　长度计量单位为一个GBK汉字　　两个英文字母计算为一个单位长度
     * @param str
     * @param symbol
     * @return
     */
    public static String getLimitLengthString(String str, int len, String symbol) {
        int iLen = len * 2;
        int counterOfDoubleByte = 0;
        String strRet = "";
        try {
            if (str != null) {
                byte[] b = str.getBytes("GBK");
                if (b.length <= iLen) {
                    return str;
                }
                for (int i = 0; i < iLen; i++) {
                    if (b[i] < 0) {
                        counterOfDoubleByte++;
                    }
                }
                if (counterOfDoubleByte % 2 == 0) {
                    strRet = new String(b, 0, iLen, "GBK") + symbol;
                    return strRet;
                } else {
                    strRet = new String(b, 0, iLen - 1, "GBK") + symbol;
                    return strRet;
                }
            } else {
                return "";
            }
        } catch (Exception ex) {
            return str.substring(0, len);
        } finally {
            strRet = null;
        }
    }

    /**
     * 截取字符串　超出的字符用symbol代替 　　
     *
     * @param len
     *            　字符串长度　长度计量单位为一个GBK汉字　　两个英文字母计算为一个单位长度
     * @param str
     * @param len
     * @return12
     */
    public static String getLimitLengthString(String str, int len) {
        return getLimitLengthString(str, len, "...");
    }

    /**
     *
     * 截取字符，不转码
     *
     * @param subject
     * @param size
     * @return
     */
    public static String subStrNotEncode(String subject, int size) {
        if (subject.length() > size) {
            subject = subject.substring(0, size);
        }
        return subject;
    }

    /**
     * 把string array or list用给定的符号symbol连接成一个字符串
     *
     * @param array
     * @param symbol
     * @return
     */
    public static String joinString(String[] array, String symbol) {
        String result = "";
        if (array != null) {
            for (int i = 0; i < array.length; i++) {
                String temp = array[i];
                if (temp != null && temp.trim().length() > 0)
                    result += (temp + symbol);
            }
            if (result.length() > 1)
                result = result.substring(0, result.length() - 1);
        }
        return result;
    }

    /**
     * 取得字符串的实际长度（考虑了汉字的情况）
     *
     * @param SrcStr
     *            源字符串
     * @return 字符串的实际长度
     */
    public static int getStringLen(String SrcStr) {
        int return_value = 0;
        if (SrcStr != null) {
            char[] theChars = SrcStr.toCharArray();
            for (int i = 0; i < theChars.length; i++) {
                return_value += (theChars[i] <= 255) ? 1 : 2;
            }
        }
        return return_value;
    }

    /**
     * 检查数据串中是否包含非法字符集
     *
     * @param str
     * @return [true]|[false] 包含|不包含
     */
    public static boolean check(String str) {
        String sIllegal = "'\"";
        int len = sIllegal.length();
        if (null == str)
            return false;
        for (int i = 0; i < len; i++) {
            if (str.indexOf(sIllegal.charAt(i)) != -1)
                return true;
        }

        return false;
    }

    /***************************************************************************
     * getHideEmailPrefix - 隐藏邮件地址前缀。
     *
     * @param email
     *            - EMail邮箱地址 例如: linwenguo@koubei.com 等等...
     * @return 返回已隐藏前缀邮件地址, 如 *********@koubei.com.
     * @version 1.0 (2006.11.27) Wilson Lin
     **************************************************************************/
    public static String getHideEmailPrefix(String email) {
        if (null != email) {
            int index = email.lastIndexOf('@');
            if (index > 0) {
                email = repeat("*", index).concat(email.substring(index));
            }
        }
        return email;
    }

    /***************************************************************************
     * repeat - 通过源字符串重复生成N次组成新的字符串。
     *
     * @param src
     *            - 源字符串 例如: 空格(" "), 星号("*"), "浙江" 等等...
     * @param num
     *            - 重复生成次数
     * @return 返回已生成的重复字符串
     * @version 1.0 (2006.10.10) Wilson Lin
     **************************************************************************/
    public static String repeat(String src, int num) {
        StringBuffer s = new StringBuffer();
        for (int i = 0; i < num; i++)
            s.append(src);
        return s.toString();
    }

    /**
     * 根据指定的字符把源字符串分割成一个数组
     *
     * @param src
     * @return
     */
    public static List<String> parseString2ListByCustomerPattern(
            String pattern, String src) {

        if (src == null)
            return null;
        List<String> list = new ArrayList<String>();
        String[] result = src.split(pattern);
        for (int i = 0; i < result.length; i++) {
            list.add(result[i]);
        }
        return list;
    }

    /**
     * 根据指定的字符把源字符串分割成一个数组
     *
     * @param src
     * @return
     */
    public static List<String> parseString2ListByPattern(String src) {
        String pattern = "，|,|、|。";
        return parseString2ListByCustomerPattern(pattern, src);
    }

    /**
     * 格式化一个float
     *
     * @param format
     *            要格式化成的格式 such as #.00, #.#
     */

    public static String formatFloat(float f, String format) {
        DecimalFormat df = new DecimalFormat(format);
        return df.format(f);
    }

    /**
     * 自定义的分隔字符串函数 例如: 1,2,3 =>[1,2,3] 3个元素 ,2,3=>[,2,3] 3个元素 ,2,3,=>[,2,3,]
     * 4个元素 ,,,=>[,,,] 4个元素
     *
     * 5.22算法修改，为提高速度不用正则表达式 两个间隔符,,返回""元素
     *
     * @param split
     *            分割字符 默认,
     * @param src
     *            输入字符串
     * @return 分隔后的list
     * @author Robin
     */
    public static List<String> splitToList(String split, String src) {
        // 默认,
        String sp = ",";
        if (split != null && split.length() == 1) {
            sp = split;
        }
        List<String> r = new ArrayList<String>();
        int lastIndex = -1;
        int index = src.indexOf(sp);
        if (-1 == index && src != null) {
            r.add(src);
            return r;
        }
        while (index >= 0) {
            if (index > lastIndex) {
                r.add(src.substring(lastIndex + 1, index));
            } else {
                r.add("");
            }

            lastIndex = index;
            index = src.indexOf(sp, index + 1);
            if (index == -1) {
                r.add(src.substring(lastIndex + 1, src.length()));
            }
        }
        return r;
    }

    /**
     * 把 名=值 参数表转换成字符串 (a=1,b=2 =>a=1&b=2)
     *
     * @param map
     * @return
     */
    public static String linkedHashMapToString(LinkedHashMap<String, String> map) {
        if (map != null && map.size() > 0) {
            String result = "";
            Iterator it = map.keySet().iterator();
            while (it.hasNext()) {
                String name = (String) it.next();
                String value = (String) map.get(name);
                result += (result.equals("")) ? "" : "&";
                result += String.format("%s=%s", name, value);
            }
            return result;
        }
        return null;
    }

    /**
     * 解析字符串返回 名称=值的参数表 (a=1&b=2 => a=1,b=2)
     *
     * @param str
     * @return
     */
    @SuppressWarnings("unchecked")
    public static LinkedHashMap<String, String> toLinkedHashMap(String str) {
        if (str != null && !str.equals("") && str.indexOf("=") > 0) {
            LinkedHashMap result = new LinkedHashMap();

            String name = null;
            String value = null;
            int i = 0;
            while (i < str.length()) {
                char c = str.charAt(i);
                switch (c) {
                    case 61: // =
                        value = "";
                        break;
                    case 38: // &
                        if (name != null && value != null && !name.equals("")) {
                            result.put(name, value);
                        }
                        name = null;
                        value = null;
                        break;
                    default:
                        if (value != null) {
                            value = (value != null) ? (value + c) : "" + c;
                        } else {
                            name = (name != null) ? (name + c) : "" + c;
                        }
                }
                i++;

            }

            if (name != null && value != null && !name.equals("")) {
                result.put(name, value);
            }

            return result;

        }
        return null;
    }

    /**
     * 根据输入的多个解释和下标返回一个值
     *
     * @param captions
     *            例如:"无,爱干净,一般,比较乱"
     * @param index
     *            1
     * @return 一般
     */
    public static String getCaption(String captions, int index) {
        if (index > 0 && captions != null && !captions.equals("")) {
            String[] ss = captions.split(",");
            if (ss != null && ss.length > 0 && index < ss.length) {
                return ss[index];
            }
        }
        return null;
    }

    /**
     * 数字转字符串,如果num<=0 则输出"";
     *
     * @param num
     * @return
     */
    public static String numberToString(Object num) {
        if (num == null) {
            return null;
        } else if (num instanceof Integer && (Integer) num > 0) {
            return Integer.toString((Integer) num);
        } else if (num instanceof Long && (Long) num > 0) {
            return Long.toString((Long) num);
        } else if (num instanceof Float && (Float) num > 0) {
            return Float.toString((Float) num);
        } else if (num instanceof Double && (Double) num > 0) {
            return Double.toString((Double) num);
        } else {
            return "";
        }
    }

    /**
     * 货币转字符串
     *
     * @param money
     * @param style
     *            样式 [default]要格式化成的格式 such as #.00, #.#
     * @return
     */

    public static String moneyToString(Object money, String style) {
        if (money != null && style != null
                && (money instanceof Double || money instanceof Float)) {
            Double num = (Double) money;

            if (style.equalsIgnoreCase("default")) {
                // 缺省样式 0 不输出 ,如果没有输出小数位则不输出.0
                if (num == 0) {
                    // 不输出0
                    return "";
                } else if ((num * 10 % 10) == 0) {
                    // 没有小数
                    return Integer.toString((int) num.intValue());
                } else {
                    // 有小数
                    return num.toString();
                }

            } else {
                DecimalFormat df = new DecimalFormat(style);
                return df.format(num);
            }
        }
        return null;
    }

    /**
     * 在sou中是否存在finds 如果指定的finds字符串有一个在sou中找到,返回true;
     *
     * @param sou
     * @param finds
     * @return
     */
    public static boolean strPos(String sou, String... finds) {
        if (sou != null && finds != null && finds.length > 0) {
            for (int i = 0; i < finds.length; i++) {
                if (sou.indexOf(finds[i]) > -1)
                    return true;
            }
        }
        return false;
    }

    public static boolean strPos(String sou, List<String> finds) {
        if (sou != null && finds != null && finds.size() > 0) {
            for (String s : finds) {
                if (sou.indexOf(s) > -1)
                    return true;
            }
        }
        return false;
    }

    public static boolean strPos(String sou, String finds) {
        List<String> t = splitToList(",", finds);
        return strPos(sou, t);
    }

    /**
     * 判断两个字符串是否相等 如果都为null则判断为相等,一个为null另一个not null则判断不相等 否则如果s1=s2则相等
     *
     * @param s1
     * @param s2
     * @return
     */
    public static boolean equals(String s1, String s2) {
        if (StringUtil.isEmpty(s1) && StringUtil.isEmpty(s2)) {
            return true;
        } else if (!StringUtil.isEmpty(s1) && !StringUtil.isEmpty(s2)) {
            return s1.equals(s2);
        }
        return false;
    }

    /**
     * 返回小写
     * @param str
     * @return
     */
    public static String lowerCase(String str) {
        return (null == str) ? "" : str.toLowerCase();
    }

    /**
     * 返回大写
     * @param str
     * @return
     */
    public static String upperCase(String str) {
        return (null == str) ? "" : str.toUpperCase();
    }

    public static int toInt(String s) {
        if (s != null && !"".equals(s.trim())) {
            try {
                return Integer.parseInt(s);
            } catch (Exception e) {
                return 0;
            }
        }
        return 0;
    }

    public static double toDouble(String s) {
        if (s != null && !"".equals(s.trim())) {
            return Double.parseDouble(s);
        }
        return 0;
    }

    public static boolean isPhone(String phone) {
        if (phone == null && "".equals(phone)) {
            return false;
        }
        String[] strPhone = phone.split("-");
        try {
            for (int i = 0; i < strPhone.length; i++) {
                Long.parseLong(strPhone[i]);
            }

        } catch (Exception e) {
            return false;
        }
        return true;

    }

    /**
     * 把xml 转为object
     *
     * @param xml
     * @return
     */
    public static Object xmlToObject(String xml) {
        try {
            ByteArrayInputStream in = new ByteArrayInputStream(xml
                    .getBytes("UTF8"));
            XMLDecoder decoder = new XMLDecoder(new BufferedInputStream(in));
            return decoder.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static long toLong(String s) {
        try {
            if (s != null && !"".equals(s.trim()))
                return Long.parseLong(s);
        } catch (Exception exception) {
        }
        return 0L;
    }

    public static String simpleEncrypt(String str) {
        if (str != null && str.length() > 0) {
            // str = str.replaceAll("0","a");
            str = str.replaceAll("1", "b");
            // str = str.replaceAll("2","c");
            str = str.replaceAll("3", "d");
            // str = str.replaceAll("4","e");
            str = str.replaceAll("5", "f");
            str = str.replaceAll("6", "g");
            str = str.replaceAll("7", "h");
            str = str.replaceAll("8", "i");
            str = str.replaceAll("9", "j");
        }
        return str;

    }

    /**
     * 过滤用户输入的URL地址（防治用户广告） 目前只针对以http或www开头的URL地址
     * 本方法调用的正则表达式，不建议用在对性能严格的地方例如:循环及list页面等
     *
     * @author fengliang
     * @param str
     *            需要处理的字符串
     * @return 返回处理后的字符串
     */
    public static String removeURL(String str) {
        if (str != null)
            str = str.toLowerCase()
                    .replaceAll("(http|www|com|cn|org|\\.)+", "");
        return str;
    }

    /**
     * 随即生成指定位数的含数字验证码字符串
     *
     * @author Peltason
     * @date 2007-5-9
     * @param bit
     *            指定生成验证码位数
     * @return String
     */
    public static String numRandom(int bit) {
        if (bit == 0)
            bit = 6; // 默认6位
        String str = "";
        str = "0123456789";// 初始化种子
        return RandomStringUtils.random(bit, str);// 返回6位的字符串
    }

    /**
     * 随即生成指定位数的含验证码字符串
     *
     * @author Peltason
     * @date 2007-5-9
     * @param bit
     *            指定生成验证码位数
     * @return String
     */
    public static String random(int bit) {
        if (bit == 0)
            bit = 6; // 默认6位
        // 因为o和0,l和1很难区分,所以,去掉大小写的o和l
        String str = "";
        str = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijkmnpqrstuvwxyz";// 初始化种子
        return RandomStringUtils.random(bit, str);// 返回6位的字符串
    }

    /**
     * 检查字符串是否属于手机号码
     *
     * @author Peltason
     * @date 2007-5-9
     * @param str
     * @return boolean
     */
    public static boolean isMobile(String str) {
        if (str == null || str.equals(""))
            return false;
        if (str.length() != 11 || !isNumeric(str))
            return false;
        if (!str.substring(0, 2).equals("13")
                && !str.substring(0, 2).equals("15")
                && !str.substring(0, 2).equals("18"))
            return false;
        return true;
    }

    /**
     * Wap页面的非法字符检查
     *
     * @author hugh115
     * @date 2007-06-29
     * @param str
     * @return
     */
    public static String replaceWapStr(String str) {
        if (str != null) {
            str = str.replaceAll("<span class=\"keyword\">", "");
            str = str.replaceAll("</span>", "");
            str = str.replaceAll("<strong class=\"keyword\">", "");
            str = str.replaceAll("<strong>", "");
            str = str.replaceAll("</strong>", "");

            str = str.replace('$', '＄');

            str = str.replaceAll("&amp;", "＆");
            str = str.replace('&', '＆');

            str = str.replace('<', '＜');

            str = str.replace('>', '＞');

        }
        return str;
    }

    /**
     * 字符串转float 如果异常返回0.00
     *
     * @param s
     *            输入的字符串
     * @return 转换后的float
     */
    public static Float toFloat(String s) {
        try {
            return Float.parseFloat(s);
        } catch (NumberFormatException e) {
            return new Float(0);
        }
    }

    /**
     * 页面中去除字符串中的空格、回车、换行符、制表符
     *
     * @author shazao
     * @date 2007-08-17
     * @param str
     * @return
     */
    public static String replaceBlank(String str) {
        if (str != null) {
            Pattern p = Pattern.compile("\\s*|\t|\r|\n");
            Matcher m = p.matcher(str);
            str = m.replaceAll("");
        }
        return str;
    }

    /**
     * 全角生成半角
     *
     * @author bailong
     * @date 2007-08-29
     * @param QJstr
     * @return
     */
    public static String Q2B(String QJstr) {
        String outStr = "";
        String Tstr = "";
        byte[] b = null;
        for (int i = 0; i < QJstr.length(); i++) {
            try {
                Tstr = QJstr.substring(i, i + 1);
                b = Tstr.getBytes("unicode");
            } catch (java.io.UnsupportedEncodingException e) {

            }
            if (b[3] == -1) {
                b[2] = (byte) (b[2] + 32);
                b[3] = 0;
                try {
                    outStr = outStr + new String(b, "unicode");
                } catch (java.io.UnsupportedEncodingException ex) {

                }
            } else {
                outStr = outStr + Tstr;
            }
        }
        return outStr;
    }

    /**
     *
     * 转换编码
     *
     * @param s
     *            源字符串
     * @param fencode
     *            源编码格式
     * @param bencode
     *            目标编码格式
     * @return 目标编码
     */
    public static String changCoding(String s, String fencode, String bencode) {
        try {
            String str = new String(s.getBytes(fencode), bencode);
            return str;
        } catch (UnsupportedEncodingException e) {
            return s;
        }
    }

    /**
     * *************************************************************************
     * 修改：刘黎明 修改时间:2007/3/1
     *
     * @param str
     * @return
     *************************************************************************
     */
    public static String removeHTMLLableExe(String str) {
        str = stringReplace(str, ">\\s*<", "><");
        str = stringReplace(str, "&nbsp;", " ");// 替换空格
        str = stringReplace(str, "<br ?/?>", "\n");// 去<br><br />
        str = stringReplace(str, "<([^<>]+)>", "");// 去掉<>内的字符
        str = stringReplace(str, "\\s\\s\\s*", " ");// 将多个空白变成一个空格
        str = stringReplace(str, "^\\s*", "");// 去掉头的空白
        str = stringReplace(str, "\\s*$", "");// 去掉尾的空白
        str = stringReplace(str, " +", " ");
        return str;
    }

    /**
     * 除去html标签
     *
     * @param str
     *            源字符串
     * @return 目标字符串
     */
    public static String removeHTMLLable(String str) {
        str = stringReplace(str, "\\s", "");// 去掉页面上看不到的字符
        str = stringReplace(str, "<br ?/?>", "\n");// 去<br><br />
        str = stringReplace(str, "<([^<>]+)>", "");// 去掉<>内的字符
        str = stringReplace(str, "&nbsp;", " ");// 替换空格
        str = stringReplace(str, "&(\\S)(\\S?)(\\S?)(\\S?);", "");// 去<br><br />
        return str;
    }

    /**
     * 去掉HTML标签之外的字符串
     *
     * @param str
     *            源字符串
     * @return 目标字符串
     */
    public static String removeOutHTMLLable(String str) {
        str = stringReplace(str, ">([^<>]+)<", "><");
        str = stringReplace(str, "^([^<>]+)<", "<");
        str = stringReplace(str, ">([^<>]+)$", ">");
        return str;
    }

    /**
     *
     * 字符串替换
     *
     * @param str
     *            源字符串
     * @param sr
     *            正则表达式样式
     * @param sd
     *            替换文本
     * @return 结果串
     */
    public static String stringReplace(String str, String sr, String sd) {
        String regEx = sr;
        Pattern p = Pattern.compile(regEx, Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(str);
        str = m.replaceAll(sd);
        return str;
    }

    /**
     *
     * 将html的省略写法替换成非省略写法
     *
     * @param str
     *            html字符串
     * @param pt
     *            标签如table
     * @return 结果串
     */
    public static String fomateToFullForm(String str, String pt) {
        String regEx = "<" + pt + "\\s+([\\S&&[^<>]]*)/>";
        Pattern p = Pattern.compile(regEx, Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(str);
        String[] sa = null;
        String sf = "";
        String sf2 = "";
        String sf3 = "";
        for (; m.find();) {
            sa = p.split(str);
            if (sa == null) {
                break;
            }
            sf = str.substring(sa[0].length(), str
                    .indexOf("/>", sa[0].length()));
            sf2 = sf + "></" + pt + ">";
            sf3 = str.substring(sa[0].length() + sf.length() + 2);
            str = sa[0] + sf2 + sf3;
            sa = null;
        }
        return str;
    }

    /**
     *
     * 得到字符串的子串位置序列
     *
     * @param str
     *            字符串
     * @param sub
     *            子串
     * @param b
     *            true子串前端,false子串后端
     * @return 字符串的子串位置序列
     */
    public static int[] getSubStringPos(String str, String sub, boolean b) {
        // int[] i = new int[(new Integer((str.length()-stringReplace( str , sub
        // , "" ).length())/sub.length())).intValue()] ;
        String[] sp = null;
        int l = sub.length();
        sp = splitString(str, sub);
        if (sp == null) {
            return null;
        }
        int[] ip = new int[sp.length - 1];
        for (int i = 0; i < sp.length - 1; i++) {
            ip[i] = sp[i].length() + l;
            if (i != 0) {
                ip[i] += ip[i - 1];
            }
        }
        if (b) {
            for (int j = 0; j < ip.length; j++) {
                ip[j] = ip[j] - l;
            }
        }
        return ip;
    }

    /**
     *
     * 根据正则表达式分割字符串
     *
     * @param str
     *            源字符串
     * @param ms
     *            正则表达式
     * @return 目标字符串组
     */
    public static String[] splitString(String str, String ms) {
        String regEx = ms;
        Pattern p = Pattern.compile(regEx, Pattern.CASE_INSENSITIVE);
        String[] sp = p.split(str);
        return sp;
    }

    /**
     * *************************************************************************
     * 根据正则表达式提取字符串,相同的字符串只返回一个
     *
     * @author 刘黎明
     * @param str
     *            源字符串
     * @param pattern
     *            正则表达式
     * @return 目标字符串数据组
     *************************************************************************
     */

    // ★传入一个字符串，把符合pattern格式的字符串放入字符串数组
    // java.utils.regex是一个用正则表达式所订制的模式来对字符串进行匹配工作的类库包
    public static String[] getStringArrayByPattern(String str, String pattern) {
        Pattern p = Pattern.compile(pattern, Pattern.CASE_INSENSITIVE);
        Matcher matcher = p.matcher(str);
        // 范型
        Set<String> result = new HashSet<String>();// 目的是：相同的字符串只返回一个。。。 不重复元素
        // boolean find() 尝试在目标字符串里查找下一个匹配子串。
        while (matcher.find()) {
            for (int i = 0; i < matcher.groupCount(); i++) { // int groupCount()
                // 返回当前查找所获得的匹配组的数量。
                // System.out.println(matcher.group(i));
                result.add(matcher.group(i));

            }
        }
        String[] resultStr = null;
        if (result.size() > 0) {
            resultStr = new String[result.size()];
            return result.toArray(resultStr);// 将Set result转化为String[] resultStr
        }
        return resultStr;

    }

    /**
     * 得到第一个b,e之间的字符串,并返回e后的子串
     *
     * @param s
     *            源字符串
     * @param b
     *            标志开始
     * @param e
     *            标志结束
     * @return b,e之间的字符串
     */

	/*
	 * String aaa="abcdefghijklmn"; String[] bbb=StringProcessor.midString(aaa,
	 * "b","l"); System.out.println("bbb[0]:"+bbb[0]);//cdefghijk
	 * System.out.println("bbb[1]:"+bbb[1]);//lmn
	 * ★这个方法是得到第二个参数和第三个参数之间的字符串,赋给元素0;然后把元素0代表的字符串之后的,赋给元素1
	 */

	/*
	 * String aaa="abcdefgllhijklmn5465"; String[]
	 * bbb=StringProcessor.midString(aaa, "b","l"); //ab cdefg llhijklmn5465 //
	 * 元素0 元素1
	 */
    public static String[] midString(String s, String b, String e) {
        int i = s.indexOf(b) + b.length();
        int j = s.indexOf(e, i);
        String[] sa = new String[2];
        if (i < b.length() || j < i + 1 || i > j) {
            sa[1] = s;
            sa[0] = null;
            return sa;
        } else {
            sa[0] = s.substring(i, j);
            sa[1] = s.substring(j);
            return sa;
        }
    }

    /**
     * 带有前一次替代序列的正则表达式替代
     *
     * @param s
     * @param pf
     * @param pb
     * @param start
     * @return
     */
    public static String stringReplace(String s, String pf, String pb, int start) {
        Pattern pattern_hand = Pattern.compile(pf);
        Matcher matcher_hand = pattern_hand.matcher(s);
        int gc = matcher_hand.groupCount();
        int pos = start;
        String sf1 = "";
        String sf2 = "";
        String sf3 = "";
        int if1 = 0;
        String strr = "";
        while (matcher_hand.find(pos)) {
            sf1 = matcher_hand.group();
            if1 = s.indexOf(sf1, pos);
            if (if1 >= pos) {
                strr += s.substring(pos, if1);
                pos = if1 + sf1.length();
                sf2 = pb;
                for (int i = 1; i <= gc; i++) {
                    sf3 = "\\" + i;
                    sf2 = replaceAll(sf2, sf3, matcher_hand.group(i));
                }
                strr += sf2;
            } else {
                return s;
            }
        }
        strr = s.substring(0, start) + strr;
        return strr;
    }

    /**
     * 存文本替换
     *
     * @param s
     *            源字符串
     * @param sf
     *            子字符串
     * @param sb
     *            替换字符串
     * @return 替换后的字符串
     */
    public static String replaceAll(String s, String sf, String sb) {
        int i = 0, j = 0;
        int l = sf.length();
        boolean b = true;
        boolean o = true;
        String str = "";
        do {
            j = i;
            i = s.indexOf(sf, j);
            if (i > j) {
                str += s.substring(j, i);
                str += sb;
                i += l;
                o = false;
            } else {
                str += s.substring(j);
                b = false;
            }
        } while (b);
        if (o) {
            str = s;
        }
        return str;
    }

    /**
     * 判断是否与给定字符串样式匹配
     *
     * @param str
     *            字符串
     * @param pattern
     *            正则表达式样式
     * @return 是否匹配是true,否false
     */
    public static boolean isMatch(String str, String pattern) {
        Pattern pattern_hand = Pattern.compile(pattern);
        Matcher matcher_hand = pattern_hand.matcher(str);
        boolean b = matcher_hand.matches();
        return b;
    }

    /**
     * 截取字符串
     *
     * @param s
     *            源字符串
     * @param jmp
     *            跳过jmp
     * @param sb
     *            取在sb
     * @param se
     *            于se
     * @return 之间的字符串
     */
    public static String subStringExe(String s, String jmp, String sb, String se) {
        if (isEmpty(s)) {
            return "";
        }
        int i = s.indexOf(jmp);
        if (i >= 0 && i < s.length()) {
            s = s.substring(i + 1);
        }
        i = s.indexOf(sb);
        if (i >= 0 && i < s.length()) {
            s = s.substring(i + 1);
        }
        if (se == "") {
            return s;
        } else {
            i = s.indexOf(se);
            if (i >= 0 && i < s.length()) {
                s = s.substring(i + 1);
            }
            return s;
        }
    }

    /**
     * *************************************************************************
     * 用要通过URL传输的内容进行编码
     *
     * @param src
     * @return 经过编码的内容
     *************************************************************************
     */
    public static String URLEncode(String src) {
        String return_value = "";
        try {
            if (src != null) {
                return_value = URLEncoder.encode(src, "GBK");

            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return_value = src;
        }

        return return_value;
    }

    public static String getGBK(String str) {

        return transfer(str);
    }

    public static String transfer(String str) {
        Pattern p = Pattern.compile("&#\\d+;");
        Matcher m = p.matcher(str);
        while (m.find()) {
            String old = m.group();
            str = str.replaceAll(old, getChar(old));
        }
        return str;
    }

    public static String getChar(String str) {
        String dest = str.substring(2, str.length() - 1);
        char ch = (char) Integer.parseInt(dest);
        return "" + ch;
    }

    /**
     * yahoo首页中切割字符串.
     *
     * @author yxg
     * @date 2007-09-17
     * @param subject
     * @param size
     * @return
     */
    public static String subYhooString(String subject, int size) {
        subject = subject.substring(1, size);
        return subject;
    }

    public static String subYhooStringDot(String subject, int size) {
        subject = subject.substring(1, size) + "...";
        return subject;
    }

    /**
     * 泛型方法(通用)，把list转换成以“,”相隔的字符串 调用时注意类型初始化（申明类型） 如：List<Integer> intList =
     * new ArrayList<Integer>(); 调用方法：StringUtil.listTtoString(intList);
     * 效率：list中4条信息，1000000次调用时间为850ms左右
     *
     * @author fengliang
     * @serialData 2008-01-09
     * @param <T>
     *            泛型
     * @param list
     *            list列表
     * @return 以“,”相隔的字符串
     */
    public static <T> String listTtoString(List<T> list) {
        if (list == null || list.size() < 1)
            return "";
        Iterator<T> i = list.iterator();
        if (!i.hasNext())
            return "";
        StringBuilder sb = new StringBuilder();
        for (;;) {
            T e = i.next();
            sb.append(e);
            if (!i.hasNext())
                return sb.toString();
            sb.append(",");
        }
    }

    /**
     * 把整形数组转换成以“,”相隔的字符串
     *
     * @author fengliang
     * @serialData 2008-01-08
     * @param a
     *            数组a
     * @return 以“,”相隔的字符串
     */
    public static String intArraytoString(int[] a) {
        if (a == null)
            return "";
        int iMax = a.length - 1;
        if (iMax == -1)
            return "";
        StringBuilder b = new StringBuilder();
        for (int i = 0;; i++) {
            b.append(a[i]);
            if (i == iMax)
                return b.toString();
            b.append(",");
        }
    }

    public static boolean isContentRepeat(String content) {
        int similarNum = 0;
        int forNum = 0;
        int subNum = 0;
        int thousandNum = 0;
        String startStr = "";
        String nextStr = "";
        boolean result = false;
        float endNum = (float) 0.0;
        if (content != null && content.length() > 0) {
            if (content.length() % 1000 > 0)
                thousandNum = (int) Math.floor(content.length() / 1000) + 1;
            else
                thousandNum = (int) Math.floor(content.length() / 1000);
            if (thousandNum < 3)
                subNum = 100 * thousandNum;
            else if (thousandNum < 6)
                subNum = 200 * thousandNum;
            else if (thousandNum < 9)
                subNum = 300 * thousandNum;
            else
                subNum = 3000;
            for (int j = 1; j < subNum; j++) {
                if (content.length() % j > 0)
                    forNum = (int) Math.floor(content.length() / j) + 1;
                else
                    forNum = (int) Math.floor(content.length() / j);
                if (result || j >= content.length())
                    break;
                else {
                    for (int m = 0; m < forNum; m++) {
                        if (m * j > content.length()
                                || (m + 1) * j > content.length()
                                || (m + 2) * j > content.length())
                            break;
                        startStr = content.substring(m * j, (m + 1) * j);
                        nextStr = content.substring((m + 1) * j, (m + 2) * j);
                        if (startStr.equals(nextStr)) {
                            similarNum = similarNum + 1;
                            endNum = (float) similarNum / forNum;
                            if (endNum > 0.4) {
                                result = true;
                                break;
                            }
                        } else
                            similarNum = 0;
                    }
                }
            }
        }
        return result;
    }

    /**
     * Ascii转为Char
     *
     * @author 甜瓜
     * @param asc
     * @return
     */
    public static String AsciiToChar(int asc) {
        String TempStr = "A";
        char tempchar = (char) asc;
        TempStr = String.valueOf(tempchar);
        return TempStr;
    }

    /**
     * 判断是否是空字符串 null和"" null返回result,否则返回字符串
     *
     * @param s
     * @return
     */
    public static String isEmpty(String s, String result) {
        if (s != null && !s.equals("")) {
            return s;
        }
        return result;
    }

    /**
     * 将带有htmlcode代码的字符转换成<>&'"
     *
     * @param str
     * @return
     */
    public static String htmlcodeToSpecialchars(String str) {
        str = str.replaceAll("&amp;", "&");
        str = str.replaceAll("&quot;", "\"");
        str = str.replaceAll("&#039;", "'");
        str = str.replaceAll("&lt;", "<");
        str = str.replaceAll("&gt;", ">");
        return str;
    }

    /**
     * 移除html标签
     *
     * @param htmlstr
     * @return
     */
    public static String removeHtmlTag(String htmlstr) {
        Pattern pat = Pattern.compile("\\s*<.*?>\\s*", Pattern.DOTALL
                | Pattern.MULTILINE | Pattern.CASE_INSENSITIVE); // \\s?[s|Sc|Cr|Ri|Ip|Pt|T]
        Matcher m = pat.matcher(htmlstr);
        String rs = m.replaceAll("");
        rs = rs.replaceAll("&nbsp", " ");
        rs = rs.replaceAll("&lt;", "<");
        rs = rs.replaceAll("&gt;", ">");
        return rs;
    }

    /**
     * 取从指定搜索项开始的字符串，返回的值不包含搜索项
     *
     * @param captions
     *            例如:"www.koubei.com"
     * @param regex
     *            分隔符，例如"."
     * @return 结果字符串，如：koubei.com，如未找到返回空串
     */
    public static String getStrAfterRegex(String captions, String regex) {
        if (!isEmpty(captions) && !isEmpty(regex)) {
            int pos = captions.indexOf(regex);
            if (pos != -1 && pos < captions.length() - 1) {
                return captions.substring(pos + 1);
            }
        }
        return "";
    }

    /**
     * 把字节码转换成16进制
     */
    public static String byte2hex(byte bytes[]) {
        StringBuffer retString = new StringBuffer();
        for (int i = 0; i < bytes.length; ++i) {
            retString.append(Integer.toHexString(0x0100 + (bytes[i] & 0x00FF))
                    .substring(1).toUpperCase());
        }
        return retString.toString();
    }

    /**
     * 把16进制转换成字节码
     *
     * @param hex
     * @return
     */
    public static byte[] hex2byte(String hex) {
        byte[] bts = new byte[hex.length() / 2];
        for (int i = 0; i < bts.length; i++) {
            bts[i] = (byte) Integer.parseInt(hex.substring(2 * i, 2 * i + 2),
                    16);
        }
        return bts;
    }

    /**
     * 转换数字为固定长度的字符串
     *
     * @param length
     *            希望返回的字符串长度
     * @param data
     *            传入的数值
     * @return
     */
    public static String getStringByInt(int length, String data) {
        String s_data = "";
        int datalength = data.length();
        if (length > 0 && length >= datalength) {
            for (int i = 0; i < length - datalength; i++) {
                s_data += "0";
            }
            s_data += data;
        }

        return s_data;
    }

    /**
     * 判断是否位数字,并可为空
     *
     * @param src
     * @return
     */
    public static boolean isNumericAndCanNull(String src) {
        Pattern numericPattern = Pattern.compile("^[0-9]+$");
        if (src == null || src.equals(""))
            return true;
        boolean return_value = false;
        if (src != null && src.length() > 0) {
            Matcher m = numericPattern.matcher(src);
            if (m.find()) {
                return_value = true;
            }
        }
        return return_value;
    }

    /**
     *
     * @param src
     * @return
     */
    public static boolean isFloatAndCanNull(String src) {
        Pattern numericPattern = Pattern
                .compile("^(([0-9]+\\.[0-9]*[1-9][0-9]*)|([0-9]*[1-9][0-9]*\\.[0-9]+)|([0-9]*[1-9][0-9]*))$");
        if (src == null || src.equals(""))
            return true;
        boolean return_value = false;
        if (src != null && src.length() > 0) {
            Matcher m = numericPattern.matcher(src);
            if (m.find()) {
                return_value = true;
            }
        }
        return return_value;
    }

    public static boolean isNotEmpty(String str) {
        if (str != null && !str.equals(""))
            return true;
        else
            return false;
    }

    public static boolean isDate(String date) {
        String regEx = "\\d{4}-\\d{2}-\\d{2}\\s\\d{2}:\\d{2}:\\d{2}";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(date);
        boolean result = m.find();
        return result;
    }

    public static boolean isFormatDate(String date, String regEx) {
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(date);
        boolean result = m.find();
        return result;
    }

    /**
     * 根据指定整型list 组装成为一个字符串
     *
     * @param list
     * @return
     */
    public static String listToString(List<Integer> list) {
        String str = "";
        if (list != null && list.size() > 0) {
            for (int id : list) {
                str = str + id + ",";
            }
            if (!"".equals(str) && str.length() > 0)
                str = str.substring(0, str.length() - 1);
        }
        return str;
    }

    /**
     * 页面的非法字符检查
     *
     * @author shazao
     * @date 2007-11-29
     * @param str
     * @return
     */
    public static String replaceStr(String str) {
        if (str != null && str.length() > 0) {
            str = str.replaceAll("~", ",");
            str = str.replaceAll(" ", ",");
            str = str.replaceAll("　", ",");
            str = str.replaceAll(" ", ",");
            str = str.replaceAll("`", ",");
            str = str.replaceAll("!", ",");
            str = str.replaceAll("@", ",");
            str = str.replaceAll("#", ",");
            str = str.replaceAll("\\$", ",");
            str = str.replaceAll("%", ",");
            str = str.replaceAll("\\^", ",");
            str = str.replaceAll("&", ",");
            str = str.replaceAll("\\*", ",");
            str = str.replaceAll("\\(", ",");
            str = str.replaceAll("\\)", ",");
            str = str.replaceAll("-", ",");
            str = str.replaceAll("_", ",");
            str = str.replaceAll("=", ",");
            str = str.replaceAll("\\+", ",");
            str = str.replaceAll("\\{", ",");
            str = str.replaceAll("\\[", ",");
            str = str.replaceAll("\\}", ",");
            str = str.replaceAll("\\]", ",");
            str = str.replaceAll("\\|", ",");
            str = str.replaceAll("\\\\", ",");
            str = str.replaceAll(";", ",");
            str = str.replaceAll(":", ",");
            str = str.replaceAll("'", ",");
            str = str.replaceAll("\\\"", ",");
            str = str.replaceAll("<", ",");
            str = str.replaceAll(">", ",");
            str = str.replaceAll("\\.", ",");
            str = str.replaceAll("\\?", ",");
            str = str.replaceAll("/", ",");
            str = str.replaceAll("～", ",");
            str = str.replaceAll("`", ",");
            str = str.replaceAll("！", ",");
            str = str.replaceAll("＠", ",");
            str = str.replaceAll("＃", ",");
            str = str.replaceAll("＄", ",");
            str = str.replaceAll("％", ",");
            str = str.replaceAll("︿", ",");
            str = str.replaceAll("＆", ",");
            str = str.replaceAll("×", ",");
            str = str.replaceAll("（", ",");
            str = str.replaceAll("）", ",");
            str = str.replaceAll("－", ",");
            str = str.replaceAll("＿", ",");
            str = str.replaceAll("＋", ",");
            str = str.replaceAll("＝", ",");
            str = str.replaceAll("｛", ",");
            str = str.replaceAll("［", ",");
            str = str.replaceAll("｝", ",");
            str = str.replaceAll("］", ",");
            str = str.replaceAll("｜", ",");
            str = str.replaceAll("＼", ",");
            str = str.replaceAll("：", ",");
            str = str.replaceAll("；", ",");
            str = str.replaceAll("＂", ",");
            str = str.replaceAll("＇", ",");
            str = str.replaceAll("＜", ",");
            str = str.replaceAll("，", ",");
            str = str.replaceAll("＞", ",");
            str = str.replaceAll("．", ",");
            str = str.replaceAll("？", ",");
            str = str.replaceAll("／", ",");
            str = str.replaceAll("·", ",");
            str = str.replaceAll("￥", ",");
            str = str.replaceAll("……", ",");
            str = str.replaceAll("（", ",");
            str = str.replaceAll("）", ",");
            str = str.replaceAll("——", ",");
            str = str.replaceAll("-", ",");
            str = str.replaceAll("【", ",");
            str = str.replaceAll("】", ",");
            str = str.replaceAll("、", ",");
            str = str.replaceAll("”", ",");
            str = str.replaceAll("’", ",");
            str = str.replaceAll("《", ",");
            str = str.replaceAll("》", ",");
            str = str.replaceAll("“", ",");
            str = str.replaceAll("。", ",");
        }
        return str;
    }

    /**
     * 全角字符变半角字符
     *
     * @author shazao
     * @date 2008-04-03
     * @param str
     * @return
     */
    public static String full2Half(String str) {
        if (str == null || "".equals(str))
            return "";
        StringBuffer sb = new StringBuffer();

        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);

            if (c >= 65281 && c < 65373)
                sb.append((char) (c - 65248));
            else
                sb.append(str.charAt(i));
        }

        return sb.toString();

    }

    /**
     * 全角括号转为半角
     *
     * @author shazao
     * @date 2007-11-29
     * @param str
     * @return
     */
    public static String replaceBracketStr(String str) {
        if (str != null && str.length() > 0) {
            str = str.replaceAll("（", "(");
            str = str.replaceAll("）", ")");
        }
        return str;
    }

    /**
     * 分割字符，从开始到第一个split字符串为止
     *
     * @param src
     *            源字符串
     * @param split
     *            截止字符串
     * @return
     */
    public static String subStr(String src, String split) {
        if (!isEmpty(src)) {
            int index = src.indexOf(split);
            if (index >= 0) {
                return src.substring(0, index);
            }
        }
        return src;
    }

    /**
     * 取url里的keyword（可选择参数）参数，用于整站搜索整合
     *
     * @author huoshao
     * @param params
     * @param qString
     * @return
     */
    public static String getKeyWord(String params, String qString) {
        String keyWord = "";
        if (qString != null) {
            String param = params + "=";
            int i = qString.indexOf(param);
            if (i != -1) {
                int j = qString.indexOf("&", i + param.length());
                if (j > 0) {
                    keyWord = qString.substring(i + param.length(), j);
                }
            }
        }
        return keyWord;
    }

    /**
     * 解析字符串返回map键值对(例：a=1&b=2 => a=1,b=2)
     *
     * @param query
     *            源参数字符串
     * @param split1
     *            键值对之间的分隔符（例：&）
     * @param split2
     *            key与value之间的分隔符（例：=）
     * @param dupLink
     *            重复参数名的参数值之间的连接符，连接后的字符串作为该参数的参数值，可为null
     *            null：不允许重复参数名出现，则靠后的参数值会覆盖掉靠前的参数值。
     * @return map
     * @author sky
     */
    @SuppressWarnings("unchecked")
    public static Map<String, String> parseQuery(String query, char split1,
                                                 char split2, String dupLink) {
        if (!isEmpty(query) && query.indexOf(split2) > 0) {
            Map<String, String> result = new HashMap();

            String name = null;
            String value = null;
            String tempValue = "";
            int len = query.length();
            for (int i = 0; i < len; i++) {
                char c = query.charAt(i);
                if (c == split2) {
                    value = "";
                } else if (c == split1) {
                    if (!isEmpty(name) && value != null) {
                        if (dupLink != null) {
                            tempValue = result.get(name);
                            if (tempValue != null) {
                                value += dupLink + tempValue;
                            }
                        }
                        result.put(name, value);
                    }
                    name = null;
                    value = null;
                } else if (value != null) {
                    value += c;
                } else {
                    name = (name != null) ? (name + c) : "" + c;
                }
            }

            if (!isEmpty(name) && value != null) {
                if (dupLink != null) {
                    tempValue = result.get(name);
                    if (tempValue != null) {
                        value += dupLink + tempValue;
                    }
                }
                result.put(name, value);
            }

            return result;
        }
        return null;
    }

    /**
     * 将list 用传入的分隔符组装为String
     *
     * @param list
     * @param slipStr
     * @return String
     */
    @SuppressWarnings("unchecked")
    public static String listToStringSlipStr(List list, String slipStr) {
        StringBuffer returnStr = new StringBuffer();
        if (list != null && list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                returnStr.append(list.get(i)).append(slipStr);
            }
        }
        if (returnStr.toString().length() > 0)
            return returnStr.toString().substring(0,
                    returnStr.toString().lastIndexOf(slipStr));
        else
            return "";
    }

    /**
     * 获取从start开始用*替换len个长度后的字符串
     *
     * @param str
     *            要替换的字符串
     * @param start
     *            开始位置
     * @param len
     *            长度
     * @return 替换后的字符串
     */
    public static String getMaskStr(String str, int start, int len) {
        if (StringUtil.isEmpty(str)) {
            return str;
        }
        if (str.length() < start) {
            return str;
        }

        // 获取*之前的字符串
        String ret = str.substring(0, start);

        // 获取最多能打的*个数
        int strLen = str.length();
        if (strLen < start + len) {
            len = strLen - start;
        }

        // 替换成*
        for (int i = 0; i < len; i++) {
            ret += "*";
        }

        // 加上*之后的字符串
        if (strLen > start + len) {
            ret += str.substring(start + len);
        }

        return ret;
    }

    /**
     * 根据传入的分割符号,把传入的字符串分割为List字符串
     *
     * @param slipStr
     *            分隔的字符串
     * @param src
     *            字符串
     * @return 列表
     */
    public static List<String> stringToStringListBySlipStr(String slipStr,
                                                           String src) {

        if (src == null)
            return null;
        List<String> list = new ArrayList<String>();
        String[] result = src.split(slipStr);
        for (int i = 0; i < result.length; i++) {
            list.add(result[i]);
        }
        return list;
    }

    /**
     * 截取字符串
     *
     * @param str
     *            原始字符串
     * @param len
     *            要截取的长度
     * @param tail
     *            结束加上的后缀
     * @return 截取后的字符串
     */
    public static String getHtmlSubString(String str, int len, String tail) {
        if (str == null || str.length() <= len) {
            return str;
        }
        int length = str.length();
        char c = ' ';
        String tag = null;
        String name = null;
        int size = 0;
        String result = "";
        boolean isTag = false;
        List<String> tags = new ArrayList<String>();
        int i = 0;
        for (int end = 0, spanEnd = 0; i < length && len > 0; i++) {
            c = str.charAt(i);
            if (c == '<') {
                end = str.indexOf('>', i);
            }

            if (end > 0) {
                // 截取标签
                tag = str.substring(i, end + 1);
                int n = tag.length();
                if (tag.endsWith("/>")) {
                    isTag = true;
                } else if (tag.startsWith("</")) { // 结束符
                    name = tag.substring(2, end - i);
                    size = tags.size() - 1;
                    // 堆栈取出html开始标签
                    if (size >= 0 && name.equals(tags.get(size))) {
                        isTag = true;
                        tags.remove(size);
                    }
                } else { // 开始符
                    spanEnd = tag.indexOf(' ', 0);
                    spanEnd = spanEnd > 0 ? spanEnd : n;
                    name = tag.substring(1, spanEnd);
                    if (name.trim().length() > 0) {
                        // 如果有结束符则为html标签
                        spanEnd = str.indexOf("</" + name + ">", end);
                        if (spanEnd > 0) {
                            isTag = true;
                            tags.add(name);
                        }
                    }
                }
                // 非html标签字符
                if (!isTag) {
                    if (n >= len) {
                        result += tag.substring(0, len);
                        break;
                    } else {
                        len -= n;
                    }
                }

                result += tag;
                isTag = false;
                i = end;
                end = 0;
            } else { // 非html标签字符
                len--;
                result += c;
            }
        }
        // 添加未结束的html标签
        for (String endTag : tags) {
            result += "</" + endTag + ">";
        }
        if (i < length) {
            result += tail;
        }
        return result;
    }

    /**
     * 压缩字符串 如：abcccd 压缩为abcd
     * @param str
     *            需要被压缩的字符串
     * @param c
     *            需要压缩的字符
     * @return 压缩后的字符串
     */
    public static String compressChars(final String str, final char c) {
        return jodd.util.StringUtil.compressChars(str, c);
    }

    /**
     * 统计字符串中包含的子字符串
     *
     * @param str
     *            需要被统计的字符串
     * @param sub
     *            需要统计的子字符串
     * @param startIndex
     *            开始统计的位置
     * @param ignoreCase
     *            是否忽略大小写
     * @return 统计的数量
     */
    public static int count(final String str, final String sub, final int startIndex, final boolean ignoreCase) {
        if (ignoreCase) {
            return jodd.util.StringUtil.countIgnoreCase(str.substring(startIndex), sub);
        } else {
            return jodd.util.StringUtil.count(str, sub, startIndex);
        }
    }

    /**
     * 切除字符串
     *
     * @param str
     *            需要被切除的字符串
     * @param prefix
     *            需要切掉的前缀 可以为null
     * @param suffix
     *            需要切掉的后缀 可以为null
     * @return 切除后的字符串
     */
    public static String cut(final String str, final String prefix, final String suffix) {
        String source = str;
        if (jodd.util.StringUtil.isNotEmpty(prefix)) {
            source = jodd.util.StringUtil.cutPrefix(source, prefix);
        }
        if (jodd.util.StringUtil.isNotEmpty(suffix)) {
            source = jodd.util.StringUtil.cutSuffix(source, suffix);
        }
        return source;
    }

    /**
     * cut字符串
     */
    public static String cutFrom(final String str, final String substring) {
        return jodd.util.StringUtil.cutFromIndexOf(str, substring);
    }

    /**
     * cut字符串
     */
    public static String cutTo(final String str, final String substring) {
        return jodd.util.StringUtil.cutToIndexOf(str, substring);
    }

    /**
     * join字符串
     */
    /*public static String join(final Iterable<?> elements, final String separator) {
        return jodd.util.StringUtil.join(elements, separator);
    }*/

    /**
     * 是否为null或者""
     *
     * @param str
     *            需要判断的字符串
     * @return null或者""返回true
     */
    public static boolean isNullOrEmpty(final String str) {
        return jodd.util.StringUtil.isEmpty(str);
    }

    /**
     * 是否为null或者""或者空白字符
     *
     * @param str
     *            需要判断的字符串
     * @return null或者""或者空白字符返回true
     */
    public static boolean isNullOrBlank(final String str) {
        return jodd.util.StringUtil.isBlank(str);
    }

    /**
     * 是否为null或者""或者空白字符
     *
     * @param str
     *            需要判断的字符串
     * @return null或者""或者空白字符返回true
     */
    public static boolean isNotNullOrBlank(final String str) {
        return !jodd.util.StringUtil.isBlank(str);
    }

    /**
     * 取两个字符串前面最大的相同部分
     *
     * @param str1
     *            字符串1
     * @param str2
     *            字符串2
     * @return 前面相同的部分
     */
    public static String maxCommonPrefix(final String str1, final String str2) {
        return jodd.util.StringUtil.maxCommonPrefix(str1, str2);
    }

    /**
     * 添加前缀，如果没有前缀添加前缀
     *
     * @param str
     *            需要处理的字符串
     * @param prefix
     *            前缀
     * @return 添加前缀后的字符串
     */
    public static String prefix(final String str, final String prefix) {
        return jodd.util.StringUtil.prefix(str, prefix);
    }

    /**
     * 反转字符串
     *
     * @param str
     *            需要处理的字符串
     * @return 反转后的字符串
     */
    public static String reverse(final String str) {
        return jodd.util.StringUtil.reverse(str);
    }

    /**
     * 添加后缀，如果没有后缀添加后缀
     *
     * @param str
     *            需要处理的字符串
     * @param suffix
     *            后缀
     * @return 添加后缀后的字符串
     */
    public static String suffix(final String str, final String suffix) {
        return jodd.util.StringUtil.suffix(str, suffix);
    }

    /**
     * surround
     *
     * @param str
     *            需要处理的字符串
     * @param prefix
     *            前缀
     * @param suffix
     *            后缀
     * @return surround后的字符串
     */
    public static String surround(final String str, final String prefix, final String suffix) {
        return jodd.util.StringUtil.surround(str, prefix, suffix);
    }

    /**
     * 去掉字符串左侧的空白字符
     *
     * @param str
     *            需要处理的字符串
     * @return 去掉左侧空白字符的字符串
     */
    public static String trimLeft(final String str) {
        return jodd.util.StringUtil.trimLeft(str);
    }

    /**
     * 去掉字符串右侧的空白字符
     *
     * @param str
     *            需要处理的字符串
     * @return 去掉右侧空白字符的字符串
     */
    public static String trimRight(final String str) {
        return jodd.util.StringUtil.trimRight(str);
    }

    /**
     * 将字符串第一个字母转化为小写
     *
     * @param str
     *            需要被转换的字符串
     * @return 转换后的字符串
     */
    public static String uncapitalize(final String str) {
        return jodd.util.StringUtil.uncapitalize(str);
    }

    /**
     * 替换为手机识别的HTML，去掉样式及属性，保留回车。
     * @param txt
     * @return
     */
    public static String toHtml(String txt){
        if (txt == null){
            return "";
        }
        return org.apache.commons.lang3.StringUtils.replace(
                org.apache.commons.lang3.StringUtils.replace(StringEscapeUtils.escapeHtml4(txt), "\n", "<br/>"),
                "\t", "&nbsp; &nbsp; ");
    }

}

package com.hrp.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * MoneyUtil
 *
 * @author KVLT
 * @date 2017-03-14.
 */
public class MoneyUtil {

    /**
     * @title 获取格式化的人民币（四舍五入）
     * @author chanson
     * @param money  待处理的人民币
     * @param scale  小数点后保留的位数
     * @param divisor 格式化值（万，百万，亿等等）
     * @return
     */
    public String getFormatMoney(double money, int scale, double divisor){
        if(divisor == 0){return "0.00";}
        if(scale < 0){return "0.00";}
        BigDecimal moneyBD = new BigDecimal(money);
        BigDecimal divisorBD = new BigDecimal(divisor);
        //RoundingMode.HALF_UP = 2
        return moneyBD.divide(divisorBD, scale, RoundingMode.HALF_UP).toString();
    }

    /**
     * @title 获取会计格式的人民币（四舍五入）——添加会计标识：','
     * @author chanson
     * @param money  待处理的人民币
     * @param scale  小数点后保留的位数
     * @param divisor 格式化值（万，百万，亿等等）
     * @return
     */
    public String getAccountantMoney(double money, int scale, double divisor){
        String disposeMoneyStr = getFormatMoney(money, scale, divisor);
        //小数点处理
        int dotPosition = disposeMoneyStr.indexOf(".");
        String exceptDotMoeny = null;//小数点之前的字符串
        String dotMeony = null;//小数点之后的字符串
        if(dotPosition > 0){
            exceptDotMoeny = disposeMoneyStr.substring(0,dotPosition);
            dotMeony = disposeMoneyStr.substring(dotPosition);
        }else{
            exceptDotMoeny = disposeMoneyStr;
        }
        //负数处理
        int negativePosition = exceptDotMoeny.indexOf("-");
        if(negativePosition == 0){
            exceptDotMoeny = exceptDotMoeny.substring(1);
        }
        StringBuffer reverseExceptDotMoney = new StringBuffer(exceptDotMoeny);
        reverseExceptDotMoney.reverse();//字符串倒转
//      reverse(reverseExceptDotMoeny);
        char[] moneyChar = reverseExceptDotMoney.toString().toCharArray();
        StringBuffer returnMeony = new StringBuffer();//返回值
        for(int i = 0; i < moneyChar.length; i++){
            if(i != 0 && i % 3 == 0){
                returnMeony.append(",");//每隔3位加','
            }
            returnMeony.append(moneyChar[i]);
        }
        returnMeony.reverse();//字符串倒转
//      reverse(returnMeony);
        if(dotPosition > 0){
            returnMeony.append(dotMeony);
        }
        if(negativePosition == 0){
            return "-" + returnMeony.toString();
        }else{
            return returnMeony.toString();
        }
    }

    /**
     *
     * @param disposeMoneyStr 需要格式化的字符串
     * @param dotCount 保留的小数点位数
     * @return
     */
    public String getAccountantMoney(String disposeMoneyStr, int dotCount){
        //小数点处理
        int dotPosition = disposeMoneyStr.indexOf(".");

        // 对数据进行截取/拼接操作
        int currentCount = disposeMoneyStr.length() - dotPosition - 1;
        if (dotCount > currentCount) {
            for (int i=0; i<dotCount-currentCount; i++) {
                disposeMoneyStr += "0";
            }
        } else {
            disposeMoneyStr = disposeMoneyStr.substring(0, disposeMoneyStr.length() - (currentCount - dotCount));
        }

        String exceptDotMoeny = null;//小数点之前的字符串
        String dotMeony = null;//小数点之后的字符串
        if(dotPosition > 0){
            exceptDotMoeny = disposeMoneyStr.substring(0,dotPosition);
            dotMeony = disposeMoneyStr.substring(dotPosition);
        }else{
            exceptDotMoeny = disposeMoneyStr;
        }
        //负数处理
        int negativePosition = exceptDotMoeny.indexOf("-");
        if(negativePosition == 0){
            exceptDotMoeny = exceptDotMoeny.substring(1);
        }
        StringBuffer reverseExceptDotMoney = new StringBuffer(exceptDotMoeny);
        reverseExceptDotMoney.reverse();//字符串倒转
//      reverse(reverseExceptDotMoeny);
        char[] moneyChar = reverseExceptDotMoney.toString().toCharArray();
        StringBuffer returnMeony = new StringBuffer();//返回值
        for(int i = 0; i < moneyChar.length; i++){
            if(i != 0 && i % 3 == 0){
                returnMeony.append(",");//每隔3位加','
            }
            returnMeony.append(moneyChar[i]);
        }
        returnMeony.reverse();//字符串倒转
//      reverse(returnMeony);
        if(dotPosition > 0){
            returnMeony.append(dotMeony);
        }
        if(negativePosition == 0){
            return "-" + returnMeony.toString();
        }else{
            return returnMeony.toString();
        }
    }

    public static void main(String[] args) {
        double money = -1269486459.86;
        int scale = 2;
        double divisor = 10000.00;
        System.out.println("原货币值: "+money);
        MoneyUtil util = new MoneyUtil();
//      System.out.println("货币值: "+utils.getAccountantMoney(money, scale, 1));
        String formatMeony = util.getFormatMoney(money, scale, divisor);
        System.out.println("格式化货币值: "+formatMeony+"万元");
        String accountantMoney = util.getAccountantMoney(money, scale, divisor);
        System.out.println("会计货币值: "+accountantMoney+"万元");
        System.out.println(util.getAccountantMoney("46891345.235", 3));
    }
}

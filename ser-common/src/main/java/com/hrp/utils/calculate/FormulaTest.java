package com.hrp.utils.calculate;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

/**
 * FormulaTest
 *
 * @author KVLT
 * @date 2017-03-14.
 */
public class FormulaTest {
    @Test
    public void testFormula() {
        // 基础数据
        Map<String, BigDecimal> values = new HashMap<String, BigDecimal>();
        values.put("dddd", BigDecimal.valueOf(56d));

        // 需要依赖的其他公式
        Map<String, String> formulas = new HashMap<String, String>();
        formulas.put("eeee", "#{dddd}*20");

        // 需要计算的公式
        String expression = "#{eeee}*-12+13-#{dddd}+24";

        //BigDecimal result = FormulaParser.parse(expression, formulas, values);
        //System.out.println(result.doubleValue());
        //Assert.assertEquals(result, BigDecimal.valueOf(-13459.0));

        expression = "#[dddd]/10";
        //expression = "#[qjzb0101]-#[qjzb0102]";
        BigDecimal result = null;
        try {
            result = FormulaParser.parse(expression, values);
        } catch (Exception e) {
            if (e.getMessage().equals("devide by zero")) {
                System.out.println("/zero");
            }
        }
        System.out.println(result.doubleValue());
    }
}

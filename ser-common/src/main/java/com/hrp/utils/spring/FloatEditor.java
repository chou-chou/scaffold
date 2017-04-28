package com.hrp.utils.spring;

import org.springframework.beans.propertyeditors.PropertiesEditor;

/** 
 * @ClassName	FloatEditor.java
 * @Description 
 * @Author		kevin 
 * @CreateTime  2016年7月19日 下午10:55:21
 * @Version 	V1.0    
 */
public class FloatEditor extends PropertiesEditor {

	@Override    
    public void setAsText(String text) throws IllegalArgumentException {    
        if (text == null || text.equals("")) {    
            text = "0";    
        }    
        setValue(Float.parseFloat(text));    
    }    
    
    @Override    
    public String getAsText() {    
        return getValue().toString();    
    }  
    
}
 
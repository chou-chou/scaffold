package com.hrp.utils.spring;

import org.springframework.beans.propertyeditors.PropertiesEditor;

/** 
 * @ClassName	IntegerEditor.java
 * @Description 
 * @Author		kevin 
 * @CreateTime  2016年7月19日 下午10:55:50
 * @Version 	V1.0    
 */
public class IntegerEditor extends PropertiesEditor {

	@Override    
    public void setAsText(String text) throws IllegalArgumentException {    
        if (text == null || text.equals("")) {    
            text = "0";    
        }    
        setValue(Integer.parseInt(text));    
    }    
    
    @Override    
    public String getAsText() {    
        return getValue().toString();    
    }  
    
}
 
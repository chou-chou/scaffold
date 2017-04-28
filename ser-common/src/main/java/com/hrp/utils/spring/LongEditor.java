package com.hrp.utils.spring;

import org.springframework.beans.propertyeditors.PropertiesEditor;

/** 
 * @ClassName	LongEditor.java
 * @Description 
 * @Author		kevin 
 * @CreateTime  2016年7月19日 下午10:54:47
 * @Version 	V1.0    
 */
public class LongEditor extends PropertiesEditor {

	@Override    
    public void setAsText(String text) throws IllegalArgumentException {    
        if (text == null || text.equals("")) {    
            text = "0";    
        }    
        setValue(Long.parseLong(text));    
    }    
    
    @Override    
    public String getAsText() {    
        return getValue().toString();    
    }  
    
}
 
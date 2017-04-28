package com.hrp.utils.spring;

import java.beans.PropertyEditorSupport;

/**
 * @ClassName:		SpringStringEmptyEditor
 * @Description:	将空串转换成null
 * @Author:    		kevin
 * @CreateDte:		2016年7月17日 下午5:28:00
 */
public class SpringStringEmptyEditor extends PropertyEditorSupport {
	
	@Override
	public void setAsText(String text) {
		if (text == null || "".equals(text)) {
			setValue(null);
		} else {
			setValue(text);
		}
	}

	@Override
	public String getAsText() {
		Object value = getValue();
		return (value != null ? value.toString() : "");
	}
}

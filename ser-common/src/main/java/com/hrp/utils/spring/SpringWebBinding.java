package com.hrp.utils.spring;

import java.text.SimpleDateFormat;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.support.WebBindingInitializer;
import org.springframework.web.context.request.WebRequest;

import com.hrp.utils.constants.ConstantDateFormatTypes;

/**
 * @ClassName:		SpringWebBinding
 * @Description:	spring mvc form 类型转换
 * @Author:    		kevin
 * @CreateDte:		2016年7月17日 下午5:28:17
 */
public class SpringWebBinding implements WebBindingInitializer {
	
	/**
	 * form 类型转换
	 */
	public final void initBinder(final WebDataBinder binder, final WebRequest request) {
		// 1. 使用spring自带的CustomDateEditor
		SimpleDateFormat dateFormat = new SimpleDateFormat(ConstantDateFormatTypes.YYYY_MM_DD);
		dateFormat.setLenient(false);
		
		SimpleDateFormat datetimeFormat = new SimpleDateFormat(ConstantDateFormatTypes.YYYY_MM_DD_HH24_MM_SS);
		datetimeFormat.setLenient(false);
		
		binder.registerCustomEditor(java.util.Date.class, new CustomDateEditor(dateFormat, true));
		binder.registerCustomEditor(java.sql.Timestamp.class, new CustomTimestampEditor(datetimeFormat, true));
		
		// 2. 自定义的PropertyEditorSupport
		//binder.registerCustomEditor(Date.class, new DateConvertEditor());
		// 字符串trim
		binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
		// 对具体字段进行转换
	}
}

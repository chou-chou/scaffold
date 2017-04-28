package com.hrp.query.filter;

import java.util.Collections;
import java.util.List;

import com.hrp.utils.lang.StringUtil;

public class FilterVariable extends FilterFragment {

	public FilterVariable(String propertyName) {
		this(null, propertyName);
	}

	public FilterVariable(String alias, String propertyName) {
		this.propertyName = propertyName;
		setTableAlias(alias);
	}

	public String getString() {
		if (StringUtil.isNotBlank(alias))
			return (new StringBuilder()).append(alias).append(".").append(propertyName).toString();
		else
			return (new StringBuilder()).append(propertyName).toString();
	}
 
	public List getValues() {
		return Collections.EMPTY_LIST;
	}

	public void setTableAlias(String alias) {
		if (StringUtil.isNotBlank(alias))
			this.alias = alias;
	}

	private final String propertyName;
	private String alias;
}
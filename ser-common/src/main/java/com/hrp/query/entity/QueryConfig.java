package com.hrp.query.entity;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.hrp.utils.lang.StringUtil;

@JsonIgnoreProperties(value = { "hibernateLazyInitializer", "handler", "fieldHandler" })
public class QueryConfig implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4716945141383027168L;

	@JsonProperty("id")
	private String id;
	/**
	 * 用户id
	 */
	private String userid;
	/**
	 * 配置XML queryid
	 */
	private String queryId;
	/**
	 * 界面url
	 */
	private String pageName;
	
	/**
	 * 显示的Column key
	 */
	private String columnsName;


	public List<String> getColumns() {
		if (StringUtil.isBlank(this.columnsName) == true) {
			return null;
		}
		return Arrays.asList(this.columnsName.split(","));
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getQueryId() {
		return queryId;
	}

	public void setQueryId(String queryId) {
		this.queryId = queryId;
	}

	public String getPageName() {
		return pageName;
	}

	public void setPageName(String pageName) {
		this.pageName = pageName;
	}


	public String getColumnsName() {
		return columnsName;
	}

	public void setColumnsName(String columnsName) {
		this.columnsName = columnsName;
	}


}

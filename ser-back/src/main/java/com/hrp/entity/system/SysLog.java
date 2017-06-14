package com.hrp.entity.system;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * SysLog
 * 操作日志类
 * @author KVLT
 * @date 2017-03-14.
 */
public class SysLog implements Serializable {

    private static final long serialVersionUID = -5034052907038321178L;

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    /*** 日志ID */
    private Integer logId;

    /*** 用户账号 */
    private String username;

    /*** 创建时间 */
    private Date createDate;

    /*** 操作 */
    private String handle;

    /*** url */
    private String url;

    /*** 操作的方法 */
    private String method;

    /*** 参数 */
    private String parameter;

    /*** ip地址 */
    private String ip;

    /*** 开始时间 */
    private String beginDate;

    /*** 结束时间 */
    private String endDate;

    public Integer getLogId() {
        return logId;
    }

    public void setLogId(Integer logId) {
        this.logId = logId;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getHandle() {
        return handle;
    }

    public void setHandle(String handle) {
        this.handle = handle;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getParameter() {
        return parameter;
    }

    public void setParameter(String parameter) {
        this.parameter = parameter;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(String beginDate) {
        this.beginDate = beginDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    @Override
    public String toString() {
        return "SysLog{" +
                "logId=" + logId +
                ", username='" + username + '\'' +
                ", createDate=" + createDate +
                ", handle='" + handle + '\'' +
                ", url='" + url + '\'' +
                ", method='" + method + '\'' +
                ", parameter='" + parameter + '\'' +
                ", ip='" + ip + '\'' +
                ", beginDate='" + beginDate + '\'' +
                ", endDate='" + endDate + '\'' +
                '}';
    }
}

package com.hrp.entity.system;

import java.io.Serializable;

/**
 * Authority
 * 项目权限实体类
 * @author KVLT
 * @date 2017-04-17.
 */
public class Authority implements Serializable {

    private Integer id;
    private String address;  // MAC地址（加密）
    private String expire;  // 有效期（加密）
    private boolean enabled;  // 是否有效

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getExpire() {
        return expire;
    }

    public void setExpire(String expire) {
        this.expire = expire;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    @Override
    public String toString() {
        return "Authority{" +
                "id=" + id +
                ", address='" + address + '\'' +
                ", expire='" + expire + '\'' +
                ", enabled=" + enabled +
                '}';
    }
}

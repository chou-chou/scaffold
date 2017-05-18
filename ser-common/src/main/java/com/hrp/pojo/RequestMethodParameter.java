package com.hrp.pojo;

/**
 * RequestMethodParameter
 *
 * @author KVLT
 * @date 2017-05-18.
 */
public class RequestMethodParameter {

    private String annotation;
    private String name;
    private String type;

    public String getAnnotation() {
        return annotation;
    }

    public void setAnnotation(String annotation) {
        this.annotation = annotation;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "RequestMethodParameter{" +
                "annotation='" + annotation + '\'' +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}

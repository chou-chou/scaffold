package com.hrp.pojo;

import com.hrp.annotation.MvcDocument;

import java.util.List;

/**
 * RequestToMethodItem
 *
 * @author KVLT
 * @date 2017-05-18.
 */
public class RequestToMethodItem implements Comparable<RequestToMethodItem> {

    public String controllerName; // 类名
    public String requestType;
    public String responseType;
    public String requestUrl;
    public String requestMethod;
    public MvcDocument document;
    public List<RequestMethodParameter> parameters;

    public RequestToMethodItem() {}

    public RequestToMethodItem(String requestUrl, String requestType, String controllerName, String requestMethodName)
    {
        this.requestUrl = requestUrl;
        this.requestType = requestType;
        this.controllerName = controllerName;
        this.requestMethod = requestMethodName;
    }

    public String getControllerName() {
        return controllerName;
    }

    public void setControllerName(String controllerName) {
        this.controllerName = controllerName;
    }

    public String getRequestMethod() {
        return requestMethod;
    }

    public void setRequestMethod(String requestMethod) {
        this.requestMethod = requestMethod;
    }

    public String getRequestType() {
        return requestType;
    }

    public void setRequestType(String requestType) {
        this.requestType = requestType;
    }

    public String getRequestUrl() {
        return requestUrl.replace("[","").replace("]", "");
    }

    public void setRequestUrl(String requestUrl) {
        this.requestUrl = requestUrl;
    }

    public String getResponseType() {
        return responseType;
    }

    public void setResponseType(String responseType) {
        this.responseType = responseType;
    }

    public List<RequestMethodParameter> getParameters() {
        return parameters;
    }

    public void setParameters(List<RequestMethodParameter> parameters) {
        this.parameters = parameters;
    }

    public MvcDocument getDocument() {
        return document;
    }

    public void setDocument(MvcDocument document) {
        this.document = document;
    }

    @Override
    public int compareTo(RequestToMethodItem o) {
        int i = this.getRequestUrl().compareTo(o.getRequestUrl());
        if (0 != i)  return i;
        else {
            i = this.getRequestType().compareTo(o.getRequestType());
        }
        return i;
    }

    public String getA() {
        String methodName = requestMethod.split("<br>")[2];
        String classPath = requestMethod.split("\\(")[0];

        String[] packageSplit = classPath.split("\\.");
        StringBuffer sb = new StringBuffer();

        for (int i = 0; i < packageSplit.length; i++) {
            sb.append(packageSplit[i]);
            if (i < packageSplit.length - 1) {
                if (i == packageSplit.length - 2) {
                    sb.append(".html#");
                } else {
                    sb.append("/");
                }
            }
        }

        sb.append("(");
        String methodPath = methodName.split("\\(")[1];
        sb.append(methodPath.replace(",", ", " ));

        String href = sb.toString();
        String a = "<br>\n<a href='http://localhost:8080/SER/doc/" + href
            + "' target='_blank'>详细接口文档</a>";
        return a;
    }

    public String getResponseParams() {
        MvcDocument document = this.getDocument();
        if (null == document) {
            return "";
        }

        String[] params = document.params();
        StringBuilder sb = new StringBuilder();
        for (String param : params) {
            sb.append(param).append("<hr>\n");
        }

        return sb.toString();
    }

    @Override
    public String toString() {
        return "RequestToMethodItem{" +
                "controllerName='" + controllerName + '\'' +
                ", requestType='" + requestType + '\'' +
                ", responseType='" + responseType + '\'' +
                ", requestUrl='" + requestUrl + '\'' +
                ", requestMethod='" + requestMethod + '\'' +
                ", document=" + document +
                ", parameters=" + parameters +
                '}';
    }
}

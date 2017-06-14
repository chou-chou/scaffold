package com.hrp.rest.utils;

import org.apache.commons.collections.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.Map;
import java.util.Set;

/**
 * HttpClientUtil
 *
 * @author KVLT
 * @date 2017-06-02.
 */
public class HttpClientUtil {

    private static final Logger logger = LoggerFactory.getLogger(HttpClientUtil.class);

    /**
     * post请求
     *
     * @param url
     * @param formParams
     * @return
     */
    public static String doPost(String url, Map<String, String> formParams) {
        if (MapUtils.isEmpty(formParams)) {
            return doPost(url);
        }

        try {
            MultiValueMap<String, String> requestEntity = new LinkedMultiValueMap<>();
            Set<String> set = formParams.keySet();
            for (String key : set) {
                requestEntity.add(key, MapUtils.getString(formParams, key, ""));
            }
            return RestClient.getClient().postForObject(url, requestEntity, String.class);
        } catch (Exception e) {
            logger.error("POST请求出错：{}", url, e);
        }

        return "";
    }

    /**
     * post请求
     *
     * @param url
     * @return
     */
    public static String doPost(String url) {
        try {
            return RestClient.getClient().postForObject(url, HttpEntity.EMPTY, String.class);
        } catch (Exception e) {
            logger.error("POST请求出错：{}", url, e);
        }

        return "";
    }

    /**
     * get请求
     *
     * @param url
     * @return
     */
    public static String doGet(String url) {
        try {
            return RestClient.getClient().getForObject(url, String.class);
        } catch (Exception e) {
            logger.error("GET请求出错：{}", url, e);
        }

        return "";
    }
}

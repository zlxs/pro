package com.youyu.platform.gitlab.util;

import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author dongdong
 * @date 2018/5/22
 */
public class Query {

    private final Map<String, Object> parameters = new HashMap<>();

    private Query append(String name, String value) {
        this.parameters.put(name, value);
        return this;
    }

    public Query appendIf(String name, String value) {
        if (value != null) {
            this.append(name, value);
        }
        return this;
    }

    public Query appendIf(String name, Integer value) {
        if (value != null) {
            this.append(name, value.toString());
        }
        return this;
    }

    public Query appendIf(String name, Boolean value) {
        if (value != null) {
            this.append(name, value.toString());
        }
        return this;
    }

    public Map<String, Object> getParameterMap() {
        return this.parameters;
    }

    public String getUrlParameterPart() {

        StringBuffer stringBuffer = new StringBuffer();
        if (!CollectionUtils.isEmpty(parameters)) {
            stringBuffer.append("?");
            for (Map.Entry<String, Object> entry : parameters.entrySet()) {
                stringBuffer.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
            }
            stringBuffer.deleteCharAt(stringBuffer.length() - 1);
        }
        return stringBuffer.toString();
    }
}

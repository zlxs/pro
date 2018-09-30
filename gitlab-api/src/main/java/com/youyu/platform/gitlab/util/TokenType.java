package com.youyu.platform.gitlab.util;

/**
 * @author dongdong
 * @date 2018/5/21
 */
public enum TokenType {

    /**
     * PRIVATE_TOKEN 请求认证时，使用的token类型为private token
     * ACCESS_TOKEN 请求认证时，使用的token类型为access token
     * OAUTH2_TOKEN 请求认证时，使用oauth2方式进行认证
     */
    PRIVATE_TOKEN("PRIVATE-TOKEN", "%s"),
    ACCESS_TOKEN("PRIVATE-TOKEN", "%s"),
    OAUTH2_TOKEN("Authorization", "Bearer %s");

    private final String tokenHeaderName;
    private final String tokenHeaderFormat;

    TokenType(String tokenHeaderName, String tokenHeaderFormat) {
        this.tokenHeaderName = tokenHeaderName;
        this.tokenHeaderFormat = tokenHeaderFormat;
    }

    public String getTokenHeaderName() {
        return tokenHeaderName;
    }

    public String getTokenHeaderFormat() {
        return tokenHeaderFormat;
    }

}

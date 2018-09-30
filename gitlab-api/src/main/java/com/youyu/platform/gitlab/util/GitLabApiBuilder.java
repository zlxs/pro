package com.youyu.platform.gitlab.util;

import com.youyu.platform.gitlab.GitLabApi;
import com.youyu.platform.gitlab.impl.GitLabApiImpl;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

/**
 * @author dongdong
 * @date 2018/5/21
 */
public class GitLabApiBuilder {

    private String host;
    private String token;
    private TokenType tokenType;
    private RestTemplate restTemplate;


    public GitLabApiBuilder host(String host) {
        this.host = host;
        return this;
    }

    public GitLabApiBuilder token(String token) {
        this.token = token;
        return this;
    }

    public GitLabApiBuilder tokenType(TokenType tokenType) {
        this.tokenType = tokenType;
        return this;
    }

    public GitLabApiBuilder clientHttpRequestFactory(ClientHttpRequestFactory factory) {
        if (factory != null && restTemplate == null) {
            restTemplate = new RestTemplate(factory);
        }
        return this;
    }

    public GitLabApiBuilder restTemplate(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
        return this;
    }

    public GitLabApi build() {
        return new GitLabApiImpl(host, token, tokenType, restTemplate);
    }
}

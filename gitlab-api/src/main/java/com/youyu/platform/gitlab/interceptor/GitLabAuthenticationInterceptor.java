package com.youyu.platform.gitlab.interceptor;

import com.youyu.platform.gitlab.util.TokenType;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import java.io.IOException;

/**
 * @author dongdong
 * @date 2018/5/21
 */
public class GitLabAuthenticationInterceptor implements ClientHttpRequestInterceptor {

    private final TokenType tokenType;
    private final String token;

    public GitLabAuthenticationInterceptor(TokenType tokenType, String token) {
        this.tokenType = tokenType;
        this.token = token;
    }

    @Override
    public ClientHttpResponse intercept(HttpRequest httpRequest, byte[] bytes, ClientHttpRequestExecution clientHttpRequestExecution) throws IOException {
        HttpHeaders headers = httpRequest.getHeaders();
        headers.add(tokenType.getTokenHeaderName(), String.format(tokenType.getTokenHeaderFormat(), token));
        return clientHttpRequestExecution.execute(httpRequest, bytes);
    }
}

package com.youyu.platform.gitlab.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.youyu.platform.gitlab.GitLabApi;
import com.youyu.platform.gitlab.interceptor.GitLabAuthenticationInterceptor;
import com.youyu.platform.gitlab.models.*;
import com.youyu.platform.gitlab.util.Query;
import com.youyu.platform.gitlab.util.TokenType;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @author dongdong
 * @date 2018/5/21
 */
public class GitLabApiImpl implements GitLabApi {

    private final String apiToken;
    private final String apiPrefix = "/api/v4";
    private final String hostUrl;
    private final TokenType tokenType;

    private RestTemplate restTemplate;

    private GitLabApiImpl(String hostUrl, String apiToken, TokenType tokenType) {
        this.hostUrl = hostUrl.endsWith("/") ? hostUrl.replaceAll("/$", "") : hostUrl;
        this.apiToken = apiToken;
        this.tokenType = tokenType;
    }

    public GitLabApiImpl(String hostUrl, String apiToken, TokenType tokenType, RestTemplate restTemplate) {
        this(hostUrl, apiToken, tokenType);
        this.restTemplate = restTemplate;
        if (restTemplate == null) {
            this.restTemplate = createRestTemplate();
        }
        initTokenHeader();
    }

    private void initTokenHeader() {
        restTemplate.setInterceptors(Collections.singletonList(new GitLabAuthenticationInterceptor(tokenType, apiToken)));
    }

    private RestTemplate createRestTemplate() {
        return new RestTemplate(createClientHttpRequestFactory());
    }

    private ClientHttpRequestFactory createClientHttpRequestFactory() {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory(httpClient);
        return factory;
    }

    @Override
    public GitLabGroup createGroup(String name, String path, String description, GitLabVisibility visibility, Boolean lfsEnabled, Boolean requestAccessEnabled, String parentId) {
        Map bodyParameterMap = new Query()
                .appendIf("name", name)
                .appendIf("path", path)
                .appendIf("description", description)
                .appendIf("visibility", visibility != null ? visibility.toString() : null)
                .appendIf("lfs_enabled", lfsEnabled)
                .appendIf("request_access_enabled", requestAccessEnabled)
                .appendIf("parent_id", parentId)
                .getParameterMap();
        ResponseEntity<String> responseEntity = doPost(GitLabGroup.URL, bodyParameterMap, null);
        return JSON.parseObject(responseEntity.getBody(), GitLabGroup.class);
    }

    @Override
    public GitLabProject createProject(String name, String path, Integer namespaceId, String description, Boolean issueEnabled, Boolean mergeRequestEnabled,
                                       Boolean jobEnabled, Boolean wikiEnabled, Boolean snippetsEnabled, Boolean resolveOutdatedDiffDiscussions,
                                       Boolean containerRegistryEnabled, Boolean sharedRunnersEnabled,
                                       GitLabVisibility visibility, String importUrl, Boolean publicJobs, Boolean onlyAllowMergeIfPipelineSucceeds,
                                       Boolean onlyAllowMergeIfAllDiscussionsAreResolved, GitLabMergeMethod mergeMethod, Boolean lfsEnabled,
                                       Boolean requestAccessEnabled, Boolean printingMergeRequestLinkEnabled) {
        Map bodyParameterMap = new Query()
                .appendIf("name", name)
                .appendIf("path", path)
                .appendIf("namespace_id", namespaceId)
                .appendIf("description", description)
                .appendIf("issue_enabled", issueEnabled)
                .appendIf("merge_request_enabled", mergeRequestEnabled)
                .appendIf("job_enabled", jobEnabled)
                .appendIf("wiki_enabled", wikiEnabled)
                .appendIf("snippets_enabled", snippetsEnabled)
                .appendIf("resolve_outdated_diff_discussions", resolveOutdatedDiffDiscussions)
                .appendIf("container_registry_enabled", containerRegistryEnabled)
                .appendIf("shared_runners_enabled", sharedRunnersEnabled)
                .appendIf("visibility", visibility != null ? visibility.toString() : null)
                .appendIf("import_url", importUrl)
                .appendIf("public_jobs", publicJobs)
                .appendIf("only_allow_merge_if_pipeline_succeeds", onlyAllowMergeIfPipelineSucceeds)
                .appendIf("only_allow_merge_if_all_discussions_are_resolved", onlyAllowMergeIfAllDiscussionsAreResolved)
                .appendIf("merge_method", mergeMethod.toString())
                .appendIf("lfs_enabled", lfsEnabled)
                .appendIf("request_access_enabled", requestAccessEnabled)
                .appendIf("printing_merge_request_link_enabled", printingMergeRequestLinkEnabled)
                .getParameterMap();
        ResponseEntity<String> responseEntity = doPost(GitLabProject.URL, bodyParameterMap, null);
        return JSON.parseObject(responseEntity.getBody(), GitLabProject.class);
    }

    @Override
    public List<GitLabGroup> findGroups(Boolean allAvailable, Boolean owned) {
        String parameterPart = new Query()
                .appendIf("per_page", 10000)
                .appendIf("all_available", allAvailable)
                .appendIf("owned", owned).getUrlParameterPart();
        ResponseEntity<String> responseEntity = doGet(GitLabGroup.URL + parameterPart);
        return JSON.parseObject(responseEntity.getBody(), new TypeReference<List<GitLabGroup>>() {
        });
    }

    @Override
    public List<GitLabProject> findGroupProjects(String id, GitLabVisibility visibility, Boolean simple, Boolean owned, Boolean starred, Boolean archived) {
        String baseUrl = new StringBuffer().append(GitLabGroup.URL).append("/").append(id).append(GitLabProject.URL).toString();
        String parameterPart = new Query()
                .appendIf("per_page", 10000)
                .appendIf("visibility", visibility != null ? visibility.toString() : null)
                .appendIf("simple", simple)
                .appendIf("owned", owned)
                .appendIf("starred", starred)
                .appendIf("archived", archived)
                .getUrlParameterPart();
        ResponseEntity<String> responseEntity = doGet(baseUrl + parameterPart);
        return JSON.parseObject(responseEntity.getBody(), new TypeReference<List<GitLabProject>>() {
        });
    }

    @Override
    public GitLabGroupAndProjectMember addMemberToGroup(String id, String userId, GitLabAccessLevel accessLevel, String expiresAt) {
        String baseUrl = new StringBuffer().append(GitLabGroup.URL).append("/").append(id).append(GitLabGroupAndProjectMember.URL).toString();
        Map<String, Object> parameterMap = new Query()
                .appendIf("user_id", userId)
                .appendIf("access_level", accessLevel.accessValue)
                .appendIf("expires_at", expiresAt).getParameterMap();
        ResponseEntity<String> responseEntity = doPost(baseUrl, null, parameterMap);
        return JSON.parseObject(responseEntity.getBody(), GitLabGroupAndProjectMember.class);
    }

    @Override
    public GitLabGroupAndProjectMember addMemberToProject(String id, String userId, GitLabAccessLevel accessLevel, String expiresAt) {
        String baseUrl = new StringBuffer().append(GitLabProject.URL).append("/").append(id).append(GitLabGroupAndProjectMember.URL).toString();
        Map bodyParameterMap = new Query()
                .appendIf("user_id", userId)
                .appendIf("access_level", accessLevel.accessValue)
                .appendIf("expires_at", expiresAt)
                .getParameterMap();
        ResponseEntity<String> responseEntity = doPost(baseUrl, bodyParameterMap, null);
        return JSON.parseObject(responseEntity.getBody(), GitLabGroupAndProjectMember.class);
    }

    @Override
    public void updateMemberForProject(String id, String userId, GitLabAccessLevel accessLevel, String expiresAt) {
        String baseUrl = new StringBuffer().append(GitLabProject.URL).append("/").append(id).append(GitLabGroupAndProjectMember.URL).append("/").append(userId).toString();
        String parameterPart = new Query()
                .appendIf("access_level", accessLevel.accessValue)
                .appendIf("expires_at", expiresAt).getUrlParameterPart();
        doPut(baseUrl + parameterPart, null);
    }

    @Override
    public void deleteMemberFromGroup(String id, int userId) {
        String baseUrl = new StringBuffer().append(GitLabGroup.URL).append("/").append(id).append(GitLabGroupAndProjectMember.URL).append("/").append(userId).toString();
        doDelete(baseUrl);
    }

    @Override
    public void deleteMemberFromProject(String id, int userId) {
        String baseUrl = new StringBuffer().append(GitLabProject.URL).append("/").append(id).append(GitLabGroupAndProjectMember.URL).append("/").append(userId).toString();
        doDelete(baseUrl);
    }

    @Override
    public List<GitLabGroupAndProjectMember> findGroupMembers(String id) {
        String baseUrl = new StringBuffer().append(GitLabGroup.URL).append("/").append(id)
                .append(GitLabGroupAndProjectMember.URL).append("?per_page=10000").toString();
        ResponseEntity<String> responseEntity = doGet(baseUrl);
        return JSON.parseObject(responseEntity.getBody(), new TypeReference<List<GitLabGroupAndProjectMember>>() {
        });
    }

    @Override
    public List<GitLabGroupAndProjectMember> findProjectMembers(String id) {
        String baseUrl = new StringBuffer().append(GitLabProject.URL).append("/").append(id)
                .append(GitLabGroupAndProjectMember.URL).append("?per_page=10000").toString();
        ResponseEntity<String> responseEntity = doGet(baseUrl);
        return JSON.parseObject(responseEntity.getBody(), new TypeReference<List<GitLabGroupAndProjectMember>>() {
        });
    }

    @Override
    public GitLabUser findUserByUsernameOrEmail(String value) {
        String parameterPart = new Query().appendIf("search", value).getUrlParameterPart();
        ResponseEntity<String> responseEntity = doGet(GitLabUser.URL + parameterPart);
        List<GitLabUser> gitLabUserList = JSON.parseObject(responseEntity.getBody(), new TypeReference<List<GitLabUser>>() {
        });
        GitLabUser user = null;
        if (!CollectionUtils.isEmpty(gitLabUserList)) {
            for (GitLabUser gitLabUser : gitLabUserList) {
                if (gitLabUser.getUsername().equals(value) || gitLabUser.getEmail().equals(value)) {
                    user = gitLabUser;
                    break;
                }
            }
        }
        return user;
    }

    @Override
    public GitLabUser findUserByEmailOrUsername(String email, String username) {
        GitLabUser gitLabUser = this.findUserByUsernameOrEmail(email);
        if (gitLabUser == null) {
            gitLabUser = this.findUserByUsernameOrEmail(username);
        }
        return gitLabUser;
    }

    @Override
    public GitLabUser findUserById(Integer id) {
        String baseUrl = GitLabUser.URL + "/" + id;
        ResponseEntity<String> responseEntity = doGet(baseUrl);
        return JSON.parseObject(responseEntity.getBody(), GitLabUser.class);
    }

    @Override
    public void deleteGroup(String id) {
        String baseUrl = GitLabGroup.URL + "/" + id;
        doDelete(baseUrl);
    }

    @Override
    public void deleteProject(String id) {
        String baseUrl = GitLabProject.URL + "/" + id;
        doDelete(baseUrl);
    }

    @Override
    public List<GitLabUser> findUsers() {
        String baseUrl = new StringBuffer().append(GitLabUser.URL).append("?per_page=10000").toString();
        ResponseEntity<String> responseEntity = doGet(baseUrl);
        return JSON.parseObject(responseEntity.getBody(), new TypeReference<List<GitLabUser>>() {
        });
    }

    @Override
    public List<GitLabBranch> listRepositoryBranches(String id, String search) {
        String baseUrl = new StringBuffer().append(GitLabProject.URL).append("/").append(id).append("/repository/branches").toString();
        String parameterPart = new Query()
                .appendIf("search", search)
                .getUrlParameterPart();
        ResponseEntity<String> responseEntity = doGet(baseUrl + parameterPart);
        return JSON.parseObject(responseEntity.getBody(), new TypeReference<List<GitLabBranch>>() {
        });
    }

    private ResponseEntity<String> doPost(String url, Object json, Map<String, Object> parameters) {
        HttpEntity<Object> entity = new HttpEntity<>(json);
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(getRealPath(url));
        if (parameters != null) {
            for (Map.Entry<String, Object> entry : parameters.entrySet()) {
                Object value = entry.getValue();
                if (value != null) {
                    builder.queryParam(entry.getKey(), value);
                }
            }
        }
        return restTemplate.exchange(builder.build().toUri(), HttpMethod.POST, entity, String.class);
    }

    private ResponseEntity<String> doGet(String url) {
        return restTemplate.getForEntity(getRealPath(url), String.class);
    }

    private void doPut(String url, Object json) {
        restTemplate.put(getRealPath(url), json
        );
    }

    private void doDelete(String url) {
        restTemplate.delete(getRealPath(url));
    }

    private String getRealPath(String url) {
        if (!StringUtils.isEmpty(url)) {
            url = url.startsWith("/") ? url : "/" + url;
        }
        return hostUrl + apiPrefix + url;
    }

}

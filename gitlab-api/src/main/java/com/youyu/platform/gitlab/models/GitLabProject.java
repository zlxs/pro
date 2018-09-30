package com.youyu.platform.gitlab.models;

import lombok.Data;

import java.util.List;

/**
 * @author dongdong
 * @date 2018/5/21
 */
@Data
public class GitLabProject {

    public static final String URL = "/projects";

    private Integer id;
    private String description;
    private String defaultBranch;
    private String visibility;
    private String sshUrlToRepo;
    private String httpUrlToRepo;
    private String webUrl;
    private List<String> tagList;
    private GitLabUser owner;
    private String name;
    private String nameWithNamespace;
    private String path;
    private String pathWithNamespace;
    private Boolean issuesEnabled;
    private Integer openIssuesCount;
    private Boolean mergeRequestsEnabled;
    private Boolean jobsEnabled;
    private Boolean wikiEnabled;
    private Boolean snippetsEnabled;
    private Boolean resolveOutdatedDiffDiscussions;
    private Boolean containerRegistryEnabled;
    private String createdAt;
    private String lastActivityAt;
    private Integer creatorId;
    private GitLabNamespace namespace;
    private String importStatus;
    private Boolean archived;
    private String avatarUrl;
    private Boolean sharedRunnersEnabled;
    private Integer forksCount;
    private Integer starCount;
    private String runnersToken;
    private Boolean publicJobs;
    private List<GitLabProjectSharedGroup> sharedWithGroups;
    private Boolean onlyAllowMergeIfPipelineSucceeds;
    private Boolean onlyAllowMergeIfAllDiscussionsAreResolved;
    private Boolean requestAccessEnabled;
    private String mergeMethod;

}


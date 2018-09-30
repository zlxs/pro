package com.youyu.platform.gitlab.models;

import lombok.Data;

/**
 * @author dongdong
 * @date 2018/5/21
 */
@Data
public class GitLabGroup {

    public static final String URL = "/groups";

    private Integer id;
    private String name;
    private String path;
    private String description;
    private String visibility;
    private Boolean lfsEnabled;
    private String avatarUrl;
    private String webUrl;
    private Boolean requestAccessEnabled;
    private String fullName;
    private String fullPath;
    private Integer parentId;

}


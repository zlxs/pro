package com.youyu.platform.gitlab.models;

import lombok.Data;

/**
 * @author dongdong
 * @date 2018/5/21
 */
@Data
public class GitLabNamespace {

    public static final String URL = "/namespaces";

    private Integer id;
    private String name;
    private String path;
    private String kind;
    private String fullPath;
    private Integer parentId;
    private Integer membersCountWithDescendants;

}


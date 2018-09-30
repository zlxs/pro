package com.youyu.platform.gitlab.models;

import lombok.Data;

/**
 * @author dongdong
 * @date 2018/5/21
 */
@Data
public class GitLabGroupAndProjectMember {

    public static final String URL = "/members";

    private Integer id;
    private String username;
    private String name;
    private String state;
    private String createdAt;
    private Integer accessLevel;
    private String expiresAt;

    public GitLabAccessLevel getAccessLevel() {
        return GitLabAccessLevel.fromAccessValue(accessLevel);
    }

}

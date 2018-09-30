package com.youyu.platform.gitlab.models;

import lombok.Data;

/**
 * @author dongdong
 * @date 2018/5/21
 */
@Data
public class GitLabProjectSharedGroup {

    private int groupId;
    private String groupName;
    private int groupAccessLevel;

    public GitLabAccessLevel getGroupAccessLevel() {
        return GitLabAccessLevel.fromAccessValue(groupAccessLevel);
    }
}

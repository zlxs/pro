package com.youyu.platform.gitlab.models;

import lombok.Data;

/**
 * @author dongdong
 * @date 2018/5/21
 */
@Data
public class GitLabProjectIdentity {

    private String provider;
    private String externUid;

}

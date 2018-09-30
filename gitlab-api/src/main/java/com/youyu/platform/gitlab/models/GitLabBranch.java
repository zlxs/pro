package com.youyu.platform.gitlab.models;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

/**
 * @author dongdong
 * @date 2018/9/13
 */
@Data
public class GitLabBranch {

    private String name;
    private Boolean merged;
    @JSONField(name = "protected")
    private Boolean protectedBranch;
    @JSONField(name = "default")
    private Boolean defaultBranch;
    private Boolean developersCanPush;
    private Boolean developersCanMerge;
    private Boolean canPush;

}

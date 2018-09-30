package com.youyu.platform.gitlab.models;

/**
 * @author dongdong
 * @date 2018/5/22
 */
public enum GitLabMergeMethod {

    /**
     * merge: A merge commit is created for every merge, and merging is allowed as long as there are no conflicts.
     * rebase_merge: A merge commit is created for every merge, but merging is only allowed if fast-forward merge is possible. This way you could make sure that if this merge request would build, after merging to target branch it would also build.
     * ff: No merge commits are created and all merges are fast-forwarded, which means that merging is only allowed if the branch could be fast-forwarded
     */
    MERGE,
    REBASE_MERGE,
    FF;

    @Override
    public String toString() {
        return this.name().toLowerCase();
    }
}

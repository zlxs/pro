package com.youyu.platform.gitlab.models;

/**
 * @author dongdong
 * @date 2018/5/21
 */
public enum GitLabVisibility {

    /**
     * private: ProjectAssetApi access must be granted explicitly for each user.
     * internal: The project can be cloned by any logged in user.
     * public: The project can be cloned without any authentication
     */
    PRIVATE,
    INTERNAL,
    PUBLIC;

    @Override
    public String toString() {
        return this.name().toLowerCase();
    }
}

package com.youyu.platform.gitlab.models;

/**
 * @author dongdong
 * @date 2018/5/21
 */
public enum GitLabAccessLevel {

    /**
     * 10 => Guest access
     * 20 => Reporter access
     * 30 => Developer access
     * 40 => Master access
     * 50 => Owner access # Only valid for groups
     */
    Guest(10),
    Reporter(20),
    Developer(30),
    Master(40),
    Owner(50);

    public final int accessValue;

    GitLabAccessLevel(int accessValue) {
        this.accessValue = accessValue;
    }

    public static GitLabAccessLevel fromAccessValue(int accessValue) throws IllegalArgumentException {

        GitLabAccessLevel[] accessLevelArr = values();

        for (int i = 0; i < accessLevelArr.length; i++) {
            GitLabAccessLevel gitLabAccessLevel = accessLevelArr[i];
            if (gitLabAccessLevel.accessValue == accessValue) {
                return gitLabAccessLevel;
            }
        }

        throw new IllegalArgumentException("No GitLab Access Level enum constant with access value: " + accessValue);
    }

}

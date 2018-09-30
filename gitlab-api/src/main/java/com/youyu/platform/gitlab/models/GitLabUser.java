package com.youyu.platform.gitlab.models;

import lombok.Data;

import java.util.List;
import java.util.Objects;

/**
 * @author dongdong
 * @date 2018/5/21
 */
@Data
public class GitLabUser {

    public static final String URL = "/users";

    private Integer id;
    private String username;
    private String email;
    private String name;
    private String state;
    private String avatarUrl;
    private String webUrl;
    private String createdAt;
    private Boolean isAdmin;
    private String skype;
    private String linkedin;
    private String twitter;
    private String websiteUrl;
    private String organization;
    private String lastSignInAt;
    private String confirmedAt;
    private Integer themeId;
    private String lastActivityOn;
    private Integer colorSchemeId;
    private Integer projectsLimit;
    private String currentSignInAt;
    private List<GitLabProjectIdentity> identities;
    private Boolean canCreateGroup;
    private Boolean canCreateProject;
    private Boolean twoFactorEnabled;
    private Boolean external;


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        GitLabUser that = (GitLabUser) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(username, that.username) &&
                Objects.equals(email, that.email) &&
                Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, username, email, name);
    }

    @Override
    public String toString() {
        return "GitLabUser{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", name='" + name + '\'' +
                ", state='" + state + '\'' +
                ", avatarUrl='" + avatarUrl + '\'' +
                ", webUrl='" + webUrl + '\'' +
                ", createdAt='" + createdAt + '\'' +
                ", isAdmin=" + isAdmin +
                ", skype='" + skype + '\'' +
                ", linkedin='" + linkedin + '\'' +
                ", twitter='" + twitter + '\'' +
                ", websiteUrl='" + websiteUrl + '\'' +
                ", organization='" + organization + '\'' +
                ", lastSignInAt='" + lastSignInAt + '\'' +
                ", confirmedAt='" + confirmedAt + '\'' +
                ", themeId=" + themeId +
                ", lastActivityOn='" + lastActivityOn + '\'' +
                ", colorSchemeId=" + colorSchemeId +
                ", projectsLimit=" + projectsLimit +
                ", currentSignInAt='" + currentSignInAt + '\'' +
                ", identities=" + identities +
                ", canCreateGroup=" + canCreateGroup +
                ", canCreateProject=" + canCreateProject +
                ", twoFactorEnabled=" + twoFactorEnabled +
                ", external=" + external +
                '}';
    }
}

package com.youyu.platform.gitlab;

import com.alibaba.fastjson.JSON;
import com.youyu.platform.gitlab.models.*;
import com.youyu.platform.gitlab.util.GitLabApiBuilder;
import com.youyu.platform.gitlab.util.TokenType;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.List;

/**
 * @author dongdong
 * @date 2018/9/30
 */
@RunWith(JUnit4.class)
public class GitLabApiTest {

    private GitLabApi gitLabApi;

    @Before
    public void setUp() {
        gitLabApi = new GitLabApiBuilder().host("http://10.0.12.25:10181")
                .token("Mf24nzJUt5WGGjY6PrLL")
                .tokenType(TokenType.ACCESS_TOKEN)
                .clientHttpRequestFactory(null)
                .build();
    }

    @Test
    public void testCreateGroup() {
        GitLabGroup group = gitLabApi.createGroup("test1", "test1", "",
                GitLabVisibility.PUBLIC, Boolean.FALSE, Boolean.FALSE, null);
        System.out.println(group);
    }

    @Test
    public void testCreateProject() {
        GitLabProject project = gitLabApi.createProject("test5", "test5", 38, "", Boolean.TRUE, Boolean.TRUE, Boolean.TRUE, Boolean.TRUE,
                Boolean.TRUE, null, Boolean.TRUE, Boolean.TRUE, GitLabVisibility.INTERNAL,
                null, null, null, null,
                GitLabMergeMethod.REBASE_MERGE, Boolean.TRUE, Boolean.TRUE, Boolean.TRUE);
        System.out.println(project);
    }

    @Test
    public void testQueryBranch() {
        List<GitLabBranch> gitLabBranches = gitLabApi.listRepositoryBranches("22", null);
        if (gitLabBranches != null) {
            gitLabBranches.forEach(b -> {
                System.out.println(JSON.toJSONString(b));
            });
        }
    }

    @Test
    public void addMemberToGroup() {
        GitLabGroupAndProjectMember gitLabGroupAndProjectMember = gitLabApi.addMemberToGroup("38",
                "2", GitLabAccessLevel.Developer, null);
        System.out.println(gitLabGroupAndProjectMember);
    }

    @Test
    public void addMemberToProject() {
        GitLabGroupAndProjectMember gitLabGroupAndProjectMember = gitLabApi.addMemberToProject("22",
                "2", GitLabAccessLevel.Developer, null);
        System.out.println(gitLabGroupAndProjectMember);
    }

    @Test
    public void testQueryUser() {
        GitLabUser user = gitLabApi.findUserByEmailOrUsername("admin@9188.com", "admin2");
        System.out.println(user);
    }

    @Test
    public void testDeleteMemberFromProject() {
        gitLabApi.deleteMemberFromProject("22", 2);
    }

    @Test
    public void testDeleteMemberFromGroup() {
        gitLabApi.deleteMemberFromGroup("38", 2);
    }

    @Test
    public void testDeleteProject() {
        gitLabApi.deleteProject("22");
    }

    @Test
    public void testDeleteGroup() {
        gitLabApi.deleteGroup("38");
    }

    @Test
    public void testFindGroupMembers() {
        List<GitLabGroupAndProjectMember> groupMembers = gitLabApi.findGroupMembers("23");
        if (groupMembers != null) {
            groupMembers.forEach(m -> {
                System.out.println(m);
            });
        }
    }

    @Test
    public void testFindProjectMembers() {
        List<GitLabGroupAndProjectMember> projectMembers = gitLabApi.findProjectMembers("21");
        if (projectMembers != null) {
            projectMembers.forEach(m -> {
                System.out.println(m);
            });
        }
    }

}

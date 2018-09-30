package com.youyu.platform.gitlab;

import com.youyu.platform.gitlab.models.*;

import java.util.List;

/**
 * @author dongdong
 * @date 2018/5/21
 */
public interface GitLabApi {

    /**
     * 创建新的组
     *
     * @param name                 组的名称
     * @param path                 组的路径
     * @param description          组的描述
     * @param visibility           组的可见性，可以是private、internal或public.
     * @param lfsEnabled           为该组中的项目启用/禁用大型文件存储 (LFS)
     * @param requestAccessEnabled Allow users to request member access
     * @param parentId             用于创建嵌套组的父组id
     * @return
     */
    GitLabGroup createGroup(String name, String path, String description, GitLabVisibility visibility,
                            Boolean lfsEnabled, Boolean requestAccessEnabled, String parentId);

    /**
     * 创建新的项目
     *
     * @param name                                      项目的名称
     * @param path                                      项目的仓库名称
     * @param namespaceId                               项目的命名空间（默认为当前用户的命名空间）
     * @param description                               项目的描述
     * @param issueEnabled                              是否启用issue
     * @param mergeRequestEnabled                       是否允许 merge requests
     * @param jobEnabled                                是否启用job
     * @param wikiEnabled                               是否启用wiki
     * @param snippetsEnabled
     * @param resolveOutdatedDiffDiscussions
     * @param containerRegistryEnabled                  是否启用此项目的容器注册表
     * @param sharedRunnersEnabled                      是否启用shared runners
     * @param visibility                                项目的可见性级别（private,internal,public）
     * @param importUrl
     * @param publicJobs                                如果为true,则非项目成员可查看作业
     * @param onlyAllowMergeIfPipelineSucceeds
     * @param onlyAllowMergeIfAllDiscussionsAreResolved
     * @param mergeMethod
     * @param lfsEnabled                                是否启用LFS
     * @param requestAccessEnabled
     * @param printingMergeRequestLinkEnabled
     * @return
     */
    GitLabProject createProject(String name, String path, Integer namespaceId, String description, Boolean issueEnabled,
                                Boolean mergeRequestEnabled, Boolean jobEnabled, Boolean wikiEnabled, Boolean snippetsEnabled,
                                Boolean resolveOutdatedDiffDiscussions, Boolean containerRegistryEnabled, Boolean sharedRunnersEnabled,
                                GitLabVisibility visibility, String importUrl, Boolean publicJobs, Boolean onlyAllowMergeIfPipelineSucceeds,
                                Boolean onlyAllowMergeIfAllDiscussionsAreResolved, GitLabMergeMethod mergeMethod, Boolean lfsEnabled,
                                Boolean requestAccessEnabled, Boolean printingMergeRequestLinkEnabled);

    /**
     * 获取经过身份验证的用户的可见组列表
     *
     * @param allAvailable 显示用户可以访问的所有组（对于经过身份验证的用户默认为false,对于admin默认为true）
     * @param owned        限制到当前用户拥有的组
     * @return
     */
    List<GitLabGroup> findGroups(Boolean allAvailable, Boolean owned);

    /**
     * 列出组的项目
     *
     * @param id         经过身份验证的用户拥有的组的ID或URL编码的路径
     * @param visibility 可见性限制（internal,public或private）
     * @param simple     只返回每个项目的id,url,name和path
     * @param owned      按当前用户拥有的项目限制
     * @param starred    按当前用户标星的项目限制
     * @param archived   按 archived 状态限制
     * @return
     */
    List<GitLabProject> findGroupProjects(String id, GitLabVisibility visibility, Boolean simple, Boolean owned, Boolean starred, Boolean archived);

    /**
     * 为组添加成员
     *
     * @param id          经过身份验证的用户拥有的组的ID或URL编码的路径
     * @param userId      新成员用户的id
     * @param accessLevel 有效的访问级别
     * @param expiresAt   过期时间，日期字符串，格式为 yyyy-MM-dd
     * @return
     */
    GitLabGroupAndProjectMember addMemberToGroup(String id, String userId, GitLabAccessLevel accessLevel, String expiresAt);

    /**
     * 为项目添加成员
     *
     * @param id          经过身份验证的用户拥有的项目的ID或URL编码的路径
     * @param userId      新成员用户的id
     * @param accessLevel 有效的访问级别
     * @param expiresAt   过期时间，日期字符串，格式为 yyyy-MM-dd
     * @return
     */
    GitLabGroupAndProjectMember addMemberToProject(String id, String userId, GitLabAccessLevel accessLevel, String expiresAt);

    /**
     * 修改项目成员
     *
     * @param id          经过身份验证的用户拥有的项目的ID或URL编码的路径
     * @param userId      新成员用户的id
     * @param accessLevel 有效的访问级别
     * @param expiresAt   过期时间，日期字符串，格式为 yyyy-MM-dd
     */
    void updateMemberForProject(String id, String userId, GitLabAccessLevel accessLevel, String expiresAt);

    /**
     * 删除组的成员
     *
     * @param id     经过身份验证的用户拥有的组的ID或URL编码的路径
     * @param userId 成员的用户id
     */
    void deleteMemberFromGroup(String id, int userId);

    /**
     * 删除项目的成员
     *
     * @param id     经过身份验证的用户拥有的项目的ID或URL编码的路径
     * @param userId 成员的用户id
     */
    void deleteMemberFromProject(String id, int userId);

    /**
     * 列出组的成员
     *
     * @param id 经过身份验证的用户拥有的组的ID或URL编码的路径
     * @return
     */
    List<GitLabGroupAndProjectMember> findGroupMembers(String id);

    /**
     * 列出项目的成员
     *
     * @param id 经过身份验证的用户拥有的项目的ID或URL编码的路径
     * @return
     */
    List<GitLabGroupAndProjectMember> findProjectMembers(String id);

    /**
     * 按用户名或邮箱查询用户
     *
     * @param value 用户名或邮箱
     * @return
     */
    GitLabUser findUserByUsernameOrEmail(String value);


    /**
     * 按邮箱或用户名查询用户,先根据邮箱查询，没有查到再根据用户名查询
     *
     * @param email    邮箱
     * @param username 用户名
     * @return
     */
    GitLabUser findUserByEmailOrUsername(String email, String username);

    /**
     * 根据gitlab用户的id查询用户信息
     *
     * @param id gitlab用户id
     * @return
     */
    GitLabUser findUserById(Integer id);

    /**
     * 根据组的ID或URL编码的路径来删除对应的组
     *
     * @param id 经过身份验证的用户拥有的组的ID或URL编码的路径
     */
    void deleteGroup(String id);


    /**
     * 根据项目的ID或URL编码的路径来删除对应的项目
     *
     * @param id 经过身份验证的用户拥有的项目的ID或URL编码的路径
     */
    void deleteProject(String id);

    /**
     * 查询gitlab上的用户
     *
     * @return
     */
    List<GitLabUser> findUsers();

    /**
     * 查询指定仓库的分支列表
     *
     * @param id     经过身份验证的用户拥有的项目的ID或URL编码路径
     * @param search 搜索条件
     * @return
     */
    List<GitLabBranch> listRepositoryBranches(String id, String search);
}

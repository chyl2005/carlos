package com.github.carlos.service;


import com.github.carlos.common.model.Menu;
import com.github.carlos.common.model.QueryAuthorityParam;
import com.github.carlos.common.model.QueryMenuParam;
import com.github.carlos.common.model.User;
import com.github.carlos.common.model.UserInfoCacheVo;
import com.github.carlos.common.utils.JsonUtils;
import com.github.carlos.dal.bean.AuthorityDO;
import com.github.carlos.dal.bean.MenuDO;
import com.github.carlos.dal.bean.UserDO;
import com.github.carlos.dal.bean.UserRoleDO;
import com.github.carlos.dal.mapper.AuthorityMapper;
import com.github.carlos.dal.mapper.MenuMapper;
import com.github.carlos.dal.mapper.UserMapper;
import com.github.carlos.dal.mapper.UserRoleMapper;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author chenyanlong
 * @ClassName: AuthorityService
 * @Description: 权限
 * @date 2015年11月23日 下午3:53:42
 */
@Service
public class AuthorityService {
    private static final Logger LOGGER = LoggerFactory.getLogger(AuthorityService.class);
    @Autowired
    private AuthorityMapper authorityMapper;
    @Autowired
    private UserRoleMapper userRoleMapper;
    @Autowired
    private MenuMapper menuMapper;
    @Autowired
    private UserMapper userMapper;

    /**
     * @return UserInfoCacheVo 返回类型
     * @Description: 查询登录用户拥有权限的模块列表 子集授权 父级必须授权
     * @author chenyanlong
     * @date 2015年11月23日 下午3:55:08
     */
    public UserInfoCacheVo getUserInfoCache(Integer userId) {
        UserInfoCacheVo userInfoCache = new UserInfoCacheVo();
        UserDO userInfo = userMapper.findById(userId);
        User user = new User();
        user.setId(userInfo.getId());
        user.setLogin(userInfo.getUserName());
        user.setName(userInfo.getTrueName());
        userInfoCache.setUser(user);
        List<UserRoleDO> userRoleEntities = userRoleMapper.getUserRole(userId);
        Set<Integer> roleIds = userRoleEntities.stream().map(userRoleDO -> userRoleDO.getRoleId()).distinct().collect(Collectors.toSet());
        if (CollectionUtils.isEmpty(roleIds)) {
            return null;
        }

        QueryAuthorityParam authorityParam = new QueryAuthorityParam();
        authorityParam.setRoleIds(roleIds);
        //权限信息
        List<AuthorityDO> authoritys = this.authorityMapper.selectByParam(authorityParam);
        Set<Integer> moduleIds = authoritys.stream().map(authorityDO -> authorityDO.getMenuId()).distinct().collect(Collectors.toSet());
        if (CollectionUtils.isEmpty(moduleIds)) {
            return null;
        }
        // 用户拥有权限的所有模块
        QueryMenuParam menuParam = new QueryMenuParam();
        menuParam.setMenuIds(moduleIds);
        List<MenuDO> moduleEntities = this.menuMapper.selectByParam(menuParam);
        if (CollectionUtils.isNotEmpty(moduleEntities)) {
            List<Menu> menus = AuthorityUtils.getModuleVos(moduleEntities);
            userInfoCache.setMenus(menus);
        }
        userInfoCache.setMenuIds(moduleIds);
        return userInfoCache;
    }

    /**
     * @param state
     * @return List<ModuleEntity> 返回类型
     * @Description: 查询所有可用模块
     * @author chenyanlong
     * @date 2015年11月26日 上午9:45:13
     */
    public List<Menu> getAllModules(Integer state) {
        List<MenuDO> modules = this.menuMapper.selectAll();
        if (CollectionUtils.isNotEmpty(modules)) {
            List<Menu> menus = AuthorityUtils.getModuleVos(modules);
            return menus;
        }
        return Collections.emptyList();
    }

    /**
     * @param list
     * @return AuthorityEntity    返回类型
     * @Description:
     * @author chenyanlong
     * @date 2016年3月16日 下午5:42:38
     */
    @Transactional(rollbackFor = RuntimeException.class)
    public void saveOrUpdateEntity(List<AuthorityDO> list) {
        LOGGER.info("AuthorityService.saveOrUpdateEntity param={}", JsonUtils.object2Json(list));
        Set<Integer> roleIds = list.stream().map(authorityDO -> authorityDO.getRoleId()).collect(Collectors.toSet());
        for (Integer roleId : roleIds) {
            this.authorityMapper.delByRoleId(roleId);
        }
        for (AuthorityDO authorityDO : list) {
            this.authorityMapper.insertOrUpdate(authorityDO);
        }
    }

    /**
     * @param authority
     * @return AuthorityEntity    返回类型
     * @Description:
     * @author chenyanlong
     * @date 2016年3月16日 下午5:42:38
     */
    @Transactional(rollbackFor = RuntimeException.class)
    public void saveOrUpdate(AuthorityDO authority) {
        this.authorityMapper.insertOrUpdate(authority);
    }
}

package com.github.carlos.service;


import com.github.carlos.common.constant.SysConstant;
import com.github.carlos.common.model.AoData;
import com.github.carlos.common.model.Menu;
import com.github.carlos.common.model.QueryAuthorityParam;
import com.github.carlos.common.model.QueryRoleParam;
import com.github.carlos.common.utils.ThreadLocalContext;
import com.github.carlos.dal.bean.AuthorityDO;
import com.github.carlos.dal.bean.MenuDO;
import com.github.carlos.dal.bean.RoleDO;
import com.github.carlos.dal.mapper.AuthorityMapper;
import com.github.carlos.dal.mapper.MenuMapper;
import com.github.carlos.dal.mapper.RoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class RoleService {

    @Autowired
    private AuthorityMapper authorityMapper;

    @Autowired
    private MenuMapper menuMapper;


    @Autowired
    private RoleMapper roleMapper;

    /**
     * @return List<RoleEntity> 返回类型
     * @Description: 是否删除的状态
     * @author chenyanlong
     * @date 2015年11月24日 下午1:13:44
     */
    public AoData getAllRoles() {
        QueryRoleParam roleParam = new QueryRoleParam();
        roleParam.setPageSize(ThreadLocalContext.get(SysConstant.PAGE_SIZE));
        roleParam.setStartRow(ThreadLocalContext.get(SysConstant.START_ROW));
        List<RoleDO> roleDOS = this.roleMapper.selecByParam(roleParam);
        Integer count = this.roleMapper.selecByCount(roleParam);
        AoData aoData = new AoData(count, roleDOS);
        return aoData;
    }



    /**
     * @param roleId
     * @param isDel
     * @return Boolean 返回类型
     * @Description:
     * @author chenyanlong
     * @date 2015年11月24日 下午1:20:07
     */
    @Transactional(rollbackFor = RuntimeException.class)
    public void del(Integer roleId, Integer isDel) {
        this.roleMapper.del(roleId, isDel);
    }


    /**
     * @param roleId
     * @return List<ModuleVo> 返回类型
     * @Description: 获取所有模块的角色权限信息
     * @author chenyanlong
     * @date 2016年3月17日 上午9:24:26
     */
    public List<Menu> getAllModuleAuthority(Integer roleId) {
        // 当前角色所拥有的权限
        QueryAuthorityParam param = new QueryAuthorityParam();
        Set<Integer> roleIds = new HashSet<>();
        roleIds.add(roleId);
        param.setRoleIds(roleIds);
        List<AuthorityDO> authoritys = this.authorityMapper.selectByParam(param);
        Set<Integer> moduleIds = authoritys.stream().map(authorityDO -> authorityDO.getMenuId()).distinct().collect(Collectors.toSet());
        // 取出所有模块 转成树形结构
        List<MenuDO> modules = this.menuMapper.selectAll();
        List<Menu> moduleVos = null;
        if (null != modules && modules.size() > 0) {
            moduleVos = AuthorityUtils.getModuleVos(modules, moduleIds);
        }
        return moduleVos;
    }


    @Transactional(rollbackFor = RuntimeException.class)
    public void insertOrUpdate(RoleDO entity) {
        roleMapper.insertOrUpdate(entity);
    }



}

package com.github.carlos.service;


import com.github.carlos.common.constant.DeleteStatusEnum;
import com.github.carlos.common.model.AoData;
import com.github.carlos.common.model.QueryRoleParam;
import com.github.carlos.common.model.Role;
import com.github.carlos.common.model.UserVo;
import com.github.carlos.common.utils.MD5Utils;
import com.github.carlos.dal.bean.RoleDO;
import com.github.carlos.dal.bean.UserDO;
import com.github.carlos.dal.bean.UserRoleDO;
import com.github.carlos.dal.mapper.RoleMapper;
import com.github.carlos.dal.mapper.UserMapper;
import com.github.carlos.dal.mapper.UserRoleMapper;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRoleMapper userRoleMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RoleMapper roleMapper;

    public UserDO login(String name, String password) {
        return this.userMapper.selectByUserAndPassword(name, password);
    }

    /**
     * @param state
     * @return AoData    返回类型
     * @Description: 用户信息列表
     * @author chenyanlong
     * @date 2016年5月31日 上午10:23:11
     */
    public AoData getUserList(Integer state) {
        List<UserDO> userList = this.userMapper.selectByParam(state);
        Integer count = this.userMapper.selectByCount(state);
        Set<Integer> userIds = userList.stream().map(user -> user.getId()).distinct().collect(Collectors.toSet());
        AoData aoData = new AoData(count, userList);


        HashMap<Integer, ArrayList<Integer>> userRoleMap = new HashMap<Integer, ArrayList<Integer>>();
        //关联表  一个用户多个角色
        List<UserRoleDO> userRoleEntities = this.userRoleMapper.findByUserIds(userIds);
        List<Integer> roleIds = new ArrayList<>();
        for (UserRoleDO userRoleDO : userRoleEntities) {
            ArrayList<Integer> roleList = userRoleMap.get(userRoleDO.getUserId());
            if (roleList == null) {
                roleList = new ArrayList<>();
            }
            roleList.add(userRoleDO.getRoleId());
            roleIds.add(userRoleDO.getRoleId());
            userRoleMap.put(userRoleDO.getUserId(), roleList);
        }
        //角色表
        QueryRoleParam roleParam = new QueryRoleParam();
        roleParam.setRoleIds(roleIds);
        List<RoleDO> roleEntities = this.roleMapper.selecByParam(roleParam);
        Map<Integer, RoleDO> roleMap = roleEntities.stream().collect(Collectors.toMap(role -> role.getId(), role -> role));
        List<UserVo> userVos = new ArrayList<>();
        //组装数据
        for (UserDO user : userList) {
            UserVo userVo = ConvertUtils.parseToUserResponse(user);
            List<Integer> roles = userRoleMap.get(user.getId());
            List<String> roleNames = new ArrayList<>();
            if (CollectionUtils.isNotEmpty(roles)) {
                for (Integer roleId : roles) {
                    RoleDO roleDO = roleMap.get(roleId);
                    if (null != roleDO) {
                        roleNames.add(roleDO.getRoleName());
                    }
                }
            }
            userVo.setRoleName(roleNames);
            userVos.add(userVo);
        }
        return aoData;
    }

    /**
     * @return AdminuserEntity 返回类型
     * @Description:
     * @author chenyanlong
     * @date 2015年11月26日 上午9:20:08
     */
    @Transactional(rollbackFor = RuntimeException.class)
    public void saveOrUpdate(UserDO userDO) {
        this.userMapper.insertOrUpdate(userDO);

    }

    /**
     * @param userId
     * @return SystemUserEntity 返回类型
     * @Description:
     * @author chenyanlong
     * @date 2015年12月11日 下午2:50:48
     */
    public UserDO getUserInfo(Integer userId) {

        return this.userMapper.findById(userId);
    }

    /**
     * @return AdminuserEntity 返回类型
     * @Description:
     * @author chenyanlong
     * @date 2015年11月26日 上午9:20:12
     */
    @Transactional(rollbackFor = RuntimeException.class)
    public void update(UserDO userDO) {
        this.userMapper.insertOrUpdate(userDO);
    }

    @Transactional(rollbackFor = RuntimeException.class)
    public Boolean changePassword(Integer userId, String newPassword, String oldPassword) {

        UserDO userDO = this.userMapper.findById(userId);
        if (userDO != null) {
            String pwd = MD5Utils.getMD5(userDO.getUserName() + oldPassword);
            if (pwd.equals(userDO.getPassword())) {
                String password = MD5Utils.getMD5(userDO.getUserName() + newPassword);
                userDO.setPassword(password);
                this.userMapper.insertOrUpdate(userDO);
                return true;
            }
        }
        return false;
    }

    /**
     * @param id
     * @return UserEntity 返回类型
     * @Description:
     * @author chenyanlong
     * @date 2015年12月21日 下午6:18:02
     */
    @Transactional(rollbackFor = RuntimeException.class)
    public void del(Integer id) {
        this.userMapper.updateState(id, DeleteStatusEnum.DEL.getCode());
    }

    /**
     * @param userId
     * @return List<RoleVo> 返回类型
     * @Description: 所有角色的用户权限信息
     * @author chenyanlong
     * @date 2016年3月21日 下午3:31:13
     */
    public List<Role> getRoles(Integer userId) {
        List<UserRoleDO> userRoles = this.userRoleMapper.getUserRole(userId);

        Set<Integer> roleIds = userRoles.stream().map(userRoleDO -> userRoleDO.getRoleId()).distinct().collect(Collectors.toSet());

        List<RoleDO> roleEntities = this.roleMapper.selecAll();
        ArrayList<Role> roles = new ArrayList<Role>();
        for (RoleDO roleDO : roleEntities) {
            Role role = parseToRoleVo(roleDO, roleIds);
            roles.add(role);
        }
        return roles;
    }

    private Role parseToRoleVo(RoleDO roleDO, Set<Integer> roleIds) {
        Role role = new Role();
        role.setId(roleDO.getId());
        role.setCreateTime(roleDO.getGmtCreated());
        if (roleIds != null && roleIds.size() > 0 && roleIds.contains(roleDO.getId())) {
            role.setIsAuthority(1);
        } else {
            role.setIsAuthority(0);
        }
        role.setRoleName(roleDO.getRoleName());
        role.setIsDel(roleDO.getIsDel());
        return role;
    }

    /**
     * @param userId
     * @param roleId
     * @param operate 1 添加操作 0 删除
     * @return Boolean    返回类型
     * @Description:
     * @author chenyanlong
     * @date 2016年3月21日 下午4:26:49
     */
    @Transactional(rollbackFor = RuntimeException.class)
    public Boolean insertOrUpdate(Integer userId, Integer roleId, Integer operate) {
        // 添加操作
        if (operate == 1) {
            UserRoleDO entity = new UserRoleDO();
            entity.setRoleId(roleId);
            entity.setUserId(userId);
            Integer update = this.userRoleMapper.insertOrUpdate(entity);
            return update > 0 ? true : false;
        } else {// 删除操作
            Boolean flag = this.userRoleMapper.deleteByUserIdAndRoleId(userId, roleId);
            return flag;
        }
    }




}

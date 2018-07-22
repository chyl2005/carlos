package com.github.carlos.app.controller.console;


import com.github.carlos.common.constant.DeleteStatusEnum;
import com.github.carlos.common.model.AoData;
import com.github.carlos.common.model.WebResponse;
import com.github.carlos.dal.bean.AuthorityDO;
import com.github.carlos.dal.bean.RoleDO;
import com.github.carlos.service.AuthorityService;
import com.github.carlos.service.ModuleService;
import com.github.carlos.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @author chenjinlong
 * @ClassName: RoleController
 * @Description: 角色控制
 * @date 2015年11月26日
 */
@Controller
@RequestMapping("/console/role")
public class RoleController {

    @Autowired
    private ModuleService moduleService;

    @Autowired
    private RoleService roleService;
    @Autowired
    private AuthorityService authorityService;

    @RequestMapping("/index")
    public String index() {
        return "console/role/index";
    }

    @RequestMapping("/list")
    @ResponseBody
    public WebResponse gerRoleList() {
        WebResponse webResponse = WebResponse.getSuccessWebResponse();
        AoData aoData = this.roleService.getAllRoles();
        webResponse.setData(aoData);
        return webResponse;

    }

    /**
     * @param roleId
     * @return List<ModuleVo>    返回类型
     * @Description: 获取所有模块的角色权限信息
     * @author chenyanlong
     * @date 2016年3月17日 上午9:35:21
     */
    @RequestMapping("/getAllModuleAuthority")
    @ResponseBody
    public WebResponse getAllModuleAuthority(Integer roleId) {
        WebResponse webResponse = WebResponse.getSuccessWebResponse();
        webResponse.setData(this.roleService.getAllModuleAuthority(roleId));
        return webResponse;

    }

    @RequestMapping("/del")
    @ResponseBody
    public WebResponse del(Integer id) {
        WebResponse webResponse = WebResponse.getSuccessWebResponse();
        roleService.del(id, DeleteStatusEnum.DEL.getCode());
        return webResponse;
    }

    @RequestMapping("/saveOrUpdate")
    @ResponseBody
    public WebResponse saveOrUpdate(RoleDO entity) {
        WebResponse webResponse = WebResponse.getSuccessWebResponse();
        this.roleService.insertOrUpdate(entity);
        return webResponse;
    }


    /**
     * @param list
     * @return AuthorityEntity 返回类型
     * @Description:
     * @author chenyanlong
     * @date 2016年3月16日 下午5:43:48
     */
    @RequestMapping("/saveAuth")
    @ResponseBody
    public WebResponse saveAuth(@RequestBody List<AuthorityDO> list) {
        WebResponse webResponse = WebResponse.getSuccessWebResponse();
        this.authorityService.saveOrUpdateEntity(list);
        return webResponse;

    }

}

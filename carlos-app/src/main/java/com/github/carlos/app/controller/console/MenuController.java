package com.github.carlos.app.controller.console;


import com.github.carlos.common.model.AoData;
import com.github.carlos.common.model.Menu;
import com.github.carlos.common.model.WebResponse;
import com.github.carlos.dal.bean.MenuDO;
import com.github.carlos.service.AuthorityService;
import com.github.carlos.service.ModuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author chenyanlong
 * @ClassName: ModuleController
 * @Description: 模块控制
 * @date 2015年11月23日 下午2:23:10
 */
@Controller
@RequestMapping("/console/menu")
public class MenuController {

    @Resource(name = "authorityService")
    private AuthorityService authorityService;
    @Autowired
    private ModuleService moduleService;

    @RequestMapping("/index")
    public String index() {
        return "console/menu/index";
    }


    /**
     * @param parentId
     * @return AoData 返回类型
     * @Description: 模块分页数据
     * @author chenyanlong
     * @date 2015年11月27日 下午3:04:19
     */
    @RequestMapping("/list")
    @ResponseBody
    public WebResponse list(Integer parentId) {
        WebResponse webResponse = new WebResponse();
        AoData aoData = this.moduleService.getPageModules(parentId);
        webResponse.setData(aoData);
        return webResponse;
    }
    /**
     * @param @return
     * @return List<ModuleVo>    返回类型
     * @Description:
     * @author chenyanlong
     * @date 2016年3月16日 下午5:19:54
     */
    @RequestMapping("/getAllModuleVos")
    @ResponseBody
    public WebResponse getAllModuleVos() {
        WebResponse webResponse = new WebResponse();
        List<Menu> modules = this.authorityService.getAllModules(1);
        webResponse.setData(modules);
        return webResponse;

    }

    @RequestMapping("/getModules")
    @ResponseBody
    public WebResponse getModules(Integer parentId) {
        WebResponse webResponse = new WebResponse();
        webResponse.setData(this.moduleService.getModules(parentId));
        return webResponse;
    }



    /**
     * @param module
     * @return ModuleEntity 返回类型
     * @Description:
     * @author chenyanlong
     * @date 2016年3月16日 下午3:32:58
     */
    @RequestMapping("/saveOrUpdate")
    @ResponseBody
    public WebResponse saveOrUpdate(MenuDO module) {
        WebResponse webResponse = new WebResponse();
        this.moduleService.saveOrUpdate(module);
        return webResponse;
    }

    /**
     * @param moduleId
     * @return ModuleEntity 返回类型
     * @Description:
     * @author chenyanlong
     * @date 2015年11月27日 下午4:57:01
     */
    @RequestMapping("/getModule")
    @ResponseBody
    public WebResponse getModule(Integer moduleId) {
        WebResponse webResponse = new WebResponse();
        webResponse.setData(this.moduleService.findById(moduleId));
        return webResponse;
    }

    @RequestMapping("/del")
    @ResponseBody
    public WebResponse del(Integer id) {
        WebResponse webResponse = new WebResponse();
        this.moduleService.del(id);
        return webResponse;
    }


}

package com.github.carlos.app.controller.console;


import com.github.carlos.common.model.Role;
import com.github.carlos.common.model.WebResponse;
import com.github.carlos.common.utils.MD5Utils;
import com.github.carlos.dal.bean.UserDO;
import com.github.carlos.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/console/user")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("/index")
    public String index() {
        return "console/user/index";
    }

    /**
     * 用户列表
     *
     * @param state
     * @return
     */
    @RequestMapping("/list")
    @ResponseBody
    public WebResponse list(Integer state) {
        WebResponse webResponse = WebResponse.getSuccessWebResponse();
        webResponse.setData(this.userService.getUserList(state));
        return webResponse;

    }

    @RequestMapping("/saveOrUpdateUserRole")
    @ResponseBody
    public WebResponse saveOrUpdateUserRole(Integer userId, Integer roleId, Integer operate) {
        WebResponse webResponse = WebResponse.getSuccessWebResponse();
        this.userService.insertOrUpdate(userId, roleId, operate);
        return webResponse;

    }

    @RequestMapping("/getRoles")
    @ResponseBody
    public WebResponse getRoles(@RequestParam Integer userId) {
        WebResponse webResponse = WebResponse.getSuccessWebResponse();
        List<Role> list = this.userService.getRoles(userId);
        webResponse.setData(list);
        return webResponse;

    }



    /**
     * @param id
     * @return SystemUserEntity 返回类型
     * @Description:
     * @author chenyanlong
     * @date 2015年12月11日 下午2:51:24
     */
    @RequestMapping("/getUserInfo")
    @ResponseBody
    public WebResponse getUserInfo(Integer id) {
        WebResponse webResponse = WebResponse.getSuccessWebResponse();
        webResponse.setData(this.userService.getUserInfo(id));
        return webResponse;
    }

    /**
     * @param user
     * @return SystemUserEntity 返回类型
     * @Description:
     * @author chenyanlong
     * @date 2015年12月11日 下午2:27:13
     */
    @RequestMapping("/saveOrUpdate")
    @ResponseBody
    public WebResponse saveOrUpdate(UserDO user) {
        WebResponse webResponse = WebResponse.getSuccessWebResponse();
        this.userService.saveOrUpdate(user);
        return webResponse;
    }

    /**
     * @Description:
     * @author chenyanlong
     * @date 2016年4月1日 上午11:31:42
     */
    @RequestMapping("/changePassword")
    @ResponseBody
    public WebResponse changePassword(Integer userId, String newPassword, String oldPassword) {
        WebResponse webResponse = WebResponse.getSuccessWebResponse();
        this.userService.changePassword(userId, newPassword, oldPassword);
        return webResponse;
    }

    /**
     * @param
     * @return Boolean 返回类型
     * @Description:
     * @author chenyanlong
     * @date 2015年12月21日 下午6:14:31
     */
    @RequestMapping("/del")
    @ResponseBody
    public WebResponse del(Integer id) {
        WebResponse webResponse = WebResponse.getSuccessWebResponse();
        this.userService.del(id);
        return webResponse;
    }


}

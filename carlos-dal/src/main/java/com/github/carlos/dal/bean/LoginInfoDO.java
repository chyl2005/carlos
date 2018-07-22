package com.github.carlos.dal.bean;

import com.github.carlos.common.model.BaseObject;
import lombok.Data;

import java.util.Date;

/**
 * 系统用户表
 */
@Data
public class LoginInfoDO extends BaseObject {


    private Integer id;
	/**
	 * 登录key ssoid
	 */
    private String loginKey;

	/**
	 * 登录信息 权限
	 */
    private String loginInfo;


	/**
	 * 创建时间
	 */
	private Date gmtCreated;
	/**
	 * 修改时间
	 */
	private Date gmtModified;
	/**
	 * 删除标记
	 */
	private Integer isDel;



}

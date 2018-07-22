package com.github.carlos.dal.bean;

import com.github.carlos.common.model.BaseObject;
import lombok.Data;

import java.util.Date;

/**
 * 权限（访问控制）  
 * @author Administrator
 *
 */
@Data
public class AuthorityDO extends BaseObject {


	private Integer id;

	/**
	 * 角色id
	 */
	private Integer roleId;
	/**
	 * 模块ID
	 */
	private Integer menuId;
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

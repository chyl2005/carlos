package com.github.carlos.common.model;

import lombok.Data;

import java.util.Date;

@Data
public class Role extends BaseObject{

	private Integer id;
	/**
	 * 角色名
	 * 
	 */
	private String roleName;
	/**
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * 删除标记
	 */
	private Integer isDel;
	
	
	/**
	 * 是否有权限 0 没有 1 有
	 */
	private Integer isAuthority;



	
	
	
}

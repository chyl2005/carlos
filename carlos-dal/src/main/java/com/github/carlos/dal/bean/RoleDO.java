package com.github.carlos.dal.bean;


import com.github.carlos.common.model.BaseObject;
import lombok.Data;

import java.util.Date;

@Data
public class RoleDO extends BaseObject {


	private Integer id;
	/**
	 * 角色名
	 * 
	 */
	private String roleName;
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

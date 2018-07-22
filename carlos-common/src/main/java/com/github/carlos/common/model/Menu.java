package com.github.carlos.common.model;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 系统菜单
 */
@Data
public class Menu extends BaseObject{

	
	/**
	 * @Fields serialVersionUID : 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String name;
	
    /**
     * 请求方法路径 唯一
     */
	private String url;
	private String icon;
	/**
	 * 模块编号 
	 */
	private Integer orderNum;
	/**
	 * 模块父类id
	 */
	private Integer parentId;
	
	
	/**
	 * 级别
	 */
	private Integer level;
	/**
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * 状态 1 正常 0 删除
	 */
	private Integer state;
	private Integer type;

	private Integer isLink;
	
	private List<Menu>  subMenus;
	
	
	/**
	 * 是否有权限 0 没有 1 有
	 */
	private Integer isAuthority;

}

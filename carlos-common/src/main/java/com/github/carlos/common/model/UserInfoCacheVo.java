package com.github.carlos.common.model;


import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
public class UserInfoCacheVo extends BaseObject{


	private User user;
	private List<Menu> menus;
	private Set<Integer> menuIds;


}



package com.github.carlos.service;




import com.github.carlos.common.model.Menu;
import com.github.carlos.dal.bean.MenuDO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @ClassName: AuthorityUtils
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author chenyanlong
 * @date 2015年11月26日
 *
 */
public class AuthorityUtils {
	/**
	 * @Description: 模块列表转成树形结构
	 * @param moduleEntities
	 * @return List<ModuleVo> 返回类型
	 * @author chenyanlong
	 * @date 2015年11月26日 上午9:48:07
	 */
	public static List<Menu> getModuleVos(List<MenuDO> moduleEntities) {
		// 一级菜单
		List<Menu> firstList = new ArrayList<Menu>();
		// 二级菜单
		List<Menu> secondList = new ArrayList<Menu>();
		// 三级菜单
		List<Menu> thirdList = new ArrayList<Menu>();
		for (MenuDO menuDO : moduleEntities) {
			if (menuDO.getLevel() == 1) {
				firstList.add(parseToVo(menuDO));
			}
			if (menuDO.getLevel() == 2) {
				secondList.add(parseToVo(menuDO));
			}
			if (menuDO.getLevel() == 3) {
				thirdList.add(parseToVo(menuDO));
			}
		}
		// 转成map
		HashMap<Integer, Menu> firstModuleMap = new HashMap<Integer, Menu>();
		for (Menu module : firstList) {
			firstModuleMap.put(module.getId(), module);
		}
		// 转成map
		HashMap<Integer, Menu> secondModuleMap = new HashMap<Integer, Menu>();
		for (Menu module : secondList) {
			secondModuleMap.put(module.getId(), module);
		}
		// 三级组装到第二级
		for (Menu menu : thirdList) {
			// 二级列表有权限
			Menu secondMenu = secondModuleMap.get(menu.getParentId());
			if (secondMenu != null) {
				List<Menu> subModule = secondMenu.getSubMenus();
				if (null == subModule) {
					subModule = new ArrayList<Menu>();
				}
				subModule.add(menu);
				secondMenu.setSubMenus(subModule);
			}
		}
		// 二级组装到第一级
		for (Map.Entry<Integer, Menu> entry : secondModuleMap.entrySet()) {
			Menu moduleVo = entry.getValue();
			// 二级列表有权限
			Menu firstMenu = firstModuleMap.get(moduleVo.getParentId());
			if (firstMenu != null) {
				List<Menu> subModule = firstMenu.getSubMenus();
				if (null == subModule) {
					subModule = new ArrayList<Menu>();
				}
				subModule.add(moduleVo);
				firstMenu.setSubMenus(subModule);

			}
		}
		// 重新赋值
		for (Menu menu : firstList) {
			menu = firstModuleMap.get(menu.getId());
		}
		return firstList;
	}

	/**
	 * @Description: 模块列表转成树形结构
	 * @param moduleEntities
	 * @return List<ModuleVo> 返回类型
	 * @author chenyanlong
	 * @date 2015年11月26日 上午9:48:07
	 */
	public static List<Menu> getModuleVos(List<MenuDO> moduleEntities, Set<Integer> moduleIds) {
		// 一级菜单
		List<Menu> firstList = new ArrayList<Menu>();
		// 二级菜单
		List<Menu> secondList = new ArrayList<Menu>();
		// 三级菜单
		List<Menu> thirdList = new ArrayList<Menu>();
		for (MenuDO menuDO : moduleEntities) {
			Menu menu = parseToVo(menuDO);
			if (moduleIds != null && moduleIds.size() > 0 && moduleIds.contains(menu.getId())) {
				menu.setIsAuthority(1);
			} else {
				menu.setIsAuthority(0);
			}
			if (menuDO.getLevel() == 1) {
				firstList.add(menu);
			}
			if (menuDO.getLevel() == 2) {
				secondList.add(menu);
			}
			if (menuDO.getLevel() == 3) {
				thirdList.add(menu);
			}
		}
		// 转成map
		HashMap<Integer, Menu> firstModuleMap = new HashMap<Integer, Menu>();
		for (Menu module : firstList) {
			firstModuleMap.put(module.getId(), module);
		}
		// 转成map
		HashMap<Integer, Menu> secondModuleMap = new HashMap<Integer, Menu>();
		for (Menu module : secondList) {
			secondModuleMap.put(module.getId(), module);
		}
		// 三级组装到第二级
		for (Menu menu : thirdList) {
			// 二级列表有权限
			Menu secondMenu = secondModuleMap.get(menu.getParentId());
			if (secondMenu != null) {
				List<Menu> subModule = secondMenu.getSubMenus();
				if (null == subModule) {
					subModule = new ArrayList<Menu>();
				}
				subModule.add(menu);
				secondMenu.setSubMenus(subModule);
			}
		}
		// 二级组装到第一级
		for (Map.Entry<Integer, Menu> entry : secondModuleMap.entrySet()) {
			Menu menu = entry.getValue();
			// 二级列表有权限
			Menu firstMenu = firstModuleMap.get(menu.getParentId());
			if (firstMenu != null) {
				List<Menu> subModule = firstMenu.getSubMenus();
				if (null == subModule) {
					subModule = new ArrayList<Menu>();
				}
				subModule.add(menu);
				firstMenu.setSubMenus(subModule);

			}
		}
		// 重新赋值
		for (Menu menu : firstList) {
			menu = firstModuleMap.get(menu.getId());
		}
		return firstList;
	}

	/**
	 * 
	 * @Description:
	 * @param entity
	 * @return ModuleVo 返回类型
	 * @author chenyanlong
	 * @date 2015年11月25日 下午2:04:23
	 */
	public static Menu parseToVo(MenuDO entity) {
		Menu menu = new Menu();
		menu.setId(entity.getId());
		menu.setName(entity.getName());
		menu.setParentId(entity.getParentId());
		menu.setUrl(entity.getUrl());
		menu.setLevel(entity.getLevel());
		menu.setCreateTime(entity.getGmtCreated());
		menu.setState(entity.getIsDel());
		menu.setSubMenus(new ArrayList<Menu>());
		menu.setIsLink(entity.getIsLink());
		menu.setIcon(entity.getIcon());
		return menu;
	}
}

package com.github.carlos.common.utils;


import com.github.carlos.common.model.Menu;
import com.github.carlos.common.model.User;

import java.util.List;
import java.util.Set;

/**
 * Author:chyl2005
 * Date:17/6/10
 * Time:22:39
 * Desc:登录用户信息
 */
public class UserUtils {

    private static final String USER_KEY = "user";
    private static final String USER_MENUS_KEY = "user.menus";
    private static final String MENUS_ID_KEY = "user.menuIds";

    public static User getUser() {
        return (User) ThreadLocalContext.get(USER_KEY);
    }

    public static void bind(User user) {
        if (user != null && user.getId() > 0) {
            ThreadLocalContext.set(USER_KEY, user);
        }
    }

    public static void unbindUser() {
        ThreadLocalContext.remove(USER_KEY);
    }

    public static List<Menu> getMenus() {
        return (List<Menu>) ThreadLocalContext.get(USER_MENUS_KEY);
    }

    public static void bind(List<Menu> menus) {
        if (menus != null) {
            ThreadLocalContext.set(USER_MENUS_KEY, menus);
        }
    }

    public static void unbindUserMenus() {
        ThreadLocalContext.remove(USER_MENUS_KEY);
    }

    public static Set<Integer> getMenuIds() {
        return (Set<Integer>) ThreadLocalContext.get(MENUS_ID_KEY);
    }

    public static void bind(Set<Integer> menuIds) {
        if (menuIds != null) {
            ThreadLocalContext.set(MENUS_ID_KEY, menuIds);
        }
    }

    public static void unbindMenuIds() {
        ThreadLocalContext.remove(MENUS_ID_KEY);
    }
}

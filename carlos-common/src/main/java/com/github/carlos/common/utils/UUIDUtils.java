package com.github.carlos.common.utils;

import java.util.UUID;

/**
 * @author: ylong.chen@qunar.com
 * @Date: 2018/7/28 23:12
 * @description: TODO
 */
public class UUIDUtils {

    private UUIDUtils() {
    }


    public static String createUUID() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }


}

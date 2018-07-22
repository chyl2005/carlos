package com.github.carlos.common.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: ylong.chen@qunar.com
 * @Date: 2018/7/21 12:35
 * @description: TODO
 */
public class ThreadLocalContext {

    private final static ThreadLocal<HashMap<String, Object>> envstore =
            new ThreadLocal<HashMap<String, Object>>() {
                @Override
                protected HashMap<String, Object> initialValue() {
                    return new HashMap<String, Object>();
                }
            };


    public final static <T> void set(String key, T value) {
        envstore.get().put(key, value);
    }

    public final static <T> T get(String key) {
        return (T) envstore.get().get(key);
    }

    public final static <F,V> void hset(String key, F field,V value) {
        Map map = (Map) envstore.get().get(key);
        if(map==null){
            map = new HashMap();
            envstore.get().put(key,map);
        }
        map.put(field,value);
    }

    public final static <F,V> V hget(String key, F field) {
        Map map = (Map) envstore.get().get(key);
        if(map==null){
            return null;
        }
        return (V) map.get(field);
    }


    public final static void remove(String key) {
        envstore.get().remove(key);
    }

    public final static void clear() {
        envstore.remove();
    }

}

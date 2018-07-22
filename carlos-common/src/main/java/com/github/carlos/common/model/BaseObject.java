package com.github.carlos.common.model;

import com.github.carlos.common.utils.JsonUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.io.Serializable;

/**
 * @author: ylong.chen@qunar.com
 * @Date: 2018/7/21 11:48
 * @description: TODO
 */
public class BaseObject implements Serializable {



    @Override
    public String toString() {
        return JsonUtils.object2Json(this);
    }

    @Override
    public boolean equals(Object obj) {
        return EqualsBuilder.reflectionEquals(this, obj);
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }
}

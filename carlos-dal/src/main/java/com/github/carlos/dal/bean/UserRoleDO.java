package com.github.carlos.dal.bean;

import com.github.carlos.common.model.BaseObject;
import lombok.Data;

import java.util.Date;

/**
 * 用户角色表
 *
 * @author chenyanlong
 * @ClassName: UserRole
 * @Description:
 * @date 2015年11月23日 下午3:24:34
 */
@Data
public class UserRoleDO extends BaseObject {


    private Integer id;
    private Integer userId;
    private Integer roleId;

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

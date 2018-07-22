package com.github.carlos.dal.bean;

import com.github.carlos.common.model.BaseObject;
import lombok.Data;

import java.util.Date;

/**
 * 用户和设备关系表
 */
@Data
public class RelUserDeviceDO extends BaseObject {
    /**
     * @Fields serialVersionUID :
     */
    private static final long serialVersionUID = 1L;

    private Integer id;
    /**
     * 账号名
     */
    private Integer userId;

    /**
     * 设备ID
     */
    private Integer deviceId;

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

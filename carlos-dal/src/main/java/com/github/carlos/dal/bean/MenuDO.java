package com.github.carlos.dal.bean;

import com.github.carlos.common.model.BaseObject;
import lombok.Data;

import java.util.Date;

/**
 * @author chenyanlong
 * @ClassName: MenuEntity
 * @Description: 左边栏模块实体 三级结构 前两级为左边栏 第三级对应 第二级模块功能操作
 * @date 2015年11月23日 下午2:39:56
 */
@Data
public class MenuDO extends BaseObject {


    private Integer id;

    private String name;
    /**
     * 请求方法路径 唯一
     */
    private String url;


    /**
     *
     */
    private String path;
    /**
     * 模块父类id
     */
    private Integer parentId;
    /**
     * 小图标
     */
    private String icon;
    /**
     * 级别
     */
    private Integer level;

    /**
     *
     */
    private Integer isLink;
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

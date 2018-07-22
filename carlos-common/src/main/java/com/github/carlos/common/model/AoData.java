package com.github.carlos.common.model;

import com.github.carlos.common.constant.SysConstant;
import com.github.carlos.common.utils.ThreadLocalContext;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Author:chyl2005
 * Date:17/3/26
 * Time:10:13
 * Desc:分页
 */
@Data
@NoArgsConstructor
public class AoData<T> {

    private String sEcho;

    /**
     * 起始索引
     */
    private Integer iDisplayStart;
    /**
     * 每页显示的行数
     */
    private Integer iDisplayLength;
    /**
     * 实际的行数
     */
    private Integer iTotalRecords;
    /**
     * 显示的行数,这个要和上面写的一样
     */
    private Integer iTotalDisplayRecords;


    private T datas;


    public AoData(Integer count, T datas) {
        Integer startRow = ThreadLocalContext.get(SysConstant.START_ROW);
        Integer pageSize = ThreadLocalContext.get(SysConstant.PAGE_SIZE);
        this.datas=datas;
        this.iDisplayStart=startRow;
        this.iDisplayLength=pageSize;
        this.iTotalRecords=count;
        this.iTotalDisplayRecords=count;

    }
}

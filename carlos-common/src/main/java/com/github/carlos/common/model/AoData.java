package com.github.carlos.common.model;

import com.github.carlos.common.utils.PageUtil;
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
    private Integer displayStart;
    /**
     * 每页显示的行数
     */
    private Integer displayLength;
    /**
     * 实际的行数
     */
    private Integer totalRecords;
    /**
     * 显示的行数,这个要和上面写的一样
     */
    private Integer totalDisplayRecords;


    private T datas;


    public AoData(Integer count, T datas) {
        Integer startRow = PageUtil.getStartRow();
        Integer pageSize = PageUtil.getPageSize();
        this.datas = datas;
        this.displayStart = startRow;
        this.displayLength = pageSize;
        this.totalRecords = count;
        this.totalDisplayRecords = count;

    }
}

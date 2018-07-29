package com.github.carlos.service;

import com.github.carlos.dal.bean.CategoryImageRelDO;
import com.github.carlos.dal.mapper.CategoryImageRelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author: ylong.chen@qunar.com
 * @Date: 2018/7/29 20:34
 * @description: TODO
 */
@Service
public class CategoryImageRelService {


    @Autowired
    private CategoryImageRelMapper categoryImageRelMapper;


    public Boolean insertOrUpdate(CategoryImageRelDO categoryImageRel) {

        categoryImageRelMapper.insertOrUpdate(categoryImageRel);
        return true;
    }


    public Boolean del(Integer categoryId, Integer imageId) {
        categoryImageRelMapper.del(categoryId, imageId);
        return true;
    }
}

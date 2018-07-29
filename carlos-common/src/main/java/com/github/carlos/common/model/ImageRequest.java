package com.github.carlos.common.model;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author: ylong.chen@qunar.com
 * @Date: 2018/7/21 14:18
 * @description: TODO
 */
@Data
public class ImageRequest {


    private Integer itemId;
    private MultipartFile uploadFile;

}

package com.atguigu.gmall.model.to;

import lombok.Data;

import java.util.List;

/**
 * @author sketch
 * @date 2022/8/26 19:53
 * @description
 */
@Data
public class CategoryTreeTo {
    private  Long categoryId;
    private String categoryName;
    private List<CategoryTreeTo> categoryChild;
}

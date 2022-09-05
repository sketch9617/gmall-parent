package com.atguigu.gmall.model.vo;

import lombok.Data;

/**
 * @author sketch
 * @date 2022/9/5 14:40
 * @description 封装检索条件
 */

@Data
public class SearchParamVo {
    Long category3Id;
    Long category1Id;
    Long category2Id;
    String keyword;
    String[] props;
    String trademark;
    String order;
    Integer pageNo;
}

package com.atguigu.gmall.model.vo;

import lombok.Data;

/**
 * @author sketch
 * @date 2022/9/5 14:42
 * @description
 */
@Data
public class OrderMapVo {
    private String type; //排序类型， 1是综合，2是价格
    private String sort; //排序规则
}

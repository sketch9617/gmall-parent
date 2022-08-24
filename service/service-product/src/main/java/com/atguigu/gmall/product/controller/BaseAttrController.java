package com.atguigu.gmall.product.controller;

import com.atguigu.gmall.common.result.Result;
import com.atguigu.gmall.model.product.BaseAttrInfo;
import com.atguigu.gmall.model.product.BaseAttrValue;
import com.atguigu.gmall.product.service.BaseAttrInfoService;
import com.atguigu.gmall.product.service.BaseAttrValueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author sketch
 * @date 2022/8/23 22:27
 * @description 平台属性相关API
 */
@RestController
@RequestMapping("/admin/product")
public class BaseAttrController {
    @Autowired
    BaseAttrInfoService baseAttrInfoService;
    @Autowired
    BaseAttrValueService baseAttrValueService;

    // TODO: 2022/8/23

    /**
     * 1. 查询某个分类下的所有平台属性
     */
    @GetMapping("/attrInfoList/{c1Id}/{c2Id}/{c3Id}")
    public Result getAttrInfoLisr(@PathVariable("c1Id") Long c1Id,
                                  @PathVariable("c2Id") Long c2Id,
                                  @PathVariable("c3Id") Long c3Id) {
        List<BaseAttrInfo> infos = baseAttrInfoService.getAttrInfoAndValueByCategoryId(c1Id, c2Id, c3Id);
        return Result.ok(infos);
    }

    /**
     * 2. 根据平台属性id，获取这个属性的所有属性值
     */
    @GetMapping("/getAttrValueList/{attrId}")
    public Result getAttrValueList(@PathVariable("attrId") Long attrId) {
        List<BaseAttrValue> values = baseAttrValueService.getAttrValueList(attrId);
        return Result.ok(values);
    }

    /**
     * 3. 保存、修改属性信息
     */
    @PostMapping("/saveAttrInfo")
    public Result saveAttrInfo(@RequestBody BaseAttrInfo info) {
        baseAttrInfoService.saveAttrInfo(info);
        return Result.ok();
    }
}

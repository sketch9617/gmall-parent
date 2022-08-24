package com.atguigu.gmall.product.controller;

import com.atguigu.gmall.common.result.Result;
import com.atguigu.gmall.model.product.BaseTrademark;
import com.atguigu.gmall.product.service.BaseTrademarkService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author sketch
 * @date 2022/8/24 19:02
 * @description 品牌API
 */
@RestController
@RequestMapping("/admin/product")
public class BaseTrademarkController {
    @Autowired
    BaseTrademarkService baseTradeMarkService;

    // TODO: 2022/8/24

    /**
     * 1. 分页查询所有品牌
     */
    @GetMapping("/baseTrademark/{pn}/{size}")
    public Result baseTrademark(@PathVariable("pn") Long pn,
                                 @PathVariable("size") Long size) {
        Page<BaseTrademark> page = new Page<>(pn, size);
        Page<BaseTrademark> pageResult = baseTradeMarkService.page(page);
        return Result.ok(pageResult);
    }

    /**
     * 2. 根据品牌id获取品牌信息
     */
    @GetMapping("/baseTrademark/get/{id}")
    public Result getBaseTrademark(@PathVariable("id") Long id) {
        BaseTrademark trademark = baseTradeMarkService.getById(id);
        return Result.ok(trademark);
    }

    /**
     * 3. 修改品牌
     */
    @PutMapping("/baseTrademark/update")
    public Result updatebaseTrademark(@RequestBody BaseTrademark trademark) {
        baseTradeMarkService.updateById(trademark);
        return Result.ok();
    }

    /**
     * 4. 保存品牌
     */
    @PostMapping("/baseTrademark/save")
    public Result savebaseTradeMark(@RequestBody BaseTrademark trademark) {
        baseTradeMarkService.save(trademark);
        return Result.ok();
    }

    /**
     * 5. 删除品牌
     */
    @DeleteMapping("/baseTrademark/remove/{tid}")
    public Result deletebaseTradeMark(@PathVariable("tid") Long tid) {
        baseTradeMarkService.removeById(tid);
        return Result.ok();
    }
}

package com.atguigu.gmall.feign.search;

import com.atguigu.gmall.common.result.Result;
import com.atguigu.gmall.model.list.Goods;
import com.atguigu.gmall.model.vo.SearchParamVo;
import com.atguigu.gmall.model.vo.SearchResponseVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

/**
 * @author sketch
 * @date 2022/9/5 14:34
 * @description
 */
@RequestMapping("/api/inner/rpc/search")
@FeignClient("service-search")
public interface SearchFeignClient {
    @PostMapping("/goods")
    Result saveGoods(@RequestBody Goods goods);

    @DeleteMapping("/goods/{skuId}")
    Result deleteGoods(@PathVariable("skuId") Long skuId);

    @PostMapping("/goods/search")
    Result<SearchResponseVo> search(@RequestBody SearchParamVo paramVo);

    @GetMapping("/goods/hotscore/{skuId}")
    Result updateHotScore(@PathVariable("skuId") Long skuId,
                          @RequestParam("score") Long score);
}

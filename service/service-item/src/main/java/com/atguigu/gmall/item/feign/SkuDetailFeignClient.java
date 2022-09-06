package com.atguigu.gmall.item.feign;

import com.atguigu.gmall.common.result.Result;
import com.atguigu.gmall.model.product.SkuImage;
import com.atguigu.gmall.model.product.SkuInfo;
import com.atguigu.gmall.model.product.SpuSaleAttr;
import com.atguigu.gmall.model.to.CategoryViewTo;
import com.atguigu.gmall.model.to.SkuDetailTo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author sketch
 * @date 2022/8/26 21:42
 * @description
 */
@FeignClient("service-product")
@RequestMapping("/api/inner/rpc/product")
public interface SkuDetailFeignClient {
    /**
     * 1. 查询sku的基本信息
     * @param skuId
     * @return
     */
    @GetMapping("/skudetail/info/{skuId}")
    Result<SkuInfo> getSkuInfo(@PathVariable("skuId") Long skuId);


    /**
     * 2. 查询sku图片信息
     * @param skuId
     * @return
     */
    @GetMapping("/skudetail/images/{skuId}")
    Result<List<SkuImage>> getSkuImages(@PathVariable("skuId")Long skuId);

    /**
     * 3. 查询sku完整分类信息
     * @param c3Id
     * @return
     */
    @GetMapping("/skudetail/categoryview/{c3Id}")
    Result<CategoryViewTo> getCategoryView(@PathVariable("c3Id") Long c3Id);

    /**
     * 4. 查询sku实时价格
     * @param skuId
     * @return
     */
    @GetMapping("/skudetail/price/{skuId}")
    Result<BigDecimal> getSku1010Price(@PathVariable("skuId")Long skuId);

    /**
     * 5. 查询spu下的所有sku销售属性组合并排序
     * @param skuId
     * @param spuId
     * @return
     */
    @GetMapping("/skudetail/saleattrvalues/{skuId}/{spuId}")
    Result<List<SpuSaleAttr>> getSkuSaleattrvalues(@PathVariable("skuId") Long skuId,
                                                   @PathVariable("spuId") Long spuId);


    /**
     * 6. 查sku同级别所有销售属性组合,封装成json
     * @param spuId
     * @return
     */
    @GetMapping("/skudetail/valuejson/{spuId}")
    Result<String> getSKuValueJson(@PathVariable("spuId") Long spuId);
}

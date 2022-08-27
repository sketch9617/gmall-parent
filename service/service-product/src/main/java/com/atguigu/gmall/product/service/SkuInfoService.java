package com.atguigu.gmall.product.service;

import com.atguigu.gmall.model.product.SkuInfo;
import com.atguigu.gmall.model.to.SkuDetailTo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author TANGLED
* @description 针对表【sku_info(库存单元表)】的数据库操作Service
* @createDate 2022-08-23 21:40:40
*/
public interface SkuInfoService extends IService<SkuInfo> {

    /**
     * 保存所有sku信息
     * @param info
     */
    void saveSkuInfo(SkuInfo info);

    /**
     * 商品上架
     * @param skuId
     */
    void onSale(Long skuId);

    /**
     * 商品下架
     * @param skuId
     */
    void cancelSale(Long skuId);

    /**
     * 查询sku商品详情
     * @param skuId
     * @return
     */
    SkuDetailTo getSkuDetail(Long skuId);
}

package com.atguigu.gmall.product.service;

import com.atguigu.gmall.model.list.Goods;
import com.atguigu.gmall.model.product.SkuImage;
import com.atguigu.gmall.model.product.SkuInfo;
import com.atguigu.gmall.model.to.SkuDetailTo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.math.BigDecimal;
import java.util.List;

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
     * 查询sku商品详情(已抽取)
     * @param skuId
     * @return
     */
//    SkuDetailTo getSkuDetail(Long skuId);

    /**
     * 获取sku的实时价格
     * @param skuId
     * @return
     */
    BigDecimal get1010Price(Long skuId);

    /**
     * 获取sku_info信息
     * @param skuId
     * @return
     */
    SkuInfo getDetailSkuInfo(Long skuId);

    /**
     * 获取sku的所有图片
     * @param skuId
     * @return
     */
    List<SkuImage> getDetailSkuImages(Long skuId);

    /**
     * 找到所有的商品id
     * @return
     */
    List<Long> findAllSkuId();

    /**
     * 得到某个sku在es中需要存储的所有数据
     * @param skuId
     * @return
     */
    Goods getGoodsBySkuId(Long skuId);
}

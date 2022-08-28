package com.atguigu.gmall.product.mapper;

import com.atguigu.gmall.model.product.SpuSaleAttr;
import com.atguigu.gmall.model.to.ValueSkuJsonTo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
* @author TANGLED
* @description 针对表【spu_sale_attr(spu销售属性)】的数据库操作Mapper
* @createDate 2022-08-23 21:40:40
* @Entity com.atguigu.gmall.product.domain.SpuSaleAttr
*/
public interface SpuSaleAttrMapper extends BaseMapper<SpuSaleAttr> {

    /**
     * 1.根据spuId查询对应的所有销售属性名和值
     * @param spuId
     * @return
     */
    List<SpuSaleAttr> getSaleAttrAndValueBySpuId(Long spuId);

    /**
     * 2.获取spu下的所有sku销售属性组合并排序
     * @param spuId
     * @param skuId
     * @return
     */
    List<SpuSaleAttr> getSaleAttrAndValueMarkSku(Long spuId, Long skuId);

    /**
     * 3. 获取sku同级别所有销售属性组合,封装成json
     * @param spuId
     * @return
     */
    List<ValueSkuJsonTo> getAllSkuValueJson(Long spuId);
}





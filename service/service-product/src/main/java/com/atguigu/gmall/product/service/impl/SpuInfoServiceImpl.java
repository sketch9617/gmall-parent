package com.atguigu.gmall.product.service.impl;

import com.atguigu.gmall.model.product.SpuImage;
import com.atguigu.gmall.model.product.SpuInfo;
import com.atguigu.gmall.model.product.SpuSaleAttr;
import com.atguigu.gmall.model.product.SpuSaleAttrValue;
import com.atguigu.gmall.product.service.SpuImageService;
import com.atguigu.gmall.product.service.SpuSaleAttrService;
import com.atguigu.gmall.product.service.SpuSaleAttrValueService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.atguigu.gmall.product.service.SpuInfoService;
import com.atguigu.gmall.product.mapper.SpuInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
* @author TANGLED
* @description 针对表【spu_info(商品表)】的数据库操作Service实现
* @createDate 2022-08-23 21:40:39
*/
@Service
public class SpuInfoServiceImpl extends ServiceImpl<SpuInfoMapper, SpuInfo>
    implements SpuInfoService{

    @Resource
    SpuInfoMapper spuInfoMapper;
    @Autowired
    SpuSaleAttrService spuSaleAttrService;
    @Autowired
    SpuSaleAttrValueService spuSaleAttrValueService;
    @Autowired
    SpuImageService spuImageService;

    /**
     * 保存所有spu信息(spu信息,spu图片,销售属性,销售属性值,)
     * @param info
     */
    @Override
    public void saveSpuInfo(SpuInfo info) {

        //1. 保存spu基本信息
        spuInfoMapper.insert(info);
        Long spuId = info.getId();

        //2. 保存图片
        List<SpuImage> imageList = info.getSpuImageList();
        for (SpuImage spuImage : imageList) {
            spuImage.setSpuId(spuId);
        }
        spuImageService.saveBatch(imageList);

        //3. 保存销售属性,属性值
        List<SpuSaleAttr> attrNameList = info.getSpuSaleAttrList();
        for (SpuSaleAttr attr : attrNameList) {
            attr.setSpuId(spuId);
            List<SpuSaleAttrValue> valueList = attr.getSpuSaleAttrValueList();
            for (SpuSaleAttrValue value : valueList) {
                value.setSpuId(spuId);
                String saleAttrName = attr.getSaleAttrName();
                value.setSaleAttrName(saleAttrName);
            }
            //保存销售属性值
            spuSaleAttrValueService.saveBatch(valueList);
        }
        //保存销售属性
        spuSaleAttrService.saveBatch(attrNameList);
    }
}





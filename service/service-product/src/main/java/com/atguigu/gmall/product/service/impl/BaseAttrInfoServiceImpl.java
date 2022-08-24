package com.atguigu.gmall.product.service.impl;

import com.atguigu.gmall.model.product.BaseAttrInfo;
import com.atguigu.gmall.model.product.BaseAttrValue;
import com.atguigu.gmall.product.mapper.BaseAttrValueMapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.atguigu.gmall.product.service.BaseAttrInfoService;
import com.atguigu.gmall.product.mapper.BaseAttrInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
* @author TANGLED
* @description 针对表【base_attr_info(属性表)】的数据库操作Service实现
* @createDate 2022-08-23 21:40:40
*/
@Service
public class BaseAttrInfoServiceImpl extends ServiceImpl<BaseAttrInfoMapper, BaseAttrInfo>
    implements BaseAttrInfoService{

    @Resource
    BaseAttrInfoMapper baseAttrInfoMapper;
    @Resource
    BaseAttrValueMapper baseAttrValueMapper;

    /**
     * 1. 查询某个分类下的所有平台属性
     * @param c1Id
     * @param c2Id
     * @param c3Id
     * @return infos
     */
    @Override
    public List<BaseAttrInfo> getAttrInfoAndValueByCategoryId(Long c1Id, Long c2Id, Long c3Id) {
        List<BaseAttrInfo> infos = baseAttrInfoMapper.getAttrInfoAndValueByCategoryId(c1Id, c2Id, c3Id);
        return infos;
    }

    @Override
    public void saveAttrInfo(BaseAttrInfo info) {
        if (info.getId() == null) {
            //1.无id,属性新增
            addBaseAttrInfo(info);
        } else {
            //2. 有id,属性修改
            updateBaseAttrInfo(info);
        }
    }

    private void updateBaseAttrInfo(BaseAttrInfo info) {
        //修改属性名
        baseAttrInfoMapper.updateById(info);
        List<BaseAttrValue> valueList = info.getAttrValueList();
        //根据请求中的属性id判断属性的删除->修改/新增
        List<Long> vids = new ArrayList<>();
        for (BaseAttrValue attrValue : valueList) {
            Long id = attrValue.getId();
            if (id != null) {
                vids.add(id);
            }
        }
        // delete * from base_attr_value where attr_id=11 and id not in(59,61)
        if (vids.size() > 0) {
            // 删掉属性id不在当前vids的属性值
            QueryWrapper<BaseAttrValue> deleteWrapper = new QueryWrapper<>();
            deleteWrapper.eq("attr_id", info.getId());
            deleteWrapper.notIn("id", vids);
            baseAttrValueMapper.delete(deleteWrapper);
        } else {
            // 请求中没有属性id,全删除
            QueryWrapper<BaseAttrValue> deleteWrapper = new QueryWrapper<>();
            deleteWrapper.eq("attr_id", info.getId());
            baseAttrValueMapper.delete(deleteWrapper);
        }
        for (BaseAttrValue attrValue : valueList) {
            //修改属性值
            if (attrValue.getId() != null) {
                baseAttrValueMapper.updateById(attrValue);
            }
            //新增属性值
            if (attrValue.getId() == null) {
                attrValue.setAttrId(info.getId());
                baseAttrValueMapper.insert(attrValue);
            }
        }
    }

    private void addBaseAttrInfo(BaseAttrInfo info) {
        baseAttrInfoMapper.insert(info);
        //新增获取自增的属性id
        Long id = info.getId();
        //新增属性值
        List<BaseAttrValue> valueList = info.getAttrValueList();
        for (BaseAttrValue value : valueList) {
            //回填属性名记录的自增id
            value.setAttrId(id);
            baseAttrValueMapper.insert(value);
        }
    }
}





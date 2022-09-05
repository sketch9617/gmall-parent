package com.atguigu.gmall.search.repository;

import com.atguigu.gmall.model.list.Goods;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

/**
 * @author sketch
 * @date 2022/9/5 14:07
 * @description
 */
@Repository
public interface GoodsRepository extends ElasticsearchRepository<Goods,Long> {
}

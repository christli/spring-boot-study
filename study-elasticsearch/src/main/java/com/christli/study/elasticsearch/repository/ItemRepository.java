package com.christli.study.elasticsearch.repository;

import com.christli.study.elasticsearch.entity.ItemEntity;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface ItemRepository extends ElasticsearchRepository<ItemEntity, Long> {

    /**
     * 根据价格区间查询
     *
     * @param price1
     * @param price2
     * @return
     */
    List<ItemEntity> findByPriceBetween(double price1, double price2);
}


package com.christli.study.elasticsearch.entity;

import lombok.*;
import org.springframework.data.annotation.*;
import org.springframework.data.elasticsearch.annotations.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(indexName = "item_index")
public class ItemEntity {
    @Id
    private Long id;

    @Field(type = FieldType.Text/*, analyzer = "ik_max_word"*/)
    private String title; //标题

    @Field(type = FieldType.Keyword)
    private String category;// 分类

    @Field(type = FieldType.Keyword)
    private String brand; // 品牌

    @Field(type = FieldType.Double)
    private Double price; // 价格

    @Field(index = false, type = FieldType.Keyword)
    private String images; // 图片地址
}


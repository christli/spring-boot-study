# 整合Elasticsearch

- 安装参考 [Install Elasticsearch with Docker](https://www.elastic.co/guide/en/elasticsearch/reference/7.5/docker.html)
- 代码参考 [spring-data-elasticsearch](https://snakey.blog.csdn.net/article/details/107931466)

---
> - SPRING.DATA.ELASTICSEARCH.CLUSTER-NODES、CLUSTER-NAME过时
> - 这个在高版本中已经废弃，官方建议我们使用：High Level REST Client

````java

@Configuration
@EnableElasticsearchRepositories
public class RestClientConfig extends AbstractElasticsearchConfiguration {

    @Override
    @Bean
    public RestHighLevelClient elasticsearchClient() {

        final ClientConfiguration clientConfiguration = ClientConfiguration.builder()
                .connectedTo("localhost:9200")
                .build();

        return RestClients.create(clientConfiguration).rest();
    }
}
````

> - 9200：使用http请求，所以我们的rest方式的client要使用这个端口进行访问
> - 9300：使用tcp请求，是系统预留给es内部组件之间的通信方式

> - 实体简单的示例一下

````java

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
````

> - repository只需要简单的继承ElasticsearchRepository即可

````java
public interface ItemRepository extends ElasticsearchRepository<ItemEntity, Long> {
    List<ItemEntity> findByPriceBetween(double price1, double price2);
}
````

> repository的search方法基本弃用了，可以参考[elasticsearch.operations](https://docs.spring.io/spring-data/elasticsearch/docs/4.1.8/reference/html/#elasticsearch.operations)
> - 正常的方法，用repository的方法，具体看文档[elasticsearch.repositories](https://docs.spring.io/spring-data/elasticsearch/docs/4.1.8/reference/html/#elasticsearch.repositories)
> - 复杂点的，例如需要用到聚合aggregate之类的，用operations

---

## 关于Lucene

- [Lucene学习总结之一：全文检索的基本原理](https://blog.csdn.net/forfuture1978/article/details/4711308)
- [全文检索工具Lucene入门教程](https://blog.csdn.net/qq_23853743/article/details/107189544)
- [springboot 集成lucene](https://blog.csdn.net/yb546822612/article/details/103063641)
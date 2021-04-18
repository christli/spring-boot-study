package com.christli.study.elasticsearch.repository;

import com.christli.study.elasticsearch.entity.ItemEntity;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.metrics.Avg;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.*;
import org.springframework.data.elasticsearch.core.query.*;

import java.util.ArrayList;
import java.util.List;

import static org.elasticsearch.search.aggregations.AggregationBuilders.*;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ItemRepositoryTest {

    @Autowired
    private ElasticsearchRestTemplate mRestTemplate;

    @Qualifier("elasticsearchOperations")
    @Autowired
    private ElasticsearchOperations operations;

    @Autowired
    private ItemRepository itemRepository;

    @BeforeAll
    void before() {
        deleteIndex();
        createIndex();
        insert();
        insertList();
    }

    @AfterAll
    void tearDown() {
        delete();
        deleteIndex();
    }

    /**
     * 创建索引
     */
    public void createIndex() {
        // 创建索引，会根据Item类的@Document注解信息来创建
        mRestTemplate.createIndex(ItemEntity.class);
        // 配置映射，会根据Item类中的id、Field等字段来自动完成映射
        mRestTemplate.putMapping(ItemEntity.class);
    }

    /**
     * 新增
     */
    public void insert() {
        ItemEntity item = new ItemEntity(1L, "小米手机7", "手机", "小米", 2999.00, "https://img12.360buyimg.com/n1/s450x450_jfs/t1/14081/40/4987/124705/5c371b20E53786645/c1f49cd69e6c7e6a.jpg");
        itemRepository.save(item);
    }

    /**
     * 批量新增
     */
    public void insertList() {
        List<ItemEntity> list = new ArrayList<>();
        list.add(new ItemEntity(2L, "坚果手机R1", "手机", "锤子", 3999.00, "https://img12.360buyimg.com/n1/s450x450_jfs/t1/14081/40/4987/124705/5c371b20E53786645/c1f49cd69e6c7e6a.jpg"));
        list.add(new ItemEntity(3L, "华为META20", "手机", "华为", 4999.00, "https://img12.360buyimg.com/n1/s450x450_jfs/t1/14081/40/4987/124705/5c371b20E53786645/c1f49cd69e6c7e6a.jpg"));
        list.add(new ItemEntity(4L, "iPhone X", "手机", "iPhone", 5100.00, "https://img12.360buyimg.com/n1/s450x450_jfs/t1/14081/40/4987/124705/5c371b20E53786645/c1f49cd69e6c7e6a.jpg"));
        list.add(new ItemEntity(5L, "iPhone XS", "手机", "iPhone", 5999.00, "https://img12.360buyimg.com/n1/s450x450_jfs/t1/14081/40/4987/124705/5c371b20E53786645/c1f49cd69e6c7e6a.jpg"));
        // 接收对象集合，实现批量新增
        itemRepository.saveAll(list);
    }

    /**
     * 修改
     *
     * ：修改和新增是同一个接口，区分的依据就是id，这一点跟我们在页面发起PUT请求是类似的。
     */

    /**
     * 删除所有
     */
    public void delete() {
        itemRepository.deleteAll();
    }


    /**
     * 基本查询
     */
    @Test
    public void query() {
        // 查询全部，并按照价格降序排序
        Iterable<ItemEntity> items = itemRepository.findAll(Sort.by("price").descending());
        items.forEach(item -> System.out.println("item = " + item));
    }

    /**
     * 自定义方法
     */
    @Test
    public void queryByPriceBetween() {
        // 根据价格区间查询
        List<ItemEntity> list = itemRepository.findByPriceBetween(5000.00, 6000.00);
        list.forEach(item -> System.out.println("item = " + item));
    }

    /**
     * 自定义查询
     */
    @Test
    public void search() {
        // 构建查询条件
        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();
        // 添加基本分词查询
        queryBuilder.withQuery(QueryBuilders.matchQuery("title", "小米手机"));
        // 搜索，获取结果
        SearchHitsIterator<ItemEntity> items = operations.searchForStream(queryBuilder.build(), ItemEntity.class);
        // 总条数
        long total = items.getTotalHits();
        System.out.println("total = " + total);
        while (items.hasNext()){
            System.out.println("item = " + items.next().getContent());
        }
    }

    /**
     * 分页查询
     */
    @Test
    public void searchByPage() {
        // 构建查询条件
        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();
        // 添加基本分词查询
        queryBuilder.withQuery(QueryBuilders.termQuery("category", "手机"));
        // 分页：
        int page = 0;
        int size = 2;
        queryBuilder.withPageable(PageRequest.of(page, size));
        // 搜索，获取结果
        Page<ItemEntity> items = itemRepository.search(queryBuilder.build());
        long total = items.getTotalElements();
        System.out.println("总条数 = " + total);
        System.out.println("总页数 = " + items.getTotalPages());
        System.out.println("当前页：" + items.getNumber());
        System.out.println("每页大小：" + items.getSize());
        items.forEach(item -> System.out.println("item = " + item));
    }

    /**
     * 排序
     */
    @Test
    public void searchAndSort() {
        // 构建查询条件
        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();
        // 添加基本分词查询
        queryBuilder.withQuery(QueryBuilders.termQuery("category", "手机"));
        // 排序
        queryBuilder.withSort(SortBuilders.fieldSort("price").order(SortOrder.ASC));
        // 搜索，获取结果
        Page<ItemEntity> items = this.itemRepository.search(queryBuilder.build());
        // 总条数
        long total = items.getTotalElements();
        System.out.println("总条数 = " + total);
        items.forEach(item -> System.out.println("item = " + item));
    }

    /**
     * 聚合为桶
     */
    @Test
    public void testAgg() {
        Query query = new NativeSearchQueryBuilder()
                .addAggregation(terms("brands").field("brand").size(10))
//                .withQuery(QueryBuilders.matchQuery("brand", brand))
                .withSourceFilter(new FetchSourceFilter(new String[]{""}, null))
                .build();

        SearchHits<ItemEntity> searchHits = operations.search(query, ItemEntity.class);
        Terms agg = searchHits.getAggregations().get("brands");
        // 获取桶
        List<Terms.Bucket> buckets = (List<Terms.Bucket>) agg.getBuckets();
        // 遍历
        for (Terms.Bucket bucket : buckets) {
            // 3.4、获取桶中的key，即品牌名称
            System.out.println(bucket.getKeyAsString());
            // 3.5、获取桶中的文档数量
            System.out.println(bucket.getDocCount());
        }
    }

    /**
     * 嵌套聚合，求平均值
     */
    @Test
    public void testSubAgg() {
        Query query = new NativeSearchQueryBuilder()
                .addAggregation(
                        terms("brands").field("brand")
                                .subAggregation(avg("priceAvg").field("price")
                                ))
//                .withQuery(QueryBuilders.matchQuery("brand", brand))
                .withSourceFilter(new FetchSourceFilter(new String[]{""}, null))
                .build();

        SearchHits<ItemEntity> searchHits = operations.search(query, ItemEntity.class);
        Terms agg = searchHits.getAggregations().get("brands");
        // 获取桶
        List<Terms.Bucket> buckets = (List<Terms.Bucket>) agg.getBuckets();
        // 遍历
        for (Terms.Bucket bucket : buckets) {
            // 3.4、获取桶中的key，即品牌名称  3.5、获取桶中的文档数量
            System.out.println(bucket.getKeyAsString() + "，共" + bucket.getDocCount() + "台");

            // 3.6.获取子聚合结果：
            Avg avg = (Avg) bucket.getAggregations().asMap().get("priceAvg");
            System.out.println("平均售价：" + avg.getValue());
        }
    }


    /**
     * 删除索引
     */
//    @Test
    public void deleteIndex() {
        mRestTemplate.deleteIndex(ItemEntity.class);
    }
}

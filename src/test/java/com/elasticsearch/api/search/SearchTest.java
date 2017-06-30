package com.elasticsearch.api.search;

import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.junit.Test;

import com.elasticsearch.api.AbstractApi;

public class SearchTest extends AbstractApi {

	//组合查询
	@Test
	public void test() {
		recreateIndex("library");
		Client client = getClient();

		QueryBuilder queryBuilder = QueryBuilders.disMaxQuery()    //对子查询的结果做union，score沿用子查询score的最大值。这种查询广泛应用于muti-field的查询。
				.add(QueryBuilders.termQuery("title", "Elastic"))
				.add(QueryBuilders.prefixQuery("title", "el"));    //前缀查询

		System.out.println("Generated query: " + queryBuilder.toString());   //toString方法输出已经构造好的查询的json

		SearchResponse response = client.prepareSearch("library")
				.setQuery(queryBuilder)
				.addFields("title", "_source")
				.execute().actionGet();
		showHits(response);
	}

	//分页查询
	@Test
	public void paging() {
		recreateIndex("library");
		Client client = getClient();

		SearchResponse response = client.prepareSearch("library")
				.setQuery(QueryBuilders.matchAllQuery())   //matchAllQuery 匹配索引或多个索引中的所有文档
				.setFrom(10)  //分页起始位置
				.setSize(20)  //分页大小
				.execute().actionGet();
		showHits(response);
	}

	//排序 
	@Test
	public void sorting() {
		recreateIndex("library");
		Client client = getClient();

		SearchRequestBuilder b = client.prepareSearch("library")
				.setQuery(QueryBuilders.matchAllQuery())
				.addSort(SortBuilders.fieldSort("title"))
				.addSort("_score", SortOrder.DESC);

		System.err.println("Generated query: " + b.toString());

		SearchResponse response = client.prepareSearch("library")
				.setQuery(QueryBuilders.matchAllQuery())
				//.addSort(SortBuilders.fieldSort("title"))  //按title字段排序
				.addSort("_score", SortOrder.DESC)  //按得分倒序
				.execute().actionGet();
		showHits(response);
		//SortBuilders.geoDistanceSort()
	}

	private void showHits(final SearchResponse response) {
		for (SearchHit hit : response.getHits().getHits()) {
			System.out.println("Id: " + hit.getId());
			System.out.println("Score: " + hit.getScore());
			if (hit.getFields().containsKey("title")) {
				System.out.println("field.title: " + hit.getFields().get("title").getValue());
			}

			System.out.println("source.title: " + hit.getSource().get("title"));
			System.out.println();
		}
	}

}

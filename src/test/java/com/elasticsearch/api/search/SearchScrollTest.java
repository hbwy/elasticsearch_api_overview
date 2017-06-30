package com.elasticsearch.api.search;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.Client;
import org.elasticsearch.search.SearchHit;
import org.junit.Test;

import com.elasticsearch.api.AbstractApi;

public class SearchScrollTest extends AbstractApi {

	//滚动
	@Test
	public void test() {
		recreateIndex("library");
		
		Client client = getClient();
		//第一个请求指定查询条件和滚动属性
		SearchResponse responseSearch = client.prepareSearch("library")
				.setScroll("1m")
				.setSearchType(SearchType.SCAN)
				.execute().actionGet();
		//第二个请求根据滚动id取出文档
		String scrollId = responseSearch.getScrollId();
		SearchResponse response = client.prepareSearchScroll(scrollId)
				.execute().actionGet();
		
		for(SearchHit hit: response.getHits().getHits()) {
			System.out.println(hit.getId());
		}
	}

}

package com.elasticsearch.api.search;

import org.elasticsearch.action.count.CountResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.index.query.QueryBuilders;
import org.junit.Test;

import com.elasticsearch.api.AbstractApi;

public class CountTest extends AbstractApi {

    /**
     * 查询匹配的文档数  
     */
	@Test
	public void test() {
		//recreateIndex("library");
		Client client = getClient();
		
		CountResponse response = client.prepareCount("library")
				.setQuery(QueryBuilders.matchQuery("title", "Book"))
				.execute().actionGet();
		
		System.out.println(response.getCount());
	}

}

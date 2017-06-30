package com.elasticsearch.api.multi;

import java.io.IOException;

import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.action.search.MultiSearchResponse;
import org.elasticsearch.action.search.MultiSearchResponse.Item;
import org.elasticsearch.client.Client;
import org.elasticsearch.index.query.FilterBuilders;
import org.junit.Test;

import com.elasticsearch.api.AbstractApi;

public class MultiSearchTest extends AbstractApi {

	//组合多个查询请求
	@Test
	public void test() throws ElasticsearchException, IOException {
		recreateIndex("library");
		createSomeBooks("library");
		recreateIndex("news");
		Client client = getClient();

		MultiSearchResponse response = client.prepareMultiSearch()
				.add(client.prepareSearch("library", "book").request())
				.add(client.prepareSearch("news").setPostFilter(FilterBuilders.termFilter("tags", "important")))
				.execute().actionGet();

		for (Item resp : response.getResponses()) {
			System.out.println(resp.getResponse().getTookInMillis());
		}
	}

}

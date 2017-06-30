package com.elasticsearch.api.filter;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.index.query.FilterBuilder;
import org.elasticsearch.index.query.FilterBuilders;
import org.elasticsearch.search.SearchHit;
import org.junit.Test;

import com.elasticsearch.api.AbstractApi;

public class FilterTest extends AbstractApi {

	//使用过滤器
	@Test
	public void test() {
		recreateIndex("library");
		Client client = getClient();

		FilterBuilder filterBuilder = FilterBuilders.andFilter(
				FilterBuilders.existsFilter("title").filterName("exist"), 
				FilterBuilders.termFilter("title", "elastic"));

		System.out.println(filterBuilder.toString());

		SearchResponse response = client.prepareSearch("library")
				.setPostFilter(filterBuilder)
				.execute().actionGet();
		showHits(response);

	}

	private void showHits(final SearchResponse response) {
		for (SearchHit hit : response.getHits().getHits()) {
			System.out.println(hit.getId());
		}
	}

}

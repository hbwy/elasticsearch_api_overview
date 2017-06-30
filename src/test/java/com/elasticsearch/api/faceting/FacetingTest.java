package com.elasticsearch.api.faceting;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.index.query.FilterBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.facet.FacetBuilder;
import org.elasticsearch.search.facet.FacetBuilders;
import org.junit.Test;

import com.elasticsearch.api.AbstractApi;

public class FacetingTest extends AbstractApi {

	//切面计算
	@Test
	public void test() {
		recreateIndex("library");
		Client client = getClient();

		FacetBuilder facetBuilder = FacetBuilders.filterFacet("test")
				.filter(FilterBuilders.termFilter("title", "elastic"));

		System.out.println(facetBuilder.toString());

		SearchResponse response = client.prepareSearch("library")
				.addFacet(facetBuilder)
				.execute().actionGet();
		showHits(response);

	}

	private void showHits(final SearchResponse response) {
		for (SearchHit hit : response.getHits().getHits()) {
			System.out.println(hit.getId());
		}
	}

}

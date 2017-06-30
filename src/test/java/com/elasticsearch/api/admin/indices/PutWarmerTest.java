package com.elasticsearch.api.admin.indices;

import java.io.IOException;

import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.action.admin.indices.warmer.put.PutWarmerResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.search.facet.FacetBuilders;
import org.junit.Test;

import com.elasticsearch.api.AbstractApi;

public class PutWarmerTest extends AbstractApi {

	// 设置预热器api
	@Test
	public void test() throws ElasticsearchException, IOException {
		recreateIndex("library");
		Client client = getClient();

		PutWarmerResponse response = client.admin().indices()
				.preparePutWarmer("library_warmer")
				.setSearchRequest(client.prepareSearch("library")
						.addFacet(FacetBuilders.termsFacet("tags")
								.field("tags")))
				.execute().actionGet();

		System.out.println("Ack: " + response.isAcknowledged());

	}

}

package com.elasticsearch.api.admin.indices;

import java.io.IOException;

import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.action.admin.indices.alias.IndicesAliasesResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.index.query.FilterBuilders;
import org.junit.Test;

import com.elasticsearch.api.AbstractApi;

public class AliasesTest extends AbstractApi {

	//别名api
	@Test
	public void test() throws ElasticsearchException, IOException {
		recreateIndex("news");
		Client client = getClient();

		IndicesAliasesResponse response = client.admin().indices()
			.prepareAliases()
				.addAlias("news", "n")
				.addAlias("library", "elastic_books", FilterBuilders.termFilter("title", "elasticsearch"))
				.removeAlias("news", "current_news")
			.execute().actionGet();
	
		System.out.println("ACK: " + response.isAcknowledged());

	}

}

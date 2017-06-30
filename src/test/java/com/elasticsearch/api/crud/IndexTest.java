package com.elasticsearch.api.crud;

import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.Client;
import org.junit.Test;

import com.elasticsearch.api.AbstractApi;

public class IndexTest extends AbstractApi {

	@Test
	public void test() {
		Client client = getClient();

		IndexResponse response = client.prepareIndex("library", "book", "2")
				                       .setSource("{ \"title\": \"Mastering ElasticSearch\"}")
				                       .execute().actionGet();

		System.out.println("Document: " + response.getIndex() + "/" + response.getType() + "/" + response.getId());
	}
}

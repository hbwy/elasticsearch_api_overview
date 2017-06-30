package com.elasticsearch.api.multi;

import org.elasticsearch.action.bulk.BulkItemResponse;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.client.Client;
import org.junit.Test;

import com.elasticsearch.api.AbstractApi;

public class BulkTest extends AbstractApi {

	//批量操作
	@Test
	public void test() {
		Client client = getClient();

		BulkResponse response = client.prepareBulk()
				.add(client.prepareIndex("library", "book", "5").setSource("{ \"title\" : \"Solr Cookbook\"}").request())
				.add(client.prepareDelete("library", "book", "2").request())
				.execute().actionGet();

		for (BulkItemResponse hit : response.getItems()) {
			System.out.println(hit.getId());
		}
	}

}

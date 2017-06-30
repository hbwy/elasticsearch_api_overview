package com.elasticsearch.api.admin.indices;

import java.io.IOException;

import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.action.admin.indices.close.CloseIndexResponse;
import org.elasticsearch.client.Client;
import org.junit.Test;

import com.elasticsearch.api.AbstractApi;

public class CloseTest extends AbstractApi {

	//关闭索引api
	@Test
	public void test() throws ElasticsearchException, IOException {
		recreateIndex("library");
		createSomeBooks("library");
		
		Client client = getClient();
		CloseIndexResponse response = client.admin().indices()
			.prepareClose("library")
			.execute().actionGet();

		System.out.println("Ack: " + response.isAcknowledged());

	}

}

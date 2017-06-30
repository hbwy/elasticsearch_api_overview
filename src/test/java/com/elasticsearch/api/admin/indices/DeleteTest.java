package com.elasticsearch.api.admin.indices;

import java.io.IOException;

import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexResponse;
import org.elasticsearch.client.Client;
import org.junit.Test;

import com.elasticsearch.api.AbstractApi;

public class DeleteTest extends AbstractApi {

	//删除索引api
	@Test
	public void test() throws ElasticsearchException, IOException {
		recreateIndex("news");
		
		Client client = getClient();
		DeleteIndexResponse response = client.admin().indices()
			.prepareDelete("news")
			.execute().actionGet();
	
		System.out.println("Ack: " + response.isAcknowledged());

	}

}

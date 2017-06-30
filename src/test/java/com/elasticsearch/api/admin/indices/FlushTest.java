package com.elasticsearch.api.admin.indices;

import java.io.IOException;

import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.action.admin.indices.flush.FlushResponse;
import org.elasticsearch.client.Client;
import org.junit.Test;

import com.elasticsearch.api.AbstractApi;

public class FlushTest extends AbstractApi {
	//清空缓冲区api
	@Test
	public void test() throws ElasticsearchException, IOException {
		recreateIndex("library");
		Client client = getClient();
		
		FlushResponse response = client.admin().indices()
				.prepareFlush("library")
				.setFull(false)
				.execute().actionGet();

		System.out.println("Total Shards: " + response.getTotalShards());

	}

}

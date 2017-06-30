package com.elasticsearch.api.admin.indices;

import java.io.IOException;

import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.action.admin.indices.refresh.RefreshResponse;
import org.elasticsearch.client.Client;
import org.junit.Test;

import com.elasticsearch.api.AbstractApi;

public class RefreshTest extends AbstractApi {

	//刷新索引api
	@Test
	public void test() throws ElasticsearchException, IOException {
		recreateIndex("library");
		
		Client client = getClient();
		RefreshResponse response = client.admin().indices()
			.prepareRefresh("library")
			.execute().actionGet();

		System.out.println("Total Shards: " + response.getTotalShards());

	}

}

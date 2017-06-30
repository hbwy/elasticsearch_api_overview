package com.elasticsearch.api.admin.indices;

import java.io.IOException;

import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.action.admin.indices.gateway.snapshot.GatewaySnapshotResponse;
import org.elasticsearch.client.Client;
import org.junit.Test;

import com.elasticsearch.api.AbstractApi;

public class GatewaySnapshotTest extends AbstractApi {

	/**
	 * 网关快照api
	 * 该方法强制Es生成特定索引的快照，只针对那些做了分片的网关类型
	 * 目前已经废弃
	 */
	@Test
	public void test() throws ElasticsearchException, IOException {
		recreateIndex("news");
		
		Client client = getClient();
		//Note that this has no effect for local gateways
		GatewaySnapshotResponse response = client.admin().indices()
			.prepareGatewaySnapshot("news")
			.execute().actionGet();
	
		System.out.println("Successful Shards: " + response.getSuccessfulShards());

	}

}

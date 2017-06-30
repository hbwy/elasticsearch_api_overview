package com.elasticsearch.api.admin.cluster;

import java.util.Map.Entry;

import org.elasticsearch.action.admin.cluster.health.ClusterHealthResponse;
import org.elasticsearch.action.admin.cluster.health.ClusterIndexHealth;
import org.elasticsearch.client.Client;
import org.junit.Test;

import com.elasticsearch.api.AbstractApi;

public class HealthTest extends AbstractApi {

	//集群和索引健康状态api
	@Test
	public void test() {
		recreateIndex("library");

		Client client = getClient();
		ClusterHealthResponse response = client.admin().cluster()
				.prepareHealth("library")
				.execute().actionGet();

		for (Entry<String, ClusterIndexHealth> entry : response.getIndices().entrySet()) {
			System.out.println("Index: " + entry.getKey() + " (status: " + entry.getValue().getStatus() + ")");
			System.out.println("This index has : " + entry.getValue().getNumberOfShards() + " shards" + " and " + entry.getValue().getNumberOfReplicas() + " replicas.");
		}
	}

}

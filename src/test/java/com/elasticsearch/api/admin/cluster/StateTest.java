package com.elasticsearch.api.admin.cluster;

import java.util.Map.Entry;

import org.elasticsearch.action.admin.cluster.state.ClusterStateResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.cluster.routing.IndexRoutingTable;
import org.junit.Test;

import com.elasticsearch.api.AbstractApi;

public class StateTest extends AbstractApi {

	//集群状态api
	@Test
	public void test() {
		Client client = getClient();
		ClusterStateResponse response = client.admin().cluster()
			.prepareState()
			.execute().actionGet();
		
		String rn = response.getState()
				.getRoutingNodes()
				.prettyPrint();
		System.out.println(rn);
		
		for (Entry<String, IndexRoutingTable> entry : response.getState().getRoutingTable().indicesRouting().entrySet()) {
			System.out.println("Index: " + entry.getKey());
			System.out.println(entry.getValue().prettyPrint());
		}
	
	}

}

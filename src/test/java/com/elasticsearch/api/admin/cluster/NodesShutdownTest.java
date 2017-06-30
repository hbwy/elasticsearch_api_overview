package com.elasticsearch.api.admin.cluster;

import org.elasticsearch.action.admin.cluster.node.shutdown.NodesShutdownResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.cluster.node.DiscoveryNode;
import org.junit.Test;

import com.elasticsearch.api.AbstractApi;

public class NodesShutdownTest extends AbstractApi {
	//节点关闭api
	@Test
	public void test() {
	
		Client client = getClient();
		NodesShutdownResponse response = client.admin().cluster()
			.prepareNodesShutdown()
			.execute().actionGet();
		
		for (DiscoveryNode node : response.getNodes()) {
			System.out.println(node.getName());
		}

	}

}

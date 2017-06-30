package com.elasticsearch.api.admin.cluster;

import org.elasticsearch.action.admin.cluster.node.hotthreads.NodeHotThreads;
import org.elasticsearch.action.admin.cluster.node.hotthreads.NodesHotThreadsResponse;
import org.elasticsearch.client.Client;
import org.junit.Test;

import com.elasticsearch.api.AbstractApi;

public class NodesHotThreadsTest extends AbstractApi {

	//节点热点线程api
	//用于在Es出故障或CPU使用率超过正常值时检查节点状态
	@Test
	public void test() {
		
		Client client = getClient();
		NodesHotThreadsResponse response = client.admin().cluster()
			.prepareNodesHotThreads()
			.execute().actionGet();
		
		for (NodeHotThreads nodeHotThreads : response.getNodes()) {
			System.out.println(nodeHotThreads.getHotThreads());
		}

	}

}

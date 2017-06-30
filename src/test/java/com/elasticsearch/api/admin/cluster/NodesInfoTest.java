package com.elasticsearch.api.admin.cluster;

import org.elasticsearch.action.admin.cluster.node.info.NodeInfo;
import org.elasticsearch.action.admin.cluster.node.info.NodesInfoResponse;
import org.elasticsearch.action.admin.cluster.node.info.PluginInfo;
import org.elasticsearch.client.Client;
import org.junit.Test;

import com.elasticsearch.api.AbstractApi;

public class NodesInfoTest extends AbstractApi {

	//节点信息api
	@Test
	public void test() {
		Client client = getClient();
		
		NodesInfoResponse response = client.admin().cluster()
			.prepareNodesInfo()
			.setNetwork(true)
			.setPlugin(true)
			.execute().actionGet();
		
		for( NodeInfo node : response.getNodes()) {
			System.out.println("Node eth address: " + node.getNetwork().primaryInterface().getMacAddress());
			System.out.println("Plugins: ");
			for( PluginInfo plugin : node.getPlugins().getInfos()) {
				System.out.println(plugin.getName() + " " + plugin.getUrl());
			}
		}
		

	}

}

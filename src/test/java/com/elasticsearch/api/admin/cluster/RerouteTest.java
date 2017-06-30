package com.elasticsearch.api.admin.cluster;

import org.elasticsearch.action.admin.cluster.reroute.ClusterRerouteResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.cluster.routing.allocation.command.CancelAllocationCommand;
import org.elasticsearch.cluster.routing.allocation.command.MoveAllocationCommand;
import org.elasticsearch.index.shard.ShardId;
import org.junit.Test;

import com.elasticsearch.api.AbstractApi;

public class RerouteTest extends AbstractApi {

	//重新路由api
	@Test
	public void test() {
		Client client = getClient();

		ClusterRerouteResponse response = client.admin().cluster()
				.prepareReroute().setDryRun(true)
				.add(new MoveAllocationCommand(new ShardId("library", 1), "G3czOt4HQbKZT1RhpPCULw", "PvHtEMuRSJ6rLJ27AW3U6w"),
						new CancelAllocationCommand(new ShardId("library", 2), "G3czOt4HQbKZT1RhpPCULw", true))
				.execute().actionGet();

	}

}

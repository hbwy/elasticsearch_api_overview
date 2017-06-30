package com.elasticsearch.api.admin.cluster;

import org.elasticsearch.action.admin.cluster.shards.ClusterSearchShardsGroup;
import org.elasticsearch.action.admin.cluster.shards.ClusterSearchShardsResponse;
import org.elasticsearch.client.Client;
import org.junit.Test;

import com.elasticsearch.api.AbstractApi;

public class SearchShardsTest extends AbstractApi {

	//查询分片api
	@Test
	public void test() {
		recreateIndex("library");
		Client client = getClient();
		
		ClusterSearchShardsResponse response = client.admin().cluster()
			.prepareSearchShards()
				.setIndices("library")
				.setRouting("12")
			.execute().actionGet();
		
		for (ClusterSearchShardsGroup shardGroup : response.getGroups()) {
			System.out.println(shardGroup.getIndex() + "/" + shardGroup.getShardId());
		}

	}

}

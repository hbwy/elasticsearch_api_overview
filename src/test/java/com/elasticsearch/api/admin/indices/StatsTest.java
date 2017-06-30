package com.elasticsearch.api.admin.indices;

import org.elasticsearch.action.admin.indices.stats.IndexStats;
import org.elasticsearch.action.admin.indices.stats.IndicesStatsResponse;
import org.elasticsearch.client.Client;
import org.junit.Test;

import com.elasticsearch.api.AbstractApi;

public class StatsTest extends AbstractApi {

	//索引统计api
	@Test
	public void test() {
		recreateIndex("library");
		Client client = getClient();
		IndicesStatsResponse response = client.admin().indices()
				.prepareStats("library")
				.all()  	//这里会获取指定索引的所有信息
				.execute().actionGet();

		IndexStats stats = response.getIndex("library");

		System.out.println("Docs: " + stats.getTotal().docs.getCount());
	}

}

package com.elasticsearch.api.admin.indices;

import java.io.IOException;

import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.action.admin.indices.cache.clear.ClearIndicesCacheResponse;
import org.elasticsearch.client.Client;
import org.junit.Test;

import com.elasticsearch.api.AbstractApi;

public class ClearCacheTest extends AbstractApi {

	/**
	 * 清空缓存api
	 * 清空一个或多个索引的特定类型缓存
	 * 本例中清空了library索引的ID缓存，过滤器缓存，title字段缓存 
	 */
	@Test
	public void test() throws ElasticsearchException, IOException {
		recreateIndex("library");
		Client client = getClient();

		ClearIndicesCacheResponse response = client.admin().indices()
			.prepareClearCache("library")
				.setFieldDataCache(true)
				.setFields("title")
				.setFilterCache(true)
				.setIdCache(true)
			.execute().actionGet();
	
		System.out.println("Successful Shards: " + response.getSuccessfulShards());
 
	}

}

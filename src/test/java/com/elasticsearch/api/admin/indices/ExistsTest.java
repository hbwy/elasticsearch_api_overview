package com.elasticsearch.api.admin.indices;

import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsResponse;
import org.elasticsearch.client.Client;
import org.junit.Test;

import com.elasticsearch.api.AbstractApi;

public class ExistsTest extends AbstractApi {
	//索引存在api
	//检查集群中是否存在指定的索引
	@Test
	public void test() {
		Client client = getClient();
		IndicesExistsResponse response = client.admin().indices()
				.prepareExists("books", "library")
				.execute().actionGet();

		System.out.println("Indices exist: " + response.isExists());
	}

}

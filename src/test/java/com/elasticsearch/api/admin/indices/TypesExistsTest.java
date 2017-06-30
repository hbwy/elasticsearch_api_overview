package com.elasticsearch.api.admin.indices;

import org.elasticsearch.action.admin.indices.exists.types.TypesExistsResponse;
import org.elasticsearch.client.Client;
import org.junit.Test;

import com.elasticsearch.api.AbstractApi;

public class TypesExistsTest extends AbstractApi {

	//类型存在api
	@Test
	public void test() {
		recreateIndex("library");
		
		Client client = getClient();
		TypesExistsResponse response = client.admin().indices()
			.prepareTypesExists("library")
			.setTypes("book")
			.execute().actionGet();
		
		System.out.println("Types exist: " + response.isExists());
	}

}

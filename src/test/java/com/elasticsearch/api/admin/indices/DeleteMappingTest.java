package com.elasticsearch.api.admin.indices;

import java.io.IOException;

import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.action.admin.indices.mapping.delete.DeleteMappingResponse;
import org.elasticsearch.client.Client;
import org.junit.Test;

import com.elasticsearch.api.AbstractApi;

public class DeleteMappingTest extends AbstractApi {

	//删除映射api
	@Test
	public void test() throws ElasticsearchException, IOException {
		recreateIndex("news");
		
		Client client = getClient();
		DeleteMappingResponse response = client.admin().indices()
			.prepareDeleteMapping("news")
				.setType("news")
			.execute().actionGet();
	
		System.out.println("ACKed"); //success: no exceptions

	}

}

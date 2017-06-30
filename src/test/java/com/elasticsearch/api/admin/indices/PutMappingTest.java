package com.elasticsearch.api.admin.indices;

import java.io.IOException;

import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.action.admin.indices.mapping.put.PutMappingResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.junit.Test;

import com.elasticsearch.api.AbstractApi;

public class PutMappingTest extends AbstractApi {

	/**
	 * 设置映射api
	 * 允许一次性创建或修改多个索引的映射
	 * 待操作的索引必须已经存在 
	 */
	@Test
	public void test() throws ElasticsearchException, IOException {
		recreateIndex("news");
		
		Client client = getClient();
		PutMappingResponse response = client.admin().indices()
			.preparePutMapping("news")
				.setType("news")
				.setSource(XContentFactory.jsonBuilder()
					.startObject()
						.startObject("news")
							.startObject("properties")
								.startObject("title")
									.field("analyzer", "whitespace")
									.field("type", "string")
								.endObject()
							.endObject()
						.endObject()
					.endObject())
			.execute().actionGet();
	
		System.out.println("ACK: " + response.isAcknowledged());

	}

}

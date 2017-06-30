package com.elasticsearch.api.admin.indices;

import java.io.IOException;

import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.settings.ImmutableSettings;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.junit.Test;

import com.elasticsearch.api.AbstractApi;

public class CreateTest extends AbstractApi {

	//索引创建api
	@Test
	public void test() throws ElasticsearchException, IOException {
		Client client = getClient();
		CreateIndexResponse response = client.admin().indices()
			.prepareCreate("news")
			.setSettings(ImmutableSettings.settingsBuilder().put("number_of_shards", 1))  //指定分片数
			.addMapping("news", XContentFactory.jsonBuilder()
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
		
		System.out.println("Ack: " + response.isAcknowledged());

	}

}

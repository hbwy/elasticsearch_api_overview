package com.elasticsearch.api.admin.indices;

import java.io.IOException;

import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.action.admin.indices.template.put.PutIndexTemplateResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.settings.ImmutableSettings;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.junit.Test;

import com.elasticsearch.api.AbstractApi;

public class PutTemplateTest extends AbstractApi {

	/**
	 * 设置模板api
	 * 用于配置Es在创建新索引时使用的模板，模板可以持有映射和设置
	 * 本例将创建一个名为my_template的新模板，改模板可以被任何名字以product开头的索引使用
	 * 且每个使用该模板创建的索引都拥有两个副本，一个分片以及一个名为item且拥有一个title字段的类型
	 */
	@Test
	public void test() throws ElasticsearchException, IOException {
		Client client = getClient();

		PutIndexTemplateResponse response = client.admin().indices()
			.preparePutTemplate("my_template")
				.setTemplate("product*")
				.setSettings(ImmutableSettings.builder()
						.put("index.number_of_replicas", 2)
						.put("index.number_of_shards", 1))
				.addMapping("item", XContentFactory.jsonBuilder()
					.startObject()
						.startObject("item")
							.startObject("properties")
								.startObject("title")
									.field("type", "string")
								.endObject()
							.endObject()
						.endObject()
					.endObject())
			.execute().actionGet();
	
		System.out.println("Ack: " + response.isAcknowledged());

	}

}

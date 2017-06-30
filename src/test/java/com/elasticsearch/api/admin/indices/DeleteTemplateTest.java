package com.elasticsearch.api.admin.indices;

import java.io.IOException;

import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.action.admin.indices.template.delete.DeleteIndexTemplateResponse;
import org.elasticsearch.client.Client;
import org.junit.Test;

import com.elasticsearch.api.AbstractApi;

public class DeleteTemplateTest extends AbstractApi {

	/**
	 * 删除模板api
	 * 使用删除模板api可以删除一个或多个匹配指定模式的模板 
	 */
	@Test
	public void test() throws ElasticsearchException, IOException {
		Client client = getClient();

		DeleteIndexTemplateResponse response = client.admin().indices()
			.prepareDeleteTemplate("my_*")
			.execute().actionGet();
	
		System.out.println("Ack: " + response.isAcknowledged());

	}

}

package com.elasticsearch.api.crud;

import java.io.IOException;
import java.util.Map;

import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.collect.Maps;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.junit.Test;

import com.elasticsearch.api.AbstractApi;

public class UpdateTest extends AbstractApi {

	@Test
	public void test() throws ElasticsearchException, IOException {
		recreateIndex("library");
		createExampleDocument();

		Client client = getClient();

		Map<String, Object> params = Maps.newHashMap();  //Maps是Es自己封装类
		params.put("ntitle", "ElasticSearch Server Book 2");

		UpdateResponse response = client.prepareUpdate("library", "book", "2")
				                        .setFields("title", "_source")  //setFields 指定哪些文档字段可以包含在请求的响应中，此处也返回了_source
				                        .setScript("ctx._source.title = ntitle")
				                        .setScriptParams(params)
				                        .execute().actionGet();

		System.out.println("Document: " + response.getIndex() + "/" + response.getType() + "/" + response.getId());
		System.out.println(response.getGetResult().getIndex() + "/" + response.getGetResult().getType() + "/" + response.getGetResult().getId() + "/" + response.getGetResult().sourceAsString() + "/");
	}

	private void createExampleDocument() throws ElasticsearchException, IOException {
		getClient().prepareIndex("library", "book", "2")
					.setSource(XContentFactory.jsonBuilder()
					.startObject().field("title", "ElasticSearchServer Book").endObject())
					.execute().actionGet();
	}
}

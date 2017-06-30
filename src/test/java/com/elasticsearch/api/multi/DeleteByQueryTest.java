package com.elasticsearch.api.multi;

import java.util.Map.Entry;

import org.elasticsearch.action.deletebyquery.DeleteByQueryResponse;
import org.elasticsearch.action.deletebyquery.IndexDeleteByQueryResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.index.query.QueryBuilders;
import org.junit.Test;

import com.elasticsearch.api.AbstractApi;

public class DeleteByQueryTest extends AbstractApi {
    //根据 查询删除文档，通过查询所匹配的所有文档都会被删除
	@Test
	public void test() {
		Client client = getClient();

		DeleteByQueryResponse response = client.prepareDeleteByQuery("library")
				.setQuery(QueryBuilders.termQuery("title", "ElasticSearch"))
				.execute().actionGet();

		for (Entry<String, IndexDeleteByQueryResponse> resp : response.getIndices().entrySet()) {
			System.out.println(resp.getValue().getIndex());
		}
	}

}

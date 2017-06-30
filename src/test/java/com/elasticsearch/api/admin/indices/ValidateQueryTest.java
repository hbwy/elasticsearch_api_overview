package com.elasticsearch.api.admin.indices;

import java.io.IOException;

import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.action.admin.indices.validate.query.QueryExplanation;
import org.elasticsearch.action.admin.indices.validate.query.ValidateQueryResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.index.query.QueryBuilders;
import org.junit.Test;

import com.elasticsearch.api.AbstractApi;

public class ValidateQueryTest extends AbstractApi {

	/**
	 * 查询验证api
	 * 用来检查发送给Es的查询是否是合法有效的，可以返回查询中发生错误的确切位置 
	 */
	@Test
	public void test() throws ElasticsearchException, IOException {
		recreateIndex("library");

		Client client = getClient();

		ValidateQueryResponse response = client.admin().indices()
				.prepareValidateQuery("library")
				.setExplain(true)
				.setQuery(QueryBuilders.termQuery("name", "elastic search"))
				.execute().actionGet();

		System.out.println("Query Valid: " + response.isValid());

		for (QueryExplanation explanation : response.getQueryExplanation()) {
			System.out.println(explanation.getError());

		}

	}

}

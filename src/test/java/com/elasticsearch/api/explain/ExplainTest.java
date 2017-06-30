package com.elasticsearch.api.explain;

import java.io.IOException;

import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.action.explain.ExplainResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.index.query.QueryBuilders;
import org.junit.Test;

import com.elasticsearch.api.AbstractApi;

public class ExplainTest extends AbstractApi {

	//列出查询的分析过程
	@Test
	public void test() throws ElasticsearchException, IOException {
		//recreateIndex("library");
		//createSomeBooks("library");

		Client client = getClient();

		ExplainResponse response = client.prepareExplain("library", "book", "1")
				.setQuery(QueryBuilders.matchQuery("title", "Book"))
				.execute().actionGet();
		showHits(response);

	}

	private void showHits(final ExplainResponse response) {
		if (response.isExists()) {
			System.out.println(response.getExplanation().getDescription());
		}
	}

}

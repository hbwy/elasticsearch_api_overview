package com.elasticsearch.api.admin.indices;

import java.io.IOException;

import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.action.admin.indices.analyze.AnalyzeResponse;
import org.elasticsearch.action.admin.indices.analyze.AnalyzeResponse.AnalyzeToken;
import org.elasticsearch.client.Client;
import org.junit.Test;

import com.elasticsearch.api.AbstractApi;

public class AnalyzeTest extends AbstractApi {

	/**
	 * 分析api
	 * 在查看分析器、分词器、过滤器的分析处理过程时非常有用
	 * 本例用来检查library索引中使用whitespace分词器和nGram过滤器处理ElasticSearch Servers短语的分析处理过程
	 */
	@Test
	public void test() throws ElasticsearchException, IOException {
		recreateIndex("library");
		createSomeBooks("library");
		
		Client client = getClient();

		AnalyzeResponse response = client.admin().indices()
			.prepareAnalyze("library", "ElasticSearch Servers")
				//You can sen analyzer OR tokenizer/token filters: .setAnalyzer("standard")
				.setTokenizer("whitespace")
				.setTokenFilters("nGram")
			.execute().actionGet();
	
		System.out.println("Terms:");
		for (AnalyzeToken analyzeToken : response.getTokens()) {
			System.out.println("\t" + analyzeToken.getTerm());
		}

	}

}

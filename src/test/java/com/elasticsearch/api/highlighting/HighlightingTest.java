package com.elasticsearch.api.highlighting;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.highlight.HighlightField;
import org.junit.Test;

import com.elasticsearch.api.AbstractApi;

public class HighlightingTest extends AbstractApi {

	//高亮显示
	@Test
	public void test() {
		recreateIndex("wikipedia");
		Client client = getClient();

				
		SearchResponse response = client.prepareSearch("wikipedia")
				.addHighlightedField("title")
				.setQuery(QueryBuilders.termQuery("title", "actress"))
				.setHighlighterPreTags("<1>", "<2>")
				.setHighlighterPostTags("</1>", "</2>")
				//.setHighlighterTagsSchema("styled")
				.execute().actionGet();
		showHits(response);

	}
	
	private void showHits(final SearchResponse response) {
		for(SearchHit hit: response.getHits().getHits()) {
			System.out.println(hit.getId() + " title:" + hit.getSource().get("title"));
			HighlightField hField = hit.getHighlightFields().get("title");
			for (Text t : hField.fragments()) {
				System.out.println(t.string());
			}
			System.out.println("-");
		}	
	}

}

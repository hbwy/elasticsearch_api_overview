package com.elasticsearch.api.suggest;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.suggest.Suggest.Suggestion.Entry;
import org.elasticsearch.search.suggest.Suggest.Suggestion.Entry.Option;
import org.elasticsearch.search.suggest.term.TermSuggestionBuilder;
import org.junit.Test;

import com.elasticsearch.api.AbstractApi;

public class SuggesterTest extends AbstractApi {

	/**
	 * 可以这样理解建议器：在考虑性能的情况下，允许纠正用户的拼写错误，以及构建一个自动完成功能
	 *  
	 */
	@Test
	public void test() {
		recreateIndex("wikipedia");
		Client client = getClient();

//		{
//			 "query" : {
//			  "match_all" : {}
//			 },
//			 "suggest" : {
//			  "first_suggestion" : {
//			   "text" : "graphics desiganer",
//			   "term" : {
//			    "field" : "_all"
//			   }
//			  }
//			 }
//			}
				
		SearchResponse response = client.prepareSearch("wikipedia")
			.setQuery(QueryBuilders.matchAllQuery())
			.addSuggestion(new TermSuggestionBuilder("first_suggestion")
				.text("graphics desiganer")  //希望得到建议的文本
				.field("_all")  //想得到_all字段上graphics desiganer文本的建议
			).execute().actionGet();


		for( Entry<? extends Option> entry : response.getSuggest().getSuggestion("first_suggestion").getEntries()) {
			System.out.println("Check for: "+ entry.getText() + ". Options:");
			for(  Option option : entry.getOptions()) {
				System.out.println("\t" + option.getText());
			}
		}
	}

}

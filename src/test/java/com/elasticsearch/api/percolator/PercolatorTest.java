package com.elasticsearch.api.percolator;

import static org.fest.assertions.Assertions.assertThat;

import java.io.IOException;

import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.action.percolate.PercolateResponse;
//For ElasticSearch 1.0
import org.elasticsearch.action.percolate.PercolateResponse.Match;
import org.elasticsearch.action.percolate.PercolateSourceBuilder;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.index.query.QueryBuilders;
import org.junit.Test;

import com.elasticsearch.api.AbstractApi;

public class PercolatorTest extends AbstractApi {

	//预匹配器
	@Test
	public void testPercolator1_0() throws ElasticsearchException, IOException {
		logger("Test for percolator v1.0");
		recreateIndex("prcltr");
		Client client = getClient();

		
		logger("Indexing query");
		client.prepareIndex("prcltr", "_percolator", "query:1")
			.setSource(XContentFactory.jsonBuilder()
					.startObject()
						.field("query", QueryBuilders.termQuery("test", "abc"))
					.endObject()
			)
			.setRefresh(true) //For testing - we want to make sure that document is in index for the next query
			.execute().actionGet();
		
		logger("Percolate");
		PercolateResponse response = client.preparePercolate()
				.setIndices("prcltr")
				.setDocumentType("type")
				.setPercolateDoc(PercolateSourceBuilder
						.docBuilder()
							.setDoc(XContentFactory.jsonBuilder()
									.startObject()
										.field("test").value("abc")
									.endObject()))
						.execute().actionGet();
		
		for (Match queryName : response.getMatches()) {
			logger("Match: " + queryName.getId());
		}
		
		assertThat(response.getMatches()).hasSize(1);
		
	}
	
//	@Test
//	public void testPercolator0_90_3() throws ElasticSearchException, IOException {
//		logger("Test for percolator v0.90.3");
//		recreateIndex("prcltr");
//		Client client = getClient();
//		
//		logger("Indexing query");
//		client.prepareIndex("_percolator", "prcltr", "query:1")
//			.setSource(XContentFactory.jsonBuilder()
//					.startObject()
//						.field("query", QueryBuilders.termQuery("test", "abc"))
//					.endObject()
//			)
//			.setRefresh(true) //For testing - we want to make sure that document is in index for the next query
//			.execute().actionGet();
//		
//		logger("Percolate");
//		PercolateResponse response = client.preparePercolate("prcltr", "type")
//				.setSource(XContentFactory.jsonBuilder()
//							.startObject()
//								.startObject("doc")
//									.field("test").value("abc")
//								.endObject()
//							.endObject())
//						.execute().actionGet();
//		
//		for (String queryName : response.getMatches()) {
//			logger("Match: " + queryName);
//		}
//		
//		assertThat(response.getMatches()).hasSize(1);
//		
//	}

}

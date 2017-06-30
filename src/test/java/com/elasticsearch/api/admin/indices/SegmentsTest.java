package com.elasticsearch.api.admin.indices;

import java.io.IOException;
import java.util.Map.Entry;

import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.action.admin.indices.segments.IndexSegments;
import org.elasticsearch.action.admin.indices.segments.IndexShardSegments;
import org.elasticsearch.action.admin.indices.segments.IndicesSegmentResponse;
import org.elasticsearch.action.admin.indices.segments.ShardSegments;
import org.elasticsearch.client.Client;
import org.junit.Test;

import com.elasticsearch.api.AbstractApi;

public class SegmentsTest extends AbstractApi {

	//索引段信息api
	@Test
	public void test() throws ElasticsearchException, IOException {
		recreateIndex("library");
		createSomeBooks("library");
		
		Client client = getClient();
		
		IndicesSegmentResponse response = client.admin().indices()
			.prepareSegments("library")
				.execute().actionGet();
		
		IndexSegments segments = response.getIndices().get("library");
		
		for (Entry<Integer, IndexShardSegments> entry : segments.getShards().entrySet()) {
			System.out.println("Index: " + entry.getKey());
			for (ShardSegments e : entry.getValue().getShards()) {
				System.out.println("ShardId: "  + e.getShardId() 
						+ " Primary: " + e.getShardRouting().primary() 
						+ " Searches: " + e.getNumberOfSearch());
			}
		}
	}

}

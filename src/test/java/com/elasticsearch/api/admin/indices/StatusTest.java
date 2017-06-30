package com.elasticsearch.api.admin.indices;

import org.elasticsearch.action.admin.indices.status.IndexStatus;
import org.elasticsearch.action.admin.indices.status.IndicesStatusResponse;
import org.elasticsearch.client.Client;
import org.junit.Test;

import com.elasticsearch.api.AbstractApi;

public class StatusTest extends AbstractApi {

	//索引状态api
	@Test
	public void test() {
		recreateIndex("library");
		
		Client client = getClient();
		IndicesStatusResponse response = client.admin().indices()
			.prepareStatus("library")
				.setRecovery(true)
				.setSnapshot(true)
				.execute().actionGet();
		
		IndexStatus status = response.getIndex("library");
		
		System.out.println("Merge time: " + status.getMergeStats().getTotalTimeInMillis());
	}

}

package com.elasticsearch.api.admin.indices;

import java.io.IOException;

import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.action.admin.indices.settings.put.UpdateSettingsResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.settings.ImmutableSettings;
import org.junit.Test;

import com.elasticsearch.api.AbstractApi;

public class UpdateSettingsTest extends AbstractApi {

	@Test
	public void test() throws ElasticsearchException, IOException {
		recreateIndex("library");
		
		Client client = getClient();

		UpdateSettingsResponse response = client.admin().indices()
			.prepareUpdateSettings("library")
				.setSettings(ImmutableSettings.builder()
						.put("index.number_of_replicas", 2))
			.execute().actionGet();
	
		System.out.println("ACK"); //No exception - success!
 
	}

}

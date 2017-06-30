package com.elasticsearch.api.admin.indices;

import java.io.IOException;

import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.action.admin.indices.warmer.delete.DeleteWarmerResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.search.warmer.IndexWarmerMissingException;
import org.junit.Test;

import com.elasticsearch.api.AbstractApi;

public class DeleteWarmerTest extends AbstractApi {

	//删除预热器api
	@Test
	public void test() throws ElasticsearchException, IOException {
		Client client = getClient();
		
		try {
			DeleteWarmerResponse response = client.admin().indices()
				.prepareDeleteWarmer()
				.setNames("library_*")  //删除所有带library_前缀的预热器
				.execute().actionGet();
	
			System.out.println("Ack: " + response.isAcknowledged());
		} catch (IndexWarmerMissingException e) {
			System.out.println("No such warmer: " + e.getDetailedMessage());
		}

	}


}

package com.elasticsearch.api.crud;

import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.client.Client;
import org.junit.Test;

import com.elasticsearch.api.AbstractApi;

public class DeleteTest extends AbstractApi {

	@Test
	public void test() {
		Client client = getClient();

		DeleteResponse response = client.prepareDelete("library", "book", "2").execute().actionGet();

		System.out.println("Document: " + response.getIndex() + "/" + response.getType() + "/" + response.getId());
	}
}

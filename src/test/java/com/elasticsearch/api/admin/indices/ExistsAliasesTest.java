package com.elasticsearch.api.admin.indices;

import java.io.IOException;

import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.action.admin.indices.alias.exists.AliasesExistResponse;
import org.elasticsearch.client.Client;
import org.junit.Test;

import com.elasticsearch.api.AbstractApi;

public class ExistsAliasesTest extends AbstractApi {

	//别名存在api
	@Test
	public void test() throws ElasticsearchException, IOException {
		Client client = getClient();

		 AliasesExistResponse response = client.admin().indices()
			.prepareAliasesExist("elastic*", "unknown")
			.execute().actionGet();
	
		System.out.println("Exists: " + response.isExists());
 
	}

}

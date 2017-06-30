package com.elasticsearch.api.admin.indices;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.action.admin.indices.alias.get.GetAliasesResponse;

//For ElasticSearch 0.90.3
//import org.elasticsearch.action.admin.indices.alias.get.IndicesGetAliasesResponse;

import org.elasticsearch.client.Client;
import org.elasticsearch.cluster.metadata.AliasMetaData;
import org.elasticsearch.common.hppc.cursors.ObjectObjectCursor;
import org.junit.Test;

import com.elasticsearch.api.AbstractApi;

public class GetAliasesTest extends AbstractApi {

	//获取别名api
	@Test
	public void test() throws ElasticsearchException, IOException {
		Client client = getClient();

		// For ElasticSearch 0.90.3
		// IndicesGetAliasesResponse response =

		// For ElasticSearch 1.0
		GetAliasesResponse response = client.admin().indices()
				.prepareGetAliases("elastic_books", "n")
				.execute().actionGet();

		Iterator<ObjectObjectCursor<String, List<AliasMetaData>>> it = response.getAliases().iterator();
		while (it.hasNext()) {
			ObjectObjectCursor<String, List<AliasMetaData>> item = it.next();
			System.out.println("Index: " + item.key);
			for (AliasMetaData meta : item.value) {
				System.out.println("\tName: " + meta.alias() + " Filter: " + meta.getFilter());
			}
		}
	}

}

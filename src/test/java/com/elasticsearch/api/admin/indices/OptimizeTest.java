package com.elasticsearch.api.admin.indices;

import java.io.IOException;

import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.action.admin.indices.optimize.OptimizeResponse;
import org.elasticsearch.client.Client;
import org.junit.Test;

import com.elasticsearch.api.AbstractApi;

public class OptimizeTest extends AbstractApi {

	/**
	 * 索引优化pai
	 * 该方法会唤起指定索引上的段合并过程，可以设定最终合并后的索引段数量，或者仅仅从索引中删除已被标记删除的文档，
	 * 在执行完索引优化操作后还需执行清空缓冲区操作
	 */
	@Test
	public void test() throws ElasticsearchException, IOException {
		recreateIndex("library");
		
		Client client = getClient();
		OptimizeResponse response = client.admin().indices()
			.prepareOptimize("library")
				.setMaxNumSegments(2)   //设置最大索引段数量为2
				.setOnlyExpungeDeletes(false)  //是否只删除已标记为删除的文档
				.setFlush(true)
			.execute().actionGet();
	
		System.out.println("Total Shards: " + response.getTotalShards());

	}

}

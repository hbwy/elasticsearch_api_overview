package com.elasticsearch.api.multi;

import org.elasticsearch.action.get.MultiGetItemResponse;
import org.elasticsearch.action.get.MultiGetResponse;
import org.elasticsearch.client.Client;
import org.junit.Test;

import com.elasticsearch.api.AbstractApi;

public class MultiGetTest extends AbstractApi {

	//Multi GET请求可以把几个GET请求聚合在一次调用 
	@Test
	public void test() {
		Client client = getClient();
		
		MultiGetResponse response = client.prepareMultiGet()
				.add("library", "book", "1", "2")   //取出文档id为1,2的两个文档
				.execute().actionGet();
		
		for(MultiGetItemResponse resp: response.getResponses()) {  //返回的是MultiGetItemResponse[]
			System.out.println(resp.getId());
		}
	}

}

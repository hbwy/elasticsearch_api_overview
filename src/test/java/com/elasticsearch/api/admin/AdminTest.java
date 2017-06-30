package com.elasticsearch.api.admin;

import org.elasticsearch.client.Client;
import org.elasticsearch.client.ClusterAdminClient;
import org.junit.Test;

import com.elasticsearch.api.AbstractApi;

public class AdminTest extends AbstractApi {

	//获取集群管理接口
	@Test
	public void test() {
		Client client = getClient();
		
		ClusterAdminClient cluster = client.admin().cluster();
	}
}

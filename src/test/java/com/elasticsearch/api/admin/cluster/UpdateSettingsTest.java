package com.elasticsearch.api.admin.cluster;

import java.util.Map;

import org.elasticsearch.action.admin.cluster.settings.ClusterUpdateSettingsResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.collect.Maps;
import org.junit.Test;

import com.elasticsearch.api.AbstractApi;

public class UpdateSettingsTest extends AbstractApi {

	//设置更新api 
	@Test
	public void test() {
		
		Map<String, Object> map = Maps.newHashMap();
		map.put("indices.ttl.interval", "10m");
		
		Client client = getClient();
		ClusterUpdateSettingsResponse response = client.admin().cluster()
			.prepareUpdateSettings()
				.setTransientSettings(map)
			.execute().actionGet();
		
		System.out.println(response.getTransientSettings().toDelimitedString(','));

	}

}

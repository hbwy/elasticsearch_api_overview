package com.elasticsearch.api.connect;

import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.ImmutableSettings;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.junit.Test;

import com.elasticsearch.api.AbstractApi;

public class TransportClientTest extends AbstractApi {

	@Test
	public void connect() {
		//For testing purposes we will create a test node so we can connect to
		createLocalCluster("escluster2");
		
		//Create the configuration - you can omit this step and use 
		//non-argument constructor of TransportClient
		Settings settings = ImmutableSettings.settingsBuilder()
			.put("cluster.name", "escluster2").build();
		
		//Create the transport client instance
        TransportClient client = new TransportClient(settings);
        
        //add some addreses of ElasticSearch cluster nodes
        client.addTransportAddress(new InetSocketTransportAddress("127.0.0.1", 9300));

        //Now we can do something with ElasticSearch
        //...

		//At the end we should close resources. In real scenario make sure do it in finally block.
		client.close();
	}

}

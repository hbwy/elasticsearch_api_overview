package com.elasticsearch.api.connect;

import static org.elasticsearch.node.NodeBuilder.nodeBuilder;

import org.elasticsearch.client.Client;
import org.elasticsearch.node.Node;
import org.junit.Test;

import com.elasticsearch.api.AbstractApi;

public class ClientTest extends AbstractApi {

	@Test
	public void connect() {
		
		//For testing purposes we will create a test node so we can connect to
		createLocalCluster("escluster2");
		
		//We want to connect to cluster named "escluster2". Remember to set this name
		//in the ElasticSearch instance, change name set below or omit clusterName() method call.
		
		//Setting up the elasticSearch node...
		Node node = nodeBuilder()
				.clusterName("escluster2") 
				.client(true).node();
		
		//... end create the client
		Client client = node.client();
		
		//Now we can something with elasticSearch
		//...
		
		//after using we should close resource. 
		//In real life make sure do this in finally block.
		client.close();
		node.close();
	}

}

package com.elasticsearch.api;

import java.io.IOException;
import java.util.logging.Logger;

import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsResponse;
import org.elasticsearch.action.index.IndexRequestBuilder;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.ImmutableSettings;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.node.Node;
import org.elasticsearch.node.NodeBuilder;
import org.junit.After;

public class AbstractApi {
	protected static final Logger LOG = Logger.getLogger("TEST");

	private Client client;
	private Node node;
	/*
	 * 简单介绍两种连接方式的区别：
	 * 1.第一种建立客户端节点，让启动复杂化了，需要客户端必须先加入进群并建立与其他节点的连接，而这需要时间和资源，然而，操作可以更快执行，因为所有关于集群，索引，分片的信息都对
	 * 客户端节点可见
	 * 2.第二种使用传输机客户端，启动速度快，需要较少的资源，而发送查询和数据需要消耗更多的资源，因为该对象对集群和索引等信息一无所知，所以它无法把数据直接发送到正确的节点，
	 * 而是先把数据发给某个初始传输节点，然后再有Es完成剩下的转发工作。
	 */
	
	//获取Es client
	public Client getClient0() {
		if (this.client != null) {
			return this.client;
		}

		NodeBuilder builder = NodeBuilder.nodeBuilder();
        //不指定集群名则默认为‘elasticsearch’
		//gateway代表es索引的持久化存储方式
		Settings settings = ImmutableSettings.settingsBuilder().put("gateway.type", "none").build();

		 builder.settings(settings)
				.client(true)   //是否只作为客户端，即不存储索引数据
				.local(true)    //是否为本地节点，本地节点是指jvm级别中的同级，当多个节点使用同一个jvm时，这些节点可以组合成集群，而非同一个jvm下的节点不处于同一集群
				.data(false);   //是否持有索引数据，当client为true时，data默认是false

		this.node = builder.clusterName("elasticsearch-dealmoon").node();  //注意创建顺序，先node再client
		this.client = node.client();
		return this.client;
	}

	//获取指定集群下client
	public void createLocalCluster(final String clusterName) {
		NodeBuilder builder = NodeBuilder.nodeBuilder();
		Settings settings = ImmutableSettings.settingsBuilder().put("gateway.type", "none").put("cluster.name", "escluster2").build();
		builder.settings(settings).local(true).data(false);

		this.node = builder.node();
		this.client = node.client();
	}

	public Client getClient(){
		Settings settings = ImmutableSettings.settingsBuilder().put("cluster.name", "elasticsearch-dealmoon").build();
		TransportClient client = new TransportClient(settings);
		client.addTransportAddress(new InetSocketTransportAddress("192.168.1.230", 9300));  //9200是用来让http restful api访问的，9300是传输层监听的默认端口
		return client;
	}
	
	//创建一个client，如果不存在则创建
	public void createIndexIfNeeded(final String index) {
		if (!existsIndex(index)) {
			getClient().admin().indices().prepareCreate(index).execute().actionGet();
		}
	}

	public void recreateIndex(final String index) {
		logger("Recreting index: " + index);
		if (existsIndex(index)) {
			getClient().admin().indices().prepareDelete(index).execute().actionGet();
		}
		getClient().admin().indices().prepareCreate(index).execute().actionGet();
	}

	public boolean existsIndex(final String index) {
		IndicesExistsResponse response = getClient().admin().indices().prepareExists(index).execute().actionGet();
		return response.isExists();
	}

	protected void logger(final String msg) {
		LOG.info("* " + msg);
	}

	@After
	public void close() {
		if (client != null) {
			client.close();
		}

		if (node != null) {
			node.close();
		}
	}

	protected void createSomeBooks(final String index) throws ElasticsearchException, IOException {
		getClient().prepareBulk()  //XContentFactory.jsonBuilder()是Es自带的拼接json的工具类
				.add(new IndexRequestBuilder(getClient(), "library").setId("1").setType("book")
						.setSource(XContentFactory.jsonBuilder().startObject().field("title", "Book A Title").field("num", 1).endObject()).request())
				.add(new IndexRequestBuilder(getClient(), "library").setType("book")
						.setSource(XContentFactory.jsonBuilder().startObject().field("title", "Book B Title").field("num", 10).endObject()).request())
				.execute().actionGet();

	}
	
}

package com.elasticsearch.api.crud;

import java.io.IOException;

import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.ListenableActionFuture;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.index.get.GetField;
import org.junit.Test;

import com.elasticsearch.api.AbstractApi;

public class GetTest extends AbstractApi {

	/**
	 * Es的api是天生异步的，也就是说execute()调用不会等待Es的响应结果，而是直接把控制权交回给调用它的代码段，而查询请求的后台执行。
	 * 使用actionGet()方法会等待查询执行完毕并返回数据。
	 */
	@Test
	public void test() throws ElasticsearchException, IOException {
		recreateIndex("library");
		createSomeBooks("library");

		Client client = getClient();

		GetResponse response = client.prepareGet("library", "book", "1")
				                     .setFields("title", "_source")
				                     .execute().actionGet();

		if (response.isExists()) {
			GetField field = response.getField("title");

			System.out.println("Document: " + response.getIndex() + "/" + response.getType() + "/" + response.getId());
			System.out.println("Title: " + field.getValue());
			System.out.println("Source: " + response.getSourceAsString());
		} else {
			System.out.println("Document not found.");
		}
	}
	
	/**
	 * 利用回调，不等待ES返回结果
	 */
	@Test
	public void test2() throws ElasticsearchException, IOException{
		//recreateIndex("library");
		//createSomeBooks("library");
		Client client = getClient();
		ListenableActionFuture<GetResponse> future =  client.prepareGet("library", "book", "1")
											                .setFields("title", "_source")
											                .execute();
		future.addListener(new ActionListener<GetResponse>() {
			
			@Override
			public void onResponse(GetResponse response) {
				System.out.println("Document: " + response.getIndex() + "/" + response.getType() + "/" + response.getId());
			}
			
			@Override
			public void onFailure(Throwable arg0) {
				System.out.println("failed.");
				
			}
		});
		
		try {
			Thread.currentThread().sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	} 
}

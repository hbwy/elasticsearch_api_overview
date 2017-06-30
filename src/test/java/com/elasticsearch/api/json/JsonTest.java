package com.elasticsearch.api.json;

import java.io.IOException;
import java.util.Date;
import java.util.Map;

import org.elasticsearch.common.collect.Maps;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.junit.Test;

public class JsonTest {

	//Es提供的json构造工具
	@Test
	public void test() throws IOException {
		
		Map<String, Object> m = Maps.newHashMap();
		m.put("1", "Introduction");
		m.put("2", "Basics");
		m.put("3", "And the rest");
		
		
		XContentBuilder json = XContentFactory.jsonBuilder().prettyPrint()
				.startObject()
					.field("id").value("2123")
					.field("lastCommentTime", new Date())
					.nullField("published")
					.field("chapters").map(m)
					.field("title", "Mastering ElasticSearch")
					.array("tags", "search", "ElasticSearch", "nosql")
					.field("values")
						.startArray()
							.value(1)
							.value(10)
						.endArray()
				.endObject();
		System.out.println(json.string());
	}
}

package com.elasticsearch.api.search;

// For ElastciSearch 0.90.3
//import org.elasticsearch.common.geo.ShapeBuilder;


// ElasticSearch 1.0
import org.elasticsearch.common.geo.builders.ShapeBuilder;


import org.elasticsearch.index.query.MatchQueryBuilder.ZeroTermsQuery;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.junit.Test;

public class QueryBuildersTest {

	@Test
	public void buildQueries() {
		
		QueryBuilder queryBuilder = null;
		
		//matchAll
		queryBuilder = QueryBuilders.matchAllQuery()
				.boost(11f)
				.normsField("title");  //指定给哪个字段加权
		
		show(queryBuilder);
		
		//match query
		queryBuilder = QueryBuilders.matchQuery("title", "elastic")
				.boost(0.5f);
		
		show(queryBuilder);
		
		/**
		 * common terms query 常用词查询
		 * 将查询分为两组，第一组包含重要的词，出现的频率较低，第二组包含较高频率的不重要的词，先执行第一个查询，从第一组的所有词中计算分数，
		 * 通常都很重要的低频词总被列入考虑范围，对第二组中的词执行二次查询，但只为与第一个查询中匹配的文档计算得分。
		 */
		queryBuilder = QueryBuilders.commonTerms("title", "ElasticSearch and book")
				.boost(0.5f)  
				.cutoffFrequency(0.001f)   //这个值用来构建高、低频词组。此参数设置为0.001意味着频率<=0.1%的词将出现在低频词组中
				//这个参数可以设置为or或and，默认是or，指定低频词组查询时用到的布尔运算符，在此设置成and表示所有词都在文档中出现才认为是匹配
				.lowFreqOperator(org.elasticsearch.index.query.CommonTermsQueryBuilder.Operator.AND);
		
		show(queryBuilder);
		
		//multi match query 匹配多个字段
		queryBuilder = QueryBuilders.multiMatchQuery("a quick brown fox", "title", "body")
				.useDisMax(true);  //对于给定词条，只有最高分会包括在最后的文档评分中，而不是所有包含该词条的所有字段分数之和
		
		show(queryBuilder);
		
		//match phrase query 短语查询
		queryBuilder = QueryBuilders.matchPhraseQuery("title", "a quick brown fox")
				.maxExpansions(8);
		
		show(queryBuilder);
		
		//match phrase prefix query
		queryBuilder = QueryBuilders.matchPhrasePrefixQuery("title", "a quick brown fox")
				.maxExpansions(8);
		
		show(queryBuilder);
		
		//dismax query
		queryBuilder = QueryBuilders.disMaxQuery()
				.add(QueryBuilders.termQuery("title", "elastic"))
				.add(QueryBuilders.matchPhrasePrefixQuery("description", "elastic search"))
				.tieBreaker(0.1f);
		
		show(queryBuilder);
		
		//ids query 标识符查询，仅用提供的标识符来过滤返回的文档
		queryBuilder = QueryBuilders.idsQuery("book", "article")  //指定多个type
					.ids("12", "102");
		
		show(queryBuilder);
		
	}
	
	@Test
	public void buildMatchQuery() {
//		{
//		    "match" : {
//		        "message" : {
//		            "query" : "a quick brown fox",
//		            "operator" : "and",
//		            "zero_terms_query": "all"
//		        }
//		    }
//		}
		
		show(QueryBuilders.matchQuery("message", "a quick brown fox")
					.operator(org.elasticsearch.index.query.MatchQueryBuilder.Operator.AND)
					.zeroTermsQuery(ZeroTermsQuery.ALL)  //该参数表示当分析器移除所有查询词条（因为停用词）时，则返回所有文档
			);
	}
	
	/**
	 * 使用地理位置查询 
	 */
	@Test
	public void buildGeoQuery() {
//		{
//		    "query": {
//		        "geo_shape": {
//		            "location": {
//		                "shape": {
//		                    "type": "envelope",
//		                    "coordinates": [[13, 53],[14, 52]]
//		                }
//		            }
//		        }
//		    }
//		}
		
		// For ElasticSearch 0.90.3 
//		show(QueryBuilders
//				.geoShapeQuery("location", ShapeBuilder.newRectangle().topLeft(13, 53)
//						.bottomRight(14, 52)
//						.build()
//				)
//		);

		
		// For ElasticSEarch 1.0 通过定义左上和右下两个点组成一个框
		show(QueryBuilders.geoShapeQuery("location", ShapeBuilder.newEnvelope()
				.topLeft(13, 53)  //左上
				.bottomRight(14, 52)));   //右下
	}
	
	private void show(final QueryBuilder builder) {
		System.out.println(builder.getClass().getSimpleName() + ":\n" + builder.toString() + "\n");
	}

}

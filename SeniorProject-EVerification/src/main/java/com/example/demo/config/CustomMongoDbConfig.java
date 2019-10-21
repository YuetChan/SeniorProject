package com.example.demo.config;

import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;

import com.mongodb.MongoClientURI;

public class CustomMongoDbConfig {

	static public MongoTemplate mongoTemplate() {
		
		MongoClientURI uri = new MongoClientURI("mongodb://2factor:password1@ds123399.mlab.com:23399/2factorface");
		MongoDbFactory mongoDbFactory = new SimpleMongoDbFactory(uri);
		return new MongoTemplate(mongoDbFactory);
		
	}
	
}

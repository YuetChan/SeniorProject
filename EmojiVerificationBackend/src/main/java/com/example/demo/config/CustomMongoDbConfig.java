package com.example.demo.config;

import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;

import com.mongodb.MongoClientURI;

public class CustomMongoDbConfig {

	static public MongoTemplate mongoTemplate() {
		
		MongoClientURI uri = new MongoClientURI("mongodb://yuetchany:yuetchany123@ds047772.mlab.com:47772/2factor");
		MongoDbFactory mongoDbFactory = new SimpleMongoDbFactory(uri);
		return new MongoTemplate(mongoDbFactory);
		
	}
	
}

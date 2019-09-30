package com.example.demo.config;

import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;

import com.mongodb.MongoClientURI;

public class CustomMongoDbConfig {

	static public MongoTemplate mongoTemplate() {
		
		MongoClientURI uri = new MongoClientURI("mongodb://yuetchany:Saito2277@ds023303.mlab.com:23303/algorithmic");
		MongoDbFactory mongoDbFactory = new SimpleMongoDbFactory(uri);
		return new MongoTemplate(mongoDbFactory);
		
	}
	
}

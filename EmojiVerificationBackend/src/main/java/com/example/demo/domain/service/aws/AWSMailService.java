package com.example.demo.domain.service.aws;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceClientBuilder;
import com.amazonaws.services.simpleemail.model.Body;
import com.amazonaws.services.simpleemail.model.Content;
import com.amazonaws.services.simpleemail.model.Destination;
import com.amazonaws.services.simpleemail.model.Message;
import com.amazonaws.services.simpleemail.model.SendEmailRequest;
import com.example.demo.domain.iservice.IMailService;

public class AWSMailService implements IMailService {

	private BasicAWSCredentials awsCreds;
	
	private AmazonSimpleEmailService AWSEmailClient;
	
	public AWSMailService() {
		
		awsCreds = new BasicAWSCredentials("<Key>", "<Secret>");
		AWSEmailClient
        = AmazonSimpleEmailServiceClientBuilder
        .standard().withRegion(Regions.US_EAST_1).withCredentials(new AWSStaticCredentialsProvider(awsCreds)).build();
        
	}
	
	public boolean sendMessage(String TO, String FROM, String SUBJECT, String TEXTBODY) {
		
		boolean success = false;
		
	    SendEmailRequest request 
	    = new SendEmailRequest()
	    .withDestination(
	    		new Destination()
	    		.withToAddresses(TO))
	    		.withMessage(
	    				new Message()
	    				.withBody(
	    						new Body()
	    						.withText(new Content().withCharset("UTF-8").withData(TEXTBODY)))
	    						.withSubject(new Content().withCharset("UTF-8").withData(SUBJECT)))
	    						.withSource(FROM);
	        
	   AWSEmailClient.sendEmail(request);
	        
	   success = true;    
	   return success;
		
	}
	
}

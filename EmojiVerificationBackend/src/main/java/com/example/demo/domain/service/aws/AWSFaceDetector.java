package com.example.demo.domain.service.aws;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.List;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.rekognition.AmazonRekognition;
import com.amazonaws.services.rekognition.AmazonRekognitionClientBuilder;
import com.amazonaws.services.rekognition.model.Attribute;
import com.amazonaws.services.rekognition.model.DetectFacesRequest;
import com.amazonaws.services.rekognition.model.DetectFacesResult;
import com.amazonaws.services.rekognition.model.FaceDetail;
import com.amazonaws.services.rekognition.model.Image;
import com.amazonaws.util.IOUtils;
import com.example.demo.domain.iservice.IFaceDetector;

public class AWSFaceDetector implements IFaceDetector {
	
	private BasicAWSCredentials awsCreds;
	
	private AmazonRekognition rekognitionClient;

	public AWSFaceDetector() {
		//the key and secret are in facebook message
		awsCreds = new BasicAWSCredentials("", "");
		rekognitionClient = AmazonRekognitionClientBuilder.standard().withRegion("us-east-1").withCredentials(new AWSStaticCredentialsProvider(awsCreds)).build();
	
	}
	
    public List<FaceDetail> detect(File targetImageFile) {
    	
        ByteBuffer targetImageBytes = null;
        
		try (InputStream inputStream = new FileInputStream(targetImageFile)) {
			
			targetImageBytes = ByteBuffer.wrap(IOUtils.toByteArray(inputStream));
	    
		}catch (Exception e) {
	    	
	    	System.out.println("Failed to load source image " + targetImageFile.getAbsolutePath());
	        System.exit(1);
	        
	    }
		
		Image target = new Image().withBytes(targetImageBytes);
		
		DetectFacesRequest detectFacesRequest = new DetectFacesRequest().withImage(target);
		
		DetectFacesResult detectFacesResult = rekognitionClient.detectFaces(detectFacesRequest);
		List<FaceDetail> faceDetails = detectFacesResult.getFaceDetails();
		return faceDetails;
    	
    }
    
    public List<FaceDetail> detect(byte[] targetImageBytes) {
    	
        ByteBuffer targetImageBytesBuffer = null;
        
        targetImageBytesBuffer = ByteBuffer.wrap(targetImageBytes);
		Image target = new Image().withBytes(targetImageBytesBuffer);
		
		DetectFacesRequest detectFacesRequest = new DetectFacesRequest().withImage(target).withAttributes(Attribute.ALL);
		
		DetectFacesResult detectFacesResult = rekognitionClient.detectFaces(detectFacesRequest);
		List<FaceDetail> faceDetails = detectFacesResult.getFaceDetails();
		return faceDetails;
    	
    }

}

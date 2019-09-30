package com.example.demo.imageComparer;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.List;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.rekognition.AmazonRekognition;
import com.amazonaws.services.rekognition.AmazonRekognitionClientBuilder;
import com.amazonaws.services.rekognition.model.CompareFacesMatch;
import com.amazonaws.services.rekognition.model.CompareFacesRequest;
import com.amazonaws.services.rekognition.model.CompareFacesResult;
import com.amazonaws.services.rekognition.model.Image;
import com.amazonaws.util.IOUtils;

public class AWSImageComparer implements IImageComparer{
	
	private BasicAWSCredentials awsCreds;
	
	private AmazonRekognition rekognitionClient;
	
	private static final Float similarityThreshold = 70F;
	
	public AWSImageComparer() {
		//the key and secret are in facebook message
		awsCreds = new BasicAWSCredentials("<key>", "<secret>");
		rekognitionClient = AmazonRekognitionClientBuilder.standard().withRegion("us-east-1").withCredentials(new AWSStaticCredentialsProvider(awsCreds)).build();
	
	}
	
	//this will compare similarity of two person
	@Override
	public boolean compare(String targetImageReference, String originalImageReference) 
			throws IOException {
		
		boolean matched = false;
		
	    ByteBuffer originalImageBytes=null;
	    ByteBuffer targetImageBytes=null;
	    
	    //absolute path to target image, yours is different from mine, you can save ur image on deskyop as long as you use the absolute path, it will work
		File targetImageFile = new File("C:\\Users\\yuet\\git\\seniorProjectRepo\\seniorProject\\src\\main\\resources\\static\\oimg.jpg");
		try (InputStream inputStream = new FileInputStream(targetImageFile)) {
			
			targetImageBytes = ByteBuffer.wrap(IOUtils.toByteArray(inputStream));
	    
		}catch (Exception e) {
	    	
	    	System.out.println("Failed to load source image " + targetImageReference);
	        System.exit(1);
	    
	    }
		
		//absolute path to original image, yours is different from mine, you can save ur image on deskyop as long as you use the absolute path, it will work
		File originalImageFile = new File("C:\\Users\\yuet\\git\\seniorProjectRepo\\seniorProject\\src\\main\\resources\\static\\timg.jpg");
		try (InputStream inputStream = new FileInputStream(originalImageFile)) {
			
			originalImageBytes = ByteBuffer.wrap(IOUtils.toByteArray(inputStream));
			
	    }catch (Exception e) {
	    	
	    	System.out.println("Failed to load source image " + originalImageReference);
	        System.exit(1);
	        
	    }
		
	    Image source = new Image().withBytes(originalImageBytes);
	    Image target = new Image().withBytes(targetImageBytes);

	    CompareFacesRequest request 
	    = new CompareFacesRequest()
	    .withSourceImage(source)
	    .withTargetImage(target)
	    .withSimilarityThreshold(similarityThreshold);

	    CompareFacesResult compareFacesResult = rekognitionClient.compareFaces(request);
	    List <CompareFacesMatch> faceDetails = compareFacesResult.getFaceMatches();
	    
	    matched = (faceDetails.isEmpty() != true);
	    return matched;
	    
	}

	//this will compare similarity of two person
	@Override
	public boolean compare(File targetImageFile, File originalImageFile) throws IOException {
		
		boolean matched = false;
		
	    ByteBuffer originalImageBytes=null;
	    ByteBuffer targetImageBytes=null;
	    
		try (InputStream inputStream = new FileInputStream(targetImageFile)) {
			
			targetImageBytes = ByteBuffer.wrap(IOUtils.toByteArray(inputStream));
	    
		}catch (Exception e) {
	    	
	    	System.out.println("Failed to load source image " + targetImageFile.getAbsolutePath());
	        System.exit(1);
	        
	    }
		
		try (InputStream inputStream = new FileInputStream(originalImageFile)) {
			
			originalImageBytes = ByteBuffer.wrap(IOUtils.toByteArray(inputStream));
	    
		}catch (Exception e) {
	    	
	    	System.out.println("Failed to load source image " + originalImageFile.getAbsolutePath());
	        System.exit(1);
	        
	    }
		
	    Image source = new Image().withBytes(originalImageBytes);
	    Image target = new Image().withBytes(targetImageBytes);

	    CompareFacesRequest request 
	    = new CompareFacesRequest()
	    .withSourceImage(source)
	    .withTargetImage(target)
	    .withSimilarityThreshold(similarityThreshold);

	    CompareFacesResult compareFacesResult = rekognitionClient.compareFaces(request);
	    List <CompareFacesMatch> faceDetails = compareFacesResult.getFaceMatches();
	    
	    matched = (faceDetails.isEmpty() != true);
	    return matched;
	    
	}

}

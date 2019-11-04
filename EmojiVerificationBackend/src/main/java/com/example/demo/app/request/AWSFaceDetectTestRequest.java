package com.example.demo.app.request;

import java.util.List;

public class AWSFaceDetectTestRequest {
	
	private List<String> imageDataUrls;

	public List<String> getImageDataUrls() {
		
		return imageDataUrls;
		
	}

	public void setImageDataUrls(List<String> imageDataUrls) {
		
		this.imageDataUrls = imageDataUrls;
		
	}

}

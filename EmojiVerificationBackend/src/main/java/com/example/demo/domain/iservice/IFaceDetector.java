package com.example.demo.domain.iservice;

import java.io.File;
import java.util.List;

import com.amazonaws.services.rekognition.model.FaceDetail;

public interface IFaceDetector {
	
	public List<FaceDetail> detect(File targetImageFile);
	
	public List<FaceDetail> detect(byte[] targetImageBytes);

}

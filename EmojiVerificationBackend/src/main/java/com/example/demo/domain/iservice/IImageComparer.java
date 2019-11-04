package com.example.demo.domain.iservice;

import java.io.File;
import java.io.IOException;

public interface IImageComparer {
	
	boolean compare(File targetImageFile, File originalImageFile) throws IOException;
	
	boolean compare(byte[] targetImageBytes, File originalImageFile) throws IOException;

}

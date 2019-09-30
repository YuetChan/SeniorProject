package com.example.demo.imageComparer;

import java.io.File;
import java.io.IOException;

public interface IImageComparer {
	
	boolean compare(String targetImageReference, String originalImageReference) throws IOException;
	
	boolean compare(File targetImageFile, File originalImageFile) throws IOException;

}

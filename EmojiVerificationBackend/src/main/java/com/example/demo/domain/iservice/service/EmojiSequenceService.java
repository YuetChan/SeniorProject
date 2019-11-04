package com.example.demo.domain.iservice.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import com.example.demo.domain.entity.EmojiSequence;

public class EmojiSequenceService {
	
	public static List<Integer> getRandomEmojiSequenceKeys(int num) {
		
		List<Integer> emojiSequenceKeys = new ArrayList();
		//Map<Integer, String> emojiMap = EmojiSequence.getEmojiMap();
		
		for (int i = 0; i < num; i++) {
			
			emojiSequenceKeys.add(getRandomNumberInRange(0, 3));
			
		}
		
		return emojiSequenceKeys;
		
	}
	
	public static List<String> getEmojiSequenceFromKeys(List<Integer> keys) {
		
		List<String> emojiSequence = new ArrayList();
		Map<Integer, String> emojiMap = EmojiSequence.getEmojiMap();
		
		for (int i = 0; i < keys.size(); i++) {
			
			emojiSequence.add(emojiMap.get(keys.get(i)));
			
		}
		
		return emojiSequence;
		
	}
	
	public static List<String> getEmotionSequenceFromKeys(List<Integer> keys) {
		
		List<String> emotionSequence = new ArrayList();
		Map<Integer, String> emotionMap = EmojiSequence.getEmotionMap();
		
		for (int i = 0; i < keys.size(); i++) {
			
			emotionSequence.add(emotionMap.get(keys.get(i)));
			
		}
		
		return emotionSequence;
		
	}
	
	private static int getRandomNumberInRange(int min, int max) {

		if (min >= max) {
			
			throw new IllegalArgumentException("max must be greater than min");
			
		}

		Random r = new Random();
		return r.nextInt((max - min) + 1) + min;
		
	}
	
	public static boolean matchDetectedEmotionsToEmotionSequence(List<String> detectedEmotions, List<String> emotionSequence) {
		
		int numOfFrameToReact = 7;
		
		//reaction frame
		for(int i = 0; i < 0 + numOfFrameToReact; i++) {
	
		}
		
		for(int i = 0 + numOfFrameToReact; i < 25 - numOfFrameToReact; i++) {
			
			if(detectedEmotions.get(i).equals(emotionSequence.get(0))){
				
				;
				
			}else {
				
				System.out.print("First");
				return false;
				
			}
			
		}
		
		//reaction frame
		for(int i = 25 - numOfFrameToReact; i < 25; i++) {
	
		}
		
		//reaction frame
		for(int i = 25; i < 25 + numOfFrameToReact; i++) {
	
		}
		
		for(int i = 25 + numOfFrameToReact; i < 50 - numOfFrameToReact; i++) {
			
			if(detectedEmotions.get(i).equals(emotionSequence.get(1))){
				
				;
				
			}else {
				
				System.out.print("Second");
				return false;
				
			}
			
		}
		
		//reaction frame
		for(int i = 50 - numOfFrameToReact; i < 50; i++) {
			
		}
		
		//reaction frame
		for(int i = 50; i < 50 + numOfFrameToReact; i++) {
			
		}
		
		for(int i = 50 + numOfFrameToReact; i < 75 - numOfFrameToReact; i++) {
			
			if(detectedEmotions.get(i).equals(emotionSequence.get(2))){
				
				
				
			}else {
				
				System.out.print("third");
				return false;
				
			}
			
		}
		
		//reaction frame
		for(int i = 75 - numOfFrameToReact; i < 75; i++) {
			
		}
		
		//reaction frame
		for(int i = 75; i < 75 + numOfFrameToReact; i++) {
			
		}
		
		for(int i = 75 + numOfFrameToReact; i < 100 - numOfFrameToReact; i++) {
			
			if(detectedEmotions.get(i).equals(emotionSequence.get(3))){
				
				
				
			}else {
				
				System.out.print("Forth");
				return false;
				
			}
			
		}
		
		//reaction frame
		for(int i = 100 - numOfFrameToReact; i < 100; i++) {
			
		}
		
		return true;
		
	}

}

package com.example.demo.domain.iservice.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import com.example.demo.domain.entity.EmojiSequence;

public class EmojiSequenceService {
	
	public static List<String> getRandomEmojiSequence(int num) {
		
		List<String> emojiSequence = new ArrayList();
		Map<Integer, String> emojiMap = EmojiSequence.getEmojiMap();
		
		for (int i = 0; i < num; i++) {
			
			emojiSequence.add(emojiMap.get(getRandomNumberInRange(0, 3)));
			
		}
		
		return emojiSequence;
		
	}
	
	private static int getRandomNumberInRange(int min, int max) {

		if (min >= max) {
			
			throw new IllegalArgumentException("max must be greater than min");
			
		}

		Random r = new Random();
		return r.nextInt((max - min) + 1) + min;
	}

}

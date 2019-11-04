package com.example.demo.domain.entity;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EmojiSequence {
	
    private static final Map<Integer, String> emojiMap;
    static {
    	
        Map<Integer, String> eMap = new HashMap();
        eMap.put(0, "😀");
        eMap.put(1, "😡");
        eMap.put(2, "😲");
        eMap.put(3, "😐");
        
        emojiMap = Collections.unmodifiableMap(eMap);
        
    }
    
    private static final Map<Integer, String> emotionMap;
    static {
    	
        Map<Integer, String> eMap = new HashMap();
        eMap.put(0, "HAPPY");
        eMap.put(1, "ANGRY");
        eMap.put(2, "SURPRISED");
        eMap.put(3, "CALM");
        
        emotionMap = Collections.unmodifiableMap(eMap);
        
    }
    
	private int userId;
	private List<Integer> emojiSequenceKeys;
	private LocalDateTime expirationDate;
	
	public EmojiSequence() {
		
		super();
		
	}
	
	public static Map<Integer, String> getEmojiMap() {
		
		return emojiMap;
		
	}
	
	public static Map<Integer, String> getEmotionMap() {
		
		return emotionMap;
		
	}

	public int getUserId() {
		
		return userId;
		
	}

	public void setUserId(int userId) {
		
		this.userId = userId;
		
	}

	public List<Integer> getEmojiSequenceKeys() {
		
		return emojiSequenceKeys;
		
	}

	public void setEmojiSequenceKey(List<Integer> emojiSequenceKeys) {
		
		this.emojiSequenceKeys = emojiSequenceKeys;
		
	}

	public LocalDateTime getExpirationDate() {
		
		return expirationDate;
		
	}

	public void setExpirationDate(LocalDateTime expirationDate) {
		
		this.expirationDate = expirationDate;
		
	}

}

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
    
	private int userId;
	private List<String> emojiSequence;
	private LocalDateTime expirationDate;
	
	public EmojiSequence() {
		
		super();
		
	}
	

	public static Map<Integer, String> getEmojiMap() {
		
		return emojiMap;
		
	}

	public int getUserId() {
		
		return userId;
		
	}

	public void setUserId(int userId) {
		
		this.userId = userId;
		
	}

	public List<String> getEmojiSequence() {
		
		return emojiSequence;
		
	}

	public void setEmojiSequence(List<String> emojiSequence) {
		
		this.emojiSequence = emojiSequence;
		
	}

	public LocalDateTime getExpirationDate() {
		
		return expirationDate;
		
	}

	public void setExpirationDate(LocalDateTime expirationDate) {
		
		this.expirationDate = expirationDate;
		
	}

}

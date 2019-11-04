package com.example.demo.iInfrastructure;

import com.example.demo.domain.entity.EmojiSequence;

public interface IEmojiSequenceSchema {
	
	public EmojiSequence save(EmojiSequence emojiSequence);
	
	public EmojiSequence update(EmojiSequence emojiSequence);

	public EmojiSequence findByUserId(int userId);
	
	public void deleteAll();

}

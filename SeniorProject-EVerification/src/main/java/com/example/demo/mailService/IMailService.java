package com.example.demo.mailService;

public interface IMailService {

	public boolean sendMessage(String TO, String FROM, String SUBJECT, String TEXTBODY);
	
}

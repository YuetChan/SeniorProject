package com.example.demo.domain.iservice;

public interface IMailService {

	public boolean sendMessage(String TO, String FROM, String SUBJECT, String TEXTBODY);
	
}

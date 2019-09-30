package com.example.demo.controller;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;

import javax.imageio.ImageIO;
import javax.xml.bind.DatatypeConverter;
import javax.annotation.PostConstruct;

import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.activateToken.ActivateTokenSchema;
import com.example.demo.config.CustomMongoDbConfig;
import com.example.demo.imageComparer.AWSImageComparer;
import com.example.demo.loginToken.LoginToken;
import com.example.demo.loginToken.LoginTokenSchema;
import com.example.demo.output.ActivateOutput;
import com.example.demo.output.FirstLoginOutput;
import com.example.demo.output.RegisterOutput;
import com.example.demo.output.SecondLoginOutput;
import com.example.demo.request.FirstLoginRequest;
import com.example.demo.request.RegisterRequest;
import com.example.demo.request.SecondLoginRequest;
import com.example.demo.user.User;
import com.example.demo.user.UserSchema;
import com.example.demo.user.UserService;

@Controller
public class MainController {
	
	MongoOperations mongoOperations;
	
	@PostConstruct
	public void setup() {
		
		mongoOperations = (MongoOperations) CustomMongoDbConfig.mongoTemplate();
		
	}
	
	@PostMapping(value = "/user/firstLogin")
	public @ResponseBody FirstLoginOutput firstLogin(@RequestBody FirstLoginRequest firstLoginRequest) {
		
		UserService userService = new UserService();

		LoginToken loginToken = null;
		loginToken 
		= userService.firstLogin(
				firstLoginRequest.getUseremail(), firstLoginRequest.getPassword(), 
				new UserSchema(mongoOperations));
		if(loginToken != null) {
			new LoginTokenSchema(mongoOperations).save(loginToken);
			
			return new FirstLoginOutput(true, loginToken.getTokenString());
		}else
			return new FirstLoginOutput(false, "");
		
	}
	
	@PostMapping(value = "/user/secondLogin")
	public @ResponseBody SecondLoginOutput secondLogin(@RequestBody SecondLoginRequest secondLoginRequest) {
		
		UserService userService = new UserService();

		boolean loginSuccess = false;
	    try{
	    	
			String loginiImgBase64 = secondLoginRequest.getImgBase64();
	        byte[] loginImageBytes 
	        = DatatypeConverter.parseBase64Binary(
	        		loginiImgBase64.replaceAll("data:image/.+;base64,", ""));
	        
	        String loginTokenString = secondLoginRequest.getTokenString();
	        
	        loginSuccess 
	        = userService.secondLogin(
	        		loginTokenString, loginiImgBase64, 
	        		new AWSImageComparer(), 
	        		new LoginTokenSchema(mongoOperations),
	        		new UserSchema(mongoOperations));
	        	        
	    }catch(Exception e){
	    	
	    	loginSuccess = false;
	        
	    }
	    
	    return new SecondLoginOutput(loginSuccess);
		
	}
	
	
	@PostMapping(value = "/user/register")
	public @ResponseBody RegisterOutput register(@RequestBody RegisterRequest registerRequest) throws IOException {
		
		UserService userService = new UserService();
		
		boolean registerSuccess 
		= userService.register(
				registerRequest.getUseremail(), registerRequest.getPassword());
		if(registerSuccess) {

			String userImgReference 
			= saveUserImgFileToDir(
					registerRequest.getUserImgBase64(), registerRequest.getUseremail());
			
			UserSchema userSchema = new UserSchema(mongoOperations);
			
			User newUser = new User();
			newUser.setUserId(userSchema.getNextId());
			
			newUser.setUseremail(registerRequest.getUseremail());
			newUser.setPassword(registerRequest.getPassword());
			
			newUser.setActivated(false);
			
			newUser.setCreatedAt(LocalDateTime.now());
			newUser.setUpdatedAt(LocalDateTime.now());
			
			newUser.setImageReference(userImgReference);
			
			userSchema.save(newUser);
			
			
			
			
		}else
			;
		
		return new RegisterOutput(registerSuccess);
		
	}
	
	public String saveUserImgFileToDir(String userImgBase64, String useremail) throws IOException {
		
		File userImgFile = new File("C:\\Users\\yuet\\Desktop\\" + useremail + ".png");
		
        byte[] imgBytes 
        = DatatypeConverter.parseBase64Binary(
        		userImgBase64.replaceAll("data:image/.+;base64,", ""));
        BufferedImage bfi = ImageIO.read(new ByteArrayInputStream(imgBytes));
        ImageIO.write(bfi , "png", userImgFile);
        
        bfi.flush();
        
        return "C:\\Users\\yuet\\Desktop\\" + useremail + ".png";
		
	}
	
	
	
	@GetMapping(value = "/user/{userId}/activate")
	public @ResponseBody ActivateOutput activate(
			@PathVariable(name = "userId") int userId, 
			@RequestParam(name = "tokenString") String tokenString) {
				return null;
	}
	
	

}

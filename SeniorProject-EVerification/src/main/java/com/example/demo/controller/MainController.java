package com.example.demo.controller;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.UUID;

import javax.annotation.PostConstruct;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.activateToken.ActivateToken;
import com.example.demo.activateToken.ActivateTokenSchema;
import com.example.demo.activateToken.IActivateTokenSchema;
import com.example.demo.config.CustomMongoDbConfig;
import com.example.demo.loginToken.ILoginTokenSchema;
import com.example.demo.loginToken.LoginToken;
import com.example.demo.loginToken.LoginTokenSchema;
import com.example.demo.output.RegisterOutput;
import com.example.demo.output.SecondLoginOutput;
import com.example.demo.passwordResetToken.IPasswordResetTokenSchema;
import com.example.demo.passwordResetToken.PasswordResetToken;
import com.example.demo.passwordResetToken.PasswordResetTokenSchema;
import com.example.demo.registerDetail.RegisterDetail;
import com.example.demo.request.RegisterRequest;
import com.example.demo.request.SecondLoginRequest;
import com.example.demo.user.IUserSchema;
import com.example.demo.user.User;
import com.example.demo.user.UserSchema;
import com.example.demo.user.UserService;

@RestController
public class MainController {
	
	private IUserSchema userSchema = new UserSchema(CustomMongoDbConfig.mongoTemplate());
	private IActivateTokenSchema activateTokenSchema = new ActivateTokenSchema(CustomMongoDbConfig.mongoTemplate());
	private ILoginTokenSchema loginTokenSchema = new LoginTokenSchema(CustomMongoDbConfig.mongoTemplate());
	private IPasswordResetTokenSchema passwordResetTokenSchema = new PasswordResetTokenSchema(CustomMongoDbConfig.mongoTemplate());
	
	@PostMapping("/user/register")
	public @ResponseBody RegisterOutput register(@RequestBody RegisterRequest request) throws FileNotFoundException, IOException {
		
		RegisterOutput registerOutput;
		
		UserService userService = new UserService();
		
		User newUser = new User();
		newUser.setUseremail(request.getUseremail());
		newUser.setPassword(request.getPassword());
		newUser.setActivated(false);
		
		newUser.setCreatedAt(LocalDateTime.now());
		newUser.setUpdatedAt(LocalDateTime.now());
		
		RegisterDetail registerDetail = userService.register(request.getUseremail(), userSchema, activateTokenSchema);
		if(registerDetail.isRegisterSuccess()) {
			
			if(registerDetail.isRegisteredBefore()) {
				
				String imageDataUrl = request.getImageDataUrl().split(",")[1];
				byte[] imageByte = javax.xml.bind.DatatypeConverter.parseBase64Binary(imageDataUrl);
				String directory = "C:\\\\Users\\\\yuet\\\\Desktop\\\\" + request.getUseremail() +"Picture.jpg";
				new FileOutputStream(directory).write(imageByte);
				
				User olderUser = userSchema.findByUseremail(newUser.getUseremail());
				newUser.setUserId(olderUser.getUserId());
				newUser.setImageReference(directory);

				userSchema.update(newUser);
				
				ActivateToken newActivateToken = activateTokenSchema.findByUserId(newUser.getUserId());
				newActivateToken.setExpirationDate(LocalDateTime.now().plusMinutes(30));
				activateTokenSchema.update(newActivateToken);
				
				registerOutput = new RegisterOutput();
				registerOutput.setStatus("Registered");
				
			}else {
				
				String imageDataUrl = request.getImageDataUrl().split(",")[1];
				byte[] imageByte = javax.xml.bind.DatatypeConverter.parseBase64Binary(imageDataUrl);
				String directory = "C:\\Users\\yuet\\Desktop\\" + request.getUseremail() +"Picture.jpg";
				new FileOutputStream(directory).write(imageByte);
				
				newUser.setUserId(userSchema.getNextId());
				newUser.setImageReference(directory);
				
				userSchema.save(newUser);
				
				ActivateToken activateToken = new ActivateToken();
				activateToken.setUserId(newUser.getUserId());
				activateToken.setTokenString(UUID.randomUUID().toString());
				activateToken.setExpirationDate(LocalDateTime.now().plusMinutes(30));
				
				activateTokenSchema.save(activateToken);
				
				LoginToken loginToken = new LoginToken();
				loginToken.setUserId(newUser.getUserId());
				loginToken.setTokenString(UUID.randomUUID().toString());
				loginToken.setExpirationDate(LocalDateTime.now());
				
				loginTokenSchema.save(loginToken);
				
				PasswordResetToken passwordResetToken = new PasswordResetToken();
				passwordResetToken.setUserId(newUser.getUserId());
				passwordResetToken.setTokenString(UUID.randomUUID().toString());
				passwordResetToken.setExpirationDate(LocalDateTime.now());
				
				passwordResetTokenSchema.save(passwordResetToken);
				
				registerOutput = new RegisterOutput();
				registerOutput.setStatus("Registered");
				
			}
			
		}else {
			
			registerOutput = new RegisterOutput();
			registerOutput.setStatus("Not Registered");
			
		}
		
		return registerOutput;
		
	}

	public @ResponseBody activate
	
	
	public @ResponseBody SecondLoginOutput secondLogin (@RequestBody SecondLoginRequest request) {
		
		
		
		
	}
	
}

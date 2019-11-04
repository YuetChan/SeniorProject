package com.example.demo.app.controller;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.UUID;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.amazonaws.services.rekognition.model.Emotion;
import com.amazonaws.services.rekognition.model.FaceDetail;
import com.example.demo.app.output.FirstLoginOutput;
import com.example.demo.app.output.RegisterOutput;
import com.example.demo.app.output.SecondLoginOutput;
import com.example.demo.app.request.AWSFaceDetectTestRequest;
import com.example.demo.app.request.FirstLoginRequest;
import com.example.demo.app.request.RegisterRequest;
import com.example.demo.app.request.SecondLoginRequest;
import com.example.demo.config.CustomMongoDbConfig;
import com.example.demo.domain.entity.ActivateToken;
import com.example.demo.domain.entity.EmojiSequence;
import com.example.demo.domain.entity.LoginToken;
import com.example.demo.domain.entity.PasswordResetToken;
import com.example.demo.domain.entity.User;
import com.example.demo.domain.iservice.IFaceDetector;
import com.example.demo.domain.iservice.service.EmojiSequenceService;
import com.example.demo.domain.iservice.service.UserService;
import com.example.demo.domain.service.aws.AWSFaceDetector;
import com.example.demo.domain.value.RegisterDetail;
import com.example.demo.iInfrastructure.IActivateTokenSchema;
import com.example.demo.iInfrastructure.ILoginTokenSchema;
import com.example.demo.iInfrastructure.IPasswordResetTokenSchema;
import com.example.demo.iInfrastructure.IUserSchema;
import com.example.demo.iInfrastructure.infrastructure.ActivateTokenSchema;
import com.example.demo.iInfrastructure.infrastructure.EmojiSequenceSchema;
import com.example.demo.iInfrastructure.infrastructure.LoginTokenSchema;
import com.example.demo.iInfrastructure.infrastructure.PasswordResetTokenSchema;
import com.example.demo.iInfrastructure.infrastructure.UserSchema;

@RestController
public class MainController {
	
	private IUserSchema userSchema = new UserSchema(CustomMongoDbConfig.mongoTemplate());
	private IActivateTokenSchema activateTokenSchema = new ActivateTokenSchema(CustomMongoDbConfig.mongoTemplate());
	private ILoginTokenSchema loginTokenSchema = new LoginTokenSchema(CustomMongoDbConfig.mongoTemplate());
	private EmojiSequenceSchema emojiSequenceSchema = new EmojiSequenceSchema(CustomMongoDbConfig.mongoTemplate());
	private IPasswordResetTokenSchema passwordResetTokenSchema = new PasswordResetTokenSchema(CustomMongoDbConfig.mongoTemplate());
	
	@CrossOrigin(origins = "http://localhost:4200")
	@PostMapping("/user/register/")
	public @ResponseBody RegisterOutput register(@RequestBody RegisterRequest request) throws FileNotFoundException, IOException {
		
		RegisterOutput registerOutput;
		
		UserService userService = new UserService();
		
		User newUser = new User();
		newUser.setUseremail(request.getUseremail());
		newUser.setPassword(request.getPassword());
		newUser.setActivated(true);
		
		newUser.setCreatedAt(LocalDateTime.now());
		newUser.setUpdatedAt(LocalDateTime.now());
		
		RegisterDetail registerDetail = userService.register(request.getUseremail(), userSchema, activateTokenSchema);
		if(registerDetail.isRegisterSuccess()) {
			
			if(registerDetail.isRegisteredBefore()) {
				
				String imageDataUrl = request.getImageDataUrl().split(",")[1];
				byte[] imageByte = javax.xml.bind.DatatypeConverter.parseBase64Binary(imageDataUrl);
				String directory = "C:\\Users\\yuet\\Desktop\\" + request.getUseremail() +"Picture.jpg";
				new FileOutputStream(directory).write(imageByte);
				
				User olderUser = userSchema.findByUseremail(newUser.getUseremail());
				newUser.setUserId(olderUser.getUserId());
				newUser.setImageReference(directory);

				userSchema.update(newUser);
				
				ActivateToken newActivateToken = activateTokenSchema.findByUserId(newUser.getUserId());
				newActivateToken.setExpirationDate(LocalDateTime.now().plusMinutes(30));
				activateTokenSchema.update(newActivateToken);
				
				registerOutput = new RegisterOutput();
				registerOutput.setStatus("registered");
				
			}else {
				
				String imageDataUrl = request.getImageDataUrl().split(",")[1];
				byte[] imageByte = javax.xml.bind.DatatypeConverter.parseBase64Binary(imageDataUrl);
				String directory = "C:\\Users\\yuet\\Desktop\\UserPics\\" + request.getUseremail() +"Picture.jpg";
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
				
				EmojiSequence emojiSequence = new EmojiSequence();
				emojiSequence.setUserId(newUser.getUserId());
				emojiSequence.setEmojiSequenceKey(new ArrayList());
				emojiSequence.setExpirationDate(LocalDateTime.now());
				
				emojiSequenceSchema.save(emojiSequence);
				
				PasswordResetToken passwordResetToken = new PasswordResetToken();
				passwordResetToken.setUserId(newUser.getUserId());
				passwordResetToken.setTokenString(UUID.randomUUID().toString());
				passwordResetToken.setExpirationDate(LocalDateTime.now());
				
				passwordResetTokenSchema.save(passwordResetToken);
				
				registerOutput = new RegisterOutput();
				registerOutput.setStatus("registered");
				
			}
			
		}else {
			
			registerOutput = new RegisterOutput();
			registerOutput.setStatus("not registered");
			
		}
		
		return registerOutput;
		
	}

	@CrossOrigin(origins = "http://localhost:4200")
	@PostMapping("/user/firstLogin/")
	public @ResponseBody FirstLoginOutput firstLogin(@RequestBody FirstLoginRequest request) {
		
		FirstLoginOutput firstLoginOutput = new FirstLoginOutput();
		List<String> errors = new ArrayList();
		
		User foundUser = userSchema.findByUseremail(request.getUseremail());
		if(foundUser != null) {
			
			if(foundUser.getPassword().equals(request.getPassword())) {
				
				LoginToken loginToken = new LoginToken();
				loginToken.setUserId(foundUser.getUserId());
				loginToken.setTokenString(UUID.randomUUID().toString());
				loginToken.setExpirationDate(LocalDateTime.now().plusMinutes(15));
				
				loginTokenSchema.update(loginToken);
				
				EmojiSequence emojiSequence = new EmojiSequence();
				emojiSequence.setUserId(foundUser.getUserId());
				emojiSequence.setEmojiSequenceKey(EmojiSequenceService.getRandomEmojiSequenceKeys(4));
				emojiSequence.setExpirationDate(LocalDateTime.now().plusMinutes(15));
				
				emojiSequenceSchema.update(emojiSequence);
				
				firstLoginOutput.setStatus("firstLogined");
				firstLoginOutput.setErrors(errors);
				firstLoginOutput.setUserId(foundUser.getUserId());
				firstLoginOutput.setTokenString(loginToken.getTokenString());
				firstLoginOutput.setEmojiSequence(EmojiSequenceService.getEmojiSequenceFromKeys(emojiSequence.getEmojiSequenceKeys()));
				
			}else {
				
				errors.add("Invalid crendential");
				
				firstLoginOutput.setStatus("invalid");
				firstLoginOutput.setErrors(errors);
				firstLoginOutput.setTokenString("");
				
			}
			
		}else {
			
			errors.add("Invalid crendential");
			
			firstLoginOutput.setStatus("invalid");
			firstLoginOutput.setErrors(errors);
			firstLoginOutput.setTokenString("");
			
		}
		
		return firstLoginOutput;
		
	}
	
	@CrossOrigin(origins = "http://localhost:4200")
	@PostMapping ("/user/secondLogin/")
	public @ResponseBody SecondLoginOutput secondLogin(@RequestBody SecondLoginRequest request) 
			throws FileNotFoundException, IOException {
		
		SecondLoginOutput secondLoginOutput = null;
		List<String> errors = new ArrayList();
		
		LoginToken loginToken = loginTokenSchema.findByUserId(request.getUserId());
		if(loginToken.getTokenString().equals(request.getTokenString())){
			
			if(loginToken.getExpirationDate().isAfter(LocalDateTime.now())) {
				
				IFaceDetector awsFaceDetector = new AWSFaceDetector();
				List<List<FaceDetail>> faceDetailInFrames = new ArrayList();
				for(int i = 0; i < 100; i++) {
					
					String imageDataUrl = request.getImageDataUrls().get(i).split(",")[1];
					byte[] imageByte = javax.xml.bind.DatatypeConverter.parseBase64Binary(imageDataUrl);
					//String directory = "C:\\Users\\yuet\\Desktop\\AWSFaceDetectTest\\" + request.getUserId() +"Picture.jpg";
					//new FileOutputStream(directory).write(imageByte);
					faceDetailInFrames.add(awsFaceDetector.detect(imageByte));
					//System.out.print("called");
				
				}
				
				
				List<String> detectedEmotions = new ArrayList();
				for(int i = 0; i < 100; i++) {
					
					double maxConfi = 0;
					String detectedEmotion = "";
					for(int j = 0; j < faceDetailInFrames.get(i).get(0).getEmotions().size(); j++) {
						
						Emotion emotion = faceDetailInFrames.get(i).get(0).getEmotions().get(j);
						if(emotion.getConfidence() > maxConfi) {
							
							detectedEmotion = emotion.getType();
							maxConfi = emotion.getConfidence();
							
						}else {
							
							;
						}
						
					}
					
					detectedEmotions.add(detectedEmotion);
					//System.out.print("--" + detectedEmotion);
						
				}
				
				EmojiSequence emojiSequence = emojiSequenceSchema.findByUserId(request.getUserId());
				//System.out.print(EmojiSequenceService.matchDetectedEmotionsToEmojiSequence(detectedEmotions, emojiSequence.getEmojiSequence()));
				
				System.out.print(detectedEmotions);
				System.out.print("----"+EmojiSequenceService.getEmotionSequenceFromKeys(emojiSequence.getEmojiSequenceKeys()));
				
				if(EmojiSequenceService.matchDetectedEmotionsToEmotionSequence(detectedEmotions, EmojiSequenceService.getEmotionSequenceFromKeys(emojiSequence.getEmojiSequenceKeys()))) {
					
					secondLoginOutput = new SecondLoginOutput();
					secondLoginOutput.setStatus("secondLogined");
					secondLoginOutput.setErrors(errors);
					
				}else {
					
					secondLoginOutput = new SecondLoginOutput();
					secondLoginOutput.setStatus("invalid");
					secondLoginOutput.setErrors(errors);
					
				}
				
			}else {
				
				errors.add("Expired token");
				
				secondLoginOutput = new SecondLoginOutput();
				secondLoginOutput.setStatus("invalid");
				secondLoginOutput.setErrors(errors);
				
			}
			
		}else {
			
			errors.add("Unmatched token");
			
			secondLoginOutput = new SecondLoginOutput();
			secondLoginOutput.setStatus("invalid");
			secondLoginOutput.setErrors(errors);
			
		}
		
		return secondLoginOutput;
		
	}
	
//	@CrossOrigin(origins = "http://localhost:4200")
//	@PostMapping("/user/AWSFaceDetectTest/")
//	public boolean AWSFaceDetecTest(@RequestBody AWSFaceDetectTestRequest request) 
//			throws FileNotFoundException, IOException {
//		
//		for(int i = 0; i < request.getImageDataUrls().size(); i++) {
//			
//			String imageDataUrl = request.getImageDataUrls().get(i).split(",")[1];
//			byte[] imageByte = javax.xml.bind.DatatypeConverter.parseBase64Binary(imageDataUrl);
//			String directory = "C:\\Users\\yuet\\Desktop\\AWSFaceDetectTest\\" + i +"Picture.jpg";
//			new FileOutputStream(directory).write(imageByte);
//			
//		}
//		
//		return true;
//		
//	}
	
}

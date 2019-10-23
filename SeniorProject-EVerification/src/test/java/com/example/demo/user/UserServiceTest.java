package com.example.demo.user;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import com.example.demo.config.CustomMongoDbConfig;
import com.example.demo.imageComparer.AWSImageComparer;
import com.example.demo.imageComparer.IImageComparer;
import com.example.demo.loginToken.ILoginTokenSchema;
import com.example.demo.loginToken.LoginTokenSchema;
import com.example.demo.passwordResetToken.IPasswordResetTokenSchema;
import com.example.demo.passwordResetToken.PasswordResetToken;
import com.example.demo.passwordResetToken.PasswordResetTokenSchema;
import com.example.demo.user.UserSchema;
import com.example.demo.user.UserService;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {
	

	private ILoginTokenSchema loginTokenSchema = new LoginTokenSchema(CustomMongoDbConfig.mongoTemplate());
	private IPasswordResetTokenSchema passwordResetTokenSchema = new PasswordResetTokenSchema(CustomMongoDbConfig.mongoTemplate());

	
	
	//-------------------Before each test clear all schema-----------------------------------
	@Before
	public void setup() {
		
		loginTokenSchema.deleteAll();
		passwordResetTokenSchema.deleteAll();
		
	}
	
	//--------------------first && second login unit test----------------------------------
	
	//Still need isActivated function
	@Test
	public void FirstLoginWithRegisteredAndActivatedUserShouldReturnTrue() {
		
		boolean firstLogin = false;
		
		UserService userService = new UserService();                                                        
		UserSchema userSchema = new UserSchema();
		
		User user = new User();
		user.setUseremail("abc123@gmail.com");
		user.setPassword("abc123");
		user.setUserId(userSchema.getNextId());
		
		userSchema.save(user);
		
		firstLogin = userService.firstLogin("abc123@gmail.com", "abc123", userSchema);
		
		assertTrue(firstLogin == false);
		
	}
	//Still need isActivated function
	@Test
	public void FirstLoginWithRegisteredAndUnactivatedUserShouldReturnFalse() {
		
		boolean firstLogin = false;
		
		UserService userService = new UserService();                                                        
		UserSchema userSchema = new UserSchema();
		
		User user = new User();
		user.setUseremail("abc123@gmail.com");
		user.setPassword("abc123");
		user.setUserId(userSchema.getNextId());
		
		userSchema.save(user);
		
		firstLogin = userService.firstLogin("abc123@gmail.com", "abc123", userSchema);
		
		assertTrue(firstLogin == false);
		
	}
	@Test
	public void FirstLoginWithUnregisteredUserShouldReturnFalse(){
		
		boolean firstLogin = false;
		
		UserService userService = new UserService();                                                        
		UserSchema userSchema = new UserSchema();
		
		User user = new User();
		user.setUseremail("abc123@gmail.com");
		user.setPassword("abc123");
		user.setUserId(userSchema.getNextId());
		
		userSchema.save(user);
		
		firstLogin = userService.firstLogin("cde456@gmail.com", "abc123", userSchema);
		
		assertTrue(firstLogin == false);
	}
	

	
	
	
	//--------------------register && activate unit test------------------------------------
	
	
	
	
	
	//---------------------AWSImageComparer unit test----------------------------------
	
	@Test
	public void AWSImageComparerCompareTwoImageWithSamePersonShouldReturnTrueTest() throws IOException {
		
		boolean matched = false;
		
		// you can put ur image file anywhere you want but you have to use their absolute paths
		File targetImageFile = new File("C:\\Users\\yuet\\git\\seniorProjectRepo\\seniorProject\\src\\main\\resources\\static\\oimg.jpg");
		File originalImageFile = new File("C:\\Users\\yuet\\git\\seniorProjectRepo\\seniorProject\\src\\main\\resources\\static\\timg.jpg");
		
		IImageComparer imageComparer = new AWSImageComparer();
		//compare take in two File object and compare them as images
		matched = imageComparer.compare(targetImageFile, originalImageFile);
		
		assertTrue(matched == true);
		
	}
	
	//--------------------reset password && change password unit testing-----------------------
	@Test
	public void resetPasswordWithExistedUserAndPasswordResetTokenShouldReturnTrue() {
		
		boolean reset = false;
		
		UserService userService = new UserService();                                                        
		
		UserSchema userSchema = new UserSchema();
		
		User user = new User();
		user.setUseremail("Saito@gmail.com");
		user.setPassword("Saito");
		user.setUserId(userSchema.getNextId());
		
		userSchema.save(user);
		
		
		
		PasswordResetToken passwordResetToken = new PasswordResetToken();
		passwordResetToken.setUserId(user.getUserId());
		
		passwordResetTokenSchema.save(passwordResetToken);
		
		reset = userService.resetPassword("Saito@gmail.com", userSchema, passwordResetTokenSchema);
	
		assertTrue(reset == true);
		
	}
	
	@Test
	public void resetPasswordWithNotExistedUserShouldReturnFalse() {
		
		boolean reset = false;
		
		UserService userService = new UserService();                                                        
		
		UserSchema userSchema = new UserSchema();
		
		reset = userService.resetPassword("Saito@gmail.com", userSchema, passwordResetTokenSchema);
	
		assertTrue(reset == false);
		
	}
	
	@Test
	public void resetPasswordWithNotExistedPasswordResetTokenShouldReturnFalse() {
		
		boolean reset = false;
		
		UserService userService = new UserService();                                                        
		
		UserSchema userSchema = new UserSchema();
		
		User user = new User();
		user.setUseremail("Saito@gmail.com");
		user.setPassword("Saito");
		user.setUserId(userSchema.getNextId());
		
		userSchema.save(user);
		
		reset = userService.resetPassword("Saito@gmail.com", userSchema, passwordResetTokenSchema);
	
		assertTrue(reset == false);
		
	}
	
	@Test
	public void changePasswordWithNotMatchedPasswordResetTokenStringShouldReturnFalse() {
		
		boolean reset = false;
			
		UserService userService = new UserService();                                                        
		
		UserSchema userSchema = new UserSchema();
		
		User user = new User();
		user.setUseremail("Saito@gmail.com");
		user.setPassword("Saito");
		user.setUserId(userSchema.getNextId());
		
		userSchema.save(user);
		
		PasswordResetToken passwordResetToken = new PasswordResetToken();
		passwordResetToken.setUserId(user.getUserId());
		passwordResetToken.setTokenString("123456789");
		passwordResetToken.setExpirationDate(LocalDateTime.now().plusMinutes(999999));
		
		passwordResetTokenSchema.save(passwordResetToken);
		
		reset 
		= userService.changePassword(
				user.getUserId(), 
				"abcdefghi", 
				userSchema, 
				passwordResetTokenSchema);
	
		assertTrue(reset == false);
		
	}
	
	@Test
	public void changePasswordWithExpiredPasswordResetTokenShouldReturnFalse() {
		
		boolean reset = false;
			
		UserService userService = new UserService();                                                        
		
		UserSchema userSchema = new UserSchema();
		
		User user = new User();
		user.setUseremail("Saito@gmail.com");
		user.setPassword("Saito");
		user.setUserId(userSchema.getNextId());
		
		userSchema.save(user);
		
		PasswordResetToken passwordResetToken = new PasswordResetToken();
		passwordResetToken.setUserId(user.getUserId());
		passwordResetToken.setTokenString("123456789");
		passwordResetToken.setExpirationDate(LocalDateTime.now().minusMinutes(10));
		
		passwordResetTokenSchema.save(passwordResetToken);
		
		reset 
		= userService.changePassword(
				user.getUserId(), 
				"123456789", 
				userSchema, 
				passwordResetTokenSchema);
	
		assertTrue(reset == false);
		
	}
	
}

package com.example.demo.user;

import static org.junit.Assert.assertTrue;

import java.time.LocalDateTime;
import java.util.UUID;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import com.example.demo.config.CustomMongoDbConfig;
import com.example.demo.domain.entity.ActivateToken;
import com.example.demo.domain.entity.PasswordResetToken;
import com.example.demo.domain.entity.User;
import com.example.demo.domain.iservice.IImageComparer;
import com.example.demo.domain.iservice.service.UserService;
import com.example.demo.domain.service.aws.AWSImageComparer;
import com.example.demo.domain.value.RegisterDetail;
import com.example.demo.iInfrastructure.IActivateTokenSchema;
import com.example.demo.iInfrastructure.ILoginTokenSchema;
import com.example.demo.iInfrastructure.IPasswordResetTokenSchema;
import com.example.demo.iInfrastructure.IUserSchema;
import com.example.demo.iInfrastructure.infrastructure.ActivateTokenSchema;
import com.example.demo.iInfrastructure.infrastructure.LoginTokenSchema;
import com.example.demo.iInfrastructure.infrastructure.PasswordResetTokenSchema;
import com.example.demo.iInfrastructure.infrastructure.UserSchema;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {
	
	private IUserSchema userSchema = new UserSchema(CustomMongoDbConfig.mongoTemplate());
	private IActivateTokenSchema activateTokenSchema = new ActivateTokenSchema(CustomMongoDbConfig.mongoTemplate());
	private ILoginTokenSchema loginTokenSchema = new LoginTokenSchema(CustomMongoDbConfig.mongoTemplate());
	private IPasswordResetTokenSchema passwordResetTokenSchema = new PasswordResetTokenSchema(CustomMongoDbConfig.mongoTemplate());
	
	
	//-------------------Before each test clear all schema-----------------------------------
	@Before
	public void setup() {
		
		userSchema.deleteAll();
		activateTokenSchema.deleteAll();
		loginTokenSchema.deleteAll();
		passwordResetTokenSchema.deleteAll();
		
	}
	
	@After
	public void cleanup() {
		
		userSchema.deleteAll();
		activateTokenSchema.deleteAll();
		loginTokenSchema.deleteAll();
		passwordResetTokenSchema.deleteAll();
		
	}
	
	//--------------------first && second login unit test----------------------------------
	
	//Still need isActivated function
	@Test
	public void FirstLoginWithRegisteredAndActivatedUserShouldReturnTrue() {
		
		boolean firstLogined = false;
		
		UserService userService = new UserService();                                                        
		
		User user = new User();
		user.setUserId(userSchema.getNextId());
		user.setUseremail("abc123@gmail.com");
		user.setPassword("abc123");
		
		user.setActivated(true);
		user.setCreatedAt(LocalDateTime.now());
		user.setUpdatedAt(LocalDateTime.now());
		
		userSchema.save(user);
		
		firstLogined = userService.firstLogin("abc123@gmail.com", "abc123", userSchema);
		
		assertTrue(firstLogined == true);
		
	}
	
	//Still need isActivated function
	@Test
	public void FirstLoginWithRegisteredAndUnactivatedUserShouldReturnFalse() {
		
		boolean firstLogined = false;
		
		UserService userService = new UserService();                                                        
		
		User user = new User();
		user.setUseremail("abc123@gmail.com");
		user.setPassword("abc123");
		user.setUserId(userSchema.getNextId());
		
		user.setActivated(false);
		user.setCreatedAt(LocalDateTime.now());
		user.setUpdatedAt(LocalDateTime.now());
		
		userSchema.save(user);
		
		firstLogined = userService.firstLogin("abc123@gmail.com", "abc123", userSchema);
		
		assertTrue(firstLogined == false);
		
	}
	
	@Test
	public void FrstLoginWithUnregisteredUserShouldReturnFalse(){
		
		boolean firstLogined = false;
		
		UserService userService = new UserService();                                                        
		
		firstLogined = userService.firstLogin("cde456@gmail.com", "abc123", userSchema);
		
		assertTrue(firstLogined == false);
		
	}
	
	//--------------------register && activate unit test------------------------------------
	
	@Test
	public void RegisterWithExistingUserThatIsActivatedShouldReturnNotSuccess() {
		
		RegisterDetail registerDetail = new RegisterDetail();
		
		UserService userService = new UserService();
		
		User user = new User();
		user.setUserId(userSchema.getNextId());
		user.setUseremail("andrew@gmail.com");
		user.setPassword("andrew");
		
		user.setActivated(true);
		user.setCreatedAt(LocalDateTime.now());
		user.setUpdatedAt(LocalDateTime.now());
		
		userSchema.save(user);
		
		registerDetail = userService.register("andrew@gmail.com", userSchema, activateTokenSchema);
		
		assertTrue(!registerDetail.isRegisterSuccess());
		
	}
	
	@Test
	public void RegisterWithExistingUserThatIsNotActivateShouldReturnSuccessWhenActivateTokenIsExpired(){
		
		RegisterDetail registerDetail = new RegisterDetail();
		
		UserService userService = new UserService();

		User user = new User();
		user.setUserId(userSchema.getNextId());
		user.setUseremail("andrew@gmail.com");
		user.setPassword("andrew");
		
		user.setActivated(false);
		user.setCreatedAt(LocalDateTime.now());
		user.setUpdatedAt(LocalDateTime.now());
		
		userSchema.save(user);
		
		ActivateToken activateToken = new ActivateToken();
		activateToken.setUserId(user.getUserId());
		activateToken.setTokenString(UUID.randomUUID().toString());
		activateToken.setExpirationDate(LocalDateTime.now().minusMinutes(30));
		
		activateTokenSchema.save(activateToken);
		
		registerDetail = userService.register("andrew@gmail.com", userSchema, activateTokenSchema);
		
		assertTrue(registerDetail.isRegisterSuccess());	
	}
	
	@Test
	public void RegisterWithExistingUserThatIsNotActivateShouldReturnRegisteredBeforeWhenActivateTokenIsExpired(){
		
		RegisterDetail registerDetail = new RegisterDetail();
		
		UserService userService = new UserService();

		User user = new User();
		user.setUserId(userSchema.getNextId());
		user.setUseremail("andrew@gmail.com");
		user.setPassword("andrew");
		
		user.setActivated(false);
		user.setCreatedAt(LocalDateTime.now());
		user.setUpdatedAt(LocalDateTime.now());
		
		userSchema.save(user);
		
		ActivateToken activateToken = new ActivateToken();
		activateToken.setUserId(user.getUserId());
		activateToken.setTokenString(UUID.randomUUID().toString());
		activateToken.setExpirationDate(LocalDateTime.now().minusMinutes(30));
		
		activateTokenSchema.save(activateToken);
		
		registerDetail = userService.register("andrew@gmail.com", userSchema, activateTokenSchema);
		
		assertTrue(registerDetail.isRegisteredBefore());	
	}
	
	@Test
	public void RegisterWithNewUserShouldReturnSuccess(){
		
		RegisterDetail registerDetail = new RegisterDetail();
		
		UserService userService = new UserService();
		
		registerDetail = userService.register("andrew@gmail.com", userSchema, activateTokenSchema);
		
		assertTrue(registerDetail.isRegisterSuccess());
		
	}
	
	
	
	//---------------------AWSImageComparer unit test----------------------------------
	/*
	@Test
	public void AWSImageComparerCompareTwoImageWithSamePersonShouldReturnTrueTest() throws IOException {
		
		boolean matched = false;
		
		// you can put ur image file anywhere you want but you have to use their absolute paths
		File targetImageFile = new File("C:\\Users\\Andrew\\git\\SeniorProject\\SeniorProject-EVerification\\src\\main\\resources\\static\\oimg.jpg");
		File originalImageFile = new File("C:\\Users\\Andrew\\git\\SeniorProject\\SeniorProject-Everification\\src\\main\\resources\\static\\timg.jpg");
		
		IImageComparer imageComparer = new AWSImageComparer();
		//compare take in two File object and compare them as images
		matched = imageComparer.compare(targetImageFile, originalImageFile);
		
		assertTrue(matched == true);
		
	}
	*/
	//--------------------reset password && change password unit testing-----------------------
	@Test
	public void resetPasswordWithExistingUserThatIsActivatedShouldReturnTrue() {
		
		boolean reset = false;
		
		UserService userService = new UserService();                                                        
			
		User user = new User();
		user.setUserId(userSchema.getNextId());
		user.setUseremail("Saito@gmail.com");
		user.setPassword("Saito");
		
		user.setActivated(true);
		user.setCreatedAt(LocalDateTime.now());
		user.setUpdatedAt(LocalDateTime.now());
		
		userSchema.save(user);
		
		PasswordResetToken passwordResetToken = new PasswordResetToken();
		passwordResetToken.setUserId(user.getUserId());
		passwordResetToken.setExpirationDate(LocalDateTime.now().plusMinutes(30));
		passwordResetToken.setTokenString(UUID.randomUUID().toString());
		
		passwordResetTokenSchema.save(passwordResetToken);
		
		reset = userService.resetPassword("Saito@gmail.com", userSchema, passwordResetTokenSchema);
	
		assertTrue(reset == true);
		
	}
	
	@Test
	public void resetPasswordWithExistingUserThatIsNotActivatedShouldReturnFalse() {
		
		boolean reset = false;
		
		UserService userService = new UserService();                                                        
			
		User user = new User();
		user.setUserId(userSchema.getNextId());
		user.setUseremail("Saito@gmail.com");
		user.setPassword("Saito");
		
		user.setActivated(false);
		user.setCreatedAt(LocalDateTime.now());
		user.setUpdatedAt(LocalDateTime.now());
		
		userSchema.save(user);
		
		PasswordResetToken passwordResetToken = new PasswordResetToken();
		passwordResetToken.setUserId(user.getUserId());
		passwordResetToken.setExpirationDate(LocalDateTime.now().plusMinutes(30));
		passwordResetToken.setTokenString(UUID.randomUUID().toString());
		
		passwordResetTokenSchema.save(passwordResetToken);
		
		reset = userService.resetPassword("Saito@gmail.com", userSchema, passwordResetTokenSchema);
	
		assertTrue(reset == false);
		
	}
	
	@Test
	public void resetPasswordWithNotExistingUserShouldReturnFalse() {
		
		boolean reset = false;
		
		UserService userService = new UserService();                                                        
		
		reset = userService.resetPassword("Saito@gmail.com", userSchema, passwordResetTokenSchema);
	
		assertTrue(reset == false);
		
	}
	
	@Test
	public void changePasswordWithUnmatchedPasswordResetTokenStringShouldReturnFalse() {
		
		boolean changed = false;
			
		UserService userService = new UserService();                                                        
		
		User user = new User();
		user.setUseremail("Saito@gmail.com");
		user.setPassword("Saito");
		user.setUserId(userSchema.getNextId());
		
		user.setActivated(true);
		user.setCreatedAt(LocalDateTime.now());
		user.setUpdatedAt(LocalDateTime.now());
		
		userSchema.save(user);
		
		String token = UUID.randomUUID().toString();
		
		PasswordResetToken passwordResetToken = new PasswordResetToken();
		passwordResetToken.setUserId(user.getUserId());
		passwordResetToken.setTokenString(token);
		passwordResetToken.setExpirationDate(LocalDateTime.now().plusMinutes(30));
		
		passwordResetTokenSchema.save(passwordResetToken);
		
		changed 
		= userService.changePassword(
				user.getUserId(), token + "1234", 
				passwordResetTokenSchema);
	
		assertTrue(changed == false);
		
	}
	
	@Test
	public void changePasswordWithExpiredPasswordResetTokenShouldReturnFalse() {
		
		boolean changed = false;
			
		UserService userService = new UserService();                                                        
		
		User user = new User();
		user.setUseremail("Saito@gmail.com");
		user.setPassword("Saito");
		user.setUserId(userSchema.getNextId());
		
		user.setActivated(true);
		user.setCreatedAt(LocalDateTime.now());
		user.setUpdatedAt(LocalDateTime.now());
		
		userSchema.save(user);
		
		String token = UUID.randomUUID().toString();
		
		PasswordResetToken passwordResetToken = new PasswordResetToken();
		passwordResetToken.setUserId(user.getUserId());
		passwordResetToken.setTokenString(token);
		passwordResetToken.setExpirationDate(LocalDateTime.now().minusMinutes(30));
		
		passwordResetTokenSchema.save(passwordResetToken);
		
		changed 
		= userService.changePassword(
				user.getUserId(), token, 
				passwordResetTokenSchema);
	
		assertTrue(changed == false);
		
	}
	
}

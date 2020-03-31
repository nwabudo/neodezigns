package ng.com.neodezigns.app.ws.service.impl;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import ng.com.neodezigns.app.ws.exceptions.UserServiceException;
import ng.com.neodezigns.app.ws.io.entity.UserEntity;
import ng.com.neodezigns.app.ws.io.repositories.UserRepository;
import ng.com.neodezigns.app.ws.shared.dto.UserDTO;

class UserServiceImplTest {

	@InjectMocks
	UserServiceImpl userService;

	@Mock
	UserRepository userRepo;

	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	void testGetUserByUserName() {
		UserEntity userEntity = new UserEntity();
		userEntity.setEncryptedPassword("sftrhghauysj");
		userEntity.setFirstName("Emmanuel");
		userEntity.setId(1L);
		userEntity.setUserId("Yhtr576sag");
		userEntity.setLastName("Nwabudo");

		when(userRepo.findByUserName(anyString())).thenReturn(userEntity);

		UserDTO userDTO = userService.getUserByUserName("emmaco");
		Assertions.assertNotNull(userDTO);
		Assertions.assertEquals("Emmanuel", userDTO.getFirstName());
	}

	@Test
	final void testGetUserByUserName_UserNameNotFoundException() {
		when(userRepo.findByUserName(anyString())).thenReturn(null);
				
		Assertions.assertThrows(UserServiceException.class,
				()-> {
					userService.getUserByUserName("emmaco");
				});
	}

}

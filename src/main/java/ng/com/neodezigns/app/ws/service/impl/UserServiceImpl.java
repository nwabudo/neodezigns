package ng.com.neodezigns.app.ws.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintViolationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import ng.com.neodezigns.app.ws.custom.CustomMethods;
import ng.com.neodezigns.app.ws.exceptions.UserServiceException;
import ng.com.neodezigns.app.ws.io.entity.UserEntity;
import ng.com.neodezigns.app.ws.io.repositories.UserRepository;
import ng.com.neodezigns.app.ws.service.UserService;
import ng.com.neodezigns.app.ws.shared.Utils;
import ng.com.neodezigns.app.ws.shared.dto.UserDTO;
import ng.com.neodezigns.app.ws.ui.models.response.ErrorMessages;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserRepository userRepo;

	@Autowired
	Utils utils;

	@Autowired
	CustomMethods customMethods;

	@Autowired
	BCryptPasswordEncoder bCryptPassword;

	private static Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

	public List<UserDTO> getAllUsers() {
		List<UserEntity> users = (List<UserEntity>) userRepo.findAll();
		List<UserDTO> returnvalue = new ArrayList<>();
		for (UserEntity source : users) {
			UserDTO target = new UserDTO();
			BeanUtils.copyProperties(source, target);
			returnvalue.add(target);
		}
		return returnvalue;
	}

	public UserDTO createNewUser(UserDTO userDTO) {

		try {
			UserEntity user = new UserEntity();
			BeanUtils.copyProperties(userDTO, user);

			String publicUserId = utils.generateUserId(15);

			user.setEncryptedPassword(bCryptPassword.encode(userDTO.getPassword()));
			user.setUserId(publicUserId);
			user.setEmailVerificationStatus(true);
			String userName = customMethods.generateUserName(user);
			if (userRepo.findByUserName(userName) != null) {
				userName += user.getFirstName().substring(0, 1).toUpperCase();
			}

			user.setUserName(userName);
			if (userRepo.findByEmail(user.getEmail()) != null)
				throw new UserServiceException(ErrorMessages.RECORD_ALREADY_EXISTS + " for: " + user.getEmail());

			UserEntity storedUserDetails = userRepo.save(user);
			UserDTO returnValue = new UserDTO();
			BeanUtils.copyProperties(storedUserDetails, returnValue);
			return returnValue;
		} catch (ConstraintViolationException ex) {
			log.info("Error is: " + ex.getMessage() + "/ " + ex.getConstraintViolations());
			throw new RuntimeException("Email is not valid");
		}
	}

	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
		UserDetails userDetails = null;
		UserEntity userEntity = userRepo.findByUserName(userName);
		if (userEntity == null)
			throw new UserServiceException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage() + " for: " + userName);

		userDetails = new User(userEntity.getUserName(), userEntity.getEncryptedPassword(), new ArrayList<>());
		log.info(userDetails.toString() + "[" + userEntity.getUserName() + userEntity.getEncryptedPassword() + "]");
		return userDetails;
	}

	public UserDTO getUserByUserName(String userName) {
		UserEntity userEntity = userRepo.findByUserName(userName);
		if (userEntity == null)
			throw new UsernameNotFoundException(userName);
		UserDTO returnvalue = new UserDTO();
		BeanUtils.copyProperties(userEntity, returnvalue);
		return returnvalue;
	}

	public UserDTO getUserByUserID(String userId) {
		UserEntity userEntity = userRepo.findByUserId(userId);
		if (userEntity == null)
			throw new UsernameNotFoundException(userId + " not Found");
		UserDTO returnvalue = new UserDTO();
		BeanUtils.copyProperties(userEntity, returnvalue);
		return returnvalue;
	}

	public UserDTO updateUser(UserDTO userDTO, String userId) {
		UserDTO returnvalue = new UserDTO();
		UserEntity userEntity = userRepo.findByUserId(userId);
		if (userEntity == null)
			throw new UserServiceException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage() + " for: " + userId);
		userEntity.setFirstName(userDTO.getFirstName());
		userEntity.setLastName(userDTO.getLastName());
		UserEntity updatedUser = userRepo.save(userEntity);
		BeanUtils.copyProperties(updatedUser, returnvalue);
		return returnvalue;
	}

	@Override
	public void deleteUser(String userId) {
		UserEntity userEntity = userRepo.findByUserId(userId);
		if (userEntity == null)
			throw new UserServiceException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage() + " for: " + userId);
		userRepo.delete(userEntity);
	}
}

package ng.com.neodezigns.app.ws.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintViolationException;

import org.modelmapper.ModelMapper;
import org.modelmapper.config.Configuration.AccessLevel;
import org.modelmapper.convention.MatchingStrategies;
import org.modelmapper.convention.NamingConventions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import ng.com.neodezigns.app.ws.custom.CustomMethods;
import ng.com.neodezigns.app.ws.exceptions.UserServiceException;
import ng.com.neodezigns.app.ws.io.entity.UserEntity;
import ng.com.neodezigns.app.ws.io.repositories.UserRepository;
import ng.com.neodezigns.app.ws.service.IUserService;
import ng.com.neodezigns.app.ws.shared.Utils;
import ng.com.neodezigns.app.ws.shared.dto.AddressDTO;
import ng.com.neodezigns.app.ws.shared.dto.UserDTO;
import ng.com.neodezigns.app.ws.ui.models.response.ErrorMessages;

@Service
public class UserServiceImpl implements IUserService {

	@Autowired
	UserRepository userRepo;

	@Autowired
	Utils utils;

	@Autowired
	CustomMethods customMethods;

	@Autowired
	BCryptPasswordEncoder bCryptPassword;

	@Autowired
	ModelMapper modelMapper;

	private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

	/*
	 * public List<UserDTO> getAllUsers() { List<UserEntity> users =
	 * (List<UserEntity>) userRepo.findAll(); List<UserDTO> returnvalue = new
	 * ArrayList<>();
	 * modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT)
	 * .setFieldMatchingEnabled(true) .setFieldAccessLevel(AccessLevel.PRIVATE)
	 * .setSourceNamingConvention(NamingConventions.JAVABEANS_MUTATOR); for
	 * (UserEntity source : users) { returnvalue.add(modelMapper.map(source,
	 * UserDTO.class)); } return returnvalue; }
	 */

	public List<UserDTO> getUsers(int page, int limit) {
		List<UserDTO> returnvalue = new ArrayList<>();
		if (page > 0)
			page -= 1;
		Pageable pageableRequest = PageRequest.of(page, limit);
		Page<UserEntity> usersPage = userRepo.findAll(pageableRequest);
		List<UserEntity> users = usersPage.getContent();
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT).setFieldMatchingEnabled(true)
				.setFieldAccessLevel(AccessLevel.PRIVATE)
				.setSourceNamingConvention(NamingConventions.JAVABEANS_MUTATOR);

		for (UserEntity source : users) {
			returnvalue.add(modelMapper.map(source, UserDTO.class));
		}
		return returnvalue;
	}

	public UserDTO createNewUser(UserDTO userDTO) {
		try {
			if (userRepo.findByEmail(userDTO.getEmail()) != null)
				throw new UserServiceException(ErrorMessages.RECORD_ALREADY_EXISTS + " for: " + userDTO.getEmail());

			for (int i = 0; i < userDTO.getAddresses().size(); i++) {
				AddressDTO address = userDTO.getAddresses().get(i);
				address.setUserDetails(userDTO);
				address.setAddressId(utils.generateId(10));
				userDTO.getAddresses().set(i, address);
			}
			modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
			UserEntity user = modelMapper.map(userDTO, UserEntity.class);
			String publicUserId = utils.generateId(15);
			user.setEncryptedPassword(bCryptPassword.encode(userDTO.getPassword()));
			user.setUserId(publicUserId);
			user.setEmailVerificationStatus(true);

			String userName = customMethods.generateUserName(user);
			if (userRepo.findByUserName(userName) != null) {
				userName += user.getFirstName().substring(0, 1).toUpperCase();
			}
			user.setUserName(userName);

			UserEntity storedUserDetails = userRepo.save(user);
			UserDTO returnValue = modelMapper.map(storedUserDetails, UserDTO.class);
			return returnValue;
		} catch (ConstraintViolationException ex) {
			log.info("Error is: " + ex.getMessage() + "/ " + ex.getConstraintViolations());
			throw new UserServiceException(
					"Ensure the email address used are in lowercase such as this -> 'email@neodezign.com'"
					);
		}
	}

	public void updateUser(UserDTO userDTO, String userId) {
		//UserDTO returnvalue = new UserDTO();
		UserEntity userEntity = userRepo.findByUserId(userId);
		// modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		if (userEntity == null)
			throw new UserServiceException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage() + " for: " + userId);
		userEntity.setFirstName(userDTO.getFirstName());
		userEntity.setLastName(userDTO.getLastName());
		userRepo.save(userEntity);
		/*
		 * returnvalue = modelMapper.map(updatedUser, UserDTO.class); return
		 * returnvalue;
		 */
	}
	
	public void updateUserName(UserDTO userDTO, String userId) {
		UserEntity userEntity = userRepo.findByUserId(userId);
		if (userEntity == null)
			throw new UserServiceException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage() + " for: " + userId);
		userEntity.setUserName(userDTO.getUserName());
		userRepo.save(userEntity);
	}

	public void updateUserEmail(UserDTO userDTO, String userId) {
		UserEntity userEntity = userRepo.findByUserId(userId);
		if (userEntity == null)
			throw new UserServiceException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage() + " for: " + userId);
		userEntity.setUserName(userEntity.getUserName());
		userEntity.setEmail(userDTO.getEmail());
		userEntity.setOtherEmail(userDTO.getOtherEmail());
		userRepo.save(userEntity);
	}

	public void deleteUser(String userId) {
		UserEntity userEntity = userRepo.findByUserId(userId);
		if (userEntity == null)
			throw new UserServiceException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage() + " for: " + userId);
		userRepo.delete(userEntity);
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
			throw new UserServiceException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage() + " for: " + userName);

		UserDTO returnvalue = new UserDTO();
		BeanUtils.copyProperties(userEntity, returnvalue);
		// returnvalue = modelMapper.map(userEntity, UserDTO.class);
		return returnvalue;
	}

	public UserDTO getUserByUserID(String userId) {
		UserEntity userEntity = userRepo.findByUserId(userId);
		if (userEntity == null)
			throw new UserServiceException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage() + " for: " + userId);
		UserDTO returnvalue = new UserDTO();
		// BeanUtils.copyProperties(userEntity, returnvalue);
		returnvalue = modelMapper.map(userEntity, UserDTO.class);
		return returnvalue;
	}

}

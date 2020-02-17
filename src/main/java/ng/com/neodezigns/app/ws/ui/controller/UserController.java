package ng.com.neodezigns.app.ws.ui.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import ng.com.neodezigns.app.ws.exceptions.UserServiceException;
import ng.com.neodezigns.app.ws.service.UserService;
import ng.com.neodezigns.app.ws.shared.dto.UserDTO;
import ng.com.neodezigns.app.ws.ui.models.request.RequestOperationName;
import ng.com.neodezigns.app.ws.ui.models.request.UserRequestDetails;
import ng.com.neodezigns.app.ws.ui.models.response.ErrorMessages;
import ng.com.neodezigns.app.ws.ui.models.response.RequestOperationStatus;
import ng.com.neodezigns.app.ws.ui.models.response.ResponseStatus;
import ng.com.neodezigns.app.ws.ui.models.response.UserRest;

@RestController
@RequestMapping("users") // http://localhost:8080/users
public class UserController {
	@Autowired
	private UserService userService;

	@RequestMapping(value = "", method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE })
	public List<UserRest> getAllUsers() {
		List<UserRest> userRest = new ArrayList<>();
		List<UserDTO> userDTOs = new ArrayList<>();
		userDTOs = userService.getAllUsers();
		for (UserDTO source : userDTOs) {
			UserRest target = new UserRest();
			BeanUtils.copyProperties(source, target);
			target.setDate(source.getCreatedAt());
			userRest.add(target);
		}
		return userRest;
	}

	@RequestMapping(value = "/{userId}", method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE })
	public UserRest getUserByUserId(@PathVariable String userId) {
		UserRest userRest = new UserRest();
		UserDTO userDTO = new UserDTO();
		userDTO = userService.getUserByUserID(userId);
		BeanUtils.copyProperties(userDTO, userRest);
		userRest.setDate(userDTO.getCreatedAt());
		return userRest;
	}

	@RequestMapping(value = "", method = RequestMethod.POST, consumes = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE,
					MediaType.APPLICATION_XML_VALUE })
	public UserRest createUser(@RequestBody UserRequestDetails userRequest) throws Exception {
		UserRest user = new UserRest();
		if (userRequest.getFirstName().isEmpty() || userRequest.getEmail().isEmpty()
				|| userRequest.getPassword().isEmpty())
			throw new UserServiceException(ErrorMessages.MISSING_REQUIRED_FIELD.getErrorMessage());

		UserDTO userDTO = new UserDTO();
		BeanUtils.copyProperties(userRequest, userDTO);
		UserDTO createdUser = userService.createNewUser(userDTO);
		BeanUtils.copyProperties(createdUser, user);
		user.setDate(createdUser.getCreatedAt());
		return user;
	}

	@RequestMapping(value = "/{userId}", method = RequestMethod.PUT, consumes = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE,
					MediaType.APPLICATION_XML_VALUE })
	public UserRest updateUser(@RequestBody UserRequestDetails userRequest, @PathVariable String userId) {
		UserRest user = new UserRest();

		UserDTO userDTO = new UserDTO();
		BeanUtils.copyProperties(userRequest, userDTO);
		UserDTO createdUser = userService.updateUser(userDTO, userId);
		BeanUtils.copyProperties(createdUser, user);
		user.setDate(createdUser.getCreatedAt());
		return user;
	}

	@RequestMapping(value = "/{userId}", method = RequestMethod.DELETE, produces = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE })
	public ResponseStatus deleteUser(@PathVariable String userId) {
		ResponseStatus returnValue = new ResponseStatus();
		returnValue.setOperationName(RequestOperationName.DELETE.name());
		userService.deleteUser(userId);
		returnValue.setOperationResult(RequestOperationStatus.SUCCESS.name());
		return returnValue;
	}

}

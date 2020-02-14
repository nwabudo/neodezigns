package ng.com.neodezigns.app.ws.ui.controller;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import ng.com.neodezigns.app.ws.custom.CustomMethods;
import ng.com.neodezigns.app.ws.service.UserService;
import ng.com.neodezigns.app.ws.shared.dto.UserDTO;
import ng.com.neodezigns.app.ws.ui.models.request.UserRequestDetails;
import ng.com.neodezigns.app.ws.ui.models.response.UserRest;

@RestController
@RequestMapping("users") // http://localhost:8080/users
public class UserController {

	@Autowired
	private UserService userService;

	@RequestMapping(value = "/{userId}", method = RequestMethod.GET,
			produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE }) 
	public UserRest getUserByUserId(@PathVariable String userId) {
		UserRest userRest = new UserRest();
		UserDTO userDTO = new UserDTO();
		userDTO = userService.getUserByUserID(userId);
		BeanUtils.copyProperties(userDTO, userRest);
		userRest.setDate(CustomMethods.parseDateToArray(userDTO.getCreatedAt()));
		return userRest;
	}

	@RequestMapping(value = "", method = RequestMethod.POST, 
			consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE },
			produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public UserRest createUser(@RequestBody UserRequestDetails userRequest) {
		UserRest user = new UserRest();
		UserDTO userDTO = new UserDTO();
		BeanUtils.copyProperties(userRequest, userDTO);
		UserDTO createdUser = userService.createNewUser(userDTO);
		BeanUtils.copyProperties(createdUser, user);
																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																		user.setDate(CustomMethods.parseDateToArray(createdUser.getCreatedAt()));
		return user;
	}

	@RequestMapping(value = "", method = RequestMethod.PUT)
	public String updateUser() {
		return "update User was Called";
	}

	@RequestMapping(value = "", method = RequestMethod.DELETE)
	public String deleteUser() {
		return "delete User was Called";
	}

}

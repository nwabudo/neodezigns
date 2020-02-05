package ng.com.neodezigns.app.ws.ui.controller;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
	
	@RequestMapping(value = "", method = RequestMethod.GET)
	public String getUser() {
		return "get User was Called";
	}

	@RequestMapping(value = "", method = RequestMethod.POST)
	public UserRest createUser(@RequestBody UserRequestDetails userRequest) {
		UserRest user = new UserRest();
		
		UserDTO userDTO = new UserDTO();
		BeanUtils.copyProperties(userRequest, userDTO);
		
		UserDTO createdUser = userService.createdUser(userDTO);
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

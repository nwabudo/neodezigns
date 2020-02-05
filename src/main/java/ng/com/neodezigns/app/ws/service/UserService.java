package ng.com.neodezigns.app.ws.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import ng.com.neodezigns.app.ws.shared.dto.UserDTO;

public interface UserService extends UserDetailsService {

	UserDTO createdUser(UserDTO userDTO);
	
}

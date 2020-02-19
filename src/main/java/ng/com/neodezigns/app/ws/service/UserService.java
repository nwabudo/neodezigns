package ng.com.neodezigns.app.ws.service;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetailsService;

import ng.com.neodezigns.app.ws.shared.dto.UserDTO;

public interface UserService extends UserDetailsService {
	UserDTO createNewUser(UserDTO userDTO);
	UserDTO getUserByUserName(String userName);
	UserDTO getUserByUserID(String userId);
	List<UserDTO> getAllUsers();
	UserDTO updateUser(UserDTO userDTO, String userId);
	void deleteUser(String userId);
	List<UserDTO> getUsers(int page, int limit);
}

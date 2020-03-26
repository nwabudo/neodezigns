package ng.com.neodezigns.app.ws.ui.controller;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ng.com.neodezigns.app.ws.exceptions.UserServiceException;
import ng.com.neodezigns.app.ws.service.AddressService;
import ng.com.neodezigns.app.ws.service.IUserService;
import ng.com.neodezigns.app.ws.shared.dto.AddressDTO;
import ng.com.neodezigns.app.ws.shared.dto.UserDTO;
import ng.com.neodezigns.app.ws.ui.models.request.RequestOperationName;
import ng.com.neodezigns.app.ws.ui.models.request.UpdateEmailUsed;
import ng.com.neodezigns.app.ws.ui.models.request.UpdateUserName;
import ng.com.neodezigns.app.ws.ui.models.request.UserRequestDetails;
import ng.com.neodezigns.app.ws.ui.models.response.AddressResponse;
import ng.com.neodezigns.app.ws.ui.models.response.ErrorMessages;
import ng.com.neodezigns.app.ws.ui.models.response.RequestOperationStatus;
import ng.com.neodezigns.app.ws.ui.models.response.ResponseStatus;
import ng.com.neodezigns.app.ws.ui.models.response.UserRest;

@RestController
@RequestMapping("/users")
public class UserController {
	@Autowired
	private IUserService userService;

	@Autowired
	AddressService addressService;

	@Autowired
	ModelMapper modelMapper;

	/*
	 * @RequestMapping(value = "", method = RequestMethod.GET, produces = {
	 * MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE }) public
	 * List<UserRest> getAllUsers() { List<UserRest> userRest = new ArrayList<>();
	 * List<UserDTO> userDTOs = new ArrayList<>(); userDTOs =
	 * userService.getAllUsers(); for (UserDTO source : userDTOs) { UserRest target
	 * = modelMapper.map(source, UserRest.class);
	 * target.setDate(source.getCreatedAt()); userRest.add(target); } return
	 * userRest; }
	 */

	@RequestMapping(value = "", method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE })
	public List<UserRest> getUsers(@RequestParam(value = "page", defaultValue = "0") int page,
			@RequestParam(value = "limit", defaultValue = "25") int limit) {
		List<UserRest> returnvalue = new ArrayList<>();
		List<UserDTO> userDTO = userService.getUsers(page, limit);
		for (UserDTO source : userDTO) {
			UserRest target = modelMapper.map(source, UserRest.class);
			target.setDate(source.getCreatedAt());
			returnvalue.add(target);
		}
		return returnvalue;
	}

	@RequestMapping(value = "/{userId}", method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE })
	public UserRest getUserByUserId(@PathVariable String userId) {
		UserRest userRest = new UserRest();
		UserDTO userDTO = userService.getUserByUserID(userId);
		userRest = modelMapper.map(userDTO, UserRest.class);
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
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		UserDTO userDTO = modelMapper.map(userRequest, UserDTO.class);
		UserDTO createdUser = userService.createNewUser(userDTO);
		user = modelMapper.map(createdUser, UserRest.class);
		user.setDate(createdUser.getCreatedAt());
		return user;
	}

	@RequestMapping(value = "/{userId}", method = RequestMethod.PUT, consumes = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE,
					MediaType.APPLICATION_XML_VALUE })
	public ResponseStatus updateUser(@RequestBody UserRequestDetails userRequest, @PathVariable String userId) {
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		UserDTO userDTO = modelMapper.map(userRequest, UserDTO.class);
		ResponseStatus returnValue = new ResponseStatus();
		returnValue.setOperationName(RequestOperationName.UPDATE.name());
		userService.updateUser(userDTO, userId);
		returnValue.setOperationResult(RequestOperationStatus.SUCCESS.name());
		return returnValue;
	}

	@RequestMapping(value = "/changeUserName/{userId}", method = RequestMethod.PUT, consumes = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE,
					MediaType.APPLICATION_XML_VALUE })
	public ResponseStatus updateUserName(@RequestBody UpdateUserName userRequest, @PathVariable String userId) {
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		UserDTO userDTO = modelMapper.map(userRequest, UserDTO.class);
		ResponseStatus returnValue = new ResponseStatus();
		returnValue.setOperationName(RequestOperationName.UPDATE.name());
		userService.updateUserName(userDTO, userId);
		returnValue.setOperationResult(RequestOperationStatus.SUCCESS.name());
		return returnValue;
	}
	
	@RequestMapping(value = "/changeUserEmail/{userId}", method = RequestMethod.PUT, consumes = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE,
					MediaType.APPLICATION_XML_VALUE })
	public ResponseStatus updateUserEmail(@RequestBody UpdateEmailUsed userRequest, @PathVariable String userId) {
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		UserDTO userDTO = modelMapper.map(userRequest, UserDTO.class);
		ResponseStatus returnValue = new ResponseStatus();
		returnValue.setOperationName(RequestOperationName.UPDATE.name());
		userService.updateUserEmail(userDTO, userId);
		returnValue.setOperationResult(RequestOperationStatus.SUCCESS.name());
		return returnValue;
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

	@RequestMapping(value = "/{userId}/addresses", method = RequestMethod.GET, produces = {
			MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, "application/hal+json" })
	public CollectionModel<AddressResponse> getUsersAddresses(@PathVariable("userId") String userId) {
		List<AddressResponse> addresses = new ArrayList<>();
		List<AddressDTO> addresDTO = addressService.getAddresses(userId);
		if (addresDTO != null && !addresDTO.isEmpty()) {
			Type listToken = new TypeToken<List<AddressResponse>>() {
			}.getType();
			addresses = new ModelMapper().map(addresDTO, listToken);
			for (AddressResponse address : addresses) {
				Link addressLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UserController.class)
						.getUsersAddress(userId, address.getAddressId())).withSelfRel();
				Link userLink = WebMvcLinkBuilder.linkTo(UserController.class).slash(userId).withRel("User");
				Link usersAddresses = WebMvcLinkBuilder
						.linkTo(WebMvcLinkBuilder.methodOn(UserController.class).getUsersAddresses(userId))
						.withRel("Addresses");

				address.add(addressLink);
				address.add(userLink);
				address.add(usersAddresses);
			}
		}
		return new CollectionModel<>(addresses);
	}

	@RequestMapping(value = "/{userId}/addresses/{addressId}", method = RequestMethod.GET, produces = {
			MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, "application/hal+json" })
	public EntityModel<AddressResponse> getUsersAddress(@PathVariable("userId") String userId,
			@PathVariable("addressId") String addressId) {
		AddressResponse address = new AddressResponse();
		AddressDTO addresDTO = addressService.getAddress(userId, addressId);
		if (addresDTO == null)
			return new EntityModel<>(address);
		address = modelMapper.map(addresDTO, AddressResponse.class);
		Link addressLink = WebMvcLinkBuilder
				.linkTo(WebMvcLinkBuilder.methodOn(UserController.class).getUsersAddress(userId, addressId))
				.withSelfRel();
		Link userLink = WebMvcLinkBuilder.linkTo(UserController.class).slash(userId).withRel("User");
		Link usersAddresses = WebMvcLinkBuilder
				.linkTo(WebMvcLinkBuilder.methodOn(UserController.class).getUsersAddresses(userId))
				.withRel("Addresses");
		address.add(addressLink);
		address.add(userLink);
		address.add(usersAddresses);
		return new EntityModel<>(address);
	}
}

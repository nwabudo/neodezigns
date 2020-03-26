package ng.com.neodezigns.app.ws.service;

import java.util.List;

import ng.com.neodezigns.app.ws.shared.dto.AddressDTO;

public interface AddressService {

	List<AddressDTO> getAddresses(String userId);

	AddressDTO getAddress(String userId, String addressId);

}

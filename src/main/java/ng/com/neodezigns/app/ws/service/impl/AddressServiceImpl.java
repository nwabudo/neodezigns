package ng.com.neodezigns.app.ws.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.config.Configuration.AccessLevel;
import org.modelmapper.convention.MatchingStrategies;
import org.modelmapper.convention.NamingConventions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ng.com.neodezigns.app.ws.io.entity.AddressEntity;
import ng.com.neodezigns.app.ws.io.entity.UserEntity;
import ng.com.neodezigns.app.ws.io.repositories.AddressRepository;
import ng.com.neodezigns.app.ws.io.repositories.UserRepository;
import ng.com.neodezigns.app.ws.service.AddressService;
import ng.com.neodezigns.app.ws.shared.dto.AddressDTO;

@Service
public class AddressServiceImpl implements AddressService {

	@Autowired
	UserRepository userRepo;

	@Autowired
	AddressRepository addressRepo;

	private static final Logger LOGGER = LoggerFactory.getLogger(AddressServiceImpl.class);

	ModelMapper modelMapper = new ModelMapper();

	public List<AddressDTO> getAddresses(String userId) {
		List<AddressDTO> returnValue = new ArrayList<>();
		UserEntity userEntity = userRepo.findByUserId(userId);
		if (userEntity == null)
			return returnValue;
		Iterable<AddressEntity> addressEntityList = addressRepo.findAllByUserDetails(userEntity);
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT)
				.setFieldMatchingEnabled(true)
				.setFieldAccessLevel(AccessLevel.PRIVATE)
				.setSourceNamingConvention(NamingConventions.JAVABEANS_MUTATOR);
		for (AddressEntity source : addressEntityList) {
			returnValue.add(modelMapper.map(source, AddressDTO.class));
		}
		LOGGER.info(returnValue.toString());
		return returnValue;
	}

	public AddressDTO getAddress(String userId, String addressId) {
		AddressDTO returnValue = null;
		// modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		UserEntity userEntity = userRepo.findByUserId(userId);
		if (userEntity == null)
			return returnValue;
		AddressEntity addressEntity = addressRepo.findByAddressId(addressId);
		if (addressEntity != null) {
			returnValue = modelMapper.map(addressEntity, AddressDTO.class);
		}
		LOGGER.info(returnValue.toString());
		return returnValue;
	}

}

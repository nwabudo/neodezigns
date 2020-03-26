package ng.com.neodezigns.app.ws.io.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity(name="addresses")
public class AddressEntity implements Serializable {

	private static final long serialVersionUID = 4209374923046988553L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	
	@Column(length=30, nullable = false)
	private String addressId;
	
	@Column(length=100, nullable = false)
	private String streetName;
	
	@Column(length=15, nullable = false)
	private String city;

	@Column(length=15, nullable = false)
	private String state;

	@Column(length=15, nullable = false)
	private String country;

	@Column(length=7, nullable = false)
	private String postalCode;

	@Column(length=15, nullable = false)
	private String type;
	
	@ManyToOne
	@JoinColumn(name="users_id")
	private UserEntity userDetails;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getAddressId() {
		return addressId;
	}

	public void setAddressId(String addressId) {
		this.addressId = addressId;
	}

	public UserEntity getUserDetails() {
		return userDetails;
	}

	public void setUserDetails(UserEntity userDetails) {
		this.userDetails = userDetails;
	}

	public String getStreetName() {
		return streetName;
	}

	public void setStreetName(String streetName) {
		this.streetName = streetName;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public AddressEntity(long id, String addressId, String streetName, String city, String state, String country,
			String postalCode, String type, UserEntity userDetails) {
		this.id = id;
		this.addressId = addressId;
		this.streetName = streetName;
		this.city = city;
		this.state = state;
		this.country = country;
		this.postalCode = postalCode;
		this.type = type;
		this.userDetails = userDetails;
	}
	
	public AddressEntity() {
	}

	@Override
	public String toString() {
		return "AddressEntity [id=" + id + ", addressId=" + addressId + ", streetName=" + streetName + ", city=" + city
				+ ", state=" + state + ", country=" + country + ", postalCode=" + postalCode + ", type=" + type
				+ ", userDetails=" + userDetails + "]";
	}
}

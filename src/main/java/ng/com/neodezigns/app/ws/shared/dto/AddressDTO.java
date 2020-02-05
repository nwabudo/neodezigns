package ng.com.neodezigns.app.ws.shared.dto;

import java.io.Serializable;

public class AddressDTO implements Serializable {

	private static final long serialVersionUID = 3689817129324519130L;

	private long id;
	private String addressName;
	private String addressCity;
	private String addressLocaleState;
	private String addressCntry;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getAddressName() {
		return addressName;
	}

	public void setAddressName(String addressName) {
		this.addressName = addressName;
	}

	public String getAddressCity() {
		return addressCity;
	}

	public void setAddressCity(String addressCity) {
		this.addressCity = addressCity;
	}

	public String getAddressLocaleState() {
		return addressLocaleState;
	}

	public void setAddressLocaleState(String addressLocaleState) {
		this.addressLocaleState = addressLocaleState;
	}

	public String getAddressCntry() {
		return addressCntry;
	}

	public void setAddressCntry(String addressCntry) {
		this.addressCntry = addressCntry;
	}

}

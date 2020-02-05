package ng.com.neodezigns.app.ws.io.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class AddressEntity implements Serializable {

	private static final long serialVersionUID = 4209374923046988553L;

	@Id
	@GeneratedValue
	private long id;

	@Column(nullable = false)
	private String addressName;

	@Column(nullable = false)
	private String addressCity;

	@Column(nullable = false)
	private String addressLocaleState;

	@Column(nullable = false)
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

package ng.com.neodezigns.app.ws.io.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.Pattern;

@Entity(name = "users")
public class UserEntity implements Serializable {
	private static final long serialVersionUID = -2675537776836756234L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column(nullable = false)
	private String userId;

	@Column(nullable = false, length = 50)
	private String firstName;

	@Column(nullable = false, length = 50)
	private String lastName;

	@Pattern(regexp = "[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\." + "[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@"
			+ "(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message = "{invalid.email}")
	@Column(nullable = true, length = 80)
	private String email;

	@Pattern(regexp = "[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\." + "[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@"
			+ "(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message = "{invalid.email}")
	@Column(nullable = true, length = 80)
	private String otherEmail;

	@Column(nullable = false, length = 50)
	private String userName;

	@Column(nullable = false, length = 100)
	private String encryptedPassword;

	private String emailVerificationToken;

	@Column(nullable = false)
	private Boolean emailVerificationStatus = false;

	@Column(nullable = false)
	private Date createdAt;

	// @JsonIgnore
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "userDetails", cascade = CascadeType.ALL)
	private List<AddressEntity> address = new ArrayList<>();

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getOtherEmail() {
		return otherEmail;
	}

	public void setOtherEmail(String OtherEmail) {
		otherEmail = OtherEmail;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getEncryptedPassword() {
		return encryptedPassword;
	}

	public void setEncryptedPassword(String encryptedPassword) {
		this.encryptedPassword = encryptedPassword;
	}

	public String getEmailVerificationToken() {
		return emailVerificationToken;
	}

	public void setEmailVerificationToken(String emailVerificationToken) {
		this.emailVerificationToken = emailVerificationToken;
	}

	public Boolean getEmailVerificationStatus() {
		return emailVerificationStatus;
	}

	public void setEmailVerificationStatus(Boolean emailVerificationStatus) {
		this.emailVerificationStatus = emailVerificationStatus;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public List<AddressEntity> getAddresses() {
		return address;
	}

	public void setAddresses(List<AddressEntity> address) {
		this.address = address;
	}

	public UserEntity(long id, String userId, String firstName, String lastName,
			@Pattern(regexp = "[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message = "{invalid.email}") String email,
			@Pattern(regexp = "[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message = "{invalid.email}") String OtherEmail,
			String userName, String encryptedPassword, String emailVerificationToken, Boolean emailVerificationStatus,
			Date createdAt, List<AddressEntity> address) {
		this.id = id;
		this.userId = userId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		otherEmail = OtherEmail;
		this.userName = userName;
		this.encryptedPassword = encryptedPassword;
		this.emailVerificationToken = emailVerificationToken;
		this.emailVerificationStatus = emailVerificationStatus;
		this.createdAt = createdAt;
		this.address = address;
	}

	public UserEntity() {
	}

	@Override
	public String toString() {
		return "UserEntity [id=" + id + ", userId=" + userId + ", firstName=" + firstName + ", lastName=" + lastName
				+ ", email=" + email + ", OtherEmail=" + otherEmail + ", userName=" + userName + ", encryptedPassword="
				+ encryptedPassword + ", emailVerificationToken=" + emailVerificationToken
				+ ", emailVerificationStatus=" + emailVerificationStatus + ", createdAt=" + createdAt + ", address="
				+ address + "]";
	}
}

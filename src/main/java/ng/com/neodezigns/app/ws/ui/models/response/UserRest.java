package ng.com.neodezigns.app.ws.ui.models.response;

import java.util.ArrayList;
import java.util.List;

public class UserRest {

	private String userId;
	private String firstName;
	private String lastName;
	private String email;
	private List<Integer> date = new ArrayList<>();

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

	public List<Integer> getDate() {
		return date;
	}

	public void setDate(List<Integer> date) {
		this.date = date;
	}
	
}

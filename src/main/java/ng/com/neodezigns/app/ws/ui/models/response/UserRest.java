package ng.com.neodezigns.app.ws.ui.models.response;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ng.com.neodezigns.app.ws.custom.CustomMethods;

public class UserRest {

	private String userId;
	private String firstName;
	private String lastName;
	private String userName;
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

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public List<Integer> getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = CustomMethods.parseDateToArray(date);
	}
}

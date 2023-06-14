package library;

import java.util.ArrayList;

public class User {

	// class fields
	protected String id;
	protected String firstName;
	protected String lastName;
	protected char gender;
	protected String birthDate;
	protected String finantialPalance;
	protected ArrayList<String> phone;
	protected String password;

	// constructor
	public User(String id, String firstName, String lastName, char gender, String birthDate, String finantialPalance,
			String phone1, String password) {

		this.phone = new ArrayList<String>();

		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.gender = gender;
		this.birthDate = birthDate;
		this.finantialPalance = finantialPalance;
		this.phone.add(phone1);
		this.password = password;
	}

	// constructor
	public User(String id, String firstName, String lastName, char gender, String birthDate, String finantialPalance,
			String phone1, String phone2, String password) {

		this(id, firstName, lastName, gender, birthDate, finantialPalance, phone1, password);
		this.phone.add(phone2);

	}

	// ---------------------------------------------------------------------------------//
	// generate getters and setters methods

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	public char getGender() {
		return gender;
	}

	public void setGender(char gender) {
		this.gender = gender;
	}

	public String getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(String birthDate) {
		this.birthDate = birthDate;
	}

	public String getFinantialPalance() {
		return finantialPalance;
	}

	public void setFinantialPalance(String finantialPalance) {
		this.finantialPalance = finantialPalance;
	}

	public String getPhone1() {
		return phone.get(0);
	}

	public void setPhone1(String phone1) {
		this.phone.set(0, phone1);
	}

	public String getPhone2() {
		if (this.phone.size() == 2) {
			return phone.get(1);
		}
		return "";
	}

	public void setPhone2(String phone2) {
		if (this.phone.size() == 2) {
			this.phone.set(1, phone2);
		} else {
			this.phone.add(phone2);
		}
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
} // end User class

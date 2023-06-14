package library;

public class NewUser {
	
	private String id;
	private String firstName;
	private String lastName;
	private char gender;
	private String birthDate;
	private String financialBalance;
	private String phone1;
	private String phone2;
	private String password;
	
	public NewUser(String id, String firstName, String lastName, char gender, String birthDate, String financialBalance,
			String phone1, String phone2, String password) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.gender = gender;
		this.birthDate = birthDate;
		this.financialBalance = financialBalance;
		this.phone1 = phone1;
		this.phone2 = phone2;
		this.password = password;
	}
	
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

	public String getFinancialBalance() {
		return financialBalance;
	}

	public void setFinancialBalance(String financialBalance) {
		this.financialBalance = financialBalance;
	}

	public String getPhone1() {
		return phone1;
	}

	public void setPhone1(String phone1) {
		this.phone1 = phone1;
	}

	public String getPhone2() {
		return phone2;
	}

	public void setPhone2(String phone2) {
		this.phone2 = phone2;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	
	public String getName() {
		return this.firstName + " " + this.lastName;
	}
	
	public String getPhone() {
		
		if(phone1.isEmpty())
			return "phone: " + this.phone2;
		
		if(phone2.isEmpty())
			return "phone: " + this.phone1;
		
		return "phone1: " + this.phone1 + ", phone2: " + this.phone2;
	}
}

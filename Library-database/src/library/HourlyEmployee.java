package library;

import java.util.ArrayList;

// HourlyEmployee class
public class HourlyEmployee extends Employee {

	private String workingHour;
	private String salaryPerHour;

	// 1 rule
	// constructor with 2 phone numbers
	public HourlyEmployee(String id, String firstName, String lastName, char gender, String birthDate,
			String finantialPalance, String phone1, String phone2, String password, String employmentDate,
			String workingHour, String salaryPerHour, Rule rules) {
		super(id, firstName, lastName, gender, birthDate, finantialPalance, phone1, phone2, password, employmentDate,
				(Double.parseDouble(salaryPerHour) * Double.parseDouble(workingHour)) + "", rules);

		this.workingHour = workingHour;
		this.salaryPerHour = salaryPerHour;
	}

	// constructor with 1 phone number
	public HourlyEmployee(String id, String firstName, String lastName, char gender, String birthDate,
			String finantialPalance, String phone1, String password, String employmentDate, String workingHour,
			String salaryPerHour, Rule rules) {
		super(id, firstName, lastName, gender, birthDate, finantialPalance, phone1, password, employmentDate,
				(Double.parseDouble(salaryPerHour) * Double.parseDouble(workingHour)) + "", rules);

		this.workingHour = workingHour;
		this.salaryPerHour = salaryPerHour;
	}

	// list of rules
	// constructor with 2 phone numbers
	public HourlyEmployee(String id, String firstName, String lastName, char gender, String birthDate,
			String finantialPalance, String phone1, String phone2, String password, String employmentDate,
			String workingHour, String salaryPerHour, ArrayList<Rule> rules) {
		super(id, firstName, lastName, gender, birthDate, finantialPalance, phone1, phone2, password, employmentDate,
				(Double.parseDouble(salaryPerHour) * Double.parseDouble(workingHour)) + "", rules);

		this.workingHour = workingHour;
		this.salaryPerHour = salaryPerHour;
	}

	// constructor with 1 phone number
	public HourlyEmployee(String id, String firstName, String lastName, char gender, String birthDate,
			String finantialPalance, String phone1, String password, String employmentDate, String workingHour,
			String salaryPerHour, ArrayList<Rule> rules) {
		super(id, firstName, lastName, gender, birthDate, finantialPalance, phone1, password, employmentDate,
				(Double.parseDouble(salaryPerHour) * Double.parseDouble(workingHour)) + "", rules);

		this.workingHour = workingHour;
		this.salaryPerHour = salaryPerHour;
	}

	////////////////////////////////////////////////////////////
	// getter and setters
	public String getWorkingHour() {
		return workingHour;
	}

	public void setWorkingHour(String workingHour) {
		this.workingHour = workingHour;

		// update salary
		this.updateSalary(this.workingHour, this.workingHour);
	}

	public String getSalaryPerHour() {
		return salaryPerHour;
	}

	public void setSalaryPerHour(String salaryPerHour) {
		this.salaryPerHour = salaryPerHour;

		// update salary
		this.updateSalary(this.workingHour, this.workingHour);
	}
	////////////////////////////////////////////////////////////

	// update salary for hourly employee
	public void updateSalary(String workingHours, String salaryPerHour) {
		this.setSalary((Double.parseDouble(salaryPerHour) * Double.parseDouble(workingHours)) + "");
	}

}

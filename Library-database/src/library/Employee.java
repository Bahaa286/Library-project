package library;

import java.util.ArrayList;

public class Employee extends User {
	
	protected String employmentDate; // employment date
	protected String salary; // salary
	protected ArrayList<Rule> rules; // rules

	// 1 rule
		public Employee(String id, String firstName, String lastName, char gender, String birthDate, String finantialPalance,
				String phone1, String password, String employmentDate, String salary, Rule rules) {
			super(id, firstName, lastName, gender, birthDate, finantialPalance, phone1, password);
			
			this.employmentDate = employmentDate;
			this.salary = salary;
			this.rules = new ArrayList<Rule>();
			this.rules.add(rules);
		}
		
		public Employee(String id, String firstName, String lastName, char gender, String birthDate, String finantialPalance,
				String phone1, String phone2, String password, String employmentDate, String salary, Rule rules) {
			
			super(id, firstName, lastName, gender, birthDate, finantialPalance, phone1, phone2, password);
			
			this.employmentDate = employmentDate;
			this.salary = salary;
			this.rules = new ArrayList<Rule>();
			this.rules.add(rules);
		}
		
	// list of rules
	public Employee(String id, String firstName, String lastName, char gender, String birthDate, String finantialPalance,
			String phone1, String password, String employmentDate, String salary, ArrayList<Rule> rules) {
		super(id, firstName, lastName, gender, birthDate, finantialPalance, phone1, password);
		
		this.employmentDate = employmentDate;
		this.salary = salary;
		this.rules = rules;
	}
	
	public Employee(String id, String firstName, String lastName, char gender, String birthDate, String finantialPalance,
			String phone1, String phone2, String password, String employmentDate, String salary, ArrayList<Rule> rules) {
		
		super(id, firstName, lastName, gender, birthDate, finantialPalance, phone1, phone2, password);
		
		this.employmentDate = employmentDate;
		this.salary = salary;
		this.rules = rules;
	}
	
	//--------------------------------------------------------------//
	// getters and setters

	public String getEmploymentDate() {
		return employmentDate;
	}

	public void setEmploymentDate(String employmentDate) {
		this.employmentDate = employmentDate;
	}

	public String getSalary() {
		return salary;
	}

	public void setSalary(String salary) {
		this.salary = salary;
	}
	
	public ArrayList<Rule> getRules(){
		return rules;
	}
	
	public void setRules(ArrayList<Rule> rules) {
		this.rules = rules;
	}
	
	// end getters and setters methods
	//--------------------------------------------------------------//
	
	// is rule exist
	public boolean isRuleExist(Rule rule) {
		
		// is exist boolean
		boolean isExist = false;
		
		// rules size
		int size = this.rules.size();
		
		for(int i = 0; i < size && !isExist; i++)
			isExist = rule.equals(rules.get(i));
		
		return isExist;
	} // end isRuleExist
	
	// get rule index
	public int getRuleIndex(Rule rule) {
		
		if(this.isRuleExist(rule)) {
			// rules size
			int size = this.rules.size();
			
			for(int i = 0; i < size; i++)
				if(rules.get(i).equals(rule))
					return i;
		} // end if
		
		return -1;
	} // end getRuleIndex method
	
	// add rule
	public void addRule(Rule rule) {
		
		if(!this.isRuleExist(rule))
			this.rules.add(rule);
	} // end addRule method
	
	// add rules
	public void addRules(ArrayList<Rule> rules) {
		
		// new rules size
		int size = rules.size();
		
		for(int i = 0; i < size; i++)
			this.addRule(rules.get(i));
	} // end addRules method
	
	public void deleteRule(Rule rule) {
		
		// rule index
		int index = this.getRuleIndex(rule);
		
		if(index >= 0)
			this.rules.remove(index);
	} // end deleteRule
	
} // end class

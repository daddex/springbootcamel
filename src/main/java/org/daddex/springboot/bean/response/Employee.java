package org.daddex.springboot.bean.response;

public class Employee {
	
	private String name;
	private String lastName;
	private String addresse;
	public String getName() {
		return name;
	}
	public Employee setName(String name) {
		this.name = name;
		return this;
	}
	public String getLastName() {
		return lastName;
	}
	public Employee setLastName(String lastName) {
		this.lastName = lastName;
		return this;
	}
	public String getAddresse() {
		return addresse;
	}
	public Employee setAddresse(String addresse) {
		this.addresse = addresse;
		return this;
	}
	
}

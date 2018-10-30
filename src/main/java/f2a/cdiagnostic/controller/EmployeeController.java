package f2a.cdiagnostic.controller;

import org.apache.camel.Exchange;

import f2a.cdiagnostic.bean.response.Employee;

public class EmployeeController {

	public void find(Exchange exchange) {
		Employee emp = new Employee().setAddresse("daini").setLastName("boles").setName("da");
		exchange.getIn().setBody(emp);
		
	}
	public void add(Exchange exchange) {
		Employee emp = new Employee().setAddresse("daini").setLastName("boles").setName("da");
		exchange.getIn().setBody("funziona");
	}
}

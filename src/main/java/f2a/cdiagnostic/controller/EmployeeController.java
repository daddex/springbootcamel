package f2a.cdiagnostic.controller;

import java.util.Date;

import org.apache.camel.Exchange;

import f2a.cdiagnostic.bean.response.Employee;

public class EmployeeController {

	public void find(Exchange exchange) {
		Employee emp = new Employee().setAddresse("piazza dei cervi").setLastName("Marangoni").setName("Fumagalli");
		exchange.getIn().setBody(emp);

	}

	public void print(Exchange exchange) {
		System.out.println("batch message ".concat(String.valueOf(new Date())));
	}
}

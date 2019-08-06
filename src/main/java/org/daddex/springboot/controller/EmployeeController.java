package org.daddex.springboot.controller;

import java.util.Date;

import org.apache.camel.Exchange;

import org.daddex.springboot.bean.response.Employee;

public class EmployeeController {

	public void find(Exchange exchange) {
		Employee emp = new Employee().setAddresse("piazza dei cervi").setLastName("Marangoni").setName("Fumagalli");
		exchange.getIn().setBody(emp);

	}

	public void print(Exchange exchange) {
		System.out.println(" BATCH_ROUTE IS RUNNING AT  ".concat(String.valueOf(new Date())).concat(" AND IT SAYS CIAO !!!!"));

		try {
			//System.out.println(" STARTING ROUTE EMAIL ");
			//exchange.getContext().startRoute("ON_DEMAND_INFOCERT_DOWNLOAD_EMAIL_V1");

			//exchange.getContext().getShutdownStrategy().setTimeout(3000);
			//Thread.sleep(3000);
			//System.out.println(" STOPPING ROUTE EMAIL ");
			//exchange.getContext().stopRoute("ON_DEMAND_INFOCERT_DOWNLOAD_EMAIL_V1");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}

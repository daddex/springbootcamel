package org.daddex.springboot.api;

import javax.jws.WebService;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.daddex.springboot.bean.response.Employee;

@Path("/employees")
@WebService(name = "Professional Category", targetNamespace = "http://www.daddex.org/")
public class EmployeeAPI {
	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	@Path("/{ID}")
	public Employee employeeItem() {
		return null;
	}
	
	@POST
    @Produces({MediaType.APPLICATION_JSON})
    @Path("/") 
    public String addEmployee(String body) {
        return null;
    }
}

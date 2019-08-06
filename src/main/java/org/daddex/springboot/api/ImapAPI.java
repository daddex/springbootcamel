package org.daddex.springboot.api;


import org.daddex.springboot.bean.request.EmailNotification;


import javax.jws.WebService;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;


@Path("/emailmanager")
@WebService(name = "Mail Manager", targetNamespace = "http://www.daddex.org/")
public class ImapAPI {


    @POST
    @Produces({ MediaType.APPLICATION_JSON})
    @Path("/retrieve")
    public String retrieveEmail(EmailNotification emailRetrieved) {
        return null;
    }
}

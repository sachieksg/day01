
package day05.rest;

import day05.business.CustomerBean;
import day05.model.Customer;
import java.util.Optional;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.websocket.server.PathParam;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@RequestScoped
@Path("/customer")
public class CustomerResource 
{
    @EJB CustomerBean customerbean;
    
    @GET
    @Path("[custId]")
    @Produces(MediaType.APPLICATION_JSON)
    public Response get(@PathParam("custId") Integer custId)
    {
        Optional<Customer> opt = customerbean.findByCustomerId(custId);
        
        if(!opt.isPresent())
            return (Response.status(Response.Status.NOT_FOUND).build());
        
        return (Response.ok(opt.get().toJson()).build());
    }
    
}

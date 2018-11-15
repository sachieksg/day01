
package day04.rest;

import day04.business.CustomerBean;
import day04.model.Customer;
import java.sql.SQLException;
import java.util.Optional;
import javax.json.Json;
import javax.json.JsonObject;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.core.Response;

public class FindbyCustomerIdRunnable implements Runnable {

    private Integer custId;
    private CustomerBean customerbean;
    private AsyncResponse asyncResp;
    
    public FindbyCustomerIdRunnable(Integer cid,
            CustomerBean cb, AsyncResponse ar) {
        custId = cid;
        customerbean = cb;
        asyncResp = ar;
    }
    
    @Override
    public void run(){
        
         System.out.println(">>> running FindByCustomerIdRunnable");
        
         Optional<Customer> opt = null;
        try
        {
            opt = customerbean.findByCustomerId(custId);
            
        }
        catch(SQLException ex)
        {
           JsonObject builder = Json.createObjectBuilder()
                    .add("error", ex.getMessage())
                    .build();
                    //return Response.serverError().entity(builder).build();
                    asyncResp.resume(Response.serverError().entity(builder).build());
                    return;
        }
        
        if(!opt.isPresent())
        {
            asyncResp.resume(Response.status(Response.Status.NOT_FOUND).build());
            return;
        }
        
        asyncResp.resume(Response.ok(opt.get().toJson()).build());
        
         System.out.println(">>> resuming request");
 
    }
}

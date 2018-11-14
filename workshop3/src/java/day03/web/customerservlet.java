
package day03.web;

import day03.business.CustomerBean;
import day03.business.CustomerException;
import day03.web.model.DiscountCode;
import day03.web.model.customer;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.MediaType;

@WebServlet( urlPatterns = {"/customer"})
public class customerservlet extends HttpServlet{
    
    @EJB private CustomerBean cubean;
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        Integer customerId = Integer.parseInt(request.getParameter("customerid"));
        
        //Getting optional obj, validate if exisits 
        Optional<customer> opt = cubean.findByCustomerId(customerId);
        
        if( !opt.isPresent())
        {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            response.setContentType(MediaType.TEXT_PLAIN);
             try (PrintWriter pw = response.getWriter())
                {
                    pw.print("CustomerID %d does not exists\n" + customerId);
                }
             return;
        }
        
        //Get result from bean object
        customer cu = opt.get();
        
          response.setStatus(HttpServletResponse.SC_OK);
          response.setContentType(MediaType.APPLICATION_JSON);
                try (PrintWriter pw = response.getWriter())
                {
                    pw.print("CustomerID " + customerId);
                    pw.println(cu.toJson());
                }
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException 
    {
            customer cust = createCustomer(request);
            
            Optional<customer> opt = cubean.findByCustomerId(cust.getCustomerId());
            if (opt.isPresent())
            {
                    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    response.setContentType("text/plain");
                try (PrintWriter pw = response.getWriter())
                {
                    pw.printf("CustomerID id %d exisits", cust.getCustomerId());
                    
                } 
                return;
            }
            
            try
            {
            cubean.addNewCustomer(cust);
            }
            catch (CustomerException ex) {
                 ex.printStackTrace();
                  try (PrintWriter pw = response.getWriter())
                {
                    pw.printf(ex.getMessage());
                } 
                  return;
            }
        
        response.setStatus(HttpServletResponse.SC_ACCEPTED);
        response.setContentType("text/plain");
        try (PrintWriter pw = response.getWriter()) {
            pw.printf("Customer created");                
        
        }
        
    }
    
    private customer createCustomer(HttpServletRequest req)
    {
        customer cu = new customer();
        cu.setCustomerId(Integer.parseInt(req.getParameter("customerid")));
        cu.setName(req.getParameter("name"));
        cu.setAddressline1(req.getParameter("addressline1"));
        cu.setAddressline2(req.getParameter("addressline2"));
        cu.setPhone(req.getParameter("phone"));
        cu.setEmail(req.getParameter("email"));
        cu.setZip(req.getParameter("zip"));
        cu.setFax(req.getParameter("fax"));
        cu.setState(req.getParameter("state"));
        cu.setCity(req.getParameter("city"));
        cu.setCreditlimit(req.getParameter("creditlimit"));
        DiscountCode dc = new DiscountCode();
        dc.setDiscountCode(DiscountCode.Code.valueOf(req.getParameter("discountcode")));
        cu.setDiscountCode(dc);
        return (cu);
        
    }
    
}

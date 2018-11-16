
package day05.web;

import io.jsonwebtoken.Jwts;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonObject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ws.rs.core.MediaType;


@WebServlet( urlPatterns = {"/login"})
public class LoginServlet extends HttpServlet{
    
    @Inject private KeyManager keyMgr;
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException 
    {
        
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        
        try
        {
            request.login(username,password);
            
        }
        catch(ServletException ex)
        {
              response.setStatus(HttpServletResponse.SC_FORBIDDEN);
               response.setContentType(MediaType.TEXT_PLAIN);
               try (PrintWriter pw = response.getWriter())
                {
                    pw.println("iNCORRECT LOGIN");
                } 
                  return;
        }
        
        Map<String,Object> claims = new HashMap<>();
        claims.put("username", request.getRemoteUser());
        claims.put("role", "authenticate");
        claims.put("host", request.getRemoteHost());
        claims.put("port", request.getRemotePort());
        long exp = System.currentTimeMillis() + (1000 * 60 * 30);
        
        String token = Jwts.builder()
                .setAudience("workshop5")
                .setIssuer("workshop5")
                .setExpiration(new Date(exp))
                .addClaims(claims)
                .signWith(keyMgr.getKey())
                .compact();
        
        JsonObject result = Json.createObjectBuilder()
                .add("token_type", "bearer")
                .add("token", token)
                .build();
        
        System.out.printf("result : %s\n", result.toString());
        
        HttpSession sess = request.getSession();
        sess.invalidate();
        
         response.setStatus(HttpServletResponse.SC_OK);
          response.setContentType(MediaType.APPLICATION_JSON);
                try (PrintWriter pw = response.getWriter())
                {
                    pw.println(result.toString());
                }
    }
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package workshop01;

import java.io.IOException;
import java.io.PrintWriter;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;


@WebServlet(urlPatterns = {"/weather"})
public class saWeather   extends HttpServlet  
{
    private static final String WEATHER_URL = "http://api.openweathermap.org/data/2.5/weather";
    private Client client;
    
    @Override
    public void init() throws ServletException
    {
        client = ClientBuilder.newClient();
    }

    @Override
    public void destroy() {
       client.close();
    }
    
    
    
    @Override
    public void doGet(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        String cityname = request.getParameter("cityname");    
        response.setContentType("text/html");

        if ((cityname == null) || (cityname.trim().length() <=0))
        {
                cityname = "Singapore";
        }
        
        WebTarget target = client.target(WEATHER_URL);
        
        target = target.queryParam("q", cityname);
        
        target = target.queryParam("appid", "5df2b7373c8cb86dd9561acf5704cf4a");
        
        /*
        target = client.target(WEATHER_URL)
                .queryParam("q", cityname);
        */
        Invocation.Builder iv = target.request(MediaType.APPLICATION_JSON);
        
        JsonObject r =  iv.get(JsonObject.class);
        JsonArray w = r.getJsonArray("weather");
        
        response.setStatus(HttpServletResponse.SC_OK);
        
        try(PrintWriter pw = response.getWriter()){
          
            pw.print("<hr>");
          pw.print("<h4> There wheather for city " + cityname.toUpperCase() + "</h2>");
          pw.print("<hr>");
          for(int i=0; i < w.size(); i++)
          {
              JsonObject wd = w.getJsonObject(i);
              String main = wd.getString("main");
              String desc = wd.getString("description");
              String icon = wd.getString("icon");
              
              pw.print("<div>");
              pw.printf("%s &dsh %s", main, desc);
              pw.printf("<img src=\"http://openweathermap.org/img/w/%s.png\">",icon);
              pw.print("</div>");
          }
        }
    }
    
}

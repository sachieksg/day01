/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package day02.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author issuser
 */
@WebServlet( urlPatterns = {"/customer-sql"})
public class customersqlservlet extends HttpServlet {

    @Resource(lookup = "jdbc/sample")
    private DataSource sampleDB;
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
//    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//        response.setContentType("text/html;charset=UTF-8");
//        try (PrintWriter out = response.getWriter()) {
//            /* TODO output your page here. You may use following sample code. */
//            out.println("<!DOCTYPE html>");
//            out.println("<html>");
//            out.println("<head>");
//            out.println("<title>Servlet customersql</title>");            
//            out.println("</head>");
//            out.println("<body>");
//            out.println("<h1>Servlet customersql at " + request.getContextPath() + "</h1>");
//            out.println("</body>");
//            out.println("</html>");
//        }
//    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        
        Integer custid = Integer.parseInt(request.getParameter("custId"));
        
        //Get connection object
        try (Connection conn = sampleDB.getConnection()) {
        
            //create prepared statment
            PreparedStatement ps = conn.prepareStatement(
                        "select * from customer where customer_id = ?"
            );
            ps.setInt(1,custid);
            //Execute query 
            ResultSet rs = ps.executeQuery();
            if(rs.next())
            {
                //Found
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                response.setContentType(MediaType.TEXT_HTML);
                try (PrintWriter pw = response.getWriter())
                {
                    pw.println("<hr>");
                    pw.println("Result from database");
                    pw.println("<hr>");
                    pw.printf("<b>id:</b> %d<br/> <b>Name:</b> %s<br/> <b>Address:</b> %s<br/> <b>Phone:</b> %s<br/> <b>Email:</b> %s\n",
                            rs.getInt("customer_id"),
                            rs.getString("name"),
                            rs.getString("addressline1"),
                            rs.getString("phone"),
                            rs.getString("email")
                            );
                }
            }
            else
            {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                response.setContentType(MediaType.TEXT_PLAIN);
                try (PrintWriter pw = response.getWriter())
                {
                    pw.println("No record found");
                }
            }
        }
        catch ( SQLException ex1)
        {
            log(ex1.getMessage());
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.setContentType(MediaType.TEXT_PLAIN);
            try (PrintWriter pw = response.getWriter())
            {
                pw.println(ex1.getMessage());
            }
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
     
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}

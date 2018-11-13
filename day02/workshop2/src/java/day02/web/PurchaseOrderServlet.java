/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package day02.web;

import day02.web.model.PurchaseOrder;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author issuser
 */
@WebServlet(urlPatterns = {"/PurchaseOrder"})
public class PurchaseOrderServlet extends HttpServlet {

    
    @PersistenceContext private EntityManager em;

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

       Integer custId = Integer.parseInt(request.getParameter("custId"));
       
       TypedQuery<PurchaseOrder> query = em.createNamedQuery(
               "PurchaseOrder.findByCustomerId", PurchaseOrder.class);
       
       query.setParameter("custId", custId);
       
       List<PurchaseOrder> result = query.getResultList();
       
       JsonArrayBuilder builder = Json.createArrayBuilder();
       for (PurchaseOrder po:result){
           builder.add(po.toJson());
       }
       
       response.setStatus(HttpServletResponse.SC_OK);
       
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

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.dao.BookDB;
import model.dao.IdeaDB;
import model.dao.MessageDB;
import model.entity.Account;
import model.entity.Book;
import model.entity.Idea;

/**
 *
 * @author ThanhNhan
 */
public class Read extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try {
            /* TODO output your page here. You may use following sample code. */
            Account a = (Account) request.getSession().getAttribute("acc");
            if (a != null) {
                int type = a.getUserTypeID();
                switch (type) {
                    case 3:
                    case 2:
                    case 0:
                        String id = request.getParameter("id");
                        if (id == null) {
                            response.sendRedirect("library");
                        } else {
                            int bookID = -1;
                            try {
                                bookID = Integer.parseInt(id);
                            } catch (Exception e) {
                                throw new RuntimeException("Có lỗi xảy ra, vui lòng thử lại!");
                            }
                            Book b = BookDB.getBook(bookID);
                            b.upViewCounter();
                            ArrayList<Idea> list = new ArrayList<>();
                            for (Idea i : IdeaDB.getIdeaBook(b.getBookID())) {
                                if (i.getAppear()) {
                                    list.add(i);
                                }
                            }
                            request.setAttribute("messenger", MessageDB.getMessage(a.getUserID()));
                            request.setAttribute("idea", list);
                            request.setAttribute("book", b);
                            request.setAttribute("messenger", MessageDB.getMessage(a.getUserID()));
                            request.getRequestDispatcher("Read.jsp").include(request, response);
                        }
                        break;
                    case 1:
                        response.sendRedirect("manage");
                        break;
                }
            } else {
                response.sendRedirect("login");
            }
        } catch (IOException | ServletException e) {
            response.sendRedirect("login");
        }
    }

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
        processRequest(request, response);
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
        processRequest(request, response);
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

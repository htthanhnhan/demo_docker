/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.dao.DeviceReceiptDB;
import model.entity.Account;
import model.entity.DeviceReceipt;

/**
 *
 * @author ThanhNhan
 */
public class StatisticalDeviceTeacher extends HttpServlet {

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
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet StatisticalDeviceTeacher</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet StatisticalDeviceTeacher at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
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
        response.setContentType("text/html;charset=UTF-8");
        try {
            Account a = (Account) request.getSession().getAttribute("acc");
            if (a != null) {
                int type = a.getUserTypeID();
                switch (type) {
                    case 3:
                    case 2:
                    case 0:
                        response.sendRedirect("library");
                        break;
                    case 1:
                        int sum = 0;
                        for (DeviceReceipt d : DeviceReceiptDB.getListDeviceReceipt()) {
                            sum = sum + d.getQuantity();
                        }
                        request.setAttribute("total", sum);
                        request.setAttribute("list", DeviceReceiptDB.getListDeviceReceipt());
                        DeviceReceiptDB.statistical(DeviceReceiptDB.getListDeviceReceipt());
                        request.getRequestDispatcher("StatisticalDeviceTeacher.jsp").include(request, response);
                        break;
                }
            } else {
                response.sendRedirect("login");
            }
        } catch (IOException | ServletException e) {
            response.sendRedirect("login");
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
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("utf-8");
        try {
            PrintWriter out = response.getWriter();
            SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat d = new SimpleDateFormat("dd-MM-yyyy");
            Date from = (Date) f.parse(request.getParameter("from"));
            Date to = (Date) f.parse(request.getParameter("to"));
            String action = request.getParameter("action");
            if (from.compareTo(to) > 0) {
                throw new RuntimeException("Ng??y k???t th??c kh??ng ???????c tr?????c ng??y b???t ?????u!");
            } else {
                ArrayList<DeviceReceipt> list = new ArrayList<>();
                int sum = 0;
                for (DeviceReceipt b : DeviceReceiptDB.getListDeviceReceipt()) {
                    if (from.compareTo((Date) d.parse(b.getBorrowDate().substring(9, b.getBorrowDate().length()))) <= 0 && to.compareTo((Date) d.parse(b.getBorrowDate().substring(9, b.getBorrowDate().length()))) >= 0) {
                        list.add(b);
                        sum = sum + b.getQuantity();
                    }
                }
                request.setAttribute("from", request.getParameter("from"));
                request.setAttribute("to", request.getParameter("to"));
                request.setAttribute("total", sum);
                request.setAttribute("list", list);
                DeviceReceiptDB.statistical(list);
                request.getRequestDispatcher("StatisticalDeviceTeacher.jsp").include(request, response);
            }
        } catch (ParseException ex) {
            Logger.getLogger(StatisticalClass.class.getName()).log(Level.SEVERE, null, ex);
        }
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

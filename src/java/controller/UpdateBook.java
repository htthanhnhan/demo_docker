/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Paths;
import java.util.Random;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import model.dao.AuthorDB;
import model.dao.BookDB;
import model.dao.CategoryDB;
import model.dao.SubjectDB;
import model.entity.Account;
import model.entity.Author;
import model.entity.Book;
import model.entity.Category;

/**
 *
 * @author ThanhNhan
 */
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 200,
        maxFileSize = 1024 * 1024 * 200,
        maxRequestSize = 1024 * 1024 * 200)
public class UpdateBook extends HttpServlet {

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
            out.println("<title>Servlet UpdateBook</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet UpdateBook at " + request.getContextPath() + "</h1>");
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
                        String id = request.getParameter("id");
                        if (id == null || id.trim().equals("")) {
                            response.sendRedirect("listbook");
                        } else {
                            int bookID = -1;
                            try {
                                bookID = Integer.parseInt(id);
                            } catch (Exception e) {
                                throw new RuntimeException("Có lỗi xảy ra, vui lòng thử lại!");
                            }
                            request.setAttribute("b", new Book(bookID));
                            request.setAttribute("ca", CategoryDB.getListCategory());
                            request.setAttribute("au", AuthorDB.getListAuthor());
                            request.setAttribute("su", SubjectDB.getListSubject());
                            request.getRequestDispatcher("UpdateBook.jsp").include(request, response);
                        }
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
        PrintWriter out = response.getWriter();
        int bookID = Integer.parseInt(request.getParameter("bookID"));
        Book b = new Book(bookID);
        String bookName = request.getParameter("bookName");
//        out.print(bookName);
        int authorID[] = new int[5];
        for (int i = 0; i < 5; i++) {
            authorID[i] = Integer.parseInt(request.getParameter("authorID" + (i + 1)));
        }
        String[] authorName = new String[5];
        for (int i = 0; i < 5; i++) {
            authorName[i] = request.getParameter("authorName" + (i + 1));
        }
        int quantity = Integer.parseInt(request.getParameter("quantity"));
        String categoryID = request.getParameter("categoryID");
        int subjectID = Integer.parseInt(request.getParameter("subjectID"));
        String content = request.getParameter("content");
        String newCategoryName = request.getParameter("newcategoryName");
        String newCategoryID = request.getParameter("newcategoryID");
        if (quantity < b.getTotal()) {
            throw new RuntimeException("Vui lòng nhập số lượng ít nhất bằng tổng số lượng cũ, hoặc vào tất cả sách trong kho để xóa!");
        } else {
            if ((newCategoryName != null && !newCategoryName.trim().equals("")) && (newCategoryID == null && newCategoryID.trim().equals(""))) {
                throw new RuntimeException("Vui lòng nhập mã thể loại mới!");
            } else if ((newCategoryName != null && !newCategoryName.trim().equals("")) && (newCategoryID != null && !newCategoryID.trim().equals(""))) {
                categoryID = new Category(newCategoryID, newCategoryName).add();
            }
            for (int i = 0; i < 5; i++) {
                if (authorName[i] != null && !authorName[i].trim().equals("")) {
                    authorID[i] = new Author(authorName[i]).add();
                }
            }            
            for (int i = 0; i < 5; i++) {
                if(authorID[i] == -1) continue;
                for (int j = i+1; j < 5; j++) {
                    if(authorID[i] == authorID[j]) throw new RuntimeException("Vui lòng không chọn 1 tác giả nhiều lần!");
                }
            }
            if(authorID[0] == -1 && authorID[1] == -1 && authorID[2] == -1 && authorID[3] == -1 && authorID[4] == -1) throw new RuntimeException("Vui lòng chọn ít nhất 1 tác giả!");
            Part img = request.getPart("bookImg");
            String realPath = "D:\\workspace\\java\\DangDungLibrary\\web\\assets\\covers\\";
            Random rd = new Random();
            int n = rd.nextInt();
            if (n < 0) {
                n = -n;
            }
            String fileName = Paths.get(img.getSubmittedFileName()).getFileName().toString();
            String bookImg = "";
            if (fileName != null && !fileName.trim().equals("")) {
                img.write(realPath + n + fileName);
                img.write(n + fileName);
                realPath = request.getServletContext().getRealPath("./assets/covers/");
                img.write(realPath + n + fileName);
                bookImg = "./assets/covers/" + n + fileName;
            } else {
                bookImg = b.getBookImg();
            }
            Part pdf = request.getPart("pdfLink");
            realPath = "D:\\workspace\\java\\DangDungLibrary\\web\\assets\\pdf\\";
            fileName = Paths.get(pdf.getSubmittedFileName()).getFileName().toString();
            String pdfLink = "";
            if (fileName != null && !fileName.trim().equals("")) {
                pdf.write(realPath + n + fileName);
                pdf.write(n + fileName);
                realPath = request.getServletContext().getRealPath("./assets/pdf/");
                img.write(realPath + n + fileName);
                pdfLink = "./assets/pdf/" + n + fileName;
            } else {
                pdfLink = b.getPdfLink();
            }
            new Book(bookID, bookName, categoryID, subjectID, content, bookImg, b.getQuantity() + quantity - b.getTotal(), pdfLink).update();
            int q = quantity - b.getTotal();
            if(q > 0){
                BookDB.addBooks(bookID, q);
            }
            BookDB.deleteAuthorBook(b);
            for (int i = 0; i < 5; i++) {
                if (authorID[i] != -1) {
                    BookDB.addAuthorBook(new Book(bookID), new Author(authorID[i]));
                }
            }
        }
        response.sendRedirect("listbook");
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

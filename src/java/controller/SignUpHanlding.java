/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import entity.MessageError;
import entity.User;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.UserDao;

/**
 *
 * @authoimport javax.servlet.http.HttpSession; r demonslight998
 */
public class SignUpHanlding extends HttpServlet {

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
    UserDao userDao = new UserDao();
    HttpSession session = request.getSession();
    Map listUser = new HashMap<String, String>();
    listUser = (Map) session.getAttribute("listUser");

    String username = request.getParameter("name");
    String password = request.getParameter("password");

    if (username.length() < 6) {
      request.setAttribute("error", MessageError.USERNAME_LENGTH);
      request.getRequestDispatcher("signUp.jsp").forward(request, response);
    }
    if (!username.matches("^[^$#@%^&*]\\w+$")) {
      request.setAttribute("error", MessageError.USERNAME_FORMAT);
      request.getRequestDispatcher("signUp.jsp").forward(request, response);
    }

    if (userDao.isDuplicateUser(username, listUser)) {
      request.setAttribute("error", MessageError.USERNAME_EXISTED);
      request.getRequestDispatcher("signUp.jsp").forward(request, response);
    }

    if (password.length() < 8) {
      request.setAttribute("error", MessageError.PASSWORD_LENGTH);
      request.getRequestDispatcher("signUp.jsp").forward(request, response);
    }
    if (!password.matches("^((?=.*\\d)(?=.*[A-Z])(?=.*\\W).{8,})$")) {
      request.setAttribute("error", MessageError.PASSWORD_FORMAT);
      request.getRequestDispatcher("signUp.jsp").forward(request, response);
    }

    User newUser = new User(username, password);
    request.setAttribute("newAccount", newUser);
    request.getRequestDispatcher("welcomePage.jsp").forward(request, response);

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

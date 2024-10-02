package com.example.servlet;

import com.example.Users;

import javax.servlet.RequestDispatcher;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Optional;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (!isUserLoggedIn(request)) {
            String login = request.getParameter("login");
            String password = request.getParameter("password");

            String username = getUserByUserCredentials(login, password);

            HttpSession session = request.getSession();

            if (username != null) {
                session.setAttribute("user", username);
            }
        }

        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String username = request.getParameter("user");
        if (isUserLoggedIn(request)){
            response.sendRedirect(request.getContextPath() + "/user/hello.jsp");
        } else {
            RequestDispatcher dispatcher = request.getRequestDispatcher("/login.jsp");
            dispatcher.forward(request, response);
        }
    }

    private boolean isUserLoggedIn(HttpServletRequest request) {
        Users users = Users.getInstance();
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("user");
        return username != null && users.getUsers().stream().anyMatch(username::equalsIgnoreCase)  ;
    }

    private String getUserByUserCredentials(String login, String password) {
        Users users = Users.getInstance();
        if (login != null) {
            Optional<String> username =
                    users.getUsers().stream()
                            .filter(item -> item.equalsIgnoreCase(login))
                            .findFirst();

            if (username.isPresent() && password != null && !password.isEmpty()) {
                return username.get();
            }
        }
        return null;
    }
}

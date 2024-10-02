package com.example.filter;

import com.example.Users;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter("/user/*")
public class AuthFilter extends HttpFilter {

    private final Users users;

    public AuthFilter() {
        this.users = Users.getInstance();
    }

    public void doFilter(HttpServletRequest servletRequest, HttpServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpSession session = servletRequest.getSession();
        String username = (String) session.getAttribute("user");

        if (this.users.getUsers().stream().noneMatch(item -> item.equalsIgnoreCase(username))) {
            session.removeAttribute("user");
            servletResponse.sendRedirect(servletRequest.getContextPath() + "/login");
        } else {
            filterChain.doFilter(servletRequest, servletResponse);
        }
    }
}
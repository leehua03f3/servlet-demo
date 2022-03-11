package com.example.testservlet;

import entity.User;
import org.hibernate.Session;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "login", value = "/login")
public class Login extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.getWriter().append("Hello");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();

        User user = new User();

        String username = req.getParameter("username");
        String password = req.getParameter("password");

        user.setUsername(username);
        user.setPassword(password);

        session.save(user);
        session.getTransaction().commit();
        HibernateUtil.shutdown();

        boolean result = validate(username, password);

        if (result) {
            resp.getWriter().append("Hello " + username);
        } else {
            resp.getWriter().append("Try again");
        }
    }

    private boolean validate(String username, String password) {
        if (username.equals("admin") && password.equals("123456")) {
            return true;
        } else {
            return false;
        }
    }
}

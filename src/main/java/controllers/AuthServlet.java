package controllers;

import services.UserService;
import models.User;
import utils.PasswordUtil;
import utils.ValidationUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/auth/*")
public class AuthServlet extends HttpServlet {
    private UserService userService;

    @Override
    public void init() throws ServletException {
        userService = new UserService();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String path = request.getPathInfo();

        switch (path) {
            case "/login":
                handleLogin(request, response);
                break;
            case "/register":
                handleRegister(request, response);
                break;
            case "/logout":
                handleLogout(request, response);
                break;
            default:
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    private void handleLogin(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        if (!ValidationUtil.isValidEmail(email)) {
            request.setAttribute("error", "Invalid email format");
            request.getRequestDispatcher("/views/login.jsp").forward(request, response);
            return;
        }

        String hashedPassword = PasswordUtil.hashPassword(password);
        var userOpt = userService.loginUser(email, hashedPassword);

        if (userOpt.isPresent()) {
            User user = userOpt.get();
            HttpSession session = request.getSession();
            session.setAttribute("user", user);
            session.setAttribute("userId", user.getId());
            session.setAttribute("userRole", user.getRole());
            session.setAttribute("userName", user.getName());

            switch (user.getRole()) {
                case "ADMIN":
                    response.sendRedirect(request.getContextPath() + "/admin/dashboard");
                    break;
                case "TRAVEL_AGENT":
                    response.sendRedirect(request.getContextPath() + "/agent/dashboard");
                    break;
                case "TRAVELER":
                    response.sendRedirect(request.getContextPath() + "/traveler/dashboard");
                    break;
                default:
                    response.sendRedirect(request.getContextPath() + "/");
            }
        } else {
            request.setAttribute("error", "Invalid email or password");
            request.getRequestDispatcher("/views/login.jsp").forward(request, response);
        }
    }

    private void handleRegister(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String role = request.getParameter("role");
        String phone = request.getParameter("phone");

        if (!ValidationUtil.isValidName(name) || !ValidationUtil.isValidEmail(email)) {
            request.setAttribute("error", "Invalid input data");
            request.getRequestDispatcher("/views/register.jsp").forward(request, response);
            return;
        }

        User user = new User(name, email, PasswordUtil.hashPassword(password), role);
        user.setPhone(phone);

        try {
            if (userService.registerUser(user)) {
                request.setAttribute("success", "Registration successful! Please login.");
                request.getRequestDispatcher("/views/login.jsp").forward(request, response);
            } else {
                request.setAttribute("error", "Registration failed. Please try again.");
                request.getRequestDispatcher("/views/register.jsp").forward(request, response);
            }
        } catch (IllegalArgumentException e) {
            request.setAttribute("error", e.getMessage());
            request.getRequestDispatcher("/views/register.jsp").forward(request, response);
        }
    }

    private void handleLogout(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        response.sendRedirect(request.getContextPath() + "/views/login.jsp");
    }
}
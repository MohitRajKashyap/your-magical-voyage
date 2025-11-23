package filters;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter("/*")
public class AuthFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        HttpSession session = httpRequest.getSession(false);

        String requestURI = httpRequest.getRequestURI();
        String contextPath = httpRequest.getContextPath();

        boolean loggedIn = session != null && session.getAttribute("user") != null;
        boolean loginRequest = requestURI.equals(contextPath + "/auth/login") ||
                requestURI.equals(contextPath + "/views/login.jsp");
        boolean registerRequest = requestURI.equals(contextPath + "/auth/register") ||
                requestURI.equals(contextPath + "/views/register.jsp");
        boolean resourceRequest = requestURI.startsWith(contextPath + "/assets/") ||
                requestURI.startsWith(contextPath + "/css/") ||
                requestURI.startsWith(contextPath + "/js/") ||
                requestURI.startsWith(contextPath + "/images/");

        if (loggedIn || loginRequest || registerRequest || resourceRequest ||
                requestURI.equals(contextPath + "/") || requestURI.equals(contextPath + "/index.jsp")) {
            chain.doFilter(request, response);
        } else {
            httpResponse.sendRedirect(contextPath + "/views/login.jsp");
        }
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {}

    @Override
    public void destroy() {}
}
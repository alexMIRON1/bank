package com.bank.controller.filter;

import com.bank.model.entity.Role;
import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.*;

@WebFilter(filterName = "security")
public class SecurityFilter implements Filter {
    private static final Logger LOG = Logger.getLogger(SecurityFilter.class);
    private static final Map<Role,List<String>> LINK = new EnumMap<>(Role.class);
   static {
       LINK.put(Role.ADMIN,List.of(""));
       LINK.put(Role.CLIENT,List.of("/admin","/blockedCards"));
       LINK.put(Role.ANONYMOUS,List.of("/admin","blockedCards","/home","payments"));
   }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest)  servletRequest;
        HttpServletResponse response = (HttpServletResponse)  servletResponse;

        HttpSession session = request.getSession();
        String path = request.getServletPath();
        Role role = (Role) session.getAttribute("role");
        if(Objects.isNull(role)){
            role = Role.ANONYMOUS;
            session.setAttribute("role",role);
        }
        LOG.info("current role: " + role.getRole());
        List<String> unavailablePath = LINK.get(role);
        if(unavailablePath.contains(path)){
            LOG.debug("access was denied");
            response.sendRedirect("/bank/accessDeny");
        }else {
            filterChain.doFilter(request,response);
        }
     }
}

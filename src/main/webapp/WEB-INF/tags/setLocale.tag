<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ tag import="javax.servlet.jsp.jstl.core.Config"%>
<%
	if(request.getSession()!= null){
		HttpSession session1 = request.getSession();
		String defaultLocale = (String) session1.getAttribute("defaultLocale");
		if(defaultLocale == null){
			defaultLocale = (String) request.getServletContext().getAttribute("javax.servlet.jsp.jstl.fmt.locale");
		}
		Config.set(request,"javax.servlet.jsp.jstl.fmt.locale", defaultLocale);
	}

%>
<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ tag import="javax.servlet.jsp.jstl.core.Config"%>
<%@ tag import="java.util.Arrays"%>

<%
	Cookie[] cookies = request.getCookies();
	String defaultLocale = Arrays.stream(cookies)
			.filter(c -> "defaultLocale".equals(c.getName())).findAny()
			.map(c -> c.getValue())
			.orElse((String) request.getServletContext()
					.getAttribute("javax.servlet.jsp.jstl.fmt.locale"));
	Config.set(request, "javax.servlet.jsp.jstl.fmt.locale", defaultLocale);
%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="l" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<l:setLocale/>
<html>
<head>

    <title><fmt:message key="error.access.title"/></title>
</head>
<%@include  file="fragments/header.jspf" %>
<body>
<div class="d-flex align-items-center justify-content-center vh-100">
    <div class="text-center">
        <p class="fs-3"> <span class="text-danger"><fmt:message key="error.access.danger"/></span> <fmt:message key="error.access.text"/></p>
        <p class="lead">
            <fmt:message key="error.access.solution"/>
        </p>
        <button type="button" class="btn btn-primary" onclick="window.location = 'logout'"><fmt:message key="error.access.button"/></button>
    </div>
</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0/dist/js/bootstrap.bundle.min.js" integrity="sha384-A3rJD856KowSb7dwlZdYEkO39Gagi7vIsF0jrRAoQmDKKtQBHUuLZ9AsSv4jD4Xa" crossorigin="anonymous"></script>

</body>
</html>

<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="l" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<l:setLocale/>
<html>
<head>
    <title><fmt:message key="error.title"/></title>
</head>
<body>
<%@include  file="../fragments/header.jspf" %>
<br>

<div class="d-flex align-items-center justify-content-center vh-100">
    <div class="text-center">
        <p class="fs-3"> <span class="text-danger"><fmt:message key="error.text.danger"/></span><fmt:message key="error.text.wrong.email"/></p>
        <p class="lead">
            <fmt:message key="error.solution"/>
        </p>
        <button type="button" class="btn btn-success" onclick="window.location = 'logout'"><fmt:message key="error.button.solution"/></button>
    </div>
</div>
<br>
<hr>
<br>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0/dist/js/bootstrap.bundle.min.js" integrity="sha384-A3rJD856KowSb7dwlZdYEkO39Gagi7vIsF0jrRAoQmDKKtQBHUuLZ9AsSv4jD4Xa" crossorigin="anonymous"></script>

</body>
</html>

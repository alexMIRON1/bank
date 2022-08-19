<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Register Page</title>
</head>
<body>
<%@include  file="header.html" %>
<jsp:include page="validation.jsp"/>
<br>
<a href="../bank/login">Login</a> |
<a href="../bank/register">Register</a>
<br>
<hr>
<br>
<form action="../bank/toRegister" method="post">
    <input type="text" id = "login" name="login" placeholder="login" onkeyup="validateRegister()">
    <span id='messageLogin'></span><br>
    <input type="password" id="password" name="password" placeholder="password" onkeyup="validateRegister()">
    <span id='messagePassword'></span><br>
    <input type="password" id="confirm_password" name="confirm_password" onkeyup="validateRegister()"
           placeholder="confirm password">
    <span id='messageConfirm'></span><br>
    <button type="submit" id="button" onsubmit="validateRegister()">
        Create account
    </button>
</form>
<br>
<hr>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0/dist/js/bootstrap.bundle.min.js" integrity="sha384-A3rJD856KowSb7dwlZdYEkO39Gagi7vIsF0jrRAoQmDKKtQBHUuLZ9AsSv4jD4Xa" crossorigin="anonymous"></script>

</body>
</html>

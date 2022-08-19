<%@ page contentType="text/html;charset=UTF-8" language="java" %>


<html>
<head>
    <title>Login Page</title>
</head>
<body>
<%@include  file="header.html" %>
<br>
<a href="../bank/login">Login</a> |
<a href="../bank/register">Register</a>

<br>
<hr>
<br>

<form action="../bank/toLogin" method="post">
    <input type="text" id = "login" name="login" placeholder="login" onkeyup="matchLogin()">
    <span id='messageLogin'></span><br>
    <input type="password" id="password" name="password" placeholder="password" onkeyup="matchPassword()">
    <span id='messagePassword'></span><br>
    <button type="submit" id = "button">
        Enter account
    </button>
</form>

<br>
<hr>
<jsp:include page="validation.jsp"/>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0/dist/js/bootstrap.bundle.min.js" integrity="sha384-A3rJD856KowSb7dwlZdYEkO39Gagi7vIsF0jrRAoQmDKKtQBHUuLZ9AsSv4jD4Xa" crossorigin="anonymous"></script>

</body>
</html>

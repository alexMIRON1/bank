<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Admin</title>

</head>
<body>
<%@include  file="header.html" %>
<br>

<a href="../bank/login">Log in</a> |
<a href="../bank/register">Register</a> |
<a href="../bank/home">Home</a> |
<a href="../bank/logout">Log out</a>

<br>
<hr>
<br>
<h1>Welcome ${client.login}!</h1>
<br>
<hr>
<br>
<h2>Clients</h2>

<br>

<ul>
    <c:forEach var="client" items="${clients}">
        <li>
            --------------------------- <br>

            Client name: ${client.login} <br>
            Password: ${client.password} <br>
            Status: ${client.clientStatus.status} <br>
            Role: ${client.role.role} <br>

            <c:if test="${client.clientStatus.status eq 'blocked'}">
                <a href="../bank/unlock?id=${client.id}">unlock account</a> <br>
            </c:if>

            <c:if test="${client.clientStatus.status eq 'unblocked'}">
                <a href="../bank/lock?id=${client.id}">lock account</a> <br>
            </c:if>

            --------------------------- <br>
        </li>
    </c:forEach>
</ul>

<br>
<hr>
<br>

<h2>Requests to unblock cards</h2>

<br>

<ul>
    <c:forEach var="card" items="${unlockCards}">
        <li>
            --------------------------- <br>

            Card name: ${card.name} <br>
            Balance: ${card.balance} UAH <br>
            Status: ${card.cardStatus.status} <br>
            Owner: ${card.client.login} <br>

            <a href="../bank/unblock?id=${card.id}">unblock</a> <br>

            --------------------------- <br>
        </li>
    </c:forEach>
</ul>

<br>
<hr>
<br>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0/dist/js/bootstrap.bundle.min.js" integrity="sha384-A3rJD856KowSb7dwlZdYEkO39Gagi7vIsF0jrRAoQmDKKtQBHUuLZ9AsSv4jD4Xa" crossorigin="anonymous"></script>

</body>
</html>

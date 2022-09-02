<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="l" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<l:setLocale/>
<html>
<head>
    <title><fmt:message key="admin.title"/></title>

</head>
<body>
<%@include  file="fragments/header.jspf" %>
        <br>
        <hr>
        <br>
        <div class="text-center">
            <h1><fmt:message key="admin.welcome"/> ${client.login}!</h1>
        </div>
        <br>
        <hr>
        <br>
        <br>
        <table class="table">
            <thead class="table-dark">
            <tr>
                <th scope="col"><fmt:message key="admin.table.name"/></th>
                <th scope="col"><fmt:message key="admin.table.status"/></th>
                <th scope="col"></th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="client" items="${clients}">
                <tr>
                    <th scope="row">${client.login}</th>
                    <td>${client.clientStatus.status}</td>
                    <td>
                        <c:if test="${client.clientStatus.status eq 'blocked'}">
                            <a href="../bank/unlock?id=${client.id}" class="fw-bold text-body"><fmt:message key="admin.unlock"/></a> <br>
                        </c:if>

                        <c:if test="${client.clientStatus.status eq 'unblocked'}">
                            <a href="../bank/lock?id=${client.id}" class="fw-bold text-body"><fmt:message key="admin.lock"/></a> <br>
                        </c:if>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
        <nav >
            <ul class="pagination">
                <c:if test="${page.number!= 1}">
                    <a class="page-link" href="../bank/admin?page=${page.number-1}"><fmt:message key="pagination.previous"/></a>
                </c:if>
                </li>
                <c:forEach begin="1" end="${noOfPages}" var="i">
                <c:choose>
                <c:when test="${page.number eq i} ">
                    <td>${i}</td>
                </c:when>
                <c:otherwise>
                <li class="page-item"><a class="page-link" href="../bank/admin?page=${i}">${i}</a></li>
                <li class="page-item active">
                    </c:otherwise>
                    </c:choose>
                    </c:forEach>
                <li class="page-item">
                    <c:if test="${page.number lt noOfPages}">
                        <a class="page-link" href="../bank/admin?page=${page.number + 1}"><fmt:message key="pagination.next"/></a>
                    </c:if>
                </li>
            </ul>
        </nav>
        <form method="get" action="../bank/blockedCards">
            <div class="d-flex justify-content-end">
                <button type="submit" class="btn btn-danger btn-lg"><fmt:message key="admin.request"/></button>
            </div>
        </form>>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0/dist/js/bootstrap.bundle.min.js" integrity="sha384-A3rJD856KowSb7dwlZdYEkO39Gagi7vIsF0jrRAoQmDKKtQBHUuLZ9AsSv4jD4Xa" crossorigin="anonymous"></script>

</body>
</html>

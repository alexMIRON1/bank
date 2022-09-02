<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="l" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title><fmt:message key="blockedCards.title"/></title>
    <%@include  file="fragments/header.jspf" %>
</head>
<body>
    <br>
    <hr>
    <br>
        <table class="table">
            <thead class="table-dark">
            <tr>
                <th scope="col"><fmt:message key="blockedCards.table.id"/></th>
                <th scope="col"><fmt:message key="blockedCards.table.name"/></th>
                <th scope="col"><fmt:message key="blockedCards.table.balance"/></th>
                <th scope="col"><fmt:message key="blockedCards.table.status"/></th>
                <th scope="col"><fmt:message key="blockedCards.table.owner"/></th>
                <th scope="col"></th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="card" items="${unlockCards}">
                <tr>
                    <th scope="row">${card.name}</th>
                    <td>${card.customName}</td>
                    <td>${card.balance} </td>
                    <td>${card.cardStatus.status}</td>
                    <td>${card.client.login}</td>
                    <td><a href="../bank/unblock?id=${card.id}" class="fw-bold text-body"><fmt:message key="blockedCards.table.unblock"/></a> <br></td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
        <nav >
            <ul class="pagination">
                <c:if test="${page.number!= 1}">
                    <a class="page-link" href="../bank/blockedCards?page=${page.number-1}"><fmt:message key="pagination.previous"/></a>
                </c:if>
                </li>
                <c:forEach begin="1" end="${noOfPages}" var="i">
                <c:choose>
                <c:when test="${page.number eq i} ">
                    <td>${i}</td>
                </c:when>
                <c:otherwise>
                <li class="page-item"><a class="page-link" href="../bank/blockedCards?page=${i}">${i}</a></li>
                <li class="page-item active">
                    </c:otherwise>
                    </c:choose>
                    </c:forEach>
                <li class="page-item">
                    <c:if test="${page.number lt noOfPages}">
                        <a class="page-link" href="../bank/blockedCards?page=${page.number + 1}"><fmt:message key="pagination.next"/></a>
                    </c:if>
                </li>
            </ul>
        </nav>
</body>
</html>
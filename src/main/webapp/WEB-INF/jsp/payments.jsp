<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Payments</title>
</head>
<body>
<%@include  file="header.html" %>
<jsp:include page="validation.jsp"/>
<br>
<a href="../bank/home">Home</a> |
<a href="../bank/logout">Log out</a>

<br>
<hr>
<br>

<h1>Card</h1>

-------- <br>

Card name: ${currentCard.name} <br>
Balance: ${currentCard.balance} UAH <br>
Status: ${currentCard.cardStatus.status} <br>
Owner: ${currentCard.client.login} <br>

-------- <br>

<br>
<hr>
<br>

<h2>Payments</h2>

<br>

<c:if test="${empty bills}">
    <h2>You still do not have any bills</h2>
</c:if>

<c:if test="${not empty bills}">
    | <a href="../bank/payments?card=${bills.get(0).card.id}&sort=id">sort by id</a> |
    <a href="../bank/payments?card=${bills.get(0).card.id}&sort=newest">sort by newest</a> |
    <a href="../bank/payments?card=${bills.get(0).card.id}&sort=latest">sort by latest</a> |

    <ul>
        <c:forEach var="bill" items="${bills}">
            <li>
                ---------------------------<br>
                Bill id: ${bill.id} <br>
                Bill sum: ${bill.sum} <br>
                Bill date: ${bill.date} <br>
                Bill status: ${bill.billStatus.status} <br>

                <c:if test="${bill.billStatus.status eq 'ready'}">
                    <a href="../bank/pay-bill?bill=${bill.id}">press to pay bill</a> <br>
                    <a href="../bank/delete?bill=${bill.id}">press to delete bill</a><br>
                </c:if>
                <c:if test="${bill.billStatus.status eq 'paid'}">
                    this bill was paid <br>
                    <a href="../bank/delete?bill=${bill.id}">press to delete bill</a><br>
                </c:if>
                ---------------------------<br>
            </li>
        </c:forEach>
    </ul>
</c:if>
<nav >
    <ul class="pagination">
        <c:if test="${page.number!= 1}">
            <a class="page-link" href="../bank/payments?page=${page.number-1}&card=${bills.get(0).card.id}">Previous</a>
        </c:if>
        </li>
        <c:forEach begin="1" end="${noOfPages}" var="i">
        <c:choose>
            <c:when test="${page.number eq i} ">
                <td>${i}</td>
            </c:when>
            <c:otherwise>
                <li class="page-item"><a class="page-link" href="../bank/payments?page=${i}&card=${bills.get(0).card.id}&sort=${page.state}">${i}</a></li>
                <li class="page-item active">
            </c:otherwise>
        </c:choose>
            </c:forEach>
        <li class="page-item">
            <c:if test="${page.number lt noOfPages}">
                <a class="page-link" href="../bank/payments?page=${page.number + 1}&card=${bills.get(0).card.id}">Next</a>
            </c:if>
        </li>
    </ul>
</nav>
<br>
<hr>
<br>


<form action="../bank/make-payment" method="get">
    <input type="number" name="sum" id="sum" placeholder="input sum" onkeyup="matchMakePayment(${currentCard.balance})">
    <span id="messagePayment"></span><br>
    <button type="submit" id="button" onsubmit="checkNullPayments()">Make payment</button>
</form>

<br>
<hr>
<br>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0/dist/js/bootstrap.bundle.min.js" integrity="sha384-A3rJD856KowSb7dwlZdYEkO39Gagi7vIsF0jrRAoQmDKKtQBHUuLZ9AsSv4jD4Xa" crossorigin="anonymous"></script>

</body>
</html>

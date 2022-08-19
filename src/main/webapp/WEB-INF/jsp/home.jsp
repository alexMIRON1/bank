<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Home page</title>
    <%@include  file="header.html" %>
</head>
<body>

<br>

<c:if test="${client.role.role eq 'admin'}">
    <a href="../bank/admin">Admin</a> |
</c:if>
<a href="../bank/logout">Log out</a>

<br>
<hr>
<br>

<h1>Welcome ${client.login}!</h1>
<br>
<hr>
<br>
<c:choose>
    <c:when test="${client.login eq 'admin'}">
        <h2>Yours cards</h2>
    </c:when>
    <c:otherwise>
        <h2>Client's cards</h2>
    </c:otherwise>
</c:choose>

<br>

<c:if test="${empty cards}">
    <h2>You still do not have any cards</h2>
</c:if>

<c:if test="${not empty cards}">
    | <a href="../bank/home?sort=id">sort by id</a> |
    <a href="../bank/home?sort=name">sort by name</a> |
    <a href="../bank/home?sort=balance">sort by balance</a> |
    <br>
    <br>
    <ul>
        <c:forEach var="card" items="${cards}">
            <div class="modal fade" id="windowModalCenter" tabindex="-1" role="dialog" aria-labelledby="windowModalCenterTitle" aria-hidden="true">
                <div class="modal-dialog modal-dialog-centered" role="document">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h5 class="modal-title" id="windowModalLongTitle">Card name</h5>
                            <button type="button" class="close" data-bs-dismiss="modal" aria-label="Close">
                                <span aria-hidden="true">&times;</span>
                            </button>
                        </div>
                        <div class="modal-body">
                            <form action="../bank/setName" method="post">
                                <input type="text" value="${card.id}" name="card" hidden>
                                <input type="text" id = "customName" name="customName">
                                <button type="submit" class="btn btn-primary">OK</button>
                            </form>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                        </div>
                    </div>
                </div>
            </div>
            <li>

                --------------------------- <br>
                Card id: ${card.name} <br>
                Card name: ${card.customName}<br>
                Balance: ${card.balance} UAH <br>
                Status: ${card.cardStatus.status} <br>
                Owner: ${card.client.login} <br>

                <form action="../bank/top-up" method="post">
                    <input type="text" value="${card.id}" name="card" hidden>
                    <input type="number"name="top-up" id="top-up" placeholder="sum to top-up" onkeyup="matchTopUp()">
                    <button type="submit" id="button">Balance top-up</button>
                    <span id="messageTopUp"></span>
                </form>
                <c:choose>
                    <c:when test="${card.cardStatus.status eq 'unblocked'}">
                        | <a href="../bank/block?page=${page.number}&card=${card.id}">block card</a>
                    </c:when>
                    <c:when test="${card.cardStatus.status eq 'blocked'}">
                        | <a href="../bank/request-unblock?page=${page.number}&card=${card.id}">send request to unblock card</a>
                    </c:when>
                    <c:when test="${card.cardStatus.status eq 'ready to unblock'}">
                        please, wait while admin unblock yours card <br>
                    </c:when>
                </c:choose>

                <c:choose>
                        <c:when test="${empty card.customName}">
                            |<button type="button" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#windowModalCenter" data-bs-whatever="${card.id}">
                                Set name
                            </button>
                        </c:when>
                        <c:otherwise>
                            |<button type="button" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#windowModalCenter" data-bs-whatever="${card.id}">
                            Change name
                            </button>
                        </c:otherwise>
                </c:choose>

                | <a href="../bank/payments?card=${card.id}">payments</a> | <br>
                --------------------------- <br>
            </li>
        </c:forEach>
    </ul>
</c:if>
<nav >
    <ul class="pagination">
            <c:if test="${page.number!= 1}">
               <a class="page-link" href="../bank/home?page${page.number - 1}">Previous</a>
            </c:if>
        </li>
        <c:forEach begin="1" end="${noOfPages}" var="i">
            <c:choose>
                <c:when test="${page.number eq i} ">
                    <td>${i}</td>
                </c:when>
                <c:otherwise>
                    <li class="page-item"><a class="page-link" href="../bank/home?page=${i}&sort=${page.state}">${i}</a></li>
                    <li class="page-item active">
                </c:otherwise>
            </c:choose>
        </c:forEach>
        <li class="page-item">
        <c:if test="${page.number lt noOfPages}">
            <a class="page-link" href="../bank/home?page=${page.number + 1}">Next</a>
        </c:if>
        </li>
    </ul>
</nav>
<br>
<hr>
<br>

<form method="get" action="../bank/receive-card">
    <button type="submit">RECEIVE CARD</button>
</form>
<jsp:include page="validation.jsp"/>
<br>
<hr>
<br>
<script>
    var exampleModal = document.getElementById('windowModalCenter')
    exampleModal.addEventListener('show.bs.modal', function (event) {
        var button = event.relatedTarget
        var id = button.getAttribute('data-bs-whatever')
        var modalTitle = exampleModal.querySelector('.modal-title')
        var modalBodyInput = exampleModal.querySelector('.modal-body input')

        modalTitle.textContent = 'Set name with card id:  ' + id
        modalBodyInput.value = id
    })
</script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0/dist/js/bootstrap.bundle.min.js" integrity="sha384-A3rJD856KowSb7dwlZdYEkO39Gagi7vIsF0jrRAoQmDKKtQBHUuLZ9AsSv4jD4Xa" crossorigin="anonymous"></script>
</body>
</html>

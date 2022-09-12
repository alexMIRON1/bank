<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="l" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<l:setLocale/>
<html>
<head>
    <title><fmt:message key="payments.title"/></title>
</head>
<body>
<%@include  file="fragments/header.jspf" %>

        <br>
        <hr>
        <br>
        <div class="row row-cols-1 row-cols-md-3 mb-1 text-center">
            <div class="col">
            </div>
            <div class="col">
                <div class="card mb-4 rounded-3 shadow-sm" style="background-image: url('https://wallpaperaccess.com/full/95239.jpg')">
                    <div class="card-header py-3">
                        <h4 class="my-0 fw-normal text-success" >${currentCard.name}</h4>
                    </div>
                    <div class="card-body" >
                        <h1 class="card-title pricing-card-title text-warning"><fmt:message key="home.currency"/>${currentCard.balance}</h1>
                        <ul class="list-unstyled mt-3 mb-4">
                            <li class="text-white"><fmt:message key="home.card.name"/> ${currentCard.customName}</li>
                            <li class="text-white"><fmt:message key="home.card.status"/> ${currentCard.cardStatus.status}</li>
                            <li class="text-white"><fmt:message key="home.card.owner"/> ${currentCard.client.login}</li>
                        </ul>
                    </div>
                </div>
            </div>
            <div class="col"></div>
        </div>
        <br>
        <hr>
        <br>

        <c:if test="${empty bills}">
            <div class="text-center">
                <h2><fmt:message key="payments.empty_bills"/></h2>
            </div>
        </c:if>

        <c:if test="${not empty bills}">
            <div class="dropdown">
                <button class="btn btn-warning dropdown-toggle" type="button" id="dropdownMenuButton1" data-bs-toggle="dropdown" aria-expanded="false">
                    <fmt:message key="home.sort.title"/>
                </button>
                <ul class="dropdown-menu" aria-labelledby="dropdownMenuButton1">
                    <li><a href="../bank/payments?card=${bills.get(0).card.id}&sort=id" class="dropdown-item"><fmt:message key="payments.sort.id"/></a></li>
                    <li><a href="../bank/payments?card=${bills.get(0).card.id}&sort=newest" class="dropdown-item"><fmt:message key="payments.sort.newest"/></a></li>
                    <li><a href="../bank/payments?card=${bills.get(0).card.id}&sort=latest" class="dropdown-item"><fmt:message key="payments.sort.latest"/></a></li>
                </ul>
            </div>
            <table class="table">
                <thead class="table-dark">
                <tr>
                    <th scope="col"><fmt:message key="payments.table.id"/></th>
                    <th scope="col"><fmt:message key="payments.table.sum"/></th>
                    <th scope="col"><fmt:message key="payments.table.date"/></th>
                    <th scope="col"><fmt:message key="payments.table.status"/></th>
                    <th scope="col"><fmt:message key="payments.table.recipient"/></th>
                    <th scope="col"></th>
                    <th scope="col"></th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="bill" items="${bills}">
                    <div class="modal fade" id="windowModalCenter3" tabindex="-1" role="dialog" aria-labelledby="windowModalCenter3Title" aria-hidden="true">
                        <div class="modal-dialog modal-dialog-centered" role="document">
                            <div class="modal-content">
                                <div class="modal-header">
                                    <h5 class="modal-title" id="windowModalLongTitle3"><fmt:message key="payments.modal.title"/></h5>
                                    <button type="button" class="close" data-bs-dismiss="modal" aria-label="Close">
                                        <span aria-hidden="true">&times;</span>
                                    </button>
                                </div>
                                <div class="text-start">
                                    <div class="modal-body">
                                        <form action="../bank/receipt" method="post">
                                            <input type="text" value="${bill.id}" name="bill" hidden>
                                            <input type="email" id = "email" name="email" onkeyup="matchEmail()">
                                            <button type="submit" id = "buttonEmail" class="btn btn-success" onclick="matchEmail()"><fmt:message key="payments.modal.button.send"/></button><br>
                                            <label class="form-label text-start" for="email"><fmt:message key="payments.modal.input.email"/></label><br>
                                            <span id="messageEmail"></span>
                                        </form><br>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="modal fade" id="windowModalCenter2" tabindex="-1" role="dialog" aria-labelledby="windowModalCenter2Title" aria-hidden="true">
                        <div class="modal-dialog modal-dialog-centered" role="document">
                            <div class="modal-content">
                                <div class="modal-header">
                                    <h5 class="modal-title" id="windowModalLongTitle2"><fmt:message key="payments.modal.pay"/></h5>
                                    <button type="button" class="close" data-bs-dismiss="modal" aria-label="Close">
                                        <span aria-hidden="true">&times;</span>
                                    </button>
                                </div>
                                <div class="text-start">
                                    <div class="modal-body">
                                        <form action="../bank/pay-bill" method="post">
                                            <input type="text" value="${bill.id}" name="bill" hidden>
                                            <input type="text" id = "toCard" name="toCard" onkeyup="matchPayBill()">
                                            <button type="submit" id = "buttonPay" class="btn btn-success" onclick="matchPayBill()"><fmt:message key="payments.modal.button.pay"/></button><br>
                                            <label class="form-label text-start" for="email"><fmt:message key="payments.modal.input.id"/></label><br>
                                            <span id="messagePay"></span>
                                        </form><br>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <tr>
                        <th scope="row">${bill.id}</th>
                        <td>${bill.sum}</td>
                        <td>${bill.date}</td>
                        <td>${bill.billStatus.status}</td>
                        <td>${bill.recipient}</td>
                        <td><c:if test="${bill.billStatus.status eq 'ready'}">
                            <a href="#" class="fw-bold text-body" data-bs-toggle="modal" data-bs-target="#windowModalCenter2" data-bs-whatever="${bill.id}"><fmt:message key="payments.table.pay"/></a><br>
                            <a href="../bank/delete?bill=${bill.id}" class="fw-bold text-body"><fmt:message key="payments.table.delete"/></a><br>
                        </c:if>
                            <c:if test="${bill.billStatus.status eq 'paid'}">
                                <fmt:message key="payments.table.paid"/> <br>
                                <a href="../bank/delete?bill=${bill.id}" class="fw-bold text-body"><fmt:message key="payments.table.delete"/></a><br>
                            </c:if></td>
                        <c:if test="${bill.billStatus.status eq 'paid'}">
                            <td><a href="#" class="fw-bold text-body" data-bs-toggle="modal" data-bs-target="#windowModalCenter3" data-bs-whatever="${bill.id}"><fmt:message key="payments.table.receipt"/></a></td>
                        </c:if>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </c:if>
        <nav >
            <ul class="pagination">
                <c:if test="${page.number!= 1}">
                    <a class="page-link" href="../bank/payments?page=${page.number-1}&card=${bills.get(0).card.id}"><fmt:message key="pagination.previous"/></a>
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
                        <a class="page-link" href="../bank/payments?page=${page.number + 1}&card=${bills.get(0).card.id}"><fmt:message key="pagination.next"/></a>
                    </c:if>
                </li>
            </ul>
        </nav>
        <div class="d-flex justify-content-center">
            <form action="../bank/make-payment" method="get">
                <div class="mb-3">
                    <label for="sum" class="form-label"><fmt:message key="payments.form.title"/></label>
                    <input type="number" name="sum" id="sum" class="form-control" onkeyup="matchMakePayment(${currentCard.balance})" >
                    <span id="messagePayment"></span>
                    <div class="d-flex justify-content-center">
                        <button type="submit" id="button" class="btn btn-success" onclick="matchMakePayment(${currentCard.balance})"><fmt:message key="payments.form.button"/></button>
                    </div>
                </div>
            </form>
        </div>

        <br>
        <hr>
        <br>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0/dist/js/bootstrap.bundle.min.js" integrity="sha384-A3rJD856KowSb7dwlZdYEkO39Gagi7vIsF0jrRAoQmDKKtQBHUuLZ9AsSv4jD4Xa" crossorigin="anonymous"></script>
<jsp:include page="validation.jsp"/>
<script>
    var modalEmail = document.getElementById('windowModalCenter3')
    modalEmail.addEventListener('show.bs.modal', function (event) {
        var button = event.relatedTarget
        var id = button.getAttribute('data-bs-whatever');
        var modalTitle = modalEmail.querySelector('.modal-title')
        var modalBodyInput = modalEmail.querySelector('.modal-body input')
        modalTitle.textContent = '<fmt:message key="payments.modal.title"/>  ' + id;
        modalBodyInput.value = id
    })
</script>
<script>
    var modalPay = document.getElementById('windowModalCenter2')
    modalPay.addEventListener('show.bs.modal', function (event) {
        var button = event.relatedTarget
        var id = button.getAttribute('data-bs-whatever');
        var modalTitle = modalPay.querySelector('.modal-title')
        var modalBodyInput = modalPay.querySelector('.modal-body input')

        modalTitle.textContent = '<fmt:message key="payments.modal.pay"/>  ' + id;
        modalBodyInput.value = id
    })
</script>
</body>
</html>

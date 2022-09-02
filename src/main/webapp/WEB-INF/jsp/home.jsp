<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="l" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<l:setLocale/>
<html>
<head>
    <title><fmt:message key="home.title"/></title>
    <%@include  file="fragments/header.jspf" %>
</head>
<body>
        <br>
        <hr>
        <br>
        <div class="text-center">
            <h1><fmt:message key="home.welcome"/> ${client.login}!</h1>
        </div>
        <br>
        <c:if test="${empty cards}">
            <h2><fmt:message key="home.empty_cards"/></h2>
        </c:if>

        <c:if test="${not empty cards}">
            <div class="dropdown">
                <button class="btn btn-warning dropdown-toggle" type="button" id="dropdownMenuButton1" data-bs-toggle="dropdown" aria-expanded="false">
                    <fmt:message key="home.sort.title"/>
                </button>
                <ul class="dropdown-menu" aria-labelledby="dropdownMenuButton1">
                    <li><a href="../bank/home?sort=id" class="dropdown-item"><fmt:message key="home.sort.id"/></a></li>
                    <li><a href="../bank/home?sort=name" class="dropdown-item"><fmt:message key="home.sort.name"/></a></li>
                    <li><a href="../bank/home?sort=balance" class="dropdown-item"><fmt:message key="home.sort.balance"/></a></li>
                </ul>
            </div>
            <div class="row row-cols-1 row-cols-md-3 mb-1 text-center">
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
                                <div class="text-start">
                                    <div class="modal-body">
                                        <form action="../bank/top-up" method="post">
                                            <input type="text" value="${card.id}" name="card" hidden>
                                            <input type="number" name="top-up" id="top-up" onkeyup="matchTopUp()">
                                            <button type="submit" id="button" class="btn btn-success"><fmt:message key="home.modal.top-up.button"/></button><br>
                                            <label class="form-label" for="top-up"><fmt:message key="home.modal.top-up.label"/></label>
                                            <span id="messageTopUp"></span>
                                        </form>
                                        <form action="../bank/setName" method="post">
                                            <input type="text" id="textName" value="${card.id}" name="card" hidden>
                                            <input type="text" id = "customName" name="customName" onkeyup="matchSetName()">
                                            <button type="submit" id = "buttonName" class="btn btn-success" onclick="matchSetName()"><fmt:message key="home.modal.name.set"/></button><br>
                                            <label class="form-label text-start" for="customName"><fmt:message key="home.modal.name.label"/></label>
                                            <span id = "messageSetName"></span>
                                        </form>
                                        <c:choose>
                                            <c:when test="${card.cardStatus.status eq 'unblocked'}">
                                                <br><fmt:message key="home.modal.block.title"/>
                                                <a href="../bank/block?page=${page.number}&card=${card.id}"  class="fw-bold text-danger"> <fmt:message key="home.modal.block.link"/></a>
                                            </c:when>
                                            <c:when test="${card.cardStatus.status eq 'blocked'}">
                                                <br><fmt:message key="home.modal.request.title"/>
                                                <a href="../bank/request-unblock?page=${page.number}&card=${card.id}"  class="fw-bold text-success"><fmt:message key="home.modal.request.link"/></a>
                                            </c:when>
                                            <c:when test="${card.cardStatus.status eq 'ready to unblock'}">
                                                <br><fmt:message key="home.modal.info"/><br>
                                            </c:when>
                                        </c:choose>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="col">
                        <div class="card mb-4 rounded-3 shadow-sm" style="background-image: url('https://wallpaperaccess.com/full/95239.jpg')">
                            <div class="card-header py-3">
                                <h4 class="my-0 fw-normal text-success"><a href="#" class="text-success" data-bs-toggle="modal" data-bs-target="#windowModalCenter" data-bs-whatever="${card.id},${card.customName}">${card.name}</a></h4>
                            </div>
                            <div class="card-body" >
                                <h1 class="card-title pricing-card-title text-warning"><fmt:message key="home.currency"/>${card.balance}</h1>
                                <ul class="list-unstyled mt-3 mb-4">
                                    <li class="text-white"><fmt:message key="home.card.name"/> ${card.customName}</li>
                                    <li class="text-white"><fmt:message key="home.card.status"/> ${card.cardStatus.status}</li>
                                    <li class="text-white"><fmt:message key="home.card.owner"/> ${card.client.login}</li>
                                </ul>
                                <button type="button" class="w-100 btn btn-lg btn-warning" onclick="window.location.href = '../bank/payments?card=${card.id}'" ><fmt:message key="home.payments"/></button>
                            </div>
                        </div>
                    </div>
                </c:forEach>
            </div>
        </c:if>
        <nav >
            <ul class="pagination">
                    <c:if test="${page.number!= 1}">
                       <a class="page-link" href="../bank/home?page${page.number - 1}"><fmt:message key="pagination.previous"/></a>
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
                    <a class="page-link" href="../bank/home?page=${page.number + 1}"><fmt:message key="pagination.next"/></a>
                </c:if>
                </li>
            </ul>
        </nav>
        <form method="get" action="../bank/receive-card">
            <div class="d-flex justify-content-center">
                <button type="submit" class="btn btn-success btn-lg"><fmt:message key="home.receive-card"/></button>
            </div>
        </form>
        <br>
        <hr>
        <br>
<jsp:include page="validation.jsp"/>
<script>
    var exampleModal = document.getElementById('windowModalCenter')
    exampleModal.addEventListener('show.bs.modal', function (event) {
        var button = event.relatedTarget
        var id = button.getAttribute('data-bs-whatever').split(",")[0];
        var customName = button.getAttribute('data-bs-whatever').split(",")[1]
        var modalTitle = exampleModal.querySelector('.modal-title')
        var modalBodyInput = exampleModal.querySelector('.modal-body input')

        modalTitle.textContent = '<fmt:message key="home.modal.title"/> ' + customName;
        modalBodyInput.value = id
        document.getElementById('textName').value = id;
    })
</script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0/dist/js/bootstrap.bundle.min.js" integrity="sha384-A3rJD856KowSb7dwlZdYEkO39Gagi7vIsF0jrRAoQmDKKtQBHUuLZ9AsSv4jD4Xa" crossorigin="anonymous"></script>
</body>
</html>

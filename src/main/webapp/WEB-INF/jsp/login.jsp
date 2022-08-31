<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="l" %>
<%@ taglib prefix = "cur" uri = "/WEB-INF/jstl-tld/custom.tld"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<l:setLocale/>
<html>
<head>
    <title><fmt:message key="login.title"/></title>
</head>
<body>
<%@include  file="fragments/header.jspf" %>
<p class="fw-bolder"><cur:Currency/></p>
<section class="vh-100 bg-image"
         style="background-image: url('https://mdbcdn.b-cdn.net/img/new/fluid/city/055.webp');">
    <div class="mask d-flex align-items-center h-100 gradient-custom-3">
        <div class="container h-100">
            <div class="row d-flex justify-content-center align-items-center h-100">
                <div class="col-12 col-md-9 col-lg-7 col-xl-6">
                    <div class="card" style="border-radius: 15px;">
                        <div class="card-body p-5">
                            <h2 class="text-uppercase text-center mb-5"><fmt:message key="login.form.name"/></h2>

                            <form action="../bank/toLogin" method="post">
                                <div class="form-outline mb-4">
                                    <input type="text" id="login" name="login" class="form-control form-control-lg" onkeyup="validateRegister()" />
                                    <label class="form-label" for="login"><fmt:message key="login.label.login"/></label><br>
                                    <span id='messageLogin'></span><br>
                                </div>
                                <div class="form-outline mb-4">
                                    <input type="password" id="password" name="password" class="form-control form-control-lg" onkeyup="validateRegister()" />
                                    <label class="form-label" for="password"><fmt:message key="login.label.password"/></label><br>
                                    <span id='messagePassword'></span><br>
                                </div>
                                <div class="d-flex justify-content-center">
                                    <button type="submit" id="button" onclick="validateRegister()"
                                            class="btn btn-success btn-block btn-lg gradient-custom-4 text-body"><fmt:message key="login.button.login"/></button>
                                </div>

                                <p class="text-center text-muted mt-5 mb-0"><fmt:message key="login.link.question"/> <a href="../bank/register"
                                                                                                        class="fw-bold text-body"><u><fmt:message key="login.link.answer"/></u></a></p>

                            </form>

                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</section>
<br>
<hr>
<jsp:include page="validation.jsp"/>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0/dist/js/bootstrap.bundle.min.js" integrity="sha384-A3rJD856KowSb7dwlZdYEkO39Gagi7vIsF0jrRAoQmDKKtQBHUuLZ9AsSv4jD4Xa" crossorigin="anonymous"></script>

</body>
</html>

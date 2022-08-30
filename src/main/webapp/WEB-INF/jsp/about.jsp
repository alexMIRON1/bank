<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="l" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<l:setLocale/>
<html>
<head>
    <title><fmt:message key="about.title"/></title>
    <%@include  file="fragments/header.jspf" %>
    <style>
        h1{display: inline}
    </style>
</head>
<body>
<section class="vh-100 bg-image"
         style="background-image: url('https://wallpapercave.com/wp/wp4472149.jpg');">
    <div class="col-lg-8 mx-auto p-3 py-md-5 text-white">
        <main>
            <h1><fmt:message key="about.title.label"/> </h1> <h1 class="text-warning">IBANK</h1>
            <p class="fs-5 col-md-8"><fmt:message key="about.information"/>
                <br><fmt:message key="about.link.question"/></p>
            <div class="mb-5">
                <a href="../bank/login" class="btn btn-success btn-lg px-4"><fmt:message key="about.link.answer"/></a>
            </div>

            <hr class="col-3 col-md-2 mb-5">

            <div class="row g-5">
                <div class="col-md-6">
                    <h2><fmt:message key="about.contact.title"/> </h2>
                    <ul class="icon-list">
                        <li><fmt:message key="about.contact.email"/> <a href="https://mail.google.com/mail/u/0/#inbox" rel="noopener" target="_blank">andrej14883642@gmail.com</a></li>
                        <li class="text-white"><fmt:message key="about.contact.phone"/> +380681934561</li>
                    </ul>
                </div>
            </div>
        </main>
    </div>
</section>
</body>
</html>

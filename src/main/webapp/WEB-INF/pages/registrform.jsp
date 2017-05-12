<%@ taglib prefix="c" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>

<html>
<head>

  <link href="<c:url value="/resources/css/form.css" />" rel="stylesheet">

  <title>Home</title>
</head>
<body>

<%--        method is POST    model is "user"     action is "showinfor"         --%>
<form:form method = "post" commandName="user" action="showinfor" class="box login">
  <div id="img">
    <img src="${pageContext.request.contextPath}/resources/images/manas_logo.jpg" width="110" height="110" align="center">
    <h1><span style="color: #3464FF">IS:</span>KTUM</h1>
  </div>
  <fieldset class="boxBody">
    <form:label path="name">User</form:label>
    <form:input path="name"  tabindex="1" placeholder="user name" required="please"/>

    <label><a href="#" class="rLink" tabindex="5">Fogot password?</a>Пароль</label>

    <form:input path="password" type="password" tabindex="2"  placeholder="you password" required="please" />
  </fieldset>

  <footer>
    <label><input type="checkbox" tabindex="3">Remember me</label>
    <input type="submit" class="btnLogin" value="Login" tabindex="4">
  </footer>

  <div id="author">
    <h1>Manas Programmers</h1>
  </div>

</form:form>

</body>
</html>

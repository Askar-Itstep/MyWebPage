<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--далее не тот error 9errorPage)!--%>
<jsp:useBean id="error" scope="session" class="java.lang.String"/>
<html>
<head>
  <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
  <title>Login page</title>
  <style>
    body {font-family: Arial, Helvetica, sans-serif;}
    div.container {
      margin: 0;
      position: absolute;
      top: 50%;
      left: 50%;
      transform: translate(-50%, -50%)
    }
  </style>
</head>
<body>
<div class="container" style="border-radius: 5px; border-color: #7abaff;">
  <h3>Войдите или зарегистрируйтесь</h3>
  <form name="login" method="post" action="<c:url value="/auth"/>">
    <div class="input-group mb-3">
      <div class="input-group-prepend">
        <span class="input-group-text" id="basic-addon1">Логин</span>
      </div>
      <input type="text" name="login" class="form-control" placeholder="Введите логин" required aria-label="Введите логин" aria-describedby="basic-addon1">
    </div>
    <div class="input-group mb-3">
      <div class="input-group-prepend">
        <span class="input-group-text" id="basic-addon2">Пароль</span>
      </div>
      <input type="password" name="password" class="form-control" placeholder="Введите пароль" required aria-label="Введите пароль" aria-describedby="basic-addon2">
    </div>
    <div>
      <button type="submit" id="submit" class="btn btn-primary">Войти</button>
    </div>
  </form>

  <c:set var="flagGuest" scope="request" value="true"></c:set>
  <form method="post" action="<c:url value="/auth"/>">
    <input type="submit" value="Войти как гость" class="btn btn-primary">
    <input type="hidden" name="flagGuest" value="${flagGuest}">
  </form>

  <div>
    <a href="<c:url value="/register.jsp"/>">Зарегистрироваться</a>
  </div>
  <div>
    <br>
    <span class="badge badge-danger">${error}</span>
  </div>
</div>
</body>
</html>
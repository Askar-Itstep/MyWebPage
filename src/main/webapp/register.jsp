<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
          integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
    <title>Registration page</title>
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
<div class="container">
    <form name="login" method="post" action="<c:url value="/register"/>"
          class="bs-example bs-example-form" data-example-id="simple-input-groups">

        <div class="input-group mb-3">
            <label for="txtUsername"><b>Введите имя</b></label>
            <input type="text" id="txtUsername" class="form-control" placeholder="Введите имя" name="username" required>
        </div>
        <div class="input-group mb-3">
            <label for="txtLogin"><b>Придумайте логин</b></label>
            <input type="text" id="txtLogin" class="form-control" placeholder="Придумайте логин" name="login" required>
        </div>
        <div class="input-group mb-3">
            <label for="txtFirstPassword"><b>Введите пароль</b></label>
            <input type="password" id="txtFirstPassword" class="form-control" placeholder="Придумайте пароль" name="firstPassword" required>
        </div>
        <div class="input-group mb-3">
            <label for="txtPassword"><b>Повторите пароль</b></label>
            <input type="password" id="txtPassword" class="form-control" placeholder="Повторите пароль" name="password" required>
        </div>
        <div>
            <button class="btn btn-primary" type="submit">Зарегистрироваться</button>
        </div>
    </form>
</div>
</body>
</html>

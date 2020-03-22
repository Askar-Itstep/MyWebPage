<%@ page import="java.util.List" %>
<%@ page import="java.util.stream.Collectors" %>
<%@ page import="model.Friend" %>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Main page</title>
    <link rel="stylesheet" href="styles.css">
    <style>
        body { background-color: #b0e0e6;  }
        table{background-color: aliceblue}
    </style>
</head>
<body >
<div id="header">
    <h1>Персональный сайт гостя</h1>
    <div id="nav">
        <ul>
            <li><a href = "http://localhost:8080/MyWebPage_war/">Выйти </a></li>
            <li><a href ="<c:url value="/mainForUser.jsp"/>" class="disabled">Главная</a></li>
            <li><a href ="<c:url value="/forum.jsp"/>" class="disabled">Форум</a></li>
            <li><a href= "<c:url value="/contactus.jsp"/>"class="disabled">Контакты</a></li>
            <li><a href="<c:url value="/userGallery.jsp"/>"class="disabled">Моя галерея</a></li>
        </ul>
    </div>
</div>
<c:set var="flagGuest" value="true" scope="session"></c:set>
<wrapper>
    <div id="sidebar1" class="aside">
        <h2>Мое кредо</h2>
        <p>Халява</p>
    </div>
    <div id="sidebar2" class="aside">
        <h2>Желаете зайти в гости к Пушкину или Малдеру, а может к Скалли?</h2>
        <p>Для перехода на персональную страницу пользователя нажмите "Пойти в гости"</p>
    </div>
    <div id="article">
        <table width="70%"cellspacing="15" cellpadding="5" bgcolor="#dcdcdc" style="font-size: small">
            <caption><h2>Я иду в гости к ...</h2></caption>
            <tr style="font-weight: bold">
                <td>Респондент</td>
                <td>Аватар</td>
                <td>Кредо</td>
            </tr>
            <c:forEach items="${persons}" var="person">
                <tr>
                    <td><c:out value="${person.username}"></c:out></td>
                    <td><img src="Images/${images.stream().filter(img->img.getId()==person.getAvatarId()).findFirst().get().getImageName()}" width="80"></td>
                    <td><c:out value='${person.getCredo()}'></c:out></td>
                    <td>
                        <form method="post" action="<c:url value="/guest"/>" style="display: inline">
                            <input type="hidden" name="pers_id" value="${person.id}">
                            <input type="hidden" name="flagGuest" value="${flagGuest}">
                            <input type="submit"  value="Пойти в гости" style="width:110Px;height:40Px; font-weight: bold">
                        </form>
                </tr>
            </c:forEach>
        </table>
    </div>

</wrapper>

<div id="footer">
    <p>Contacts: admin@mysite.com</p>
    <p>Copyright © ItStep, 2019</p>
</div>
</body>
</html>

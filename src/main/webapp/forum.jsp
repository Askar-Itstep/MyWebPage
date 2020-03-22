<%@ page import="java.util.List" %>
<%@ page import="java.util.stream.Collectors" %>
<%@ page import="model.Friend" %>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Forum page</title>
    <link rel="stylesheet" href="styles.css">
    <style>
        body { background-color: #b0e0e6;  }
        table{background-color: aliceblue}
    </style>
</head>
<body>
<div id="header">
    <h1>Персональный сайт ${person.username}</h1>
    <div id="nav">
        <ul>
            <li><a href = "http://localhost:8080/MyWebPage_war/">Выйти </a></li>
            <li><a href ="<c:url value="/mainForUser.jsp"/>">Главная</a></li>
            <li><a href="<c:url value="/forum.jsp"/>">Форум</a></li>
            <li><a href= "<c:url value="/contactus.jsp"/>">Контакты</a></li>
            <li><a href="<c:url value="/userGallery.jsp"/>">Моя галерея</a></li>
        </ul>
    </div>
</div>
<wrapper >
    <div id="sidebar1" class="aside">
        <h2>Высказывания о дружбе</h2>
        <p>"Настоящий друг с тобой, когда ты не прав. Когда ты прав, всякий будет с тобой.
            Марк Твен..."</p>
    </div>
    <div id="sidebar2" class="aside" style="font-size: small">
        <h2>Возможно вы знакомы</h2>
        <p>Можете добавить друга просто нажав кнопку</p>
        <c:forEach items="${candidats}" var="candidat">
            <tr>
                <td><c:out value="${candidat.username}"></c:out></td>
                <td><img src="Images/${images.stream().filter(img->img.getId()==candidat.getAvatarId()).findFirst().get().getImageName()}" width="55"></td>
                <td>
                    <form method="post" action="<c:url value="/addFriend"/>" >
                        <input type="hidden" name="pers_id" value="${person.id}">
                        <input type="hidden" name="candidat_id" value="${candidat.id}">
                        <input type="submit"  value="Добавить?" style="width:80Px;height:20Px; font-weight: bold">
                    </form>
                </td>
            </tr>
        </c:forEach>
    </div>
    <div id="article" >
        <table width="40%"cellspacing="15" cellpadding="5" bgcolor="#dcdcdc" style="font-size: small">
            <caption><h2>Мои друзья</h2></caption>
            <tr style="font-weight: bold">
                <td>Респондент</td>
                <td>Аватар</td>
                <td>Сообщение друга</td>
                <td>Мои сообщения</td>
                <td>Новое сообщение</td>
            </tr>
            <c:forEach items="${friends}" var="friend">
                <tr>
                    <td><c:out value="${friend.username}"></c:out></td>
                    <td><img src="Images/${images.stream().filter(img->img.getId()==friend.getAvatarId()).findFirst().get().getImageName()}" width="65"></td>
                    <td><c:out value='${friend.getMessages().get("")}'></c:out></td>
                    <td><c:out value='${friend.getMessages().keySet().stream().reduce(" ", (n, p)->p+=" "+=n)}'>></c:out></td>
                    <td>
                        <form method="post" action="<c:url value="/message"/>" style="display: inline">
                            <textarea name="new_message" class="responseField" wrap="soft"></textarea>
                            <input type="hidden" name="pers_id" value="${person.id}">
                            <input type="hidden" name="friend_id" value="${friend.id}">
                            <input type="submit"  value="Send" style="width:60Px;height:20Px; font-weight: bold">
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


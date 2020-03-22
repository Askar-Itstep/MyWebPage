<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Контакты</title>
    <link rel="stylesheet" href="styles.css">
    <style>
        body { background-color: #b0e0e6;  }
        table{background-color: aliceblue}
    </style>
    <script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.5/jquery.min.js"></script>
    <script src="myScripts.js" type="text/javascript"></script>
    <script>
        $(window).load(function () {
            localStorage['keyGuest'] = '${flagGuest}';
            var guest = localStorage['keyGuest'];
            if(guest != 'true') {
                alert("Это хозяин страницы");
                $('input').attr('disabled', false);
                $('.myRef').click(function(e) {
                    return true;
                });

            } else {
                $('.myRef').click(function(e) {
                    e.preventDefault();
                });
            }
        });
    </script>
</head>
<body>
<div id="header">
    <h1>Персональный сайт ${person.username}</h1>
    <div id="nav">
        <ul>
            <li><a href = "http://localhost:8080/MyWebPage_war/">Выйти </a></li>
            <li><a href ="<c:url value="/mainForUser.jsp"/>">Главная</a></li>
            <li><a class="myRef" href ="<c:url value="/forum.jsp"/>">Форум</a></li>
            <li><a href= "<c:url value="/contactus.jsp"/>">Контакты</a></li>
            <li><a href="<c:url value="/userGallery.jsp"/>">Моя галерея</a></li>
        </ul>
    </div>
</div>
<wrapper>
    <div id="sidebar1" class="aside">
<%--        <h2>Мое кредо</h2>--%>
<%--        <p>${person.credo}</p>--%>
    </div>
    <div id="sidebar2" class="aside">
        <h2>По всем имеющимся вопросам прошу обращаться по телефонам или на электронную почту</h2>

    </div>
    <div id="article">
        <h2>Мои контакты</h2>
        <br>
        <p>Мое имя: ${person.username}</p>
<%--        <table width="100%" align="left">--%>
            <p>Мой телефон: <c:out value="${person.phone}"></c:out></p>
            <p>Мой email: <c:out value="${person.email}"></c:out></p>
            <p>Мой адрес: <c:out value="${person.address}"></c:out></p>
            <p><iframe src="https://ru.wikipedia.org/wiki/Фокс_Малдер"  width="90%" height="600"></iframe></p>

<%--        </table>--%>

    </div>
</wrapper>
<div id="footer">
    <p>Contacts: admin@mysite.com</p>
    <p>Copyright © ItStep, 2019</p>
</div>
</body>
</html>

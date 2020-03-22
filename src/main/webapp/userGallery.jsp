
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <link rel="stylesheet" href="styles.css">
    <title>Gallery page</title>
    <style>
        body { background-color: lavender  }
        table{background-color: aliceblue}
    </style>
    <script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.5/jquery.min.js"></script>
    <script src="myScripts.js" type="text/javascript"></script>
    <script>
        $(window).load(function () {
            localStorage['keyGuest'] = '${flagGuest}';
            var guest = localStorage['keyGuest'];
            if(guest != 'true') {
                // alert("Это хозяин страницы");
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
            <li><a href="#">Контакты</a></li>
            <li><a href="<c:url value="/userGallery.jsp"/>">Моя галерея</a></li>
        </ul>
    </div>
</div>

<div class="wrapper">
    <div id="sidebar1" class="aside">
        <h2>Роль фотографии в жизни человека</h2>
        <p>"«Хорошая фотография, как хорошая собака: немая, но красноречивая.»
            Эжен Атже, французский фотохудожник..."</p>
    </div>
    <div id="sidebar2" class="aside">
        <h2>А Вы помните как все было?</h2>
        <p>Здесь собраны снимки и изображения о самых лучших моментах моей жизни.</p>
    </div>
    <div id="article">
        <h2>Моя галерея</h2>
        <br>
        <p><table width="30%"cellspacing="55" cellpadding="5" bgcolor="#b0e0e6">
        <c:forEach items="${images}" var="image">
            <c:if test="${image.id != person.getAvatarId() && image.persId == person.id}">
                <tr>
                    <td><img src="Images/${image.imageName}" width="350"  alt="${image.imageName}"></td>
                    <td>
                        <form method="post" action="<c:url value="/deleteImage"/>" style="display: inline">
                            <input type="hidden" name="image_id" value="${image.id}">
                            <input type="hidden" name="pers_id" value="${person.id}">
                            <input type="submit" value="Удалить">
                        </form>
<%--                        <a href="<c:url value="/deleteImage?image_id=${image.id}&pers_id=${person_id}"/>">Удалить изображение</a>--%>
                    </td>
                </tr>
            </c:if>
        </c:forEach>
        <br>
        <a class="myRef" href="<c:url value="/uploadImage.jsp?pers_id=${person_id}"/>">Добавить изображение</a>
    </table></p>
    </div>
</div>
<div id="footer">
    <p>Contacts: admin@mysite.com</p>
    <p>Copyright © ItStep, 2019</p>
</div>
</body>
</html>

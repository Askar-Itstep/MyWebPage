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
    <script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.5/jquery.min.js"></script>
    <script src="myScripts.js" type="text/javascript"></script>
    <script>
        $(window).load(function () {
            localStorage['keyGuest'] = '${flagGuest}';
            var guest = localStorage['keyGuest'];
            if(guest != 'true') {
                //alert("Это хозяин страницы");
                // в форме чата исп-ся input'ы
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
<p hidden="hidden">flagGuest:  ${flagGuest}</p>
<body >
<div id="header">
    <h1>Персональный сайт ${person.username}</h1>
    <div id="nav">
        <ul>
            <li><a href = "http://localhost:8080/MyWebPage_war/">Выйти </a></li>
            <li><a  href ="<c:url value="/mainForUser.jsp"/>" >Главная</a></li>
            <li><a class="myRef"  href ="<c:url value="/forum.jsp"/>" >Форум</a></li>
            <li><a href= "<c:url value="/contactus.jsp"/>">Контакты</a></li>
            <li><a href="<c:url value="/userGallery.jsp"/>">Моя галерея</a></li>
        </ul>
    </div>
</div>

<wrapper>
    <div id="sidebar1" class="aside">
        <h2>Мое кредо</h2>
        <p>${person.credo}</p>
    </div>
    <div id="sidebar2" class="aside">
        <h2>Раздел мое творчество по сути является моим блогом...</h2>
        <p>Итак из записей.</p>

    </div>
    <div id="article" >
        <h2>Немного обо мне</h2>
        <br>
        <p>Мое имя: ${person.username}</p>
        <p>Мой возраст: ${person.age}</p>
        <table width="100%">
            <tr align="left">
                <c:forEach items="${images}" var="image">
                    <c:if test="${image.id == person.getAvatarId()}">
                        <td><img src="Images/${image.imageName}"  width="300" ></td>
                    </c:if>
                </c:forEach>
                <td><a class="myRef" href='<c:url value="/editPerson?pers_id=${person.id}"/>'>Редактировать</a> </td>
            </tr>
        </table>
        <h4>Мое творчество</h4>
        <table width="100%" cellspacing="55" cellpadding="5" bgcolor="#faebd7" style="font-size: medium">
            <c:forEach items="${mystories}" var="mystory">
                <tr align="center"><td><c:out value="${mystory.text}"/></td>
                    <td><a href='<c:url value="/editText?id=${mystory.id}"/>'>Редактировать</a>
                        <form  method="post" action="<c:url value="/deleteText"/>" style="display: inline">
                            <input type="hidden" name="mystory_id" value="${mystory.id}">
                            <input type="hidden" name="pers_id" value="${person.id}">
                            <input type="submit" disabled="disabled" value="Удалить">
                        </form>
                    </td>
                </tr>
            </c:forEach>
        </table>  <br>
        <form name="myStory" action="<c:url value="/createTextPage.jsp"/>">
            <input  disabled="disabled" type="submit"   value="Новая история">
            <input type="hidden" name="pers_id"  value="${person.id}">
        </form>
    </div>
</wrapper>
<div id="footer">
    <p>Contacts: admin@mysite.com</p>
    <p>Copyright © ItStep, 2019</p>
</div>
</body>
</html>

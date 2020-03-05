<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>Title</title>
</head>
<body>
<%--<P>flagAvatar: ${param.flagAvatar}</P>--%>

<h2>Выберите файл для загрузки</h2>
<form method="POST" action="<c:url value="/upload"/>" enctype="multipart/form-data" >
    <input type="file" name="file" />
    <input type="hidden" name="pers_id" value="${person.id}">
    <input type="hidden" name="flagAvatar" value="${param.flagAvatar}">
    <input type="submit" value="Загрузить изображение"/>
</form>

<br/>
<form method="POST" action="mainForUser.jsp">
    <input type="submit" value="Вернуться"  name="toBack"/>
</form>
</body>
</html>

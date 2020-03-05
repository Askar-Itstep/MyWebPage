<%--<jsp:useBean id="flagNoAvatar2" scope="request" />--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>Edit my page</title>
</head>
<body>

<c:set var="flagAvatar" scope="request" value="true"></c:set>

<h3>Коррекция данных</h3>
<%--avatar--%>
<form action="uploadImage.jsp" method="post">
    <input type="hidden" name="flagAvatar" value="${flagAvatar}">
    <input type="hidden" name="pers_id" value="${person.id}"  /><br><br>
    <input type="submit" value="Изменить аватар">
</form>
<%--username--%>
<form method="post" action="<c:url value="/editPerson"/>">
    <p>Имя: <input name="new_name" value="${person.username}" >
        <input type="submit" value="Изменить имя">

    </p>
</form>
<%--credo--%>
<form method="post" action="<c:url value="/editPerson"/>">
    <br>Новый девиз<br>
    <p>
        <label>
            <textarea name="person_credo" cols="80" rows="10"  placeholder="${person.credo}"></textarea>
        </label>
    </p>
    <input name="pers_id" value="${person.id}" type="hidden" /><br><br>
    <input type="submit" value="Изменить кредо" />
</form>
</body>
</html>

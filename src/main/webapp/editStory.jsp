<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="mystory" scope="request" class="model.MyStory"/>
<html>
<head>
    <meta charset="UTF-8">
    <title>Edit my story</title>
</head>
<body>
<h3>Edit my story</h3>
<form method="post" action="<c:url value="/editText"/>">
    <input type="hidden" value="${mystory.id}" name="id" />
    <label>Текст</label><br>
    <p>
        <label>
            <textarea name="text" cols="80" rows="10"  placeholder="${mystory.text}"></textarea>
        </label>
    </p>
    <input name="pers_id" value="${mystory.pers_id}" type="hidden" /><br><br>
    <input type="submit" value="Отправить" />
</form>
</body>
</html>

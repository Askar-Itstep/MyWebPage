<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
          integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
    <title>Обновление личной страницы</title>
</head>
<body>
<h3>Новая история</h3>
<form method="post" action="<c:url value="/createInfo"/>">
    <p>
        <textarea name="text" cols="80" rows="10"></textarea>
    </p>
<%
    int pers_id= Integer.parseInt(request.getParameter("pers_id"));
    request.setAttribute("pers_id", pers_id);
%>
    <input type= hidden name="pers_id" value="${pers_id}">
    <input type="submit" value="Save" />
</form>

</body>
</html>

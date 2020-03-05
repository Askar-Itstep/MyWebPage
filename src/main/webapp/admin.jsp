<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="persons" scope="session" type="java.util.List<model.Person>"/>
<jsp:useBean id="person" scope="session" class="model.Person"/>
<html>
<head>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
          integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
    <title>Main page</title>
    <script type="text/javascript">
        window.history.forward();
        function noBack() { window.history.forward(); }
    </script>
</head>
<%--<body onload="noBack();" onpageshow="if (event.persisted) noBack();" onunload="">--%>
<nav style = "background-color:coral; height:75px">
    <a href = "http://localhost:8080">Выйти </a> |
    <a href = "https://mystat.itstep.org/ru/auth/login/index">MyStat</a> |
    <a href = "https://www.google.kz">Google </a> |
    <p>Панель навигации </p>
</nav>
<article><p align="center">
    <table bgcolor="#9acd32" width="500">
        <h2>Таблица пользователей</h2>
        <tr>
            <th width="50">ID</th>
            <th>Логин</th>
            <th>Пароль</th>
            <th>Role ID</th>
            <th></th>
        </tr>
    <c:set var="now" value="${param.now}"></c:set>
        <c:forEach items="${persons}" var="person">

            <tr style="background-color:${(person.dateDel.toString().compareTo(now) > 0 )? 'greenyellow' : 'red'}">
                <td width="50"><c:out value="${person.id}"/></td>
                <td><c:out value="${person.login}"/></td>
                <td><c:out value="${person.password}"/></td>
                <td><c:out value="${person.roleId}"/></td>
                <td><form method="post" action="<c:url value="/deletePerson"/>" >
                    <input type="hidden" name="id" value="${person.id}">
                    <input type="submit" value="УДАЛИТЬ" >
                </form></td>
            </tr>
        </c:forEach>
    </table></p>
</article>
<aside>
    <p><h3>Статистика по пользователям табл. # 1</h3></p>
    <table bgcolor="#9acd32">
        <tr>

            <th>Количество регистраций</th>
            <th>Количество регистраций с начала года</th>
            <th>Количество всех удаленных пользователей</th>
            <th>Количество удаленных пользователей с начала года</th>
        </tr>
        <tr>
            <td><c:out value="${statistic.totalReg}"></c:out></td>
            <td><c:out value="${statistic.quantityRegYear}"></c:out></td>
            <td><c:out value="${statistic.totalDel}"></c:out></td>
            <td><c:out value="${statistic.quantityDelYear}"></c:out></td>
        </tr>
    </table>
</aside>
<article>
    <p><h3>Статистика по пользователям табл. # 2</h3></p>
    <table bgcolor="#9acd32">
        <tr>
            <th>Дата первой регистрации</th>
            <th>Дата последнего удаления</th>
            <th>Дата последней регистрации</th>
            <th>Дата первого удаления</th>

        </tr>
        <tr>
            <c:forEach items="${statistic.dictDate}" var="entry">
                <c:if test="${entry.key.equals('dateFirstReg')}">
                    <td><c:out value="${entry.value}"></c:out></td>
                </c:if>
                <c:choose>
                    <c:when test="${entry.key.equals('dateLastReg')}">
                        <td><c:out value="${entry.value}"></c:out></td>
                    </c:when>
                    <c:when test="${entry.key.equals('dateFirstDel')}">
                        <td><c:out value="${entry.value}"></c:out></td>
                    </c:when>
                    <c:when test="${entry.key.equals('dateLastDel')}">
                        <td><c:out value="${entry.value}"></c:out></td>
                    </c:when>
                </c:choose>
            </c:forEach>
        </tr>
    </table>
</article>
<br><br>
<footer style = "background-color:khaki; height:80px">
    <p>ItStep product inc. Rakhimzhanov.A.K. 2019  </p>
</footer>
</body>
</html>

<%--
  Created by IntelliJ IDEA.
  User: Elena
  Date: 23.03.2018
  Time: 14:13
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@page import="java.util.List"%>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="/css/styles.css" type="text/css"/>
    <title>Title</title>
</head>
<body>
<div class="form-style-2">
    <div class="form-style-2-heading">Уже зарегистрированы</div>
<table border=1>
    <thead>
    <tr>
        <th>Имя пользователя</th>
        <th>Дата рождения</th>

    </tr>
    </thead>
    <tbody>
    <c:forEach items="${usersFromServer}" var="user">
        <tr>
            <td><c:out value="${user.name}"/></td>
            <td><c:out value="${user.birthDate}"/></td>

        </tr>
    </c:forEach>
    </tbody>
</table>
</div>

<br><br>
<div class="form-style-2">
    <div class="form-style-2-heading">Пожалуйста зарегистрируйтесь</div>
    <form method="post" action="/signUp">
        <label for="name">Имя пользователя
            <input type="text" name="name" id="name" maxlength="20" class="input-field">
        </label>

        <label for="birthDate">Дата рождения
            <input type="text" name="birthDate" id="birthDate" maxlength="20" class="input-field">
        </label>

        <label for="password">Пароль
            <input type="password" name="password" id="password" maxlength="20" class="input-field">
        </label><br><br>
        <input type="submit" value="Добавить">
    </form>
</div>

</body>
</html>


<%--
  Created by IntelliJ IDEA.
  User: Elena
  Date: 06.04.2018
  Time: 18:02
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="/css/styles.css" type="text/css"/>
    <title>Title</title>
</head>
<body>
<div class="form-style-2">
    <div class="form-style-2-heading">Пользователи</div>
    <table border=1>
        <thead>
        <tr>
            <th>ID</th>
            <th>Имя</th>
            <th>Дата рождения</th>

        </tr>
        </thead>
        <tbody>
        <c:forEach items="${usersFromServer}" var="user">
            <tr>
                <td><c:out value="${user.id}"/></td>
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
    <form method="POST" action="/users">
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

<div class="form-style-2">
    <div class="form-style-2-heading">Редактировать данные о пользователе</div>
    <form method="POST" action="/users" accept-charset="UTF-8">

        <table>
            <tr><td>Логин</td><td><input type= "text" name="newName"  maxlength="50"
                                                  required id="newName"/>
            </td></tr>

            <tr><td>Новый пароль</td><td><input type= "text" name="newPassword" maxlength="50"
                                              required id="newPassword"/>
            </td></tr>

        </table>
        </br></br>
        <input type= 'submit' value= 'Редактировать'/>
    </form>
</div>

<div class="form-style-2">
    <div class="form-style-2-heading">Удалить пользователя</div>
    <form method="POST" action="/users" accept-charset="UTF-8">

        <table>
            <tr><td>ID</td><td><input type= "text" pattern="\d*" name="userIdDelete" maxlength="4"
                                      required id="userIdDelete"/>
            </td></tr>
        </table>

        </br></br>
        <input type= 'submit' value= 'Удалить'/>
    </form>
</div>

</body>
</html>

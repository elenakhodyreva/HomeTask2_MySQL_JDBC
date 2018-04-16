<%--
  Created by IntelliJ IDEA.
  User: Elena
  Date: 24.03.2018
  Time: 17:39
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>

    <span style="color: ${cookie.color.value}">Hello!</span>

    <form method="post" action="/home">
        <label for="color">
            <select name="color" id="color">
                <option value="red">Красный</option>
                <option value="violet">Фиолетовый</option>
                <option value="blue">Синий</option>
            </select>
        </label>
        
        <input type="submit" value="Выбрать цвет">
    </form>

</body>
</html>

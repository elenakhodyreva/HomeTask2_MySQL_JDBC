<%--
  Created by IntelliJ IDEA.
  User: Elena
  Date: 24.03.2018
  Time: 12:00
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
    <div class="form-style-2-heading">Книги</div>
    <table border=1>
        <thead>
        <tr>
            <th>Название книги</th>
            <th>Автор</th>
            <th>Количество</th>
            <th>Стоимость</th>

        </tr>
        </thead>
        <tbody>
        <c:forEach items="${myBooks}" var="book">
            <tr>
                <td><c:out value="${book.name}"/></td>
                <td><c:out value="${book.author}"/></td>
                <td><c:out value="${book.count}"/></td>
                <td><c:out value="${book.cost}"/></td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
</br></br>

<div class="form-style-2">
    <div class="form-style-2-heading">Добавить книгу</div>
    <form method="POST" action="/products" accept-charset="UTF-8">

        <table>
            <tr><td>Название книги</td><td><input type= "text" name="bookName"  maxlength="50" required id="name"/>
            </td></tr>

            <tr><td>Автор</td><td><input type= "text" name="bookAuthor"  maxlength="50" required id="author"/>
            </td></tr>

            <tr><td>Количество</td><td><input type= "text" pattern="\d*" name="bookCount" maxlength="4" required id="count"/>
            </td></tr>

            <tr><td>Стоимость, руб</td><td><input type= "text" pattern="\d*" name="bookCost" maxlength="7" required id="cost"/>
            </td></tr>

        </table>


        </br></br>
        <input type= 'submit' value= 'Добавить'/>


    </form>
</div>

<div class="form-style-2">
    <div class="form-style-2-heading">Редактировать данные о книге</div>
    <form method="POST" action="/products" accept-charset="UTF-8">

        <table>
            <tr><td>Название книги</td><td><input type= "text" name="newBookName"  maxlength="50"
                                                  required id="newBookName"/>
            </td></tr>

            <tr><td>Автор</td><td><input type= "text" name="newBookAuthor"  maxlength="50"
                                         required id="newBookAuthor"/>
            </td></tr>

            <tr><td>Количество</td><td><input type= "text" pattern="\d*" name="newCount" maxlength="4"
                                              required id="newCount"/>
            </td></tr>

            <tr><td>Стоимость, руб</td><td><input type= "text" pattern="\d*" name="newCost" maxlength="7"
                                                  required id="newCost"/>
            </td></tr>

        </table>


        </br></br>
        <input type= 'submit' value= 'Редактировать'/>
    </form>
</div>

<div class="form-style-2">
    <div class="form-style-2-heading">Удалить книгу</div>
    <form method="POST" action="/products" accept-charset="UTF-8">

        <table>
            <tr><td>ID</td><td><input type= "text" pattern="\d*" name="idForDelete" maxlength="4"
                                              required id="idForDelete"/>
            </td></tr>
        </table>


        </br></br>
        <input type= 'submit' value= 'Удалить'/>
    </form>
</div>

</body>
</html>

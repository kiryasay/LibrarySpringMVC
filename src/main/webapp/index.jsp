<%@ page contentType="text/html;charset=utf-8" %>
<html lang="ru" xmlns:th="http://thymleaf.org">
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="./WEB-INF/views/styles/header.css">
    <title>Title</title>
</head>
<body>
<header>
    <div class="header">
        <div class="logo">
            <img src="./WEB-INF/views/image/logo.png" alt="logo" class="logo_image">
        </div>
        <div class="peoplePage pages">
            <a th:href="@{/people}">
                Клиенты
            </a>
        </div>
        <div class="booksPage pages">
            <a th:href="@{/books}">
                Книги
            </a>
        </div>
    </div>
</header>
</body>
</html>

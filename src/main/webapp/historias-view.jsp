<%--
  Created by IntelliJ IDEA.
  User: USER
  Date: 27/4/2025
  Time: 21:48
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>historia_view</title>
</head>
<body>
<div class="stories-container">
  <c:forEach items="${restaurante.historias}" var="historia">
    <div class="story" data-id="${historia.id}">
      <img src="${historia.imagenUrl}" alt="${historia.contenido}">
      <div class="story-content">${historia.contenido}</div>
      <div class="story-time">
        <fmt:formatDate value="${historia.fechaPublicacion}" pattern="HH:mm"/>
      </div>
    </div>
  </c:forEach>
</div>
</body>
</html>

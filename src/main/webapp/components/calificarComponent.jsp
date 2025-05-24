<%--
  Created by IntelliJ IDEA.
  User: galoc
  Date: 5/24/2025
  Time: 3:29 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<div class="mb-3">
    <label class="form-label">${param.tituloCalificacion}</label>
    <div class="d-flex justify-content-between mb-3">
        <div class="rating-option">
            <input type="radio" id="puntaje1" name="${param.name}" value="1" required>
            <label for="puntaje1">★</label>
        </div>
        <div class="rating-option">
            <input type="radio" id="puntaje2" name="${param.name}" value="2">
            <label for="puntaje2">★★</label>
        </div>
        <div class="rating-option">
            <input type="radio" id="puntaje3" name="${param.name}" value="3">
            <label for="puntaje3">★★★</label>
        </div>
        <div class="rating-option">
            <input type="radio" id="puntaje4" name="${param.name}" value="4">
            <label for="puntaje4">★★★★</label>
        </div>
        <div class="rating-option">
            <input type="radio" id="puntaje5" name="${param.name}" value="5">
            <label for="puntaje5">★★★★★</label>
        </div>
    </div>
</div>
</body>
</html>

<%@ page import="entidades.Restaurante" %>
<%@ page import="java.util.List" %><%--
  Created by IntelliJ IDEA.
  User: alejo
  Date: 26/4/2025
  Time: 20:00
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Calificar Restaurante</title>
    <style>
        body {
            font-family: 'Arial', sans-serif;
            background-color: #f5f5f5;
            margin: 0;
            padding: 20px;
        }

        .container {
            max-width: 600px;
            margin: 0 auto;
            background: white;
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
        }

        h1 {
            color: #e74c3c;
            text-align: center;
        }

        .form-group {
            margin-bottom: 15px;
        }

        label {
            display: block;
            margin-bottom: 5px;
            font-weight: bold;
        }

        input[type="number"], select, textarea {
            width: 100%;
            padding: 8px;
            border: 1px solid #ddd;
            border-radius: 4px;
            box-sizing: border-box;
        }

        .rating {
            display: flex;
            justify-content: space-between;
            margin-bottom: 15px;
        }

        .rating-option {
            text-align: center;
        }

        .rating-option input {
            margin-right: 5px;
        }

        textarea {
            height: 100px;
            resize: vertical;
        }

        button {
            background-color: #e74c3c;
            color: white;
            border: none;
            padding: 10px 15px;
            border-radius: 4px;
            cursor: pointer;
            font-size: 16px;
            width: 100%;
        }

        button:hover {
            background-color: #c0392b;
        }
    </style>
</head>
<body>
<div class="container">
    <h1>sCalificar Restaurante</h1>

    <form action="${pageContext.request.contextPath}/calificar" method="POST">
        <input type="hidden" name="action" value="crear">

        <div class="form-group">
            <label for="idRestaurante">Restaurante:</label>
            <select id="idRestaurante" name="idRestaurante" required>
                <option value="">-- Seleccione un restaurante --</option>
                <%
                    for (Restaurante restaurante : (List<Restaurante>) request.getAttribute("restaurantes")) {
                %>
                <option value="<%= restaurante.getId() %>"><%= restaurante.getNombre() %>
                </option>
                <%
                    }
                %>
            </select>
        </div>

        <div class="form-group">
            <label>Puntaje:</label>
            <div class="rating">
                <div class="rating-option">
                    <input type="radio" id="puntaje1" name="puntaje" value="1" required>
                    <label for="puntaje1">1 ★</label>
                </div>
                <div class="rating-option">
                    <input type="radio" id="puntaje2" name="puntaje" value="2">
                    <label for="puntaje2">2 ★★</label>
                </div>
                <div class="rating-option">
                    <input type="radio" id="puntaje3" name="puntaje" value="3">
                    <label for="puntaje3">3 ★★★</label>
                </div>
                <div class="rating-option">
                    <input type="radio" id="puntaje4" name="puntaje" value="4">
                    <label for="puntaje4">4 ★★★★</label>
                </div>
                <div class="rating-option">
                    <input type="radio" id="puntaje5" name="puntaje" value="5">
                    <label for="puntaje5">5 ★★★★★</label>
                </div>
            </div>
        </div>

        <div class="form-group">
            <label for="comentario">Comentario:</label>
            <textarea id="comentario" name="comentario"
                      placeholder="Describe tu experiencia..." required></textarea>
        </div>
        <input type="hidden" name="idComensal" value="1">
        <button type="submit">Enviar Calificación</button>
    </form>
</div>
</body>
</html>
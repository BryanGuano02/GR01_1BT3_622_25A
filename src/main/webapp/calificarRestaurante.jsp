<%--<%@ page import="entidades.Restaurante" %>--%>
<%--<%@ page import="java.util.List" %>&lt;%&ndash;--%>
<%--  Created by IntelliJ IDEA.--%>
<%--  User: alejo--%>
<%--  Date: 26/4/2025--%>
<%--  Time: 20:00--%>
<%--  To change this template use File | Settings | File Templates.--%>
<%--&ndash;%&gt;--%>
<%--<%@ page contentType="text/html;charset=UTF-8" language="java" %>--%>
<%--<html lang="es">--%>
<%--<head>--%>
<%--    <meta charset="UTF-8">--%>
<%--    <title>Calificar Restaurante</title>--%>
<%--    <style>--%>
<%--        body {--%>
<%--            font-family: 'Arial', sans-serif;--%>
<%--            background-color: #f5f5f5;--%>
<%--            margin: 0;--%>
<%--            padding: 20px;--%>
<%--        }--%>

<%--        .container {--%>
<%--            max-width: 600px;--%>
<%--            margin: 0 auto;--%>
<%--            background: white;--%>
<%--            padding: 20px;--%>
<%--            border-radius: 8px;--%>
<%--            box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);--%>
<%--        }--%>

<%--        h1 {--%>
<%--            color: #e74c3c;--%>
<%--            text-align: center;--%>
<%--        }--%>

<%--        .form-group {--%>
<%--            margin-bottom: 15px;--%>
<%--        }--%>

<%--        label {--%>
<%--            display: block;--%>
<%--            margin-bottom: 5px;--%>
<%--            font-weight: bold;--%>
<%--        }--%>

<%--        input[type="number"], select, textarea {--%>
<%--            width: 100%;--%>
<%--            padding: 8px;--%>
<%--            border: 1px solid #ddd;--%>
<%--            border-radius: 4px;--%>
<%--            box-sizing: border-box;--%>
<%--        }--%>

<%--        .rating {--%>
<%--            display: flex;--%>
<%--            justify-content: space-between;--%>
<%--            margin-bottom: 15px;--%>
<%--        }--%>

<%--        .rating-option {--%>
<%--            text-align: center;--%>
<%--        }--%>

<%--        .rating-option input {--%>
<%--            margin-right: 5px;--%>
<%--        }--%>

<%--        textarea {--%>
<%--            height: 100px;--%>
<%--            resize: vertical;--%>
<%--        }--%>

<%--        button {--%>
<%--            background-color: #e74c3c;--%>
<%--            color: white;--%>
<%--            border: none;--%>
<%--            padding: 10px 15px;--%>
<%--            border-radius: 4px;--%>
<%--            cursor: pointer;--%>
<%--            font-size: 16px;--%>
<%--            width: 100%;--%>
<%--        }--%>

<%--        button:hover {--%>
<%--            background-color: #c0392b;--%>
<%--        }--%>
<%--    </style>--%>
<%--</head>--%>
<%--<body>--%>
<%--<div class="container">--%>
<%--    <h1>Calificar Restaurante</h1>--%>

<%--    <form action="${pageContext.request.contextPath}/calificar" method="POST">--%>
<%--        <input type="hidden" name="action" value="crear">--%>

<%--        <div class="form-group">--%>
<%--            <label for="idRestaurante">Restaurante:</label>--%>
<%--            <select id="idRestaurante" name="idRestaurante" required>--%>
<%--                <option value="">-- Seleccione un restaurante --</option>--%>
<%--                <%--%>
<%--                    for (Restaurante restaurante : (List<Restaurante>) request.getAttribute("restaurantes")) {--%>
<%--                %>--%>
<%--                <option value="<%= restaurante.getId() %>"><%= restaurante.getNombre() %>--%>
<%--                </option>--%>
<%--                <%--%>
<%--                    }--%>
<%--                %>--%>
<%--            </select>--%>
<%--        </div>--%>

<%--        <div class="form-group">--%>
<%--            <label>Puntaje:</label>--%>
<%--            <div class="rating">--%>
<%--                <div class="rating-option">--%>
<%--                    <input type="radio" id="puntaje1" name="puntaje" value="1" required>--%>
<%--                    <label for="puntaje1">1 ★</label>--%>
<%--                </div>--%>
<%--                <div class="rating-option">--%>
<%--                    <input type="radio" id="puntaje2" name="puntaje" value="2">--%>
<%--                    <label for="puntaje2">2 ★★</label>--%>
<%--                </div>--%>
<%--                <div class="rating-option">--%>
<%--                    <input type="radio" id="puntaje3" name="puntaje" value="3">--%>
<%--                    <label for="puntaje3">3 ★★★</label>--%>
<%--                </div>--%>
<%--                <div class="rating-option">--%>
<%--                    <input type="radio" id="puntaje4" name="puntaje" value="4">--%>
<%--                    <label for="puntaje4">4 ★★★★</label>--%>
<%--                </div>--%>
<%--                <div class="rating-option">--%>
<%--                    <input type="radio" id="puntaje5" name="puntaje" value="5">--%>
<%--                    <label for="puntaje5">5 ★★★★★</label>--%>
<%--                </div>--%>
<%--            </div>--%>
<%--        </div>--%>

<%--        <div class="form-group">--%>
<%--            <label for="comentario">Comentario:</label>--%>
<%--            <textarea id="comentario" name="comentario"--%>
<%--                      placeholder="Describe tu experiencia..." required></textarea>--%>
<%--        </div>--%>
<%--        <input type="hidden" name="idComensal" value="1">--%>
<%--        <button type="submit">Enviar Calificación</button>--%>
<%--    </form>--%>
<%--</div>--%>
<%--</body>--%>
<%--</html>--%>
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
    <title>U-Food | Calificar Restaurante</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;600&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <!-- Font Awesome para iconos -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    <style>
        body {
            background-color: #f8f9fa;
            padding: 20px;
        }

        .header {
            display: flex;
            justify-content: space-between;
            align-items: center;
            margin-bottom: 30px;
        }

        .user-info {
            display: flex;
            align-items: center;
            gap: 10px;
        }

        .user-info img {
            width: 40px;
            height: 40px;
            border-radius: 50%;
        }

        .card {
            border-radius: 10px;
            box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
        }

        .card-body {
            padding: 30px;
        }

        .btn-submit {
            background-color: #28a745;
            color: white;
            width: 100%;
            padding: 10px;
        }

        .rating-option {
            text-align: center;
            cursor: pointer;
        }

        .rating-option input {
            display: none;
        }

        .rating-option label {
            font-size: 1.5rem;
            color: #ddd;
            cursor: pointer;
        }

        .rating-option input:checked + label,
        .rating-option:hover label {
            color: #ffc107;
        }

        .rating-option:hover ~ .rating-option label {
            color: #ddd;
        }
    </style>
</head>
<body>
<div class="container mt-5">
    <div class="d-flex justify-content-between align-items-center mb-4">
        <h2 class="text-primary">Calificar Restaurante</h2>
        <div class="d-flex align-items-center">
            <img src="https://ui-avatars.com/api/?name=Comensal&background=ff6b6b&color=fff"
                 alt="Usuario" class="rounded-circle me-2" width="40">
            <span class="fw-bold">Comensal</span>
        </div>
    </div>

    <div class="card shadow">
        <div class="card-body">
            <form action="${pageContext.request.contextPath}/calificar" method="POST">
                <input type="hidden" name="action" value="crear">

                <!-- Restaurante -->
                <div class="mb-3">
                    <label for="idRestaurante" class="form-label">Restaurante:</label>
                    <select class="form-select" id="idRestaurante" name="idRestaurante" required>
                        <option value="">-- Seleccione un restaurante --</option>
                        <%
                            for (Restaurante restaurante : (List<Restaurante>) request.getAttribute("restaurantes")) {
                        %>
                        <option value="<%= restaurante.getId() %>"><%= restaurante.getNombre() %></option>
                        <%
                            }
                        %>
                    </select>
                </div>

                <!-- Puntaje -->
                <div class="mb-3">
                    <label class="form-label">Puntaje:</label>
                    <div class="d-flex justify-content-between mb-3">
                        <div class="rating-option">
                            <input type="radio" id="puntaje1" name="puntaje" value="1" required>
                            <label for="puntaje1">★</label>
                        </div>
                        <div class="rating-option">
                            <input type="radio" id="puntaje2" name="puntaje" value="2">
                            <label for="puntaje2">★★</label>
                        </div>
                        <div class="rating-option">
                            <input type="radio" id="puntaje3" name="puntaje" value="3">
                            <label for="puntaje3">★★★</label>
                        </div>
                        <div class="rating-option">
                            <input type="radio" id="puntaje4" name="puntaje" value="4">
                            <label for="puntaje4">★★★★</label>
                        </div>
                        <div class="rating-option">
                            <input type="radio" id="puntaje5" name="puntaje" value="5">
                            <label for="puntaje5">★★★★★</label>
                        </div>
                    </div>
                </div>

                <!-- Comentario -->
                <div class="mb-4">
                    <label for="comentario" class="form-label">Comentario:</label>
                    <textarea class="form-control" id="comentario" name="comentario"
                              placeholder="Describe tu experiencia..." rows="4" required></textarea>
                </div>

                <input type="hidden" name="idComensal" value="1">

                <button type="submit" class="btn btn-primary w-100 py-2">
                    <i class="fas fa-star me-2"></i> Enviar Calificación
                </button>
            </form>
        </div>
    </div>
</div>
<!-- Bootstrap JS -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
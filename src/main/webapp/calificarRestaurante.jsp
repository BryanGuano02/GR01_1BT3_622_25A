<%--&lt;%&ndash;<%@ page import="entidades.Restaurante" %>&ndash;%&gt;--%>
<%--&lt;%&ndash;<%@ page import="java.util.List" %>&lt;%&ndash;&ndash;%&gt;--%>
<%--&lt;%&ndash;  Created by IntelliJ IDEA.&ndash;%&gt;--%>
<%--&lt;%&ndash;  User: alejo&ndash;%&gt;--%>
<%--&lt;%&ndash;  Date: 26/4/2025&ndash;%&gt;--%>
<%--&lt;%&ndash;  Time: 20:00&ndash;%&gt;--%>
<%--&lt;%&ndash;  To change this template use File | Settings | File Templates.&ndash;%&gt;--%>
<%--&lt;%&ndash;&ndash;%&gt;&ndash;%&gt;--%>
<%--&lt;%&ndash;<%@ page contentType="text/html;charset=UTF-8" language="java" %>&ndash;%&gt;--%>
<%--&lt;%&ndash;<html lang="es">&ndash;%&gt;--%>
<%--&lt;%&ndash;<head>&ndash;%&gt;--%>
<%--&lt;%&ndash;    <meta charset="UTF-8">&ndash;%&gt;--%>
<%--&lt;%&ndash;    <title>Calificar Restaurante</title>&ndash;%&gt;--%>
<%--&lt;%&ndash;    <style>&ndash;%&gt;--%>
<%--&lt;%&ndash;        body {&ndash;%&gt;--%>
<%--&lt;%&ndash;            font-family: 'Arial', sans-serif;&ndash;%&gt;--%>
<%--&lt;%&ndash;            background-color: #f5f5f5;&ndash;%&gt;--%>
<%--&lt;%&ndash;            margin: 0;&ndash;%&gt;--%>
<%--&lt;%&ndash;            padding: 20px;&ndash;%&gt;--%>
<%--&lt;%&ndash;        }&ndash;%&gt;--%>

<%--&lt;%&ndash;        .container {&ndash;%&gt;--%>
<%--&lt;%&ndash;            max-width: 600px;&ndash;%&gt;--%>
<%--&lt;%&ndash;            margin: 0 auto;&ndash;%&gt;--%>
<%--&lt;%&ndash;            background: white;&ndash;%&gt;--%>
<%--&lt;%&ndash;            padding: 20px;&ndash;%&gt;--%>
<%--&lt;%&ndash;            border-radius: 8px;&ndash;%&gt;--%>
<%--&lt;%&ndash;            box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);&ndash;%&gt;--%>
<%--&lt;%&ndash;        }&ndash;%&gt;--%>

<%--&lt;%&ndash;        h1 {&ndash;%&gt;--%>
<%--&lt;%&ndash;            color: #e74c3c;&ndash;%&gt;--%>
<%--&lt;%&ndash;            text-align: center;&ndash;%&gt;--%>
<%--&lt;%&ndash;        }&ndash;%&gt;--%>

<%--&lt;%&ndash;        .form-group {&ndash;%&gt;--%>
<%--&lt;%&ndash;            margin-bottom: 15px;&ndash;%&gt;--%>
<%--&lt;%&ndash;        }&ndash;%&gt;--%>

<%--&lt;%&ndash;        label {&ndash;%&gt;--%>
<%--&lt;%&ndash;            display: block;&ndash;%&gt;--%>
<%--&lt;%&ndash;            margin-bottom: 5px;&ndash;%&gt;--%>
<%--&lt;%&ndash;            font-weight: bold;&ndash;%&gt;--%>
<%--&lt;%&ndash;        }&ndash;%&gt;--%>

<%--&lt;%&ndash;        input[type="number"], select, textarea {&ndash;%&gt;--%>
<%--&lt;%&ndash;            width: 100%;&ndash;%&gt;--%>
<%--&lt;%&ndash;            padding: 8px;&ndash;%&gt;--%>
<%--&lt;%&ndash;            border: 1px solid #ddd;&ndash;%&gt;--%>
<%--&lt;%&ndash;            border-radius: 4px;&ndash;%&gt;--%>
<%--&lt;%&ndash;            box-sizing: border-box;&ndash;%&gt;--%>
<%--&lt;%&ndash;        }&ndash;%&gt;--%>

<%--&lt;%&ndash;        .rating {&ndash;%&gt;--%>
<%--&lt;%&ndash;            display: flex;&ndash;%&gt;--%>
<%--&lt;%&ndash;            justify-content: space-between;&ndash;%&gt;--%>
<%--&lt;%&ndash;            margin-bottom: 15px;&ndash;%&gt;--%>
<%--&lt;%&ndash;        }&ndash;%&gt;--%>

<%--&lt;%&ndash;        .rating-option {&ndash;%&gt;--%>
<%--&lt;%&ndash;            text-align: center;&ndash;%&gt;--%>
<%--&lt;%&ndash;        }&ndash;%&gt;--%>

<%--&lt;%&ndash;        .rating-option input {&ndash;%&gt;--%>
<%--&lt;%&ndash;            margin-right: 5px;&ndash;%&gt;--%>
<%--&lt;%&ndash;        }&ndash;%&gt;--%>

<%--&lt;%&ndash;        textarea {&ndash;%&gt;--%>
<%--&lt;%&ndash;            height: 100px;&ndash;%&gt;--%>
<%--&lt;%&ndash;            resize: vertical;&ndash;%&gt;--%>
<%--&lt;%&ndash;        }&ndash;%&gt;--%>

<%--&lt;%&ndash;        button {&ndash;%&gt;--%>
<%--&lt;%&ndash;            background-color: #e74c3c;&ndash;%&gt;--%>
<%--&lt;%&ndash;            color: white;&ndash;%&gt;--%>
<%--&lt;%&ndash;            border: none;&ndash;%&gt;--%>
<%--&lt;%&ndash;            padding: 10px 15px;&ndash;%&gt;--%>
<%--&lt;%&ndash;            border-radius: 4px;&ndash;%&gt;--%>
<%--&lt;%&ndash;            cursor: pointer;&ndash;%&gt;--%>
<%--&lt;%&ndash;            font-size: 16px;&ndash;%&gt;--%>
<%--&lt;%&ndash;            width: 100%;&ndash;%&gt;--%>
<%--&lt;%&ndash;        }&ndash;%&gt;--%>

<%--&lt;%&ndash;        button:hover {&ndash;%&gt;--%>
<%--&lt;%&ndash;            background-color: #c0392b;&ndash;%&gt;--%>
<%--&lt;%&ndash;        }&ndash;%&gt;--%>
<%--&lt;%&ndash;    </style>&ndash;%&gt;--%>
<%--&lt;%&ndash;</head>&ndash;%&gt;--%>
<%--&lt;%&ndash;<body>&ndash;%&gt;--%>
<%--&lt;%&ndash;<div class="container">&ndash;%&gt;--%>
<%--&lt;%&ndash;    <h1>Calificar Restaurante</h1>&ndash;%&gt;--%>

<%--&lt;%&ndash;    <form action="${pageContext.request.contextPath}/calificar" method="POST">&ndash;%&gt;--%>
<%--&lt;%&ndash;        <input type="hidden" name="action" value="crear">&ndash;%&gt;--%>

<%--&lt;%&ndash;        <div class="form-group">&ndash;%&gt;--%>
<%--&lt;%&ndash;            <label for="idRestaurante">Restaurante:</label>&ndash;%&gt;--%>
<%--&lt;%&ndash;            <select id="idRestaurante" name="idRestaurante" required>&ndash;%&gt;--%>
<%--&lt;%&ndash;                <option value="">-- Seleccione un restaurante --</option>&ndash;%&gt;--%>
<%--&lt;%&ndash;                &lt;%&ndash;%>--%>
<%--&lt;%&ndash;                    for (Restaurante restaurante : (List<Restaurante>) request.getAttribute("restaurantes")) {&ndash;%&gt;--%>
<%--&lt;%&ndash;                %>&ndash;%&gt;--%>
<%--&lt;%&ndash;                <option value="<%= restaurante.getId() %>"><%= restaurante.getNombre() %>&ndash;%&gt;--%>
<%--&lt;%&ndash;                </option>&ndash;%&gt;--%>
<%--&lt;%&ndash;                &lt;%&ndash;%>--%>
<%--&lt;%&ndash;                    }&ndash;%&gt;--%>
<%--&lt;%&ndash;                %>&ndash;%&gt;--%>
<%--&lt;%&ndash;            </select>&ndash;%&gt;--%>
<%--&lt;%&ndash;        </div>&ndash;%&gt;--%>

<%--&lt;%&ndash;        <div class="form-group">&ndash;%&gt;--%>
<%--&lt;%&ndash;            <label>Puntaje:</label>&ndash;%&gt;--%>
<%--&lt;%&ndash;            <div class="rating">&ndash;%&gt;--%>
<%--&lt;%&ndash;                <div class="rating-option">&ndash;%&gt;--%>
<%--&lt;%&ndash;                    <input type="radio" id="puntaje1" name="puntaje" value="1" required>&ndash;%&gt;--%>
<%--&lt;%&ndash;                    <label for="puntaje1">1 ★</label>&ndash;%&gt;--%>
<%--&lt;%&ndash;                </div>&ndash;%&gt;--%>
<%--&lt;%&ndash;                <div class="rating-option">&ndash;%&gt;--%>
<%--&lt;%&ndash;                    <input type="radio" id="puntaje2" name="puntaje" value="2">&ndash;%&gt;--%>
<%--&lt;%&ndash;                    <label for="puntaje2">2 ★★</label>&ndash;%&gt;--%>
<%--&lt;%&ndash;                </div>&ndash;%&gt;--%>
<%--&lt;%&ndash;                <div class="rating-option">&ndash;%&gt;--%>
<%--&lt;%&ndash;                    <input type="radio" id="puntaje3" name="puntaje" value="3">&ndash;%&gt;--%>
<%--&lt;%&ndash;                    <label for="puntaje3">3 ★★★</label>&ndash;%&gt;--%>
<%--&lt;%&ndash;                </div>&ndash;%&gt;--%>
<%--&lt;%&ndash;                <div class="rating-option">&ndash;%&gt;--%>
<%--&lt;%&ndash;                    <input type="radio" id="puntaje4" name="puntaje" value="4">&ndash;%&gt;--%>
<%--&lt;%&ndash;                    <label for="puntaje4">4 ★★★★</label>&ndash;%&gt;--%>
<%--&lt;%&ndash;                </div>&ndash;%&gt;--%>
<%--&lt;%&ndash;                <div class="rating-option">&ndash;%&gt;--%>
<%--&lt;%&ndash;                    <input type="radio" id="puntaje5" name="puntaje" value="5">&ndash;%&gt;--%>
<%--&lt;%&ndash;                    <label for="puntaje5">5 ★★★★★</label>&ndash;%&gt;--%>
<%--&lt;%&ndash;                </div>&ndash;%&gt;--%>
<%--&lt;%&ndash;            </div>&ndash;%&gt;--%>
<%--&lt;%&ndash;        </div>&ndash;%&gt;--%>

<%--&lt;%&ndash;        <div class="form-group">&ndash;%&gt;--%>
<%--&lt;%&ndash;            <label for="comentario">Comentario:</label>&ndash;%&gt;--%>
<%--&lt;%&ndash;            <textarea id="comentario" name="comentario"&ndash;%&gt;--%>
<%--&lt;%&ndash;                      placeholder="Describe tu experiencia..." required></textarea>&ndash;%&gt;--%>
<%--&lt;%&ndash;        </div>&ndash;%&gt;--%>
<%--&lt;%&ndash;        <input type="hidden" name="idComensal" value="1">&ndash;%&gt;--%>
<%--&lt;%&ndash;        <button type="submit">Enviar Calificación</button>&ndash;%&gt;--%>
<%--&lt;%&ndash;    </form>&ndash;%&gt;--%>
<%--&lt;%&ndash;</div>&ndash;%&gt;--%>
<%--&lt;%&ndash;</body>&ndash;%&gt;--%>
<%--&lt;%&ndash;</html>&ndash;%&gt;--%>
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
<%--    <title>U-Food | Calificar Restaurante</title>--%>
<%--    <meta charset="UTF-8">--%>
<%--    <meta name="viewport" content="width=device-width, initial-scale=1.0">--%>
<%--    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;600&display=swap" rel="stylesheet">--%>
<%--    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">--%>
<%--    <!-- Bootstrap CSS -->--%>
<%--    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">--%>
<%--    <!-- Font Awesome para iconos -->--%>
<%--    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">--%>
<%--    <style>--%>
<%--        body {--%>
<%--            background-color: #f8f9fa;--%>
<%--            padding: 20px;--%>
<%--        }--%>

<%--        .header {--%>
<%--            display: flex;--%>
<%--            justify-content: space-between;--%>
<%--            align-items: center;--%>
<%--            margin-bottom: 30px;--%>
<%--        }--%>

<%--        .user-info {--%>
<%--            display: flex;--%>
<%--            align-items: center;--%>
<%--            gap: 10px;--%>
<%--        }--%>

<%--        .user-info img {--%>
<%--            width: 40px;--%>
<%--            height: 40px;--%>
<%--            border-radius: 50%;--%>
<%--        }--%>

<%--        .card {--%>
<%--            border-radius: 10px;--%>
<%--            box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);--%>
<%--        }--%>

<%--        .card-body {--%>
<%--            padding: 30px;--%>
<%--        }--%>

<%--        .btn-submit {--%>
<%--            background-color: #28a745;--%>
<%--            color: white;--%>
<%--            width: 100%;--%>
<%--            padding: 10px;--%>
<%--        }--%>

<%--        .rating-option {--%>
<%--            text-align: center;--%>
<%--            cursor: pointer;--%>
<%--        }--%>

<%--        .rating-option input {--%>
<%--            display: none;--%>
<%--        }--%>

<%--        .rating-option label {--%>
<%--            font-size: 1.5rem;--%>
<%--            color: #ddd;--%>
<%--            cursor: pointer;--%>
<%--        }--%>

<%--        .rating-option input:checked + label,--%>
<%--        .rating-option:hover label {--%>
<%--            color: #ffc107;--%>
<%--        }--%>

<%--        .rating-option:hover ~ .rating-option label {--%>
<%--            color: #ddd;--%>
<%--        }--%>
<%--    </style>--%>
<%--</head>--%>
<%--<body>--%>
<%--<div class="container mt-5">--%>
<%--    <div class="d-flex justify-content-between align-items-center mb-4">--%>
<%--        <h2 class="text-primary">Calificar Restaurante</h2>--%>
<%--        <div class="d-flex align-items-center">--%>
<%--            <img src="https://ui-avatars.com/api/?name=Comensal&background=ff6b6b&color=fff"--%>
<%--                 alt="Usuario" class="rounded-circle me-2" width="40">--%>
<%--            <span class="fw-bold">Comensal</span>--%>
<%--        </div>--%>
<%--    </div>--%>

<%--    <div class="card shadow">--%>
<%--        <div class="card-body">--%>
<%--            <form action="${pageContext.request.contextPath}/calificar" method="POST">--%>
<%--                <input type="hidden" name="action" value="crear">--%>

<%--                <!-- Restaurante -->--%>
<%--                <div class="mb-3">--%>
<%--                    <label for="idRestaurante" class="form-label">Restaurante:</label>--%>
<%--                    <select class="form-select" id="idRestaurante" name="idRestaurante" required>--%>
<%--                        <option value="">-- Seleccione un restaurante --</option>--%>
<%--                        <%--%>
<%--                            for (Restaurante restaurante : (List<Restaurante>) request.getAttribute("restaurantes")) {--%>
<%--                        %>--%>
<%--                        <option value="<%= restaurante.getId() %>"><%= restaurante.getNombre() %></option>--%>
<%--                        <%--%>
<%--                            }--%>
<%--                        %>--%>
<%--                    </select>--%>
<%--                </div>--%>

<%--                <!-- Puntaje -->--%>
<%--                <div class="mb-3">--%>
<%--                    <label class="form-label">Puntaje:</label>--%>
<%--                    <div class="d-flex justify-content-between mb-3">--%>
<%--                        <div class="rating-option">--%>
<%--                            <input type="radio" id="puntaje1" name="puntaje" value="1" required>--%>
<%--                            <label for="puntaje1">★</label>--%>
<%--                        </div>--%>
<%--                        <div class="rating-option">--%>
<%--                            <input type="radio" id="puntaje2" name="puntaje" value="2">--%>
<%--                            <label for="puntaje2">★★</label>--%>
<%--                        </div>--%>
<%--                        <div class="rating-option">--%>
<%--                            <input type="radio" id="puntaje3" name="puntaje" value="3">--%>
<%--                            <label for="puntaje3">★★★</label>--%>
<%--                        </div>--%>
<%--                        <div class="rating-option">--%>
<%--                            <input type="radio" id="puntaje4" name="puntaje" value="4">--%>
<%--                            <label for="puntaje4">★★★★</label>--%>
<%--                        </div>--%>
<%--                        <div class="rating-option">--%>
<%--                            <input type="radio" id="puntaje5" name="puntaje" value="5">--%>
<%--                            <label for="puntaje5">★★★★★</label>--%>
<%--                        </div>--%>
<%--                    </div>--%>
<%--                </div>--%>

<%--                <!-- Comentario -->--%>
<%--                <div class="mb-4">--%>
<%--                    <label for="comentario" class="form-label">Comentario:</label>--%>
<%--                    <textarea class="form-control" id="comentario" name="comentario"--%>
<%--                              placeholder="Describe tu experiencia..." rows="4" required></textarea>--%>
<%--                </div>--%>

<%--                <input type="hidden" name="idComensal" value="1">--%>

<%--                <button type="submit" class="btn btn-primary w-100 py-2">--%>
<%--                    <i class="fas fa-star me-2"></i> Enviar Calificación--%>
<%--                </button>--%>
<%--            </form>--%>
<%--        </div>--%>
<%--    </div>--%>
<%--</div>--%>
<%--<!-- Bootstrap JS -->--%>
<%--<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>--%>
<%--</body>--%>
<%--</html>--%>
<%@ page import="entidades.Restaurante" %>
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

        .restaurant-info {
            background-color: #f8f9fa;
            border-radius: 8px;
            padding: 15px;
            margin-bottom: 20px;
        }

        .restaurant-name {
            font-size: 1.5rem;
            font-weight: bold;
            color: #333;
        }

        .restaurant-type {
            color: #6c757d;
            font-style: italic;
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
            <!-- Información del restaurante (debe venir del backend) -->
            <div class="restaurant-info">
                <%
                    Restaurante restaurante = (Restaurante) request.getAttribute("restaurante");
                    if (restaurante != null) {
                %>
                <div class="restaurant-name"><%= restaurante.getNombre() %></div>
                <div class="restaurant-type mb-2">
                    <i class="fas fa-utensils me-1"></i> <%= restaurante.getTipoComida() %>
                </div>
                <p class="mb-0"><%= restaurante.getDescripcion() %></p>
                <%
                    } else {
                %>
                <div class="alert alert-warning">No se encontró información del restaurante</div>
                <%
                    }
                %>
            </div>

            <form action="${pageContext.request.contextPath}/calificar" method="POST">
                <input type="hidden" name="action" value="crear">
                <input type="hidden" name="idRestaurante" value="<%= restaurante != null ? restaurante.getId() : "" %>">

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
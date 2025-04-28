<%--
  Created by IntelliJ IDEA.
  User: alejo
  Date: 27/4/2025
  Time: 8:57
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="entidades.Restaurante" %>
<%@ page import="java.util.List" %>
<%@ page import="entidades.Calificacion" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.HashMap" %>
<head>
    <title>U-Food | Dashboard Restaurantes</title>
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
            margin-bottom: 20px;
        }

        .card-body {
            padding: 20px;
        }

        .restaurant-card {
            transition: transform 0.2s;
            cursor: pointer;
        }

        .restaurant-card:hover {
            transform: translateY(-5px);
            box-shadow: 0 6px 12px rgba(0, 0, 0, 0.15);
        }

        .restaurant-img {
            height: 120px;
            object-fit: cover;
            border-radius: 8px 8px 0 0;
        }

        .search-container {
            margin-bottom: 20px;
        }

        .btn-action {
            margin-right: 10px;
        }

        .rating-stars {
            color: #ffc107;
        }

        .restaurant-type {
            font-size: 0.9rem;
            color: #6c757d;
        }
    </style>
</head>
<body>
<div class="container mt-5">
    <div class="d-flex justify-content-between align-items-center mb-4">
        <h2 class="text-primary">Restaurantes Disponibles</h2>
        <div class="d-flex align-items-center">
            <img src="https://ui-avatars.com/api/?name=Usuario&background=ff6b6b&color=fff"
                 alt="Usuario" class="rounded-circle me-2" width="40">
            <span class="fw-bold">Usuario</span>
        </div>
    </div>

    <!-- Card de búsqueda -->
    <div class="card shadow">
        <div class="card-body">
            <div class="row">
                <div class="col-md-8">
                    <div class="input-group mb-3">
                        <input type="text" class="form-control" placeholder="Buscar restaurantes..."
                               aria-label="Buscar restaurantes" aria-describedby="button-search">
                        <button class="btn btn-primary" type="button" id="button-search">
                            <i class="fas fa-search me-2"></i>Buscar
                        </button>
                    </div>
                </div>
                <div class="col-md-4 text-end">
                    <button class="btn btn-success">
                        <i class="fas fa-heart me-2"></i>Guardar Preferencias
                    </button>
                </div>
            </div>
        </div>
    </div>

    <!-- Listado de restaurantes -->
    <div class="row">
<%--        <%--%>
        <%--            List<Restaurante> restaurantes = (List<Restaurante>) request.getSession().getAttribute("restaurantes");--%>

        <%--            for (int i = 0; i < restaurantes.size(); i++) {--%>
        <%--        %>--%>
        <%--        <div class="col-md-6 col-lg-4 mb-4">--%>
        <%--            <div class="card restaurant-card h-100">--%>
        <%--                <div class="card-body">--%>
        <%--                    <h5 class="card-title"><%= restaurantes[i] %>--%>
        <%--                    </h5>--%>
        <%--                    <p class="restaurant-type mb-2">--%>
        <%--                        <i class="fas fa-utensils me-1"></i> <%= tipos[i] %>--%>
        <%--                    </p>--%>
        <%--                    <div class="mb-2">--%>
        <%--                        <% for (int j = 0; j < 5; j++) { %>--%>
        <%--                        <i class="fas fa-star <%= j < ratings[i] ? "rating-stars" : "text-secondary" %>"></i>--%>
        <%--                        <% } %>--%>
        <%--                        <span class="ms-1">(<%= ratings[i] %>)</span>--%>
        <%--                    </div>--%>
        <%--                    <p class="card-text">Descripción breve del restaurante y sus especialidades.</p>--%>
        <%--                </div>--%>
        <%--                <div class="card-footer bg-white">--%>
        <%--                    <a href="calificar?idRestaurante=<%= i %>" class="btn btn-sm btn-outline-success">--%>
        <%--                        <i class="fas fa-star"></i> Calificar--%>
        <%--                    </a>--%>
        <%--                    &lt;%&ndash;                    <button class="btn btn-sm btn-outline-success">&ndash;%&gt;--%>
        <%--                    &lt;%&ndash;                        <i class="fas fa-star"></i> Calificar&ndash;%&gt;--%>
        <%--                    &lt;%&ndash;                    </button>&ndash;%&gt;--%>
        <%--                </div>--%>
        <%--            </div>--%>
        <%--        </div>--%>
        <%--        <% } %>--%>
        <%
            // Obtener las listas de la sesión
            List<Restaurante> restaurantes = (List<Restaurante>) request.getAttribute("restaurantes");
            List<Calificacion> calificaciones = (List<Calificacion>) request.getAttribute("calificaciones");

            // Crear un mapa rápido de calificaciones por restaurante (ID -> Calificacion)
            Map<Long, Calificacion> calificacionMap = new HashMap<>();
            if (calificaciones != null) {
                for (Calificacion calif : calificaciones) {
                    calificacionMap.put(calif.getRestaurante().getId(), calif);
                }
            }
        %>

        <% for (Restaurante restaurante : restaurantes) {
            Calificacion calificacion = calificacionMap.get(restaurante.getId());
            double puntaje = calificacion != null ? calificacion.getPuntaje() : -1;
        %>
        <div class="col-md-6 col-lg-4 mb-4">
            <div class="card restaurant-card h-100">
                <div class="card-body">
                    <h5 class="card-title"><%= restaurante.getNombre() %>
                    </h5>
                    <p class="restaurant-type mb-2">
                        <i class="fas fa-utensils me-1"></i> <%= restaurante.getTipoComida() %>
                    </p>
                    <div class="mb-2">
                        <% if (puntaje >= 0) {
                            int rating = (int) Math.round(puntaje);
                        %>
                        <% for (int j = 0; j < 5; j++) { %>
                        <i class="fas fa-star <%= j < rating ? "rating-stars" : "text-secondary" %>"></i>
                        <% } %>
                        <span class="ms-1">(<%= String.format("%.1f", puntaje) %>)</span>
                        <% } else { %>
                        <span class="text-muted">-</span>
                        <% } %>
                    </div>
                    <p class="card-text"><%= restaurante.getDescripcion() %>
                    </p>
                </div>
                <div class="card-footer bg-white">
                    <a href="calificar?idRestaurante=<%= restaurante.getId() %>" class="btn btn-sm btn-outline-success">
                        <i class="fas fa-star"></i> Calificar
                    </a>
                </div>
            </div>
        </div>
        <% } %>
    </div>
</div>

<!-- Bootstrap JS -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
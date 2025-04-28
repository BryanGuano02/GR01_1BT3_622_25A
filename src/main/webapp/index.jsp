<%--
  Created by IntelliJ IDEA.
  User: alejo
  Date: 27/4/2025
  Time: 8:57
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="entidades.Restaurante" %>
<%@ page import="entidades.Calificacion" %>
<%@ page import="entidades.Historia" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>U-Food | Dashboard Restaurantes</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;600&display=swap" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    <style>
        body { background-color: #f8f9fa; padding: 20px; }
        .restaurant-card { transition: transform 0.2s; cursor: pointer; }
        .restaurant-card:hover { transform: translateY(-5px); box-shadow: 0 6px 12px rgba(0,0,0,0.15); }
        .rating-stars { color: #ffc107; }
        .story-thumb { width: 80px; height: 80px; border-radius: 8px; object-fit: cover; }
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

    <!-- Buscador y filtro -->
    <div class="card shadow mb-4">
        <div class="card-body">
            <div class="row">
                <div class="col-md-8">
                    <div class="input-group">
                        <input type="text" class="form-control" placeholder="Buscar restaurantes...">
                        <button class="btn btn-primary"><i class="fas fa-search me-2"></i>Buscar</button>
                    </div>
                </div>
                <div class="col-md-4 text-end">
                    <button class="btn btn-success"><i class="fas fa-heart me-2"></i>Guardar Preferencias</button>
                </div>
            </div>
        </div>
    </div>

    <!-- Listado de restaurantes -->
    <div class="row">
        <%
            List<Restaurante> restaurantes = (List<Restaurante>) request.getAttribute("restaurantes");
            List<Calificacion> calificaciones = (List<Calificacion>) request.getAttribute("calificaciones");
            Map<Long, Calificacion> calMap = new HashMap<>();
            if (calificaciones != null) {
                for (Calificacion c : calificaciones) calMap.put(c.getRestaurante().getId(), c);
            }
            DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM HH:mm");
        %>
        <% for (Restaurante restaurante : restaurantes) {
            Calificacion cal = calMap.get(restaurante.getId());
            double puntaje = cal != null ? cal.getPuntaje() : -1;
        %>
        <div class="col-md-6 col-lg-4 mb-4">
            <div class="card restaurant-card h-100">
                <div class="card-body">
                    <h5 class="card-title"><%= restaurante.getNombre() %></h5>
                    <p class="text-muted mb-2">
                        <i class="fas fa-utensils me-1"></i>
                        <%= restaurante.getTipoComida() %>
                    </p>
                    <div class="mb-2">
                        <% if (puntaje >= 0) {
                            int rating = (int) Math.round(puntaje);
                            for (int i = 0; i < 5; i++) {
                        %>
                        <i class="fas fa-star <%= i < rating ? "rating-stars" : "text-secondary" %>"></i>
                        <%    } %>
                        <span class="ms-1">(<%= String.format("%.1f", puntaje) %>)</span>
                        <% } else { %>
                        <span class="text-muted">Sin calificación</span>
                        <% } %>
                    </div>
                    <p class="card-text">
                        <%= restaurante.getDescripcion() != null
                                ? restaurante.getDescripcion()
                                : "" %>
                    </p>

                    <!-- Historias: si no hay, muestro una por defecto -->
                    <%
                        List<Historia> historias = restaurante.getHistorias();
                        if (historias != null && !historias.isEmpty()) {
                    %>
                    <div class="mt-3">
                        <h6>Historias</h6>
                        <div class="d-flex overflow-auto">
                            <% for (Historia h : historias) { %>
                            <div class="me-3 text-center">
                                <img src="<%= h.getImagenUrl() %>" alt="Historia" class="story-thumb">
                                <small class="d-block"><%= h.getContenido() %></small>
                                <small class="text-muted"><%= h.getFechaPublicacion().format(fmt) %></small>
                            </div>
                            <% } %>
                        </div>
                    </div>
                    <% } else { %>
                    <div class="mt-3">
                        <h6>Historias</h6>
                        <div class="d-flex overflow-auto">
                            <div class="me-3 text-center">
                                <!-- URL de tu imagen por defecto -->
                                <img src="https://via.placeholder.com/80?text=Sin+Historia"
                                     alt="Sin historias" class="story-thumb">
                                <small class="d-block">Sin historias disponibles.</small>
                                <small class="text-muted">&nbsp;</small>
                            </div>
                        </div>
                    </div>
                    <% } %>
                </div>
                <div class="card-footer bg-white text-center">
                    <a href="calificar?idRestaurante=<%= restaurante.getId() %>"
                       class="btn btn-outline-success">
                        <i class="fas fa-star me-1"></i>Calificar
                    </a>
                </div>
            </div>
        </div>
        <% } %>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>

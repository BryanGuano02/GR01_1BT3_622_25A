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
        /* ESTILOS EXISTENTES (SE MANTIENEN IGUAL) */
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

        /* NUEVOS ESTILOS AGREGADOS */
        .menu-item {
            padding: 8px;
            margin-bottom: 5px;
            background-color: #f8f9fa;
            border-radius: 5px;
            border-left: 3px solid #0d6efd;
        }

        .menu-container {
            max-height: 300px;
            overflow-y: auto;
        }

        .btn-menu {
            margin-left: 5px;
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
                    <a href="SvComparar" class="btn btn-info me-2">
                        <i class="fas fa-balance-scale me-2"></i>Comparar
                    </a>
                    <button class="btn btn-success">
                        <i class="fas fa-heart me-2"></i>Guardar Preferencias
                    </button>
                </div>
            </div>
        </div>
    </div>

    <!-- Listado de restaurantes -->
    <div class="row">
        <%
            List<Restaurante> restaurantes = (List<Restaurante>) request.getAttribute("restaurantes");
            List<Calificacion> calificaciones = (List<Calificacion>) request.getAttribute("calificaciones");

            Map<Long, Calificacion> calificacionMap = new HashMap<>();
            if (calificaciones != null) {
                for (Calificacion calif : calificaciones) {
                    calificacionMap.put(calif.getRestaurante().getId(), calif);
                }
            }

            for (Restaurante restaurante : restaurantes) {
                Calificacion calificacion = calificacionMap.get(restaurante.getId());
                double puntaje = calificacion != null ? calificacion.getPuntaje() : -1;
                boolean tieneMenu = restaurante.getHistorias() != null && !restaurante.getHistorias().isEmpty();
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

                    <% if (tieneMenu) { %>
                    <button class="btn btn-sm btn-outline-primary btn-menu" data-bs-toggle="modal"
                            data-bs-target="#menuModal<%= restaurante.getId() %>">
                        <i class="fas fa-utensils"></i> Ver Menú
                    </button>
                    <% } %>
                </div>
            </div>
        </div>

        <!-- Modal para el menú (se agrega solo si tiene menú) -->
        <% if (tieneMenu) { %>
        <div class="modal fade" id="menuModal<%= restaurante.getId() %>" tabindex="-1">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title">Menú de <%= restaurante.getNombre() %></h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
                    </div>
                    <div class="modal-body menu-container">
                        <% for (String menu : restaurante.getHistorias()) { %>
                        <div class="menu-item mb-2">
                            <%= menu %>
                        </div>
                        <% } %>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cerrar</button>
                    </div>
                </div>
            </div>
        </div>
        <% } %>
        <% } %>
    </div>
</div>

<!-- Bootstrap JS -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
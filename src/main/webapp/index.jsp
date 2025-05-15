<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
    <title>U-Food | Inicio</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;600&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    <link rel="stylesheet" href="styles.css">
</head>
<body>
<div class="container mt-5">
    <%
        request.setAttribute("titulo", "Lista de Restaurantes "); // Ejemplo: para resaltar menú
        request.setAttribute("botonAtras", false); // Ejemplo: para resaltar menú
    %>
    <%@ include file="layout/header.jsp" %>

    <!-- Barra de búsqueda y acciones -->
    <div class="card shadow mb-4">
        <div class="card-body">
            <div class="row">
                <div class="col-md-8">
                    <form id="searchForm" onsubmit="buscarRestaurantes(event)">
                        <div class="input-group">
                            <input type="text" class="form-control" id="searchInput" name="busqueda"
                                   placeholder="Buscar restaurantes..."
                                   value="${param.busqueda}">
                            <button class="btn btn-primary" type="submit">
                                <i class="fas fa-search me-2"></i>Buscar
                            </button>
                        </div>
                    </form>
                </div>
                <div class="col-md-4 text-end">
                    <c:if test="${not empty sessionScope.usuario && sessionScope.usuario.tipoUsuario == 'COMENSAL'}">
                        <a href="${pageContext.request.contextPath}/filtrarRestaurantes.jsp" class="btn btn-info me-2">
                            <i class="fas fa-filter me-2"></i>Filtrar
                        </a>
                        <a href="${pageContext.request.contextPath}/planificar" class="btn btn-success me-2">
                            <i class="fas fa-calendar-plus me-2"></i>Crear Planificación
                        </a>
                    </c:if>
                    <button type="button" class="btn btn-secondary me-2" data-bs-toggle="modal" data-bs-target="#notificacionesModal">
                        <i class="fas fa-bell me-2"></i>Notificaciones
                    </button>
                    <a href="${pageContext.request.contextPath}/comparar" class="btn btn-warning">
                        <i class="fas fa-balance-scale me-2"></i>Comparar
                    </a>
                </div>
            </div>
        </div>
    </div>

    <!-- Listado de restaurantes -->
    <div class="row" id="restaurantes-container">
        <c:choose>
            <c:when test="${empty restaurantes}">
                <div class="col-12">
                    <div class="card no-restaurants">
                        <i class="fas fa-utensils fa-4x mb-3"></i>
                        <h3>No hay restaurantes disponibles</h3>
                        <p class="text-muted">No se encontraron restaurantes que coincidan con tu búsqueda.</p>
                    </div>
                </div>
            </c:when>
            <c:otherwise>
                <c:forEach items="${restaurantes}" var="restaurante">
                    <div class="col-md-6 col-lg-4 mb-4">
                        <div class="card restaurant-card h-100">
                            <div class="restaurant-img-placeholder">
                                <i class="fas fa-utensils fa-3x"></i>
                            </div>
                            <div class="card-body">
                                <h5 class="card-title">
                                    <c:out value="${not empty restaurante.nombre ? restaurante.nombre : 'Sin nombre'}"/>
                                </h5>
                                <p class="restaurant-type mb-2">
                                    <i class="fas fa-utensils me-1"></i>
                                    <c:out value="${not empty restaurante.tipoComida ? restaurante.tipoComida : 'No especificado'}"/>
                                </p>

                                <div class="mb-2">
                                    <c:choose>
                                        <c:when test="${restaurante.puntajePromedio > 0}">
                                            <c:forEach begin="1" end="5" var="i">
                                                <i class="fas fa-star ${i <= restaurante.puntajePromedio ? 'rating-stars' : 'text-secondary'}"></i>
                                            </c:forEach>
                                            <span class="ms-1">(<fmt:formatNumber value="${restaurante.puntajePromedio}"
                                                                                  pattern="#.##"/>)</span>
                                        </c:when>
                                        <c:otherwise>
                                            <span class="text-muted">Sin calificaciones</span>
                                        </c:otherwise>
                                    </c:choose>
                                </div>

                                <p class="card-text">
                                    <c:out value="${not empty restaurante.descripcion ? restaurante.descripcion : 'Sin descripción'}"/>
                                </p>

                                <div class="restaurant-actions">
                                    <c:if test="${not empty sessionScope.usuario && sessionScope.usuario.tipoUsuario == 'COMENSAL'}">
                                        <a href="${pageContext.request.contextPath}/calificar?idRestaurante=${restaurante.id}"
                                           class="btn btn-sm btn-outline-success">
                                            <i class="fas fa-star"></i> Calificar
                                        </a>
                                        <form action="${pageContext.request.contextPath}/suscribirse" method="post" style="display:inline;">
                                            <input type="hidden" name="idRestaurante" value="${restaurante.id}" />
                                            <input type="hidden" name="idComensal" value="${sessionScope.usuario.id}" />
                                            <button type="submit" class="btn btn-sm btn-outline-info">
                                                <i class="fas fa-bell"></i> Suscribirse
                                            </button>
                                        </form>
                                    </c:if>
                                    <c:if test="${not empty restaurante.historias && !restaurante.historias.isEmpty()}">
                                        <button class="btn btn-sm btn-outline-primary" data-bs-toggle="modal"
                                                data-bs-target="#menuModal${restaurante.id}">
                                            <i class="fas fa-utensils"></i> Ver Menú
                                        </button>
                                    </c:if>
                                </div>
                            </div>
                        </div>
                    </div>

                    <!-- Modal para el menú -->
                    <c:if test="${not empty restaurante.historias && !restaurante.historias.isEmpty()}">
                        <div class="modal fade" id="menuModal${restaurante.id}" tabindex="-1">
                            <div class="modal-dialog">
                                <div class="modal-content">
                                    <div class="modal-header">
                                        <h5 class="modal-title">Menú de ${restaurante.nombre}</h5>
                                        <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
                                    </div>
                                    <div class="modal-body menu-container">
                                        <c:forEach items="${restaurante.historias}" var="menu">
                                            <c:if test="${not empty menu}">
                                                <div class="menu-item mb-2">
                                                        ${menu}
                                                </div>
                                            </c:if>
                                        </c:forEach>
                                    </div>
                                    <div class="modal-footer">
                                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cerrar
                                        </button>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </c:if>
                </c:forEach>
            </c:otherwise>
        </c:choose>
    </div>
</div>

<!-- Modal de Notificaciones -->
<div class="modal fade" id="notificacionesModal" tabindex="-1" aria-labelledby="notificacionesModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-dialog-scrollable">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="notificacionesModalLabel">
                    <i class="fas fa-bell me-2"></i>Bandeja de Notificaciones
                </h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Cerrar"></button>
            </div>
            <div class="modal-body">
                <ul class="list-group">
                    <c:choose>
                        <c:when test="${empty notificaciones}">
                            <li class="list-group-item text-muted">No tienes notificaciones.</li>
                        </c:when>
                        <c:otherwise>
                            <c:forEach var="notificacion" items="${notificaciones}">
                                <li class="list-group-item notificacion-item" data-id="${notificacion.id}" data-leida="${notificacion.leida}">
                                    <i class="fas fa-info-circle text-primary me-2"></i>
                                    <c:out value="${notificacion.mensaje}"/>
                                    <c:if test="${!notificacion.leida}">
                                        <span class="badge bg-secondary float-end">Nuevo</span>
                                        <button class="btn btn-sm btn-outline-success ms-2 marcar-leida-btn float-end">Marcar como leída</button>
                                    </c:if>
                                    <br>
                                    <small class="text-muted">
                                        <% java.time.format.DateTimeFormatter formatter = java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
                                           entidades.Notificacion notif = (entidades.Notificacion) pageContext.getAttribute("notificacion");
                                           out.print(notif.getFechaCreacion().format(formatter)); %>
                                    </small>
                                </li>
                            </c:forEach>
                        </c:otherwise>
                    </c:choose>
                </ul>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cerrar</button>
            </div>
        </div>
    </div>
</div>
<script>
// Lógica para marcar como leída visualmente y por backend
setTimeout(function() {
    document.querySelectorAll('.marcar-leida-btn').forEach(function(btn) {
        btn.addEventListener('click', function() {
            var li = btn.closest('.notificacion-item');
            var id = li.getAttribute('data-id');
            fetch('notificaciones/leida?id=' + id, {
                method: 'POST',
                headers: { 'X-Requested-With': 'XMLHttpRequest' }
            });
            // Engaño visual: ocultar botón y badge, marcar como leída visualmente
            btn.style.display = 'none';
            var badge = li.querySelector('.badge');
            if (badge) badge.style.display = 'none';
            li.setAttribute('data-leida', 'true');
        });
    });
}, 100);
</script>

<!-- Bootstrap JS -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
<script>
    document.addEventListener('DOMContentLoaded', function () {
        // Manejar el formulario de búsqueda
        const searchForm = document.getElementById('searchForm');
        if (searchForm) {
            searchForm.addEventListener('submit', function (e) {
                e.preventDefault();
                const searchTerm = document.getElementById('searchInput').value.trim();
                buscarRestaurantes(searchTerm);
            });
        }

        // Verificar si ya hay restaurantes cargados
        const hasRestaurants = document.querySelectorAll('.restaurant-card').length > 0;
        const hasSearchParam = new URL(window.location.href).searchParams.get('busqueda');

        if (!hasRestaurants && !hasSearchParam) {
            cargarRestaurantes();
        }
    });

    function cargarRestaurantes() {
        const container = document.getElementById('restaurantes-container');
        if (!container) return;

        container.innerHTML = `
            <div class="col-12 loading-spinner">
                <div class="spinner-border text-primary" role="status">
                    <span class="visually-hidden">Cargando...</span>
                </div>
            </div>`;

        fetch('${pageContext.request.contextPath}/inicio')
            .then(response => {
                if (!response.ok) throw new Error('Error en la respuesta del servidor');
                return response.text();
            })
            .then(html => {
                // Parsear el HTML para extraer solo los restaurantes
                const tempDiv = document.createElement('div');
                tempDiv.innerHTML = html;
                const restaurantesHTML = tempDiv.querySelector('#restaurantes-container').innerHTML;
                container.innerHTML = restaurantesHTML;
                inicializarModales();
            })
            .catch(error => {
                console.error('Error al cargar restaurantes:', error);
                container.innerHTML = `
                    <div class="col-12 error-message">
                        <i class="fas fa-exclamation-triangle fa-2x mb-2"></i>
                        <h4>Error al cargar los restaurantes</h4>
                        <p>Por favor intenta recargar la página</p>
                        <button onclick="location.reload()" class="btn btn-primary mt-2">
                            <i class="fas fa-sync-alt me-2"></i>Recargar
                        </button>
                    </div>`;
            });
    }

    function buscarRestaurantes(searchTerm) {
        const container = document.getElementById('restaurantes-container');
        if (!container) return;

        container.innerHTML = `
            <div class="col-12 loading-spinner">
                <div class="spinner-border text-primary" role="status">
                    <span class="visually-hidden">Buscando...</span>
                </div>
            </div>`;

        const url = '${pageContext.request.contextPath}/inicio?busqueda=' + encodeURIComponent(searchTerm);

        fetch(url, {
            headers: {
                'X-Requested-With': 'XMLHttpRequest'
            }
        })
            .then(response => {
                if (!response.ok) throw new Error('Error en la búsqueda');
                return response.text();
            })
            .then(html => {
                // Parsear el HTML para extraer solo los restaurantes
                const tempDiv = document.createElement('div');
                tempDiv.innerHTML = html;
                const restaurantesHTML = tempDiv.querySelector('#restaurantes-container').innerHTML;
                container.innerHTML = restaurantesHTML;
                inicializarModales();

                // Actualizar URL sin recargar la página
                window.history.pushState({}, '', url);
            })
            .catch(error => {
                console.error('Error en la búsqueda:', error);
                container.innerHTML = `
                <div class="col-12 error-message">
                    <i class="fas fa-exclamation-triangle fa-2x mb-2"></i>
                    <h4>Error en la búsqueda</h4>
                    <p>${error.message}</p>
                    <button onclick="buscarRestaurantes('${searchTerm}')" class="btn btn-primary mt-2">
                        <i class="fas fa-sync-alt me-2"></i>Reintentar
                    </button>
                </div>`;
            });
    }

    function inicializarModales() {
        if (typeof bootstrap !== 'undefined' && bootstrap.Modal) {
            document.querySelectorAll('.modal').forEach(modalEl => {
                new bootstrap.Modal(modalEl);
            });
        }
    }
</script>
</body>
</html>

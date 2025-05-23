<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Añadir Restaurante</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;600&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="styles.css">
</head>
<body>
    <div class="container mt-5">

        <% request.setAttribute("titulo", "Añadir Restaurante a Planificación"); request.setAttribute("botonAtras", true); %>
        <%@ include file="layout/header.jsp" %>

        <div class="card shadow mb-4">
            <div class="card-body">
                <h3 class="mb-4"><i class="fas fa-utensils me-2"></i>Añadir Restaurante a Planificación: ${planificacion.nombre}</h3>

                <c:if test="${not empty mensaje}">
                    <div class="alert alert-info">${mensaje}</div>
                </c:if>

                <div class="alert alert-light">
                    <!-- <p><strong>ID:</strong> ${planificacion.id}</p> -->
                    <p><strong>Nombre:</strong> ${planificacion.nombre}</p>
                    <p><strong>Hora:</strong> ${planificacion.hora}</p>
                    <p><strong>Estado:</strong> ${planificacion.estado}</p>
                </div>

                <!-- Mostrar el restaurante actual si existe -->
                <c:if test="${not empty planificacion.restaurante}">
                    <h4 class="mt-4 mb-3">Restaurante actual</h4>
                    <div class="alert alert-info">
                        <!-- <p><strong>ID:</strong> ${planificacion.restaurante.id}</p> -->
                        <p><strong>Nombre:</strong> ${planificacion.restaurante.nombre}</p>
                        <p><strong>Tipo de comida:</strong> ${planificacion.restaurante.tipoComida}</p>
                        <p><strong>Puntaje promedio:</strong> ${planificacion.restaurante.puntajePromedio}</p>
                    </div>
                </c:if>

                <!-- Formulario para seleccionar un restaurante -->
                <h4 class="mt-4 mb-3">Seleccionar restaurante</h4>
                <form action="${pageContext.request.contextPath}/agregarRestaurante" method="POST">
                    <input type="hidden" name="planificacionId" value="${planificacion.id}">

                    <div class="mb-3">
                        <label for="restauranteId" class="form-label">Restaurante</label>
                        <select class="form-select" id="restauranteId" name="restauranteId" required>
                            <option value="">Seleccione un restaurante</option>
                            <c:forEach var="restaurante" items="${restaurantes}">
                                <option value="${restaurante.id}">${restaurante.nombre} - ${restaurante.tipoComida} (${restaurante.puntajePromedio} ★)</option>
                            </c:forEach>
                        </select>
                    </div>

                    <button type="submit" class="btn btn-primary w-100 py-2">
                        <i class="fas fa-plus me-2"></i>Añadir Restaurante
                    </button>
                </form>
            </div>
        </div>
    </div>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>

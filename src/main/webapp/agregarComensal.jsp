<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Añadir Comensal</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;600&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="styles.css">
</head>
<body>
    <div class="container mt-5">

        <% request.setAttribute("titulo", "Añadir Comensal a Planificación"); request.setAttribute("botonAtras", true); %>
        <%@ include file="layout/header.jsp" %>

        <div class="card shadow mb-4">
            <div class="card-body">
                <h3 class="mb-4"><i class="fas fa-user-plus me-2"></i>Añadir Comensal a Planificación: ${planificacion.nombre}</h3>

                <c:if test="${not empty mensaje}">
                    <div class="alert alert-info">${mensaje}</div>
                </c:if>

                <div class="alert alert-light">
                    <!-- <p><strong>ID:</strong> ${planificacion.id}</p> -->
                    <p><strong>Nombre:</strong> ${planificacion.nombre}</p>
                    <p><strong>Hora:</strong> ${planificacion.hora}</p>
                    <p><strong>Estado:</strong> ${planificacion.estado}</p>
                </div>

                <!-- Lista de comensales actuales -->
                <h4 class="mt-4 mb-3">Comensales actuales</h4>
                <c:choose>
                    <c:when test="${empty planificacion.comensales}">
                        <div class="alert alert-warning">No hay comensales añadidos a esta planificación.</div>
                    </c:when>
                    <c:otherwise>
                        <div class="table-responsive">
                            <table class="table table-bordered align-middle">
                                <thead class="table-light">
                                    <tr>
                                        <th>ID</th>
                                        <th>Nombre de Usuario</th>
                                        <th>Email</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach var="comensal" items="${planificacion.comensales}">
                                        <tr>
                                            <td>${comensal.id}</td>
                                            <td>${comensal.nombreUsuario}</td>
                                            <td>${comensal.email}</td>
                                        </tr>
                                    </c:forEach>
                                </tbody>
                            </table>
                        </div>
                    </c:otherwise>
                </c:choose>

                <!-- Formulario para añadir un nuevo comensal -->
                <h4 class="mt-4 mb-3">Añadir nuevo comensal</h4>
                <form action="${pageContext.request.contextPath}/agregarComensal" method="POST">
                    <input type="hidden" name="planificacionId" value="${planificacion.id}">

                    <div class="mb-3">
                        <label for="comensalId" class="form-label">ID del Comensal</label>
                        <input type="number" class="form-control" id="comensalId" name="comensalId" required>
                        <div class="form-text">Ingrese el ID del comensal que desea añadir a la planificación.</div>
                    </div>

                    <button type="submit" class="btn btn-primary w-100 py-2">
                        <i class="fas fa-plus me-2"></i>Añadir Comensal
                    </button>
                </form>
            </div>
        </div>
    </div>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>

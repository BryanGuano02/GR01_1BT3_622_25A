<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Detalle de Planificación</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;600&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="styles.css">
</head>
<body>
    <div class="container mt-5">
        <% request.setAttribute("titulo", "Detalle de Planificación"); request.setAttribute("botonAtras", true); %>
        <%@ include file="layout/header.jsp" %>

        <div class="card shadow mb-4">
            <div class="card-body">
                <h3 class="mb-4"><i class="fas fa-calendar-check me-2"></i>Detalle de Planificación</h3>

                <c:if test="${not empty mensaje}">
                    <div class="alert alert-info">${mensaje}</div>
                </c:if>

                <div class="alert alert-light">
                    <div class="row">
                        <div class="col-md-6">
                            <p><strong>ID:</strong> ${planificacion.id}</p>
                            <p><strong>Nombre:</strong> ${planificacion.nombre}</p>
                            <p><strong>Hora:</strong> ${planificacion.hora}</p>
                            <p><strong>Estado:</strong> ${planificacion.estado}</p>
                            <p><strong>Planificador:</strong> ${planificacion.comensalPlanificador.nombreUsuario}</p>
                        </div>
                        <div class="col-md-6">
                            <c:if test="${not empty planificacion.restaurante}">
                                <div class="mb-3">
                                    <h5>Restaurante Seleccionado</h5>
                                    <div class="alert alert-info">
                                        <p><strong>Nombre:</strong> ${planificacion.restaurante.nombre}</p>
                                        <p><strong>Tipo de comida:</strong> ${planificacion.restaurante.tipoComida}</p>
                                        <p><strong>Puntaje promedio:</strong> ${planificacion.restaurante.puntajePromedio} ★</p>
                                    </div>
                                </div>
                            </c:if>
                        </div>
                    </div>
                </div>

                <!-- Pestañas para comensales y restaurantes -->
                <ul class="nav nav-tabs mt-4" id="myTab" role="tablist">
                    <li class="nav-item" role="presentation">
                        <button class="nav-link active" id="comensales-tab" data-bs-toggle="tab" data-bs-target="#comensales"
                                type="button" role="tab" aria-controls="comensales" aria-selected="true">
                            <i class="fas fa-users me-1"></i>Comensales
                        </button>
                    </li>
                    <li class="nav-item" role="presentation">
                        <button class="nav-link" id="restaurantes-tab" data-bs-toggle="tab" data-bs-target="#restaurantes"
                                type="button" role="tab" aria-controls="restaurantes" aria-selected="false">
                            <i class="fas fa-utensils me-1"></i>Restaurantes
                        </button>
                    </li>
                    <li class="nav-item ms-auto" role="presentation">
                        <c:if test="${planificacion.estado == 'Activa'}">
                            <a href="${pageContext.request.contextPath}/terminarVotacion?id=${planificacion.id}"
                               class="btn btn-sm btn-warning mt-1" role="button">
                                <i class="fas fa-check me-1"></i> Terminar Votación
                            </a>
                        </c:if>
                    </li>
                </ul>

                <!-- Contenido de las pestañas -->
                <div class="tab-content" id="myTabContent">
                    <!-- Pestaña de Comensales -->
                    <div class="tab-pane fade show active p-3" id="comensales" role="tabpanel" aria-labelledby="comensales-tab">
                        <h4 class="mt-3 mb-3">Comensales actuales</h4>
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
                        <c:if test="${planificacion.estado == 'Activa'}">
                            <h4 class="mt-4 mb-3">Añadir nuevo comensal</h4>
                            <form action="${pageContext.request.contextPath}/agregarComensal" method="POST">
                                <input type="hidden" name="planificacionId" value="${planificacion.id}">

                                <div class="mb-3">
                                    <label for="comensalId" class="form-label">ID del Comensal</label>
                                    <input type="number" class="form-control" id="comensalId" name="comensalId" required>
                                    <div class="form-text">Ingrese el ID del comensal que desea añadir a la planificación.</div>
                                </div>

                                <button type="submit" class="btn btn-primary">
                                    <i class="fas fa-plus me-2"></i>Añadir Comensal
                                </button>
                            </form>
                        </c:if>
                    </div>

                    <!-- Pestaña de Restaurantes -->
                    <div class="tab-pane fade p-3" id="restaurantes" role="tabpanel" aria-labelledby="restaurantes-tab">
                        <!-- Mostrar el restaurante actual si existe -->
                        <c:if test="${not empty planificacion.restaurante}">
                            <h4 class="mt-3 mb-3">Restaurante actual</h4>
                            <div class="alert alert-info">
                                <p><strong>Nombre:</strong> ${planificacion.restaurante.nombre}</p>
                                <p><strong>Tipo de comida:</strong> ${planificacion.restaurante.tipoComida}</p>
                                <p><strong>Puntaje promedio:</strong> ${planificacion.restaurante.puntajePromedio} ★</p>
                                <c:if test="${not empty planificacion.restaurante.descripcion}">
                                    <p><strong>Descripción:</strong> ${planificacion.restaurante.descripcion}</p>
                                </c:if>
                                <c:if test="${not empty planificacion.restaurante.horaApertura && not empty planificacion.restaurante.horaCierre}">
                                    <p><strong>Horario:</strong> ${planificacion.restaurante.horaApertura} - ${planificacion.restaurante.horaCierre}</p>
                                </c:if>
                            </div>
                        </c:if>

                        <!-- Formulario para seleccionar un restaurante -->
                        <c:if test="${planificacion.estado == 'Activa'}">
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

                                <button type="submit" class="btn btn-primary">
                                    <i class="fas fa-plus me-2"></i>Añadir Restaurante
                                </button>
                            </form>
                        </c:if>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>

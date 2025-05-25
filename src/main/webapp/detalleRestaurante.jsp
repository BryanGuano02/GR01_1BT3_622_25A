<%@ page import="entidades.Restaurante" %>
<%@ page import="entidades.Calificacion" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html lang="es">

<head>
    <title>U-Food | Detalles de Restaurante</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;600&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="styles.css">
    <style>
        .restaurant-header {
            padding: 1.5rem;
            border-radius: 0.5rem;
            background-color: #f8f9fa;
            margin-bottom: 1.5rem;
        }

        .restaurant-name {
            font-size: 1.8rem;
            font-weight: 600;
            margin-bottom: 0.5rem;
        }

        .restaurant-info {
            margin-bottom: 1.5rem;
        }

        .info-card {
            background: #fff;
            padding: 1rem;
            border-radius: 0.5rem;
            box-shadow: 0 0.125rem 0.25rem rgba(0, 0, 0, 0.075);
            margin-bottom: 1rem;
        }

        .rating-stars {
            color: #ffc107;
            font-size: 1.2rem;
        }

        .info-label {
            font-weight: 500;
            margin-bottom: 0.25rem;
            color: #6c757d;
        }        .calificacion-card {
            background: #fff;
            border-radius: 0.5rem;
            padding: 1.25rem;
            margin-bottom: 1.5rem;
            border-left: 4px solid #0d6efd;
            box-shadow: 0 0.125rem 0.25rem rgba(0, 0, 0, 0.075);
        }

        .calificacion-fecha {
            font-size: 0.85rem;
            color: #6c757d;
        }

        .calificacion-comentario {
            margin-top: 0.75rem;
            line-height: 1.5;
        }

        .calificacion-usuario {
            margin-top: 0.75rem;
            margin-bottom: 0.75rem;
            color: #333;
        }

        .no-calificaciones {
            text-align: center;
            padding: 2rem 0;
        }

        .actions-container {
            display: flex;
            gap: 0.5rem;
            margin-top: 1rem;
        }
    </style>
</head>

<body>
    <div class="container mt-5">
        <%
        request.setAttribute("titulo", "Detalles de Restaurante");
        request.setAttribute("botonAtras", true);
        %>
        <%@ include file="layout/header.jsp" %>

        <!-- Información del restaurante -->
        <div class="card shadow mb-4">
            <div class="card-body">
                <c:choose>
                    <c:when test="${not empty restaurante}">
                        <div class="restaurant-header">
                            <div class="restaurant-name">${restaurante.nombre}</div>
                            <div class="d-flex align-items-center mb-2">
                                <div class="badge bg-primary me-2">${restaurante.tipoComida}</div>
                                <div class="rating-stars me-2">
                                    <c:choose>
                                        <c:when test="${restaurante.puntajePromedio > 0}">
                                            <fmt:formatNumber value="${restaurante.puntajePromedio}" maxFractionDigits="1" var="puntaje" />
                                            <c:forEach begin="1" end="5" var="i">
                                                <c:choose>
                                                    <c:when test="${i <= puntaje}">
                                                        <i class="fas fa-star"></i>
                                                    </c:when>
                                                    <c:when test="${i <= puntaje + 0.5 && i > puntaje}">
                                                        <i class="fas fa-star-half-alt"></i>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <i class="far fa-star"></i>
                                                    </c:otherwise>
                                                </c:choose>
                                            </c:forEach>
                                        </c:when>
                                        <c:otherwise>
                                            <span class="text-muted">Sin calificaciones</span>
                                        </c:otherwise>
                                    </c:choose>
                                </div>
                                <div class="text-muted">
                                    <fmt:formatNumber value="${restaurante.puntajePromedio}" maxFractionDigits="1" /> (${calificaciones.size()} calificaciones)
                                </div>
                            </div>
                            <p>${restaurante.descripcion}</p>
                        </div>

                        <div class="row">
                            <div class="col-md-6">
                                <div class="info-card">
                                    <div class="info-label"><i class="fas fa-clock me-2"></i>Horarios:</div>
                                    <p class="mb-0">Abierto de ${restaurante.horaApertura} a ${restaurante.horaCierre}</p>
                                </div>
                            </div>
                            <div class="col-md-6">
                                <div class="info-card">
                                    <div class="info-label"><i class="fas fa-dollar-sign me-2"></i>Rango de precios:</div>
                                    <p class="mb-0">
                                        <c:forEach begin="1" end="${restaurante.precio}" var="i">
                                            $
                                        </c:forEach>
                                    </p>
                                </div>
                            </div>
                            <div class="col-md-6">
                                <div class="info-card">
                                    <div class="info-label"><i class="fas fa-map-marker-alt me-2"></i>Distancia:</div>
                                    <p class="mb-0">${restaurante.distanciaUniversidad} km de la universidad</p>
                                </div>
                            </div>
                            <div class="col-md-6">
                                <div class="info-card">
                                    <div class="info-label"><i class="fas fa-stopwatch me-2"></i>Tiempo de espera promedio:</div>
                                    <p class="mb-0">${restaurante.tiempoEspera} minutos</p>
                                </div>
                            </div>
                        </div>

                        <!-- Acciones -->
                        <c:if test="${not empty sessionScope.usuario && sessionScope.usuario.tipoUsuario == 'COMENSAL'}">
                            <div class="actions-container">
                                <a href="${pageContext.request.contextPath}/calificar?idRestaurante=${restaurante.id}" class="btn btn-outline-primary d-flex align-items-center">
    <i class="fas fa-star me-2"></i>Calificar
</a>
                                <c:choose>
                                    <c:when test="${restaurante.estaSuscrito}">
                                        <button class="btn btn-outline-secondary" disabled>
                                            <i class="fas fa-check me-2"></i>Suscrito
                                        </button>
                                    </c:when>
                                    <c:otherwise>
                                        <form action="${pageContext.request.contextPath}/suscribirse" method="POST">
                                            <input type="hidden" name="idRestaurante" value="${restaurante.id}">
                                            <input type="hidden" name="idComensal" value="${sessionScope.usuario.id}">
                                            <button type="submit" class="btn btn-outline-primary">
                                                <i class="fas fa-bell me-2"></i>Suscribirse
                                            </button>
                                        </form>
                                    </c:otherwise>
                                </c:choose>
                            </div>
                        </c:if>
                    </c:when>
                    <c:otherwise>
                        <div class="alert alert-warning">
                            <i class="fas fa-exclamation-triangle me-2"></i>No se encontró información del restaurante
                        </div>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>

        <!-- Sección de calificaciones -->
        <div class="card shadow mb-4">
            <div class="card-header bg-primary text-white d-flex justify-content-between align-items-center">
                <h5 class="mb-0"><i class="fas fa-comments me-2"></i>Calificaciones</h5>
            </div>
            <div class="card-body">
                <c:choose>
                    <c:when test="${not empty calificaciones}">
                        <c:forEach items="${calificaciones}" var="calificacion">                            <div class="calificacion-card">
                                <div class="d-flex justify-content-between align-items-center mb-3">
                                    <div class="rating-stars">
                                        <c:forEach begin="1" end="5" var="i">
                                            <c:choose>
                                                <c:when test="${i <= calificacion.puntaje}">
                                                    <i class="fas fa-star"></i>
                                                </c:when>
                                                <c:otherwise>
                                                    <i class="far fa-star"></i>
                                                </c:otherwise>
                                            </c:choose>
                                        </c:forEach>
                                    </div>
                                    <div class="calificacion-fecha">
                                        <i class="far fa-calendar-alt me-1"></i>
                                        ${calificacion.fechaFormateada}
                                    </div>
                                </div><div class="calificacion-usuario d-flex align-items-center">
                                    <i class="fas fa-user me-2 text-primary"></i>
                                    <span class="fw-bold">${calificacion.comensal.nombreUsuario}</span>
                                </div>
                                <div class="calificacion-comentario">
                                    ${calificacion.comentario}
                                </div>
                            </div>
                        </c:forEach>
                    </c:when>
                    <c:otherwise>
                        <div class="no-calificaciones">
                            <i class="fas fa-comment-slash fa-3x text-muted mb-3"></i>
                            <p class="text-muted">Este restaurante no tiene calificaciones todavía.</p>
                            <c:if test="${not empty sessionScope.usuario && sessionScope.usuario.tipoUsuario == 'COMENSAL'}">
                                <a href="${pageContext.request.contextPath}/calificar?idRestaurante=${restaurante.id}" class="btn btn-outline-primary">
                                    <i class="fas fa-star me-2"></i>Sé el primero en calificar
                                </a>
                            </c:if>
                        </div>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
    </div>

    <!-- Bootstrap JS -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>

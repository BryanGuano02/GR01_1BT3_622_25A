<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Ranking de Restaurantes</title>
    <link rel="stylesheet" href="styles.css">
</head>
<body>
<div class="container mt-5">
    <h1 class="text-center">Ranking de Restaurantes</h1>
    <div class="row">
        <c:choose>
            <c:when test="${not empty restaurantesOrdenados}">
                <c:forEach items="${restaurantesOrdenados}" var="restaurante" varStatus="status">
                    <div class="col-md-6 col-lg-4 mb-4">
                        <div class="card restaurant-card h-100">
                            <div class="restaurant-img-placeholder">
                                <i class="fas fa-utensils fa-3x"></i>
                                <div class="position-badge">#${status.index + 1}</div>
                            </div>
                            <div class="card-body">
                                <h5 class="card-title">
                                    <a href="${pageContext.request.contextPath}/detalleRestaurante?id=${restaurante.id}" class="text-decoration-none text-dark">
                                        <c:out value="${restaurante.nombre}"/>
                                    </a>
                                </h5>
                                <p class="restaurant-type mb-2">
                                    <i class="fas fa-utensils me-1"></i>
                                    <c:out value="${restaurante.tipoComida}"/>
                                </p>
                                <div class="mb-2">
                                    <c:forEach begin="1" end="5" var="i">
                                        <i class="fas fa-star ${i <= restaurante.puntajePromedio ? 'text-warning' : 'text-secondary'}"></i>
                                    </c:forEach>
                                    <span class="ms-1">
                                        (<fmt:formatNumber value="${restaurante.puntajePromedio}" pattern="#.##"/>)
                                    </span>
                                </div>
                                <p class="card-text">
                                    <c:out value="${restaurante.descripcion}"/>
                                </p>
                                <div class="d-grid mt-3">
                                    <a href="${pageContext.request.contextPath}/detalleRestaurante?id=${restaurante.id}" class="btn btn-outline-primary btn-sm">
                                        <i class="fas fa-info-circle me-1"></i>Ver detalles
                                    </a>
                                </div>
                            </div>
                        </div>
                    </div>
                </c:forEach>
            </c:when>
            <c:otherwise>
                <div class="col-12">
                    <div class="card no-restaurants">
                        <i class="fas fa-utensils fa-4x mb-3"></i>
                        <h3>No hay restaurantes disponibles</h3>
                        <p class="text-muted">No se encontraron restaurantes que coincidan con tu búsqueda.</p>
                    </div>
                </div>
            </c:otherwise>
        </c:choose>
    </div>
</div>
</body>
</html>

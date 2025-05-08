<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="entidades.Restaurante" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>Resultados de Búsqueda</title>
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
        request.setAttribute("titulo", "Resultados"); // Ejemplo: para resaltar menú
        request.setAttribute("botonAtras", true); // Ejemplo: para resaltar menú
    %>
    <%@ include file="layout/header.jsp" %>
</div>

<div class="container">
    <h2>Restaurantes encontrados:</h2>
    <div class="restaurant-grid">
        <%
            List<Restaurante> restaurantes = (List<Restaurante>) request.getAttribute("restaurantesFiltrados");
            if (restaurantes != null && !restaurantes.isEmpty()) {
                for (Restaurante restaurante : restaurantes) {
        %>
        <div class="restaurant-card">
            <div class="restaurant-name"><%= restaurante.getNombre() %>
            </div>
            <div class="restaurant-description"><%= restaurante.getDescripcion() %>
            </div>
        </div>
        <%
            }
        } else {
        %>
        <p>No se encontraron restaurantes que cumplan con los filtros seleccionados.</p>
        <%
            }
        %>
    </div>
</div>

</body>
</html>

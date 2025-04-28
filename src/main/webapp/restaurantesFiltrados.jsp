<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="entidades.Restaurante" %>
<%-- Cambia "tu.paquete" al real --%>
<html>
<head>
  <title>Resultados de Búsqueda</title>
  <style>
    body {
      font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
      background-color: #f2f4f7;
      margin: 0;
      padding: 0;
    }
    .header {
      display: flex;
      justify-content: space-between;
      align-items: center;
      background-color: white;
      padding: 20px;
      box-shadow: 0px 2px 5px rgba(0,0,0,0.1);
    }
    .header h1 {
      color: #007bff;
      margin: 0;
    }
    .user-info {
      display: flex;
      align-items: center;
    }
    .user-circle {
      width: 40px;
      height: 40px;
      background-color: #ff6b6b;
      color: white;
      border-radius: 50%;
      display: flex;
      align-items: center;
      justify-content: center;
      font-weight: bold;
      margin-right: 10px;
    }
    .container {
      background-color: white;
      margin: 40px auto;
      padding: 30px;
      border-radius: 12px;
      max-width: 900px;
      box-shadow: 0px 2px 10px rgba(0,0,0,0.1);
    }
    .restaurant-grid {
      display: grid;
      grid-template-columns: repeat(auto-fill, minmax(250px, 1fr));
      gap: 20px;
    }
    .restaurant-card {
      background-color: #ffffff;
      border: 1px solid #ccc;
      border-radius: 12px;
      padding: 20px;
      box-shadow: 0px 2px 5px rgba(0,0,0,0.1);
      transition: transform 0.2s ease;
    }
    .restaurant-card:hover {
      transform: translateY(-5px);
      box-shadow: 0px 4px 10px rgba(0,0,0,0.15);
    }
    .restaurant-name {
      font-size: 20px;
      font-weight: bold;
      color: #007bff;
      margin-bottom: 10px;
    }
    .restaurant-description {
      color: #555;
    }
  </style>
</head>
<body>

<div class="header">
  <h1>Resultados</h1>
  <div class="user-info">
    <div class="user-circle">US</div>
    <span>Usuario</span>
  </div>
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
      <div class="restaurant-name"><%= restaurante.getNombre() %></div>
      <div class="restaurant-description"><%= restaurante.getDescripcion() %></div>
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

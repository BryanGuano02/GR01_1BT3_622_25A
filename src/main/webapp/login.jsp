<%--
  Created by IntelliJ IDEA.
  User: alejo
  Date: 2/5/2025
  Time: 8:30
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Login</title>
</head>
<body>
<h2>Iniciar Sesión</h2>

<% if (request.getAttribute("error") != null) { %>
<p style="color:red;"><%= request.getAttribute("error") %>
</p>
<% } %>

<form action="login" method="post">
    <label for="email">Email:</label>
    <input type="email" id="email" name="email" required><br><br>

    <label for="password">Contraseña:</label>
    <input type="password" id="password" name="password" required><br><br>

    <label for="tipoUsuario">Tipo de usuario:</label>
    <select id="tipoUsuario" name="tipoUsuario" required>
        <option value="">Seleccione...</option>
        <option value="comensal">Comensal</option>
        <option value="restaurante">Restaurante</option>
    </select><br><br>

    <input type="submit" value="Iniciar Sesión">
</form>
</body>
</html>

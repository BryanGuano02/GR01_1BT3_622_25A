<%--
  Created by IntelliJ IDEA.
  User: USER
  Date: 5/5/2025
  Time: 23:08
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>Registro - UFood</title>
</head>
<body>
<h1>Registro de Usuario</h1>
<% if (request.getAttribute("error") != null) { %>
<p style="color: red;">${error}</p>
<% } %>
<form action="${pageContext.request.contextPath}/registro" method="post">
  <div>
    <label>Tipo de usuario:</label>
    <input type="radio" id="restaurante" name="tipoUsuario" value="RESTAURANTE" checked>
    <label for="restaurante">Restaurante</label>
    <input type="radio" id="comensal" name="tipoUsuario" value="COMENSAL">
    <label for="comensal">Comensal</label>
  </div>
  <div>
    <label for="nombreUsuario">Nombre de usuario:</label>
    <input type="text" id="nombreUsuario" name="nombreUsuario" required>
  </div>
  <div>
    <label for="email">Email:</label>
    <input type="email" id="email" name="email" required>
  </div>
  <div>
    <label for="contrasena">Contraseña:</label>
    <input type="password" id="contrasena" name="contrasena" required>
  </div>
  <button type="submit">Registrarse</button>
</form>
<p>¿Ya tienes una cuenta? <a href="${pageContext.request.contextPath}/login.jsp">Inicia sesión aquí</a></p>
</body>
</html>
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
    <title>Login - UFood</title>
  </head>
  <body>
  <h1>Iniciar Sesión</h1>
  <% if (request.getParameter("registroExitoso") != null) { %>
  <p style="color: green;">Registro exitoso! Por favor inicie sesión.</p>
  <% } %>
  <% if (request.getAttribute("error") != null) { %>
  <p style="color: red;">${error}</p>
  <% } %>
  <form action="${pageContext.request.contextPath}/login" method="post">
    <div>
      <label for="nombreUsuario">Nombre de usuario:</label>
      <input type="text" id="nombreUsuario" name="nombreUsuario" required>
    </div>
    <div>
      <label for="contrasena">Contraseña:</label>
      <input type="password" id="contrasena" name="contrasena" required>
    </div>
    <button type="submit">Iniciar Sesión</button>
  </form>
  <p>¿No tienes una cuenta? <a href="${pageContext.request.contextPath}/registro.jsp">Regístrate aquí</a></p>
  </body>
  </html>
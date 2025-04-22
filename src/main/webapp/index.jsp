<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>U-Food</title>
</head>
<body>
<h2>Registrar un nuevo restaurante</h2>
<form action="${pageContext.request.contextPath}/restaurante" method="post">
    <input type="hidden" name="accion" value="guardar">
    <label for="nombre">Nombre:</label>
    <input type="text" name="nombre" id="nombre" required/>
    <br>

    <label for="descripcion">Descripción:</label>
    <input type="text" name="descripcion" id="descripcion" required/>
    <br>
    <label for="horarioAtencion">Horario de Atención:</label>
    <input type="text" name="horarioAtencion" id="horarioAtencion" required/>
    <br>
    <br>
    <button type="submit">Guardar Restaurante</button>
</form>
</body>
</html>

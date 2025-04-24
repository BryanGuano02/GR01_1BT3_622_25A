<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>U-Food</title>
    <style>
        .form-group { margin-bottom: 15px; }
        label { display: inline-block; width: 150px; }
        .horario-container {
            display: inline-block;
            border: 1px solid #ddd;
            padding: 8px;
            border-radius: 4px;
        }
        input[type="time"] {
            padding: 6px;
            border: 1px solid #ccc;
            border-radius: 4px;
        }
    </style>
</head>
<body>
<h2>Registrar un nuevo restaurante</h2>
<form action="${pageContext.request.contextPath}/restaurante" method="post">
    <input type="hidden" name="accion" value="guardar">

    <!-- Nombre -->
    <div class="form-group">
        <label for="nombre">Nombre:</label>
        <input type="text" name="nombre" id="nombre" required style="width: 300px;"/>
    </div>

    <!-- Tipo de Cocina -->
    <div class="form-group">
        <label for="tipoComida">Tipo de Comida:</label>
        <select name="tipoComida" id="tipoComida" required style="width: 308px;">
            <option value="">Seleccione un tipo...</option>
            <option value="COMIDA_RAPIDA">Comida Rápida</option>
            <option value="COMIDA_CASERA">Comida Casera</option>
            <option value="COMIDA_COSTEÑA">Comida Costeña</option>
            <option value="PLATOS_CARTA">Platos a la Carta</option>
        </select>
    </div>

    <!-- Horario Atención -->
    <div class="form-group">
        <label>Horario de Atención:</label>
        <div class="horario-container">
            <input type="time" name="horaApertura" required>
            <span> a </span>
            <input type="time" name="horaCierre" required>
        </div>
    </div>

    <div class="form-group">
        <button type="submit" style="margin-left: 155px; padding: 8px 16px;">
            Guardar Restaurante
        </button>
    </div>
</form>
</body>
</html>
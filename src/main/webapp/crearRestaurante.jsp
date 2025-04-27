<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>U-Food | Panel Administrativo</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;600&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <!-- Font Awesome para iconos -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    <style>
        body {
            background-color: #f8f9fa;
            padding: 20px;
        }

        .header {
            display: flex;
            justify-content: space-between;
            align-items: center;
            margin-bottom: 30px;
        }

        .user-info {
            display: flex;
            align-items: center;
            gap: 10px;
        }

        .user-info img {
            width: 40px;
            height: 40px;
            border-radius: 50%;
        }

        .card {
            border-radius: 10px;
            box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
        }

        .card-body {
            padding: 30px;
        }

        .btn-submit {
            background-color: #28a745;
            color: white;
            width: 100%;
            padding: 10px;
        }

        .time-inputs {
            display: flex;
            align-items: center;
            gap: 10px;
        }
    </style>
</head>
<body>
<div class="container mt-5">
    <div class="d-flex justify-content-between align-items-center mb-4">
        <h2 class="text-primary">Registrar Nuevo Restaurante</h2>
        <div class="d-flex align-items-center">
            <img src="https://ui-avatars.com/api/?name=Admin&background=ff6b6b&color=fff"
                 alt="Usuario" class="rounded-circle me-2" width="40">
            <span class="fw-bold">Administrador</span>
        </div>
    </div>

    <div class="card shadow">
        <div class="card-body">
            <form action="${pageContext.request.contextPath}/restaurante" method="post">
                <input type="hidden" name="accion" value="guardar">

                <!-- Nombre -->
                <div class="mb-3">
                    <label for="nombre" class="form-label">Nombre del Restaurante</label>
                    <input type="text" class="form-control" name="nombre" id="nombre" required
                           placeholder="Ej: La Cevichería">
                </div>

<%--                Descripcion--%>
                <div class="mb-3">
                    <label for="descripcion" class="form-label">Descripción</label>
                    <input type="text" class="form-control" name="descripcion" id="descripcion" required
                           placeholder="Ej: Almuerzos ricos">
                </div>
                <!-- Tipo de Comida -->
                <div class="mb-3">
                    <label for="tipoComida" class="form-label">Tipo de Comida</label>
                    <select class="form-select" name="tipoComida" id="tipoComida" required>
                        <option value="" disabled selected>Seleccione una opción</option>
                        <option value="Comida Rápida">🍔 Comida Rápida</option>
                        <option value="Comida Casera">🍲 Comida Casera</option>
                        <option value="Comida Costeña">🐟 Comida Costeña</option>
                        <option value="Platos a la Carta">🍽️ Platos a la Carta</option>
                    </select>
                </div>

                <!-- Horario de Atención -->
                <div class="mb-4">
                    <label class="form-label">Horario de Atención</label>
                    <div class="d-flex align-items-center">
                        <input type="time" class="form-control me-2" style="width: 150px;" name="horaApertura" required>
                        <span class="mx-2">a</span>
                        <input type="time" class="form-control ms-2" style="width: 150px;" name="horaCierre" required>
                    </div>
                </div>

                <button type="submit" class="btn btn-primary w-100 py-2">
                    <i class="bi bi-save me-2"></i> Guardar Restaurante
                </button>
            </form>
        </div>
    </div>
</div>
<!-- Bootstrap JS -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
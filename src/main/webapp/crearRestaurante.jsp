<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="DAO.RestauranteDAO, entidades.Restaurante, java.util.List" %>
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
            margin-bottom: 30px;
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

        .table-responsive {
            margin-top: 20px;
        }

        .table th {
            background-color: #f8f9fa;
        }

        .table-hover tbody tr:hover {
            background-color: rgba(0, 0, 0, 0.02);
        }

        .badge-comida {
            background-color: #6c757d;
            color: white;
            padding: 5px 10px;
            border-radius: 20px;
            font-size: 0.85em;
        }

        .historia-item {
            padding: 8px;
            margin-bottom: 5px;
            background-color: #f8f9fa;
            border-radius: 5px;
            border-left: 3px solid #0d6efd;
        }

        .historia-fecha {
            font-size: 0.8em;
            color: #6c757d;
        }

        .modal-historia textarea {
            min-height: 150px;
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

                <!-- Descripcion -->
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
                    <i class="fas fa-save me-2"></i> Guardar Restaurante
                </button>
            </form>
        </div>
    </div>

    <!-- Tabla de Restaurantes Registrados -->
    <div class="card shadow">
        <div class="card-body">
            <h4 class="mb-4">Restaurantes Registrados</h4>
            <div class="table-responsive">
                <table class="table table-hover">
                    <thead>
                    <tr>
                        <th>ID</th>
                        <th>Nombre</th>
                        <th>Descripción</th>
                        <th>Tipo de Comida</th>
                        <th>Horario</th>
                        <th>Menús</th>
                        <th>Acciones</th>
                    </tr>
                    </thead>
                    <tbody>
                    <%
                        RestauranteDAO dao = new RestauranteDAO();
                        List<Restaurante> restaurantes = dao.obtenerTodosLosRestaurantes();
                        dao.cerrar();

                        for (Restaurante r : restaurantes) {
                    %>
                    <tr>
                        <td><%= r.getId() %></td>
                        <td><strong><%= r.getNombre() %></strong></td>
                        <td><%= r.getDescripcion() != null ? r.getDescripcion() : "N/A" %></td>
                        <td><span class="badge-comida"><%= r.getTipoComida() %></span></td>
                        <td><%= r.getHoraApertura() + " - " + r.getHoraCierre() %></td>
                        <td>
                            <% if (!r.getHistorias().isEmpty()) { %>
                            <% for (String h : r.getHistorias()) { %>
                            <div class="historia-item mb-2">
                                <%= h %>
                            </div>
                            <% } %>
                            <% } else { %>
                            <span class="text-muted">Sin menús</span>
                            <% } %>
                        </td>
                        <td>
                            <button class="btn btn-sm btn-primary" data-bs-toggle="modal"
                                    data-bs-target="#modalMenu<%= r.getId() %>">
                                <i class="fas fa-plus"></i> Menú
                            </button>

                            <!-- Modal para agregar menú -->
                            <div class="modal fade" id="modalMenu<%= r.getId() %>">
                                <div class="modal-dialog">
                                    <div class="modal-content">
                                        <div class="modal-header">
                                            <h5 class="modal-title">Agregar Menú - <%= r.getNombre() %></h5>
                                            <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
                                        </div>
                                        <form action="${pageContext.request.contextPath}/restaurante" method="post">
                                            <input type="hidden" name="accion" value="agregarHistoria">
                                            <input type="hidden" name="restauranteId" value="<%= r.getId() %>">
                                            <div class="modal-body">
                                                <div class="mb-3">
                                                    <textarea class="form-control" name="historia"
                                                              placeholder="Describa el menú del día..." required></textarea>
                                                </div>
                                            </div>
                                            <div class="modal-footer">
                                                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancelar</button>
                                                <button type="submit" class="btn btn-primary">Guardar</button>
                                            </div>
                                        </form>
                                    </div>
                                </div>
                            </div>
                        </td>
                    </tr>
                    <% } %>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</div>

<!-- Bootstrap JS -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
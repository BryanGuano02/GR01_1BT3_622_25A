<%@ page import="DAO.UsuarioDAOImpl" %>
<%@ page import="jakarta.persistence.EntityManagerFactory" %>
<%@ page import="jakarta.persistence.Persistence" %>
<%@ page import="entidades.Restaurante" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>U-Food | Panel Administrativo</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;600&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    <link rel="stylesheet" href="styles.css">
    <style>
        .disabled-form {
            opacity: 0.6;
            pointer-events: none;
        }
        .edit-mode {
            display: none;
        }
        .view-mode .view-content {
            padding: 10px;
            background-color: #f8f9fa;
            border-radius: 5px;
            margin-bottom: 10px;
        }
    </style>
</head>
<body>
<div class="container mt-5">
    <%
        // Obtener el restaurante del usuario actual
        Restaurante restauranteUsuario = (Restaurante) session.getAttribute("usuario");
        boolean tieneDatos = restauranteUsuario != null &&
                restauranteUsuario.getNombre() != null &&
                !restauranteUsuario.getNombre().isEmpty();

        request.setAttribute("titulo", tieneDatos ? "Administrar Restaurante" : "Registrar Restaurante");
        request.setAttribute("botonAtras", false);
    %>
    <%@ include file="layout/header.jsp" %>

    <div class="card shadow">
        <div class="card-body">
            <% if (tieneDatos) { %>
            <!-- Modo visualización/edición -->
            <div class="view-mode">
                <h4 class="mb-4">Información de tu Restaurante</h4>

                <div class="mb-3">
                    <label class="form-label">Nombre del Restaurante</label>
                    <div class="view-content"><%= restauranteUsuario.getNombre() %></div>
                </div>

                <div class="mb-3">
                    <label class="form-label">Descripción</label>
                    <div class="view-content"><%= restauranteUsuario.getDescripcion() != null ? restauranteUsuario.getDescripcion() : "N/A" %></div>
                </div>

                <div class="mb-3">
                    <label class="form-label">Tipo de Comida</label>
                    <div class="view-content"><%= restauranteUsuario.getTipoComida() %></div>
                </div>

                <div class="mb-3">
                    <label class="form-label">Horario de Atención</label>
                    <div class="view-content">
                        <%= restauranteUsuario.getHoraApertura() %> - <%= restauranteUsuario.getHoraCierre() %>
                    </div>
                </div>

                <button id="btnEditar" class="btn btn-primary">
                    <i class="fas fa-edit me-2"></i> Editar Información
                </button>
            </div>

            <!-- Formulario de edición (oculto inicialmente) -->
            <div class="edit-mode">
                <form action="${pageContext.request.contextPath}/restaurante" method="post">
                    <input type="hidden" name="accion" value="actualizar">

                    <h4 class="mb-4">Editar Restaurante</h4>

                    <div class="mb-3">
                        <label for="nombre" class="form-label">Nombre del Restaurante</label>
                        <input type="text" class="form-control" name="nombre" id="nombre" required
                               value="<%= restauranteUsuario.getNombre() %>">
                    </div>

                    <div class="mb-3">
                        <label for="descripcion" class="form-label">Descripción</label>
                        <input type="text" class="form-control" name="descripcion" id="descripcion" required
                               value="<%= restauranteUsuario.getDescripcion() != null ? restauranteUsuario.getDescripcion() : "" %>">
                    </div>

                    <div class="mb-3">
                        <label for="tipoComida" class="form-label">Tipo de Comida</label>
                        <select class="form-select" name="tipoComida" id="tipoComida" required>
                            <option value="Comida Rápida" <%= "Comida Rápida".equals(restauranteUsuario.getTipoComida()) ? "selected" : "" %>>🍔 Comida Rápida</option>
                            <option value="Comida Casera" <%= "Comida Casera".equals(restauranteUsuario.getTipoComida()) ? "selected" : "" %>>🍲 Comida Casera</option>
                            <option value="Comida Costeña" <%= "Comida Costeña".equals(restauranteUsuario.getTipoComida()) ? "selected" : "" %>>🐟 Comida Costeña</option>
                            <option value="Platos a la Carta" <%= "Platos a la Carta".equals(restauranteUsuario.getTipoComida()) ? "selected" : "" %>>🍽️ Platos a la Carta</option>
                        </select>
                    </div>

                    <div class="mb-4">
                        <label class="form-label">Horario de Atención</label>
                        <div class="d-flex align-items-center">
                            <input type="time" class="form-control me-2" style="width: 150px;"
                                   name="horaApertura" required value="<%= restauranteUsuario.getHoraApertura() %>">
                            <span class="mx-2">a</span>
                            <input type="time" class="form-control ms-2" style="width: 150px;"
                                   name="horaCierre" required value="<%= restauranteUsuario.getHoraCierre() %>">
                        </div>
                    </div>

                    <div class="d-flex justify-content-between">
                        <button type="button" id="btnCancelarEdicion" class="btn btn-secondary">
                            <i class="fas fa-times me-2"></i> Cancelar
                        </button>
                        <button type="submit" class="btn btn-primary">
                            <i class="fas fa-save me-2"></i> Guardar Cambios
                        </button>
                    </div>
                </form>
            </div>
            <% } else { %>
            <!-- Formulario de creación (solo se muestra si no tiene datos) -->
            <form action="${pageContext.request.contextPath}/restaurante" method="post">
                <input type="hidden" name="accion" value="guardar">

                <h4 class="mb-4">Registra tu Restaurante</h4>

                <div class="mb-3">
                    <label for="nombre" class="form-label">Nombre del Restaurante</label>
                    <input type="text" class="form-control" name="nombre" id="nombre" required
                           placeholder="Ej: La Cevichería">
                </div>

                <div class="mb-3">
                    <label for="descripcion" class="form-label">Descripción</label>
                    <input type="text" class="form-control" name="descripcion" id="descripcion" required
                           placeholder="Ej: Almuerzos ricos">
                </div>

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

                <div class="mb-4">
                    <label class="form-label">Horario de Atención</label>
                    <div class="d-flex align-items-center">
                        <input type="time" class="form-control me-2" style="width: 150px;" name="horaApertura" required>
                        <span class="mx-2">a</span>
                        <input type="time" class="form-control ms-2" style="width: 150px;" name="horaCierre" required>
                    </div>
                </div>

                <button type="submit" class="btn btn-primary w-100 py-2">
                    <i class="fas fa-save me-2"></i> Registrar Restaurante
                </button>
            </form>
            <% } %>
        </div>
    </div>

    <!-- Sección de menús (solo visible si ya tiene datos) -->
    <% if (tieneDatos) { %>
    <!-- Sección de menús -->
    <div class="card shadow mt-4">
        <div class="card-body">
            <h4 class="mb-4">Gestión de Menús</h4>

            <% if (!restauranteUsuario.getHistorias().isEmpty()) { %>
            <div class="mb-4">
                <h5>Menús Registrados</h5>
                <% for (String menu : restauranteUsuario.getHistorias()) { %>
                <div class="card mb-2">
                    <div class="card-body">
                        <%= menu %>
                    </div>
                </div>
                <% } %>
            </div>
            <% } else { %>
            <p class="text-muted">No hay menús registrados aún.</p>
            <% } %>

            <button class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#modalMenu">
                <i class="fas fa-plus me-2"></i> Agregar Nuevo Menú
            </button>
        </div>
    </div>

    <!-- Modal para agregar menú -->
    <div class="modal fade" id="modalMenu" tabindex="-1" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title">Agregar Menú</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <form action="${pageContext.request.contextPath}/restaurante" method="post" id="menuForm">
                    <input type="hidden" name="accion" value="agregarHistoria">
                    <div class="modal-body">
                        <div class="mb-3">
                            <label class="form-label">Descripción del Menú</label>
                            <textarea class="form-control" name="historia" id="historiaInput"
                                      placeholder="Describa el menú del día..." required rows="5"></textarea>
                        </div>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancelar</button>
                        <button type="submit" class="btn btn-primary">Guardar Menú</button>
                    </div>
                </form>
            </div>
        </div>
    </div>
    <% } %>
</div>

<!-- Bootstrap JS -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
<script>
    // Manejar el modo edición/visualización
    document.addEventListener('DOMContentLoaded', function() {
        const btnEditar = document.getElementById('btnEditar');
        const btnCancelar = document.getElementById('btnCancelarEdicion');

        if (btnEditar && btnCancelar) {
            btnEditar.addEventListener('click', function() {
                document.querySelector('.view-mode').style.display = 'none';
                document.querySelector('.edit-mode').style.display = 'block';
            });

            btnCancelar.addEventListener('click', function() {
                document.querySelector('.edit-mode').style.display = 'none';
                document.querySelector('.view-mode').style.display = 'block';
            });
        }
    });
</script>
</body>
</html>
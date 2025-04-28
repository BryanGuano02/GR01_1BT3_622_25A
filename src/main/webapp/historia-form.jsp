<%--
  Created by IntelliJ IDEA.
  User: USER
  Date: 27/4/2025
  Time: 21:47
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="es">
<head>
  <meta charset="UTF-8">
  <title>Publicar Historia | U-Food</title>
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <!-- Google Fonts -->
  <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;600&display=swap" rel="stylesheet">
  <!-- Bootstrap CSS -->
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
  <!-- Font Awesome -->
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
  <style>
    body {
      background-color: #f8f9fa;
      font-family: 'Poppins', sans-serif;
      padding: 20px;
    }
    .card {
      border-radius: 10px;
      box-shadow: 0 4px 6px rgba(0,0,0,0.1);
    }
    .card-body {
      padding: 30px;
    }
    .btn-submit {
      background-color: #28a745;
      color: #fff;
    }
    .form-label {
      font-weight: 500;
    }
  </style>
</head>
<body>
<div class="container mt-5">
  <div class="row justify-content-center">
    <div class="col-md-8">

      <div class="d-flex justify-content-between align-items-center mb-4">
        <h3 class="text-primary">Publicar Nueva Historia</h3>
        <a href="${pageContext.request.contextPath}/inicio" class="btn btn-outline-secondary">
          <i class="fas fa-arrow-left me-1"></i> Volver
        </a>
      </div>

      <div class="card shadow-sm">
        <div class="card-body">

          <form action="${pageContext.request.contextPath}/historias" method="post" enctype="multipart/form-data">

            <input type="hidden" name="idRestaurante" value="${param.id}"/>

            <div class="mb-4">
              <label for="imagen" class="form-label">Imagen del menú:</label>
              <input type="file" id="imagen" name="imagen"
                     accept="image/*" required
                     class="form-control"/>
            </div>

            <div class="mb-4">
              <label for="contenido" class="form-label">Descripción:</label>
              <textarea id="contenido" name="contenido"
                        rows="4" required
                        class="form-control"
                        placeholder="Cuenta algo sobre esta historia..."></textarea>
            </div>

            <button type="submit" class="btn btn-submit w-100 py-2">
              <i class="fas fa-paper-plane me-2"></i> Publicar Historia
            </button>

          </form>

        </div>
      </div>

    </div>
  </div>
</div>

<!-- Bootstrap Bundle JS -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>U-Food | Resultado Comparación</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;600&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        body {
            background-color: #f8f9fa;
            padding: 20px;
        }

        .card {
            border-radius: 10px;
            box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
        }

        .card-body {
            padding: 30px;
        }

        .better {
            background-color: #e8f5e9;
        }

        .no-rating {
            color: #6c757d;
            font-style: italic;
        }

        .rating-info {
            font-size: 0.9em;
            color: #6c757d;
            margin-top: 5px;
        }

        .table th {
            background-color: #0d6efd;
            color: white;
        }
    </style>
</head>
<body>
    <div class="container mt-5">
        <div class="d-flex justify-content-between align-items-center mb-4">
            <h2 class="text-primary">Resultado de Comparación</h2>
            </div>
        </div>

        <div class="card shadow">
            <div class="card-body">
                <div class="table-responsive">
                    <table class="table table-bordered">
                        <tr>
                            <th>Características</th>
                            <th>${restaurante1.nombre}</th>
                            <th>${restaurante2.nombre}</th>
                        </tr>
                        <tr>
                            <td>Tipo de Comida</td>
                            <td>${restaurante1.tipoComida}</td>
                            <td>${restaurante2.tipoComida}</td>
                        </tr>
                        <tr>
                            <td>Descripción</td>
                            <td>${restaurante1.descripcion}</td>
                            <td>${restaurante2.descripcion}</td>
                        </tr>
                        <tr>
                            <td>Horario</td>
                            <td>${restaurante1.horaApertura} - ${restaurante1.horaCierre}</td>
                            <td>${restaurante2.horaApertura} - ${restaurante2.horaCierre}</td>
                        </tr>
                        <tr>
                            <td>Calificación más reciente</td>
                            <td class="${calificacion1.puntaje >= (calificacion2 != null ? calificacion2.puntaje : 0) ? 'better' : ''}">
                                <c:choose>
                                    <c:when test="${calificacion1 != null}">
                                        ${calificacion1.puntaje}
                                        <div class="rating-info">
                                            Comentario: ${calificacion1.comentario}
                                        </div>
                                    </c:when>
                                    <c:otherwise>
                                        <span class="no-rating">Sin calificaciones</span>
                                    </c:otherwise>
                                </c:choose>
                            </td>
                            <td class="${calificacion2.puntaje >= (calificacion1 != null ? calificacion1.puntaje : 0) ? 'better' : ''}">
                                <c:choose>
                                    <c:when test="${calificacion2 != null}">
                                        ${calificacion2.puntaje}
                                        <div class="rating-info">
                                            Comentario: ${calificacion2.comentario}
                                        </div>
                                    </c:when>
                                    <c:otherwise>
                                        <span class="no-rating">Sin calificaciones</span>
                                    </c:otherwise>
                                </c:choose>
                            </td>
                        </tr>
                    </table>
                </div>

                <a href="SvComparar" class="btn btn-primary w-100 py-2 mt-3">
                    <i class="fas fa-redo me-2"></i> Realizar otra comparación
                </a>
            </div>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
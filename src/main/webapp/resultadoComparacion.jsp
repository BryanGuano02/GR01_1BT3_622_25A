<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Resultado de Comparación</title>
    <style>
        .comparison-table {
            width: 100%;
            border-collapse: collapse;
            margin: 20px 0;
        }
        
        .comparison-table th, .comparison-table td {
            padding: 12px;
            border: 1px solid #ddd;
            text-align: left;
        }
        
        .comparison-table th {
            background-color: #4CAF50;
            color: white;
        }
        
        .better {
            background-color: #e8f5e9;
        }
        
        .no-rating {
            color: #999;
            font-style: italic;
        }
        
        .container {
            max-width: 1000px;
            margin: 20px auto;
            padding: 20px;
        }
        
        .btn-volver {
            background-color: #4CAF50;
            color: white;
            padding: 10px 20px;
            text-decoration: none;
            border-radius: 4px;
            display: inline-block;
            margin-top: 20px;
        }
        
        .rating-info {
            font-size: 0.9em;
            color: #666;
            margin-top: 5px;
        }
    </style>
</head>
<body>
    <div class="container">
        <h2>Comparación de Restaurantes</h2>
        
        <table class="comparison-table">
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
        
        <a href="SvComparar" class="btn-volver">Realizar otra comparación</a>
    </div>
</body>
</html>
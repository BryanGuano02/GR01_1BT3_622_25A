<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Comparar Restaurantes</title>
    <style>
        .container {
            max-width: 800px;
            margin: 20px auto;
            padding: 20px;
        }
        .comparison-form {
            background-color: #fff;
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0 2px 4px rgba(0,0,0,0.1);
        }
        .form-group {
            margin-bottom: 20px;
        }
        select {
            width: 100%;
            padding: 10px;
            border: 1px solid #ddd;
            border-radius: 4px;
            margin-top: 5px;
        }
        .btn-comparar {
            background-color: #4CAF50;
            color: white;
            padding: 12px 24px;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            font-size: 16px;
            width: 100%;
        }
        .btn-comparar:hover {
            background-color: #45a049;
        }
        .error-message {
            color: #dc3545;
            background-color: #f8d7da;
            padding: 10px;
            margin-bottom: 20px;
            border-radius: 4px;
            border: 1px solid #f5c6cb;
        }
    </style>
</head>
<body>
    <div class="container">
        <h2>Comparar Restaurantes</h2>
        
        <c:if test="${not empty error}">
            <div class="error-message">
                ${error}
            </div>
        </c:if>
        
        <div class="comparison-form">
            <form action="SvComparar" method="get">
                <input type="hidden" name="accion" value="comparar">
                
                <div class="form-group">
                    <label for="restaurante1">Primer Restaurante:</label>
                    <select name="restaurante1" id="restaurante1" required>
                        <option value="">Seleccione un restaurante</option>
                        <c:forEach items="${restaurantes}" var="rest">
                            <option value="${rest.id}">${rest.nombre}</option>
                        </c:forEach>
                    </select>
                </div>
                
                <div class="form-group">
                    <label for="restaurante2">Segundo Restaurante:</label>
                    <select name="restaurante2" id="restaurante2" required>
                        <option value="">Seleccione un restaurante</option>
                        <c:forEach items="${restaurantes}" var="rest">
                            <option value="${rest.id}">${rest.nombre}</option>
                        </c:forEach>
                    </select>
                </div>
                
                <button type="submit" class="btn-comparar">Comparar Restaurantes</button>
            </form>
        </div>
    </div>

    <script>
        // Validación en el lado del cliente
        document.querySelector('form').addEventListener('submit', function(e) {
            const rest1 = document.getElementById('restaurante1').value;
            const rest2 = document.getElementById('restaurante2').value;
            
            if (rest1 === rest2) {
                e.preventDefault();
                alert('Por favor seleccione dos restaurantes diferentes');
            }
        });

        // Validación al cambiar la selección
        document.getElementById('restaurante2').addEventListener('change', function() {
            const rest1 = document.getElementById('restaurante1').value;
            if (this.value === rest1 && this.value !== '') {
                this.value = '';
                alert('Por favor seleccione un restaurante diferente');
            }
        });
    </script>
</body>
</html>
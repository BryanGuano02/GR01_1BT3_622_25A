<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>U-Food | Comparar Restaurantes</title>
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
    <div class="container mt-5">
        <div class="d-flex justify-content-between align-items-center mb-4">
            <h2 class="text-primary">Comparar Restaurantes</h2>
            </div>
        </div>

        <c:if test="${not empty error}">
            <div class="alert alert-danger" role="alert">
                ${error}
            </div>
        </c:if>

        <div class="card shadow">
            <div class="card-body">
                <form action="SvComparar" method="get">
                    <input type="hidden" name="accion" value="comparar">

                    <div class="mb-3">
                        <label for="restaurante1" class="form-label">Primer Restaurante</label>
                        <select class="form-select" name="restaurante1" id="restaurante1" required>
                            <option value="">Seleccione un restaurante</option>
                            <c:forEach items="${restaurantes}" var="rest">
                                <option value="${rest.id}">${rest.nombre}</option>
                            </c:forEach>
                        </select>
                    </div>

                    <div class="mb-3">
                        <label for="restaurante2" class="form-label">Segundo Restaurante</label>
                        <select class="form-select" name="restaurante2" id="restaurante2" required>
                            <option value="">Seleccione un restaurante</option>
                            <c:forEach items="${restaurantes}" var="rest">
                                <option value="${rest.id}">${rest.nombre}</option>
                            </c:forEach>
                        </select>
                    </div>

                    <button type="submit" class="btn btn-primary w-100 py-2">
                        <i class="fas fa-exchange-alt me-2"></i> Comparar Restaurantes
                    </button>
                </form>
            </div>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    <script>
        document.querySelector('form').addEventListener('submit', function(e) {
            const rest1 = document.getElementById('restaurante1').value;
            const rest2 = document.getElementById('restaurante2').value;
            
            if (rest1 === rest2) {
                e.preventDefault();
                alert('Por favor seleccione dos restaurantes diferentes');
            }
        });

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
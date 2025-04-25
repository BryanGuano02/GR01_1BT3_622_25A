<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>U-Food | Panel Administrativo</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;600&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
    <style>
        :root {
            --primary-color: #ff6b6b;
            --secondary-color: #4ecdc4;
            --dark-color: #292f36;
            --light-color: #f7fff7;
            --accent-color: #ffd166;
            --sidebar-width: 250px;
        }

        * {
            box-sizing: border-box;
            margin: 0;
            padding: 0;
            font-family: 'Poppins', sans-serif;
        }

        body {
            display: flex;
            background-color: #f5f7fa;
            color: var(--dark-color);
            line-height: 1.6;
        }

        /* Sidebar Styles */
        .sidebar {
            width: var(--sidebar-width);
            background: var(--dark-color);
            color: white;
            height: 100vh;
            position: fixed;
            padding: 20px 0;
            transition: all 0.3s;
        }

        .logo {
            text-align: center;
            padding: 0 20px 20px;
            border-bottom: 1px solid rgba(255, 255, 255, 0.1);
        }

        .logo h1 {
            color: white;
            font-size: 24px;
            font-weight: 600;
        }

        .logo span {
            color: var(--secondary-color);
        }

        .nav-menu {
            margin-top: 30px;
        }

        .nav-item {
            padding: 12px 20px;
            display: flex;
            align-items: center;
            cursor: pointer;
            transition: all 0.3s;
        }

        .nav-item:hover {
            background: rgba(255, 255, 255, 0.1);
        }

        .nav-item.active {
            background: var(--primary-color);
        }

        .nav-item i {
            margin-right: 10px;
            width: 20px;
            text-align: center;
        }

        /* Main Content Styles */
        .main-content {
            margin-left: var(--sidebar-width);
            flex: 1;
            padding: 30px;
        }

        .header {
            display: flex;
            justify-content: space-between;
            align-items: center;
            margin-bottom: 30px;
        }

        .header h2 {
            color: var(--primary-color);
            font-weight: 600;
            font-size: 28px;
        }

        .user-info {
            display: flex;
            align-items: center;
        }

        .user-info img {
            width: 40px;
            height: 40px;
            border-radius: 50%;
            margin-right: 10px;
        }

        /* Form Styles */
        .card {
            background: white;
            border-radius: 15px;
            box-shadow: 0 5px 20px rgba(0, 0, 0, 0.05);
            padding: 30px;
            margin-bottom: 30px;
        }

        .form-group {
            margin-bottom: 25px;
        }

        label {
            display: block;
            margin-bottom: 8px;
            font-weight: 500;
            color: var(--dark-color);
        }

        input[type="text"],
        select {
            width: 100%;
            padding: 12px 15px;
            border: 2px solid #e0e0e0;
            border-radius: 8px;
            font-size: 16px;
            transition: all 0.3s;
        }

        input[type="text"]:focus,
        select:focus {
            border-color: var(--secondary-color);
            outline: none;
            box-shadow: 0 0 0 3px rgba(78, 205, 196, 0.2);
        }

        .time-inputs {
            display: flex;
            align-items: center;
            gap: 10px;
        }

        input[type="time"] {
            padding: 12px;
            border: 2px solid #e0e0e0;
            border-radius: 8px;
            font-size: 16px;
            flex: 1;
        }

        .separator {
            font-weight: 500;
            color: var(--dark-color);
        }

        button[type="submit"] {
            background-color: var(--primary-color);
            color: white;
            border: none;
            padding: 14px 30px;
            border-radius: 8px;
            font-size: 16px;
            font-weight: 500;
            cursor: pointer;
            width: 100%;
            transition: all 0.3s;
        }

        button[type="submit"]:hover {
            background-color: #ff5252;
            transform: translateY(-2px);
            box-shadow: 0 5px 15px rgba(255, 107, 107, 0.4);
        }

        /* Responsive Styles */
        @media (max-width: 768px) {
            .sidebar {
                width: 70px;
                overflow: hidden;
            }

            .logo h1 {
                font-size: 0;
            }

            .logo h1:after {
                content: "U";
                font-size: 24px;
                color: white;
            }

            .nav-item span {
                display: none;
            }

            .nav-item i {
                margin-right: 0;
                font-size: 20px;
            }

            .main-content {
                margin-left: 70px;
            }
        }
    </style>
</head>
<body>
<!-- Menu -->
<!--<div class="sidebar">
    <div class="logo">
        <h1>U<span>-Food</span></h1>
    </div>

    <div class="nav-menu">
        <div class="nav-item active">
            <i class="fas fa-utensils"></i>
            <span>Registrar Restaurante</span>
        </div>
        <div class="nav-item">
            <i class="fas fa-list"></i>
            <span>Insert</span>
        </div>
        <div class="nav-item">
            <i class="fas fa-chart-bar"></i>
            <span>Insert</span>
        </div>
        <div class="nav-item">
            <i class="fas fa-cog"></i>
            <span>Insert</span>
        </div>
        <div class="nav-item">
            <i class="fas fa-users"></i>
            <span>Insert</span>
        </div>
    </div>
</div>-->

<!-- Main Content Area -->
<div class="main-content">
    <div class="header">
        <h2>Registrar Nuevo Restaurante</h2>
        <div class="user-info">
            <img src="https://ui-avatars.com/api/?name=Admin&background=ff6b6b&color=fff" alt="Usuario">
            <span>Administrador</span>
        </div>
    </div>

    <div class="card">
        <form action="${pageContext.request.contextPath}/restaurante" method="post">
            <input type="hidden" name="accion" value="guardar">

            <!-- Nombre -->
            <div class="form-group">
                <label for="nombre">Nombre del Restaurante</label>
                <input type="text" name="nombre" id="nombre" required placeholder="Ej: La Cevichería">
            </div>

            <!-- Tipo de Comida -->
            <div class="form-group">
                <label for="tipoComida">Tipo de Comida</label>
                <select name="tipoComida" id="tipoComida" required>
                    <option value="" disabled selected>Seleccione una opción</option>
                    <option value="COMIDA_RAPIDA">🍔 Comida Rápida</option>
                    <option value="COMIDA_CASERA">🍲 Comida Casera</option>
                    <option value="COMIDA_COSTEÑA">🐟 Comida Costeña</option>
                    <option value="PLATOS_CARTA">🍽️ Platos a la Carta</option>
                </select>
            </div>

            <!-- Horario de Atención -->
            <div class="form-group">
                <label>Horario de Atención</label>
                <div class="time-inputs">
                    <input type="time" name="horaApertura" required>
                    <span class="separator">a</span>
                    <input type="time" name="horaCierre" required>
                </div>
            </div>

            <button type="submit">
                <i class="fas fa-save"></i> Guardar Restaurante
            </button>
        </form>
    </div>
</div>
</body>
</html>
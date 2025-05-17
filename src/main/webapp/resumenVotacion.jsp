<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>

<html>
<head>
    <title>Resumen de Votaciones</title>
</head>
<body>

<h2>Resumen de Votaciones</h2>

<p>Promedio: <%= request.getAttribute("promedio") %></p>

<ul>
    <%
        List<Calificacion> calificaciones = (List<Calificacion>) request.getAttribute("calificaciones");
        if (calificaciones != null) {
            for (Calificacion cal : calificaciones) {
    %>
                <li>Puntaje: <%= cal.getPuntaje() %> - <%= cal.getComentario() %></li>
    <%
            }
        }
    %>
</ul>

</body>
</html>

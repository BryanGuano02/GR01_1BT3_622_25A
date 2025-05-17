<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>

<html>
<head>
    <title>Votación del Menú</title>
</head>
<body>

<h2>Menú del Día</h2>
<ul>
    <%
        List<String> menuDelDia = (List<String>) request.getAttribute("menuDelDia");
        if (menuDelDia != null) {
            for (String item : menuDelDia) {
    %>
                <li><%= item %></li>
    <%
            }
        }
    %>
</ul>

<%
    Boolean votacionActiva = (Boolean) request.getAttribute("votacionActiva");
    if (Boolean.TRUE.equals(votacionActiva)) {
%>
    <h2>Votar Menú del Día</h2>
    <form action="votacion" method="post">
        <label>Puntaje (0-5):</label>
        <input type="number" name="puntaje" min="0" max="5" step="0.1" required><br>
        <label>Comentario:</label>
        <textarea name="comentario" required></textarea><br>
        <button type="submit">Votar</button>
    </form>
<%
    } else {
%>
    <h3>La votación ha sido cerrada.</h3>
<%
    }
%>

</body>
</html>
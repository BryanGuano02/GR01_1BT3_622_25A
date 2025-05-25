
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<%
    String[] opcionesDeRetorno = (String[]) request.getAttribute("opcionesDeRetorno");
    String[] opcionesTiempoEspera = (String[]) request.getAttribute("opcionesTiempoEspera");
    String[] opciones = new String[5];

    if (request.getParameter("name").equals("volveria")  ){
        opciones = opcionesDeRetorno;
    }
    if (request.getParameter("name").equals("tiempoEspera")){
        opciones = opcionesTiempoEspera;
    }
    if (!request.getParameter("name").equals("volveria") && !request.getParameter("name").equals("tiempoEspera")){
        opciones = new String[] { "", "", "", "", "" };
    }
%>
<div class="mb-3">
    <label class="form-label">${param.tituloCalificacion}</label>
    <div class="d-flex justify-content-between mb-3">
        <div class="rating-option">
            <input type="radio" id="puntaje1" name="${param.name}" value="1" >
            <label for="puntaje1">★  <%=opciones[0]%> </label>
        </div>
        <div class="rating-option">
            <input type="radio" id="puntaje2" name="${param.name}" value="2">
            <label for="puntaje2">★★ <%=opciones[1]%></label>
        </div>
        <div class="rating-option">
            <input type="radio" id="puntaje3" name="${param.name}" value="3">
            <label for="puntaje3">★★★ <%=opciones[2]%></label>
        </div>
        <div class="rating-option">
            <input type="radio" id="puntaje4" name="${param.name}" value="4">
            <label for="puntaje4">★★★★ <%=opciones[3]%></label>
        </div>
        <div class="rating-option">
            <input type="radio" id="puntaje5" name="${param.name}" value="5">
            <label for="puntaje5">★★★★★ <%=opciones[4]%></label>
        </div>
    </div>
</div>
</body>
</html>

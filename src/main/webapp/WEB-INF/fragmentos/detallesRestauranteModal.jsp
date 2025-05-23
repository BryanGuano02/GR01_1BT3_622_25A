<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<div class="modal-header bg-primary text-white">
  <h5 class="modal-title">${restaurante.nombre}</h5>
  <button type="button" class="btn-close btn-close-white" data-bs-dismiss="modal" aria-label="Close"></button>
</div>
<div class="modal-body">
  <div class="row">
    <div class="col-md-6">
      <p><strong>Tipo:</strong>
        <c:choose>
          <c:when test="${restaurante.tipoComida == 'Comida Rápida'}">🍔</c:when>
          <c:when test="${restaurante.tipoComida == 'Comida Casera'}">🍲</c:when>
          <c:otherwise>🍽️</c:otherwise>
        </c:choose>
        ${restaurante.tipoComida}
      </p>
      <p><strong>Descripción:</strong> ${restaurante.descripcion}</p>
    </div>
    <div class="col-md-6">
      <p><strong>Horario:</strong> ${restaurante.horaApertura} - ${restaurante.horaCierre}</p>
      <p><strong>Calificación:</strong>
        <c:forEach begin="1" end="5" var="i">
          <i class="fas fa-star ${i <= restaurante.puntajePromedio ? 'text-warning' : 'text-secondary'}"></i>
        </c:forEach>
        (<fmt:formatNumber value="${restaurante.puntajePromedio}" pattern="#.##"/>)
      </p>
    </div>
  </div>

  <c:if test="${not empty restaurante.menuDelDia}">
    <div class="mt-4 border-top pt-3">
      <h5><i class="fas fa-utensils me-2"></i>Menú del Día</h5>
      <div class="card">
        <div class="card-body">
          <pre class="mb-0">${restaurante.menuDelDia.descripcion}</pre>
          <div class="mt-2 text-end">
            <span class="badge bg-danger">
              <i class="fas fa-heart"></i> ${restaurante.menuDelDia.cantidadVotos} likes
            </span>
          </div>
        </div>
      </div>
    </div>
  </c:if>
</div>
<div class="modal-footer">
  <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">
    <i class="fas fa-times me-2"></i> Cerrar
  </button>
  <c:if test="${not empty sessionScope.usuario && sessionScope.usuario.tipoUsuario == 'COMENSAL'}">
    <form action="${pageContext.request.contextPath}/suscribirse" method="post" class="d-inline">
      <input type="hidden" name="idRestaurante" value="${restaurante.id}">
      <input type="hidden" name="idComensal" value="${sessionScope.usuario.id}">
      <button type="submit" class="btn btn-primary">
        <i class="fas fa-bell me-2"></i> Suscribirse
      </button>
    </form>
  </c:if>
</div>
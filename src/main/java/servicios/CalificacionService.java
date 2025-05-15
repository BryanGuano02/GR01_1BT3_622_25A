package servicios;

import DAO.CalificacionDAO;
import DAO.UsuarioDAO;
import entidades.Calificacion;
import entidades.Comensal;
import entidades.Restaurante;
import exceptions.ServiceException;
import java.util.List;
import java.util.Map;

public class CalificacionService {
    private final CalificacionDAO calificacionDAO;
    private final UsuarioDAO usuarioDAO;

    public CalificacionService(CalificacionDAO calificacionDAO, UsuarioDAO usuarioDAO) {
        this.calificacionDAO = calificacionDAO;
        this.usuarioDAO = usuarioDAO;
    }

    public void crearCalificacion(Calificacion calificacion) throws ServiceException {
        try {
            calificacionDAO.crear(calificacion);
            actualizarPuntajePromedio(calificacion.getRestaurante());
        } catch (Exception e) {
            throw new ServiceException("Error al crear calificación: " + e.getMessage(), e);
        }
    }

    public List<Calificacion> obtenerCalificacionesPorRestaurante(Long restauranteId) {
        try {
            return calificacionDAO.obtenerCalificacionesPorRestaurante(restauranteId);
        } catch (Exception e) {
            throw new RuntimeException("Error al obtener calificaciones", e);
        }
    }

    public void calificar(Map<String, Object> parametrosCalificacion) throws ServiceException {
        try {
            Double puntaje = (Double) parametrosCalificacion.get("puntaje");
            String comentario = (String) parametrosCalificacion.get("comentario");
            Long idComensal = (Long) parametrosCalificacion.get("idComensal");
            Long idRestaurante = (Long) parametrosCalificacion.get("idRestaurante");

            Comensal comensal = usuarioDAO.obtenerComensalPorId(idComensal);
            Restaurante restaurante = (Restaurante) usuarioDAO.findById(idRestaurante);

            if (comensal == null || restaurante == null) {
                throw new ServiceException("Comensal o restaurante no encontrado");
            }

            Calificacion calificacion = new Calificacion();
            calificacion.setPuntaje(puntaje);
            calificacion.setComentario(comentario);
            calificacion.setComensal(comensal);
            calificacion.setRestaurante(restaurante);

            crearCalificacion(calificacion);
        } catch (Exception e) {
            throw new ServiceException("Error al procesar la calificación: " + e.getMessage(), e);
        }
    }

    public Double calcularPuntajePromedio(Long restauranteId) {
        try {
            return calificacionDAO.calcularPromedioCalificaciones(restauranteId);
        } catch (Exception e) {
            throw new RuntimeException("Error al calcular promedio", e);
        }
    }

    private void actualizarPuntajePromedio(Restaurante restaurante) {
        try {
            Double nuevoPromedio = calcularPuntajePromedio(restaurante.getId());
            restaurante.setPuntajePromedio(nuevoPromedio);
            usuarioDAO.save(restaurante);
        } catch (Exception e) {
            throw new RuntimeException("Error al actualizar promedio", e);
        }
    }
}

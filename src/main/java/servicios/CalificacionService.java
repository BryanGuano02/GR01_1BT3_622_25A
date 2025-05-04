package servicios;

import DAO.CalificacionDAO;
import DAO.ComensalDAO;
import DAO.RestauranteDAO;
import entidades.Calificacion;
import entidades.Comensal;
import entidades.Restaurante;

import java.util.List;
import java.util.Map;

public class CalificacionService {
    private final CalificacionDAO calificacionDAO;
    private final RestauranteDAO restauranteDAO;
    private final ComensalDAO comensalDAO;

    public CalificacionService() {
        this.calificacionDAO = new CalificacionDAO();
        this.restauranteDAO = new RestauranteDAO();
        this.comensalDAO = new ComensalDAO();
    }

    public Boolean calificar(Map<String, Object> parametrosCalificacion) {
        Long idComensal = (Long) parametrosCalificacion.get("idComensal");
        Long idRestaurante = (Long) parametrosCalificacion.get("idRestaurante");
        Double puntaje = (Double) parametrosCalificacion.get("puntaje");
        String comentario = (String) parametrosCalificacion.get("comentario");

        if (esIDNull(idComensal, idRestaurante)) return false;
        if (esPuntajeInvalido(puntaje)) return false;

        Calificacion calificacion = construirCalificacion(puntaje, comentario, idComensal, idRestaurante);
        calificacionDAO.crear(calificacion);
        actualizarPromedioRestaurante(idRestaurante);

        return true;
    }

    private Calificacion construirCalificacion(Double puntaje, String comentario, Long idComensal, Long idRestaurante) {
        Comensal comensal = comensalDAO.obtenerComensalPorId(idComensal);
        Restaurante restaurante = restauranteDAO.obtenerRestaurantePorId(idRestaurante);
        return new Calificacion(puntaje, comentario, comensal, restaurante);
    }

    private boolean esPuntajeInvalido(Double puntaje) {
        return puntaje < 1 || puntaje > 5;
    }

    private boolean esIDNull(Long idComensal, Long idRestaurante) {
        return idRestaurante == null || idComensal == null;
    }

    private void actualizarPromedioRestaurante(Long idRestaurante) {
        List<Calificacion> calificaciones = calificacionDAO.obtenerTodosLosCalificaciones(idRestaurante);
        Restaurante restauranteAActualizar = restauranteDAO.obtenerRestaurantePorId(idRestaurante);
        Double calificacionPromedio = calcularPromedioCalificaciones(calificaciones);

        if (restauranteAActualizar.getPuntajePromedio() != calificacionPromedio) {
            restauranteAActualizar.setPuntajePromedio(calificacionPromedio);
            restauranteDAO.actualizar(restauranteAActualizar);
        }
    }

    public Double calcularPromedioCalificaciones(List<Calificacion> calificaciones) {
        if (calificaciones == null || calificaciones.isEmpty()) {
            return 0.0;
        }

        Double suma = 0.0;
        for (Calificacion calificacion : calificaciones) {
            suma += calificacion.getPuntaje();
        }

        return suma / calificaciones.size();
    }
}

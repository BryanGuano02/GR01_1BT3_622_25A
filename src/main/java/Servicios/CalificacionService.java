package Servicios;

import DAO.CalificacionDAO;
import DAO.RestauranteDAO;
import entidades.Calificacion;
import entidades.Restaurante;

public class CalificacionService {
    private final int puntaje;
    private final String comentario;
    private final Long idComensal;
    private final Long idRestaurante;
    private CalificacionDAO calificacionDAO;
    private RestauranteDAO restauranteDAO;

    public CalificacionService(int puntaje, String comentario, Long idComensal, Long idRestaurante, CalificacionDAO calificacionDAO, RestauranteDAO restauranteDAO) {
        this.puntaje = puntaje;
        this.comentario = comentario;
        this.idComensal = idComensal;
        this.idRestaurante = idRestaurante;
        this.calificacionDAO = calificacionDAO;
        this.restauranteDAO = restauranteDAO;
    }

    public Boolean calificar() {
        if (idRestaurante == null || idComensal == null) return false;
        if (puntaje < 1 || puntaje > 5) return false;
        return !yaCalifico(idComensal, idRestaurante);

        Calificacion calificacion = new Calificacion(puntaje, comentario, idComensal, idRestaurante);
        CalificacionDAO calificacionDAO = new CalificacionDAO();
        calificacionDAO.crear(calificacion);

        actualizarPromedioRestaurante(idRestaurante);
    }

    private void actualizarPromedioRestaurante(Long idRestaurante) {
        Double promedio = calificacionDAO.calcularPromedioPorRestaurante(idRestaurante);
        if (promedio != null) {
            Restaurante restaurante = restauranteDAO.buscarPorId(idRestaurante);
            restaurante.setPromedioCalificaciones(promedio);
            restauranteDAO.actualizar(restaurante);
        }
    }

    private Boolean yaCalifico(Long idComensal, Long idRestaurante) {
        return calificacionDAO.existeCalificacionDeComensal(idComensal, idRestaurante);
    }

}

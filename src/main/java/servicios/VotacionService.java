package servicios;

import DAO.UsuarioDAO;
import DAO.UsuarioDAOImpl;
import DAO.VotacionDAO;
import entidades.Calificacion;
import entidades.Comensal;
import entidades.Restaurante;

import java.util.List;

public class VotacionService {
    private final VotacionDAO votacionDAO;
    private final UsuarioDAO usuarioDAO;

    public VotacionService(VotacionDAO votacionDAO, UsuarioDAO usuarioDAO) {
        this.votacionDAO = votacionDAO;
        this.usuarioDAO = usuarioDAO;
    }

    /**
     * Registrar un voto.
     */
    public void registrarVoto(Long idComensal, Long idRestaurante, double puntaje, String comentario) {
        Comensal comensal = usuarioDAO.obtenerComensalPorId(idComensal);
        Restaurante restaurante = (Restaurante) usuarioDAO.findById(idRestaurante);

        if (comensal != null && restaurante != null && restaurante.isVotacionActiva()) {
            boolean yaVoto = votacionDAO.obtenerCalificacionesPorComensal(idComensal).stream()
                    .anyMatch(c -> c.getRestaurante().getId().equals(idRestaurante));

            if (!yaVoto) {
                Calificacion calificacion = new Calificacion(puntaje, comentario, comensal, restaurante);
                votacionDAO.guardarCalificacion(calificacion);
            }
        }
    }

    /**
     * Detener la votación y calcular el promedio.
     */
    public double detenerVotacion(Long idRestaurante) {
        Restaurante restaurante = (Restaurante) usuarioDAO.findById(idRestaurante);

        if (restaurante != null) {
            restaurante.setVotacionActiva(false);
            usuarioDAO.save(restaurante);
            return votacionDAO.calcularPromedioCalificaciones(idRestaurante);
        }
        return 0.0;
    }

    /**
     * Obtener calificaciones por restaurante.
     */
    public List<Calificacion> obtenerCalificacionesPorRestaurante(Long idRestaurante) {
        return votacionDAO.obtenerCalificacionesPorRestaurante(idRestaurante);
    }
}


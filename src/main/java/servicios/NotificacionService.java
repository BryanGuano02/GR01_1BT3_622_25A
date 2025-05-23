package servicios;

import DAO.NotificacionDAO;
import DAO.UsuarioDAO;
import entidades.Comensal;
import entidades.Historia;
import entidades.Notificacion;
import entidades.Restaurante;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class NotificacionService {

    private UsuarioDAO usuarioDAO;
    private NotificacionDAO notificacionDAO;
    private EntityManagerFactory emf;

    public NotificacionService(UsuarioDAO usuarioDAO, NotificacionDAO notificacionDAO) {
        if (usuarioDAO == null && notificacionDAO == null) {
            this.emf = null;
        } else {
            this.emf = Persistence.createEntityManagerFactory("UFood_PU");
        }
        this.usuarioDAO = usuarioDAO;
        this.notificacionDAO = notificacionDAO;
    }

    private void notificarComensalMenuDia(Comensal comensal, String nombreRestaurante, Historia historia) {
        StringBuilder mensaje = new StringBuilder();
        mensaje.append(nombreRestaurante).append(" ha publicado un nuevo menú del día:");

        if (historia != null && historia.getContenido() != null && !historia.getContenido().isEmpty()) {
            mensaje.append("\n\n").append(historia.getContenido());
        }

        comensal.agregarNotificacion(mensaje.toString());
        usuarioDAO.save(comensal);
    }

    public boolean notificarComensalesMenuDia(Restaurante restaurante, Historia historia) {
        try {
            if (restaurante.getSuscripciones() == null || restaurante.getSuscripciones().isEmpty()) {
                return false;
            }
            restaurante.getSuscripciones().forEach(suscripcion -> {
                Comensal comensal = suscripcion.getComensal();
                notificarComensalMenuDia(comensal, restaurante.getNombre(), historia);
            });
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public Boolean marcarComoLeida(Notificacion notificacion) {
        if (notificacion != null && !notificacion.isLeida()) {
            notificacion.setLeida(true);
            return true;
        }
        return false;
    }

    public Boolean marcarNotificacionComoLeida(Notificacion notificacion) {
        if (notificacion != null && notificacion.getId() != null) {
            Notificacion notifBD = notificacionDAO.buscarPorId(notificacion.getId());
            if (notifBD != null && !notifBD.isLeida()) {
                if (marcarComoLeida(notifBD)) {
                    notificacionDAO.actualizar(notifBD);
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Notifica a un comensal que se ha elegido un restaurante para una planificación
     *
     * @param comensal El comensal a notificar
     * @param mensaje  El mensaje de la notificación
     * @return true si la notificación fue enviada exitosamente, false en caso contrario
     */
    public boolean notificarRestauranteElegido(Comensal comensal, String mensaje) {
        try {
            if (comensal == null) {
                return false;
            }

            comensal.agregarNotificacion(mensaje);

            if (usuarioDAO != null) {
                usuarioDAO.save(comensal);
            }

            return true;
        } catch (Exception e) {
            return false;
        }
    }
}

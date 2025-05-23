package servicios;

import DAO.SuscripcionDAO;
import DAO.UsuarioDAO;
import entidades.Comensal;
import entidades.Restaurante;
import entidades.Suscripcion;
import exceptions.ServiceException;

import java.util.logging.Level;
import java.util.logging.Logger;

public class SuscripcionService {
    private static final Logger LOGGER = Logger.getLogger(SuscripcionService.class.getName());
    private final UsuarioDAO usuarioDAO;
    private final SuscripcionDAO suscripcionDAO;

    public SuscripcionService(UsuarioDAO usuarioDAO, SuscripcionDAO suscripcionDAO) {
        this.usuarioDAO = usuarioDAO;
        this.suscripcionDAO = suscripcionDAO;
        // No necesitamos crear un NotificacionService aquí para esta implementación
    }

    /**
     * Suscribe un comensal a un restaurante
     *
     * @param idComensal    ID del comensal
     * @param idRestaurante ID del restaurante
     * @throws ServiceException si hay problemas al procesar la suscripción
     */
    public void suscribir(Long idComensal, Long idRestaurante) throws ServiceException {
        try {
            // Verificar si ya existe la suscripción
            if (estaSuscrito(idComensal, idRestaurante)) {
                throw new ServiceException("Ya estás suscrito a este restaurante");
            }

            // Obtener entidades
            Restaurante restaurante = (Restaurante) usuarioDAO.findById(idRestaurante);
            Comensal comensal = usuarioDAO.obtenerComensalPorId(idComensal);

            if (restaurante == null) {
                throw new ServiceException("El restaurante no existe");
            }

            if (comensal == null) {
                throw new ServiceException("El comensal no existe");
            }

            // Crear la suscripción
            Suscripcion suscripcion = new Suscripcion();
            suscripcion.setComensal(comensal);
            suscripcion.setRestaurante(restaurante);

            // Guardar en base de datos
            boolean resultado = suscripcionDAO.crear(suscripcion);
            if (!resultado) {
                throw new ServiceException("No se pudo completar la suscripción");
            }

            // Crear notificación para el comensal
            String mensaje = "Te has suscrito al restaurante " + restaurante.getNombre() + ".";
            comensal.agregarNotificacion(mensaje);
            usuarioDAO.save(comensal);

            LOGGER.log(Level.INFO, "Suscripción creada: Comensal {0} - Restaurante {1}",
                    new Object[] { comensal.getNombreUsuario(), restaurante.getNombre() });
        } catch (ServiceException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error al procesar la suscripción", e);
            throw new ServiceException("Error al procesar la suscripción: " + e.getMessage());
        }
    }

    public void desuscribir(Long idComensal, Long idRestaurante) throws ServiceException {
        try {
            // Verificar si existe la suscripción
            if (!estaSuscrito(idComensal, idRestaurante)) {
                throw new ServiceException("No estás suscrito a este restaurante");
            }

            // Obtener entidades para notificación
            Restaurante restaurante = (Restaurante) usuarioDAO.findById(idRestaurante);
            Comensal comensal = usuarioDAO.obtenerComensalPorId(idComensal);

            // Eliminar la suscripción
            boolean resultado = suscripcionDAO.eliminarSuscripcion(idComensal, idRestaurante);
            if (!resultado) {
                throw new ServiceException("No se pudo cancelar la suscripción");
            }

            System.out.println("Desuscripción exitosa: " + resultado);

            // Crear notificación para el comensal
            // String mensaje = "Has cancelado tu suscripción al restaurante " +
            // restaurante.getNombre() + ".";
            // comensal.agregarNotificacion(mensaje);
            usuarioDAO.save(comensal);

            LOGGER.log(Level.INFO, "Suscripción eliminada: Comensal {0} - Restaurante {1}",
                    new Object[] { comensal.getNombreUsuario(), restaurante.getNombre() });
        } catch (ServiceException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error al procesar la desuscripción", e);
            throw new ServiceException("Error al procesar la desuscripción: " + e.getMessage());
        }
    }

    /**
     * Verifica si un comensal está suscrito a un restaurante
     *
     * @param idComensal    ID del comensal
     * @param idRestaurante ID del restaurante
     * @return true si está suscrito, false en caso contrario
     */
    public boolean estaSuscrito(Long idComensal, Long idRestaurante) {
        return suscripcionDAO.existeSuscripcion(idComensal, idRestaurante);
    }
}

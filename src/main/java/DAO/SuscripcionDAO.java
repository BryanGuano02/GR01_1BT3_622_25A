package DAO;

import entidades.Suscripcion;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.util.logging.Level;
import java.util.logging.Logger;

public class SuscripcionDAO {

    private static final Logger LOGGER = Logger.getLogger(SuscripcionDAO.class.getName());
    private final EntityManagerFactory emf;

    public SuscripcionDAO(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public SuscripcionDAO() {
        this.emf = Persistence.createEntityManagerFactory("UFood_PU");
    }

    public boolean existeSuscripcion(Long idComensal, Long idRestaurante) {
        EntityManager em = null;
        try {
            em = emf.createEntityManager();
            Long count = em.createQuery(
                "SELECT COUNT(s) FROM Suscripcion s WHERE s.comensal.id = :idComensal AND s.restaurante.id = :idRestaurante AND s.estado = :estado",
                Long.class)
                .setParameter("idComensal", idComensal)
                .setParameter("idRestaurante", idRestaurante)
                .setParameter("estado", "ACTIVA")
                .getSingleResult();
            return count > 0;
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error al verificar suscripción", e);
            return false;
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }

    public boolean crear(Suscripcion nuevaSuscripcion) {
        EntityManager em = null;
        try {
            em = emf.createEntityManager();
            em.getTransaction().begin();
            em.persist(nuevaSuscripcion);
            em.getTransaction().commit();
            return true;
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error al crear suscripción", e);
            if (em != null && em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            return false;
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }

    public boolean eliminarSuscripcion(Long idComensal, Long idRestaurante) {
        EntityManager em = null;
        try {
            em = emf.createEntityManager();
            em.getTransaction().begin();

            // Buscar la suscripción por idComensal e idRestaurante
            Suscripcion suscripcion = em.createQuery(
                "SELECT s FROM Suscripcion s WHERE s.comensal.id = :idComensal AND s.restaurante.id = :idRestaurante",
                Suscripcion.class)
                .setParameter("idComensal", idComensal)
                .setParameter("idRestaurante", idRestaurante)
                .getSingleResult();

            // Cambiar el estado a "INACTIVA" en lugar de eliminar
            suscripcion.setEstado("INACTIVA");

            // Guardar los cambios
            em.merge(suscripcion);
            em.getTransaction().commit();

            return true;
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error al cambiar estado de suscripción a INACTIVA", e);
            if (em != null && em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            return false;
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }
}

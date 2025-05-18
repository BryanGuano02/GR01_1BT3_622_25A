package DAO;

import entidades.VotacionHistoria;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;

import java.util.logging.Level;
import java.util.logging.Logger;

public class VotacionHistoriaDAO {

    private static final Logger LOGGER = Logger.getLogger(VotacionHistoriaDAO.class.getName());
    private final EntityManagerFactory emf;

    public VotacionHistoriaDAO(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public VotacionHistoriaDAO() {
        this.emf = Persistence.createEntityManagerFactory("UFood_PU");
    }

    public VotacionHistoria obtener(Long idRestaurante, String historia) {
        EntityManager em = null;
        try {
            em = emf.createEntityManager();
            TypedQuery<VotacionHistoria> query = em.createQuery(
                "SELECT v FROM VotacionHistoria v WHERE v.restaurante.id = :idRestaurante AND v.historia = :historia",
                VotacionHistoria.class
            );
            query.setParameter("idRestaurante", idRestaurante);
            query.setParameter("historia", historia);
            return query.getResultStream().findFirst().orElse(null);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error al obtener votación", e);
            return null;
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }

    public void cerrarVotacion(Long idRestaurante, String historia) {
        EntityManager em = null;
        try {
            em = emf.createEntityManager();
            em.getTransaction().begin();
            TypedQuery<VotacionHistoria> query = em.createQuery(
                "SELECT v FROM VotacionHistoria v WHERE v.restaurante.id = :idRestaurante AND v.historia = :historia",
                VotacionHistoria.class
            );
            query.setParameter("idRestaurante", idRestaurante);
            query.setParameter("historia", historia);
            VotacionHistoria votacion = query.getResultStream().findFirst().orElse(null);
            if (votacion != null) {
                votacion.setCerrada(true);
                em.merge(votacion);
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error al cerrar votación", e);
            if (em != null && em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }

    public boolean estaCerrada(Long idRestaurante, String historia) {
        VotacionHistoria votacion = obtener(idRestaurante, historia);
        return votacion != null && votacion.isCerrada();
    }
}
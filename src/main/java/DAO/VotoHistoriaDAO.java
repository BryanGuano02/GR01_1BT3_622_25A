package DAO;

import entidades.VotoHistoria;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class VotoHistoriaDAO {
    private static final Logger LOGGER = Logger.getLogger(VotoHistoriaDAO.class.getName());
    private final EntityManagerFactory emf;

    public VotoHistoriaDAO(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public VotoHistoriaDAO() {
        this.emf = Persistence.createEntityManagerFactory("UFood_PU");
    }

    public boolean crear(VotoHistoria voto) {
        EntityManager em = null;
        try {
            em = emf.createEntityManager();
            em.getTransaction().begin();
            em.persist(voto);
            em.getTransaction().commit();
            return true;
        } catch (Exception e) {
            if (em != null && em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            LOGGER.log(Level.SEVERE, "Error al crear voto", e);
            return false;
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }

    public boolean existeVoto(Long idComensal, Long idRestaurante, String historia) {
        EntityManager em = null;
        try {
            em = emf.createEntityManager();
            TypedQuery<Long> query = em.createQuery(
                "SELECT COUNT(v) FROM VotoHistoria v WHERE v.comensal.id = :idComensal AND v.restaurante.id = :idRestaurante AND v.historia = :historia",
                Long.class
            );
            query.setParameter("idComensal", idComensal);
            query.setParameter("idRestaurante", idRestaurante);
            query.setParameter("historia", historia);
            return query.getSingleResult() > 0;
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }

    public long contarLikes(Long idRestaurante, String historia) {
        EntityManager em = null;
        try {
            em = emf.createEntityManager();
            TypedQuery<Long> query = em.createQuery(
                "SELECT COUNT(v) FROM VotoHistoria v WHERE v.restaurante.id = :idRestaurante AND v.historia = :historia AND v.like = true",
                Long.class
            );
            query.setParameter("idRestaurante", idRestaurante);
            query.setParameter("historia", historia);
            return query.getSingleResult();
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }

    public long contarDislikes(Long idRestaurante, String historia) {
        EntityManager em = null;
        try {
            em = emf.createEntityManager();
            TypedQuery<Long> query = em.createQuery(
                "SELECT COUNT(v) FROM VotoHistoria v WHERE v.restaurante.id = :idRestaurante AND v.historia = :historia AND v.like = false",
                Long.class
            );
            query.setParameter("idRestaurante", idRestaurante);
            query.setParameter("historia", historia);
            return query.getSingleResult();
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }
}

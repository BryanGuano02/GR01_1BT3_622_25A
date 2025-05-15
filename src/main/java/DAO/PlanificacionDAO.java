package DAO;

import entidades.Planificacion;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PlanificacionDAO {
    private static final Logger LOGGER = Logger.getLogger(CalificacionDAO.class.getName());
    private final EntityManagerFactory emf;

    public PlanificacionDAO(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public PlanificacionDAO() {
        this.emf = Persistence.createEntityManagerFactory("UFood_PU");
    }

    public boolean crear(Planificacion nuevaPlanificacion) {
        EntityManager em = null;
        try {
            em = emf.createEntityManager();
            em.getTransaction().begin();
            em.persist(nuevaPlanificacion);
            em.getTransaction().commit();
            return true;
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error al crear planificación", e);
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

    public Planificacion obtenerPlanificacionPorId(Long planificacionId) {
        EntityManager em = emf.createEntityManager();
        try {
            em = emf.createEntityManager();
            return em.find(Planificacion.class, planificacionId);
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }

    public List<Planificacion> obtenerPlanificacionesPorId(Long idComensalPlanificador) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery("SELECT p FROM Planificacion p WHERE p.comensalPlanificador.id = :idComensalPlanificador", Planificacion.class)
                    .setParameter("idComensalPlanificador", idComensalPlanificador)
                    .getResultList();
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }
}

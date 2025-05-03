package DAO;

import entidades.Calificacion;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;

import java.util.ArrayList;
import java.util.List;


public class CalificacionDAO {
    private final EntityManagerFactory emf;

    public CalificacionDAO() {
        emf = Persistence.createEntityManagerFactory("UFood_PU");
    }

    public Calificacion obtenerPorId(Long id) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.find(Calificacion.class, id);
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }

    public void actualizar(Calificacion calificacion) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();

            em.merge(calificacion);
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw e;
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }

    public List<Calificacion> obtenerTodosLosCalificaciones(Long idRestaurante) {
        EntityManager em = emf.createEntityManager();
        List<Calificacion> calificaciones = new ArrayList<>();

        try {
            TypedQuery<Calificacion> query = em.createQuery(
                    "SELECT c FROM Calificacion c WHERE c.restaurante.id = :idRestaurante",
                    Calificacion.class);

            query.setParameter("idRestaurante", idRestaurante);

            calificaciones = query.getResultList();

        } finally {
            // Cerrar el EntityManager
            if (em != null && em.isOpen()) {
                em.close();
            }
        }

        return calificaciones;
    }

    public List<Calificacion> obtenerTodosLosCalificaciones() {
        EntityManager em = emf.createEntityManager();
        List<Calificacion> calificaciones = new ArrayList<>();

        try {
            TypedQuery<Calificacion> query = em.createQuery("SELECT r FROM Calificacion r", Calificacion.class);

            calificaciones = query.getResultList();

        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }

        return calificaciones;
    }

    public void crear(Calificacion nuevaCalificacion) {
        EntityManager em = emf.createEntityManager();

        try {
            em.getTransaction().begin();
            em.persist(nuevaCalificacion);
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw e;
        } finally {
            em.close();
        }
    }

    public void cerrar() {
        if (emf != null && emf.isOpen()) {
            emf.close();
        }
    }

}

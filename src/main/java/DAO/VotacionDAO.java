package DAO;

import entidades.Calificacion;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;

import java.util.List;

public class VotacionDAO {
    private final EntityManagerFactory emf;

    public VotacionDAO(EntityManagerFactory emf) {
        this.emf = emf;
    }

    /**
     * Guardar o actualizar una calificación.
     */
    public void guardarCalificacion(Calificacion calificacion) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            if (calificacion.getId() == null) {
                em.persist(calificacion);
            } else {
                em.merge(calificacion);
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new RuntimeException("Error al guardar la calificación", e);
        } finally {
            em.close();
        }
    }

    /**
     * Obtener todas las calificaciones de un comensal.
     */
    public List<Calificacion> obtenerCalificacionesPorComensal(Long comensalId) {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<Calificacion> query = em.createQuery(
                    "SELECT c FROM Calificacion c WHERE c.comensal.id = :comensalId", Calificacion.class);
            query.setParameter("comensalId", comensalId);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    /**
     * Obtener todas las calificaciones de un restaurante.
     */
    public List<Calificacion> obtenerCalificacionesPorRestaurante(Long restauranteId) {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<Calificacion> query = em.createQuery(
                    "SELECT c FROM Calificacion c WHERE c.restaurante.id = :restauranteId", Calificacion.class);
            query.setParameter("restauranteId", restauranteId);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    /**
     * Calcular el promedio de calificaciones de un restaurante.
     */
    public Double calcularPromedioCalificaciones(Long restauranteId) {
        EntityManager em = emf.createEntityManager();
        try {
            Double promedio = em.createQuery(
                            "SELECT AVG(c.puntaje) FROM Calificacion c WHERE c.restaurante.id = :restauranteId",
                            Double.class)
                    .setParameter("restauranteId", restauranteId)
                    .getSingleResult();
            return promedio != null ? promedio : 0.0;
        } catch (NoResultException e) {
            return 0.0;
        } finally {
            em.close();
        }
    }

    /**
     * Cerrar el EntityManagerFactory.
     */
    public void close() {
        if (emf != null && emf.isOpen()) {
            emf.close();
        }
    }
}


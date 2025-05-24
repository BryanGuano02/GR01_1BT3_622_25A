package DAO;

import entidades.DueñoRestaurante;
import jakarta.persistence.*;

public class DueñoRestauranteDAO {
    private final EntityManagerFactory emf;

    public DueñoRestauranteDAO(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public DueñoRestaurante findByNombreUsuario(String nombreUsuario) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery(
                            "SELECT d FROM DueñoRestaurante d LEFT JOIN FETCH d.restaurante WHERE d.nombreUsuario = :nombreUsuario",
                            DueñoRestaurante.class)
                    .setParameter("nombreUsuario", nombreUsuario)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        } finally {
            em.close();
        }
    }

    public void save(DueñoRestaurante dueño) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            if (dueño.getId() == null) {
                em.persist(dueño);
                if (dueño.getRestaurante() != null) {
                    em.persist(dueño.getRestaurante());
                }
            } else {
                em.merge(dueño);
                if (dueño.getRestaurante() != null) {
                    em.merge(dueño.getRestaurante());
                }
            }
            tx.commit();
        } catch (Exception e) {
            if (tx != null && tx.isActive()) tx.rollback();
            throw new RuntimeException("Error al guardar dueño", e);
        } finally {
            em.close();
        }
    }
}
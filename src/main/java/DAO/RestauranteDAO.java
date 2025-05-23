package DAO;

import entidades.Restaurante;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;

public class RestauranteDAO {
    private final EntityManager em;

    public RestauranteDAO(EntityManager em) {
        this.em = em;
    }

    public Restaurante obtenerRestauranteConDetalles(Long id) {
        try {
            return em.createQuery(
                            "SELECT DISTINCT r FROM Restaurante r " +
                                    "LEFT JOIN FETCH r.menuDelDia " +
                                    "WHERE r.id = :id", Restaurante.class)
                    .setParameter("id", id)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
}
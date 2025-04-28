package modelos;

import entidades.Restaurante;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

public class RestauranteDAO {
    private EntityManagerFactory emf;

    public RestauranteDAO() {
        // Obtener la EntityManagerFactory (normalmente se hace una vez en la aplicación)
        emf = Persistence.createEntityManagerFactory("UFood_PU");
    }

    public List<Restaurante> obtenerTodosLosRestaurantes() {
        EntityManager em = emf.createEntityManager();
        List<Restaurante> restaurantes = new ArrayList<>();

        try {
            // JOIN FETCH para cargar historias junto con el restaurante
            TypedQuery<Restaurante> query = em.createQuery(
                    "SELECT DISTINCT r FROM Restaurante r " +
                            "LEFT JOIN FETCH r.historias h " +
                            "ORDER BY r.id", Restaurante.class);
            restaurantes = query.getResultList();
        } finally {
            em.close();
        }

        return restaurantes;
    }

    public Restaurante buscarPorId(Long id) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.find(Restaurante.class, id);
        } finally {
            em.close();
        }
    }

    public void actualizar(Restaurante restaurante) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(restaurante);
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

    // Otros métodos del DAO...

    public void cerrar() {
        if (emf != null && emf.isOpen()) {
            emf.close();
        }
    }
}

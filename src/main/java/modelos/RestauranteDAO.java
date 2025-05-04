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
        try {
            TypedQuery<Restaurante> query = em.createQuery(
                    "SELECT DISTINCT r FROM Restaurante r LEFT JOIN FETCH r.historias",
                    Restaurante.class);
            return query.getResultList();
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }

    public void guardarRestaurante(Restaurante restaurante) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(restaurante);
            em.getTransaction().commit();
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }




    // Otros métodos del DAO...

    public void cerrar() {
        if (emf != null && emf.isOpen()) {
            emf.close();
        }
    }
}

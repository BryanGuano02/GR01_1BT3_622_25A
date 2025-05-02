package DAO;

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
            // Crear la consulta JPQL para obtener todos los restaurantes
            TypedQuery<Restaurante> query = em.createQuery("SELECT r FROM Restaurante r", Restaurante.class);

            // Ejecutar la consulta y obtener los resultados
            restaurantes = query.getResultList();

        } finally {
            // Cerrar el EntityManager
            if (em != null && em.isOpen()) {
                em.close();
            }
        }

        return restaurantes;
    }

    // Otros métodos del DAO...

    public void cerrar() {
        if (emf != null && emf.isOpen()) {
            emf.close();
        }
    }
}

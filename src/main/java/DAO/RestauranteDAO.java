package DAO;

import entidades.Restaurante;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

public class RestauranteDAO {
    private final EntityManagerFactory emf;

    public RestauranteDAO() {
        // Obtener la EntityManagerFactory (normalmente se hace una vez en la aplicación)
        emf = Persistence.createEntityManagerFactory("UFood_PU");
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

    public Restaurante obtenerRestaurantePorId(Long idRestaurante) {
        EntityManager em = emf.createEntityManager();
        try {
            Restaurante restaurante = em.find(Restaurante.class, idRestaurante);
            return restaurante;
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
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

    public void actualizar(Restaurante restaurante) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = null;

        try {
            transaction = em.getTransaction();
            transaction.begin();

            // Busca el restaurante existente o lanza excepción si no existe
            Restaurante restauranteExistente = em.find(Restaurante.class, restaurante.getId());

            if (restauranteExistente == null) {
                throw new IllegalArgumentException("No se encontró el restaurante con ID: " + restaurante.getId());
            }

            // Actualiza todos los campos
            restauranteExistente.setNombre(restaurante.getNombre());
            restauranteExistente.setDescripcion(restaurante.getDescripcion());
            restauranteExistente.setTipoComida(restaurante.getTipoComida());
            restauranteExistente.setHoraApertura(restaurante.getHoraApertura());
            restauranteExistente.setHoraCierre(restaurante.getHoraCierre());
            restauranteExistente.setPuntajePromedio(restaurante.getPuntajePromedio());

            // El merge no es estrictamente necesario aquí porque estamos trabajando con la entidad administrada
            em.merge(restauranteExistente);

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            throw new RuntimeException("Error al actualizar el restaurante", e);
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

package modelos;

import entidades.Calificacion;
import entidades.Restaurante;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;

import java.util.ArrayList;
import java.util.List;


public class CalificacionDAO {
    private EntityManagerFactory emf;

    public CalificacionDAO() {
        // Obtener la EntityManagerFactory (normalmente se hace una vez en la aplicación)
        emf = Persistence.createEntityManagerFactory("UFood_PU");
    }

    public List<Calificacion> obtenerTodosLosCalificaciones() {
        EntityManager em = emf.createEntityManager();
        List<Calificacion> calificaciones = new ArrayList<>();

        try {
            // Crear la consulta JPQL para obtener todos los restaurantes
            TypedQuery<Calificacion> query = em.createQuery("SELECT r FROM Calificacion r", Calificacion.class);

            // Ejecutar la consulta y obtener los resultados
            calificaciones = query.getResultList();

        } finally {
            // Cerrar el EntityManager
            if (em != null && em.isOpen()) {
                em.close();
            }
        }

        return calificaciones;
    }

    // Otros métodos del DAO...

    public void cerrar() {
        if (emf != null && emf.isOpen()) {
            emf.close();
        }
    }
}

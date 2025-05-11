package servicios;

import DAO.CalificacionDAO;
import DAO.PlanificacionDAO;
import DAO.UsuarioDAOImpl;
import entidades.Comensal;
import entidades.Planificacion;
import jakarta.persistence.*;

import java.util.List;

public class PlanificacionService {
    private final PlanificacionDAO planificacionDAO;
    private final CalificacionDAO calificacionDAO;
    private final UsuarioDAOImpl usuarioDAO;

    public PlanificacionService() {
        this.planificacionDAO = new PlanificacionDAO();
        this.calificacionDAO = new CalificacionDAO();

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("UFood_PU");
        this.usuarioDAO = new UsuarioDAOImpl(emf);
    }

    public Planificacion crearPlanificacion(String nombre, String hora) {
        validarParametrosCreacion(nombre, hora);
        Planificacion planificacion = new Planificacion(nombre, hora);
        planificacionDAO.crear(planificacion);
        return planificacion;
    }

    private void validarParametrosCreacion(String nombre, String hora) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre es requerido");
        }
        if (hora == null || hora.trim().isEmpty()) {
            throw new IllegalArgumentException("La hora es requerida");
        }
    }


    public Boolean agregarComensales(Long planificacionId, List<Long> comensalIds) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("UFood_PU");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();

            Planificacion planificacion = findPlanificacion(em, planificacionId);
            agregarComensalesALaPlanificacion(em, planificacion, comensalIds);

            tx.commit();
            return true;
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            e.printStackTrace();
            return false;
        } finally {
            em.close();
            emf.close();
        }
    }

    private Planificacion findPlanificacion(EntityManager em, Long planificacionId) {
        Planificacion planificacion = em.find(Planificacion.class, planificacionId);
        if (planificacion == null) {
            throw new EntityNotFoundException("Planificación no encontrada con ID: " + planificacionId);
        }
        return planificacion;
    }

    private void agregarComensalesALaPlanificacion(EntityManager em, Planificacion planificacion, List<Long> comensalIds) {
        for (Long comensalId : comensalIds) {
            Comensal comensal = em.find(Comensal.class, comensalId);
            if (comensal != null) {
                planificacion.addComensal(comensal);
            }
        }
    }
}


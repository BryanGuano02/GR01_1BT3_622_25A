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
        Planificacion planificacion = new Planificacion(nombre, hora);
        planificacionDAO.crear(planificacion);
        return planificacion;
    }

    public Boolean agregarComensales(Long planificacionId, List<Long> comensalIds) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("UFood_PU");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();

            Planificacion planificacion = em.find(Planificacion.class, planificacionId);
            for (Long comensalId : comensalIds) {
                Comensal comensal = em.find(Comensal.class, comensalId);
                planificacion.addComensal(comensal);
            }

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


}


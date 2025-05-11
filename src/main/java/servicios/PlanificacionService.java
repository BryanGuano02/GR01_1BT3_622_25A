package servicios;

import DAO.CalificacionDAO;
import DAO.PlanificacionDAO;
import DAO.UsuarioDAOImpl;
import entidades.Calificacion;
import entidades.Comensal;
import entidades.Planificacion;
import entidades.Restaurante;
import jakarta.persistence.*;

import java.util.List;

public class PlanificacionService {
    private final PlanificacionDAO planificacionDAO;
    private final CalificacionDAO calificacionDAO;
    private final EntityManagerFactory emf;
    private final UsuarioDAOImpl usuarioDAO;

    public PlanificacionService() {
        this.planificacionDAO = new PlanificacionDAO();
        this.calificacionDAO = new CalificacionDAO();
        this.emf = Persistence.createEntityManagerFactory("UFood_PU");
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

public boolean registrarVoto(Long planificacionId, Long restauranteId, Long comensalId, double puntaje) {
    EntityManager em = emf.createEntityManager();
    try {
        em.getTransaction().begin();
        Planificacion plan = em.find(Planificacion.class, planificacionId);
        Comensal comensal = em.find(Comensal.class, comensalId);
        Restaurante restaurante = em.find(Restaurante.class, restauranteId);

        if (plan == null || comensal == null || restaurante == null || 
            !plan.getComensales().contains(comensal)) {
            return false;
        }

        Calificacion calificacion = new Calificacion();
        calificacion.setComensal(comensal);
        calificacion.setRestaurante(restaurante);
        calificacion.setPuntaje(puntaje);

        em.persist(calificacion);
        em.getTransaction().commit();
        return true;

    } catch (Exception e) {
        if (em.getTransaction().isActive()) {
            em.getTransaction().rollback();
        }
        return false;
    } finally {
        em.close();
    }
}

public boolean confirmarPlanificacion(Long planificacionId) {
    EntityManager em = emf.createEntityManager();
    try {
        em.getTransaction().begin();
        Planificacion plan = em.find(Planificacion.class, planificacionId);
        if (plan == null) {
            return false;
        }

        plan.setEstado("CONFIRMADA");
        em.merge(plan);
        em.getTransaction().commit();
        return true;

    } catch (Exception e) {
        if (em.getTransaction().isActive()) {
            em.getTransaction().rollback();
        }
        return false;
    } finally {
        em.close();
    }
}

public boolean cancelarPlanificacion(Long planificacionId) {
    EntityManager em = emf.createEntityManager();
    try {
        em.getTransaction().begin();
        Planificacion plan = em.find(Planificacion.class, planificacionId);
        if (plan == null || "CONFIRMADA".equalsIgnoreCase(plan.getEstado())) {
            return false;
        }

        plan.setEstado("CANCELADA");
        em.merge(plan);
        em.getTransaction().commit();
        return true;

    } catch (Exception e) {
        if (em.getTransaction().isActive()) {
            em.getTransaction().rollback();
        }
        return false;
    } finally {
        em.close();
    }
}
        
public Restaurante resolverEmpateRestaurantes(List<Restaurante> restaurantesEmpatados) {
    if (restaurantesEmpatados == null || restaurantesEmpatados.isEmpty()) {
        return null;
    }
    // Seleccionar un restaurante al azar de la lista de empatados
    int indiceAleatorio = (int) (Math.random() * restaurantesEmpatados.size());
    return restaurantesEmpatados.get(indiceAleatorio);
}

public boolean cancelarPlanificacionSinVotos(Long planificacionId) {
    EntityManager em = emf.createEntityManager();
    try {
        em.getTransaction().begin();
        Planificacion plan = em.find(Planificacion.class, planificacionId);
        
        if (plan == null || "CONFIRMADA".equalsIgnoreCase(plan.getEstado())) {
            return false;
        }

        // Consulta para verificar si hay votos para esta planificación
        String queryStr = "SELECT COUNT(c) FROM Calificacion c " +
                         "WHERE c.comensal IN (SELECT com FROM Planificacion p JOIN p.comensales com " +
                         "WHERE p.id = :planId)";
        
        Long cantidadVotos = em.createQuery(queryStr, Long.class)
                              .setParameter("planId", planificacionId)
                              .getSingleResult();

        if (cantidadVotos == 0) {
            plan.setEstado("CANCELADA");
            em.merge(plan);
            em.getTransaction().commit();
            return true;
        }

        return false;

    } catch (Exception e) {
        if (em.getTransaction().isActive()) {
            em.getTransaction().rollback();
        }
        return false;
    } finally {
        em.close();
    }
}
    
}
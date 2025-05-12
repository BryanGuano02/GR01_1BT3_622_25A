package servicios;

import DAO.CalificacionDAO;
import DAO.PlanificacionDAO;
import DAO.UsuarioDAOImpl;
import entidades.Comensal;
import entidades.Planificacion;
import entidades.Restaurante;
import jakarta.persistence.*;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

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

    public Boolean recomendarRestaurante(Restaurante restaurante){
        final Double PUNTAJE_MINIMO = 3.5;
        final Double DISTANCIA_MAXIMA = 5.0;
        final int TIEMPO_MAXIMO_ESPERA = 30;

        if (restaurante == null) {
            throw new IllegalArgumentException("El restaurante no puede ser nulo");
        }
        if (restaurante.getPuntajePromedio() == null || restaurante.getDistanciaUniversidad() == null || restaurante.getTiempoEspera() == 0) {
            throw new IllegalArgumentException("Los atributos del restaurante no pueden ser nulos");
        }

        return restaurante.getPuntajePromedio() >= PUNTAJE_MINIMO
                && restaurante.getDistanciaUniversidad() <= DISTANCIA_MAXIMA
                && restaurante.getTiempoEspera() <= TIEMPO_MAXIMO_ESPERA;
    }

    public int calcularMinutosRestantesParaVotacion(LocalDateTime ahora, LocalDateTime horaLimite ) {


        return (int) Duration.between(ahora, horaLimite).toMinutes();
    }

    public Restaurante obtenerRestauranteMasVotado(Map<Restaurante, Integer> votos) {

        return votos.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse(null);

    }

    public void confirmarRestauranteDelGrupo(Planificacion planificacion, String restauranteConfirmado) {
        List<Comensal> comensales = planificacion.getComensales();
        for (Comensal comensal : comensales) {
            notificar(comensal, "Se ha confirmado: " + restauranteConfirmado);
        }
    }

    public void notificar(Comensal comensal, String restauranteConfirmado) {

    }
}


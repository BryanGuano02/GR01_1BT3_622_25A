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
import java.util.Random;
import java.util.stream.Collectors;

public class PlanificacionService {
    private final PlanificacionDAO planificacionDAO;
    private final UsuarioDAOImpl usuarioDAO;
    private EntityManagerFactory emf;

    public PlanificacionService(PlanificacionDAO planificacionDAO) {
        if (planificacionDAO == null) {
            this.emf = null;
        } else {
            this.emf = Persistence.createEntityManagerFactory("UFood_PU");
        }
        this.planificacionDAO = planificacionDAO;

        this.usuarioDAO = new UsuarioDAOImpl(emf);
    }

    public Planificacion crearPlanificacion(String nombre, String hora, Comensal comensal) {
        validarParametrosCreacion(nombre, hora);
        Planificacion planificacion = new Planificacion(nombre, hora);
        if (comensal == null) {
            throw new IllegalArgumentException("Comensal no encontrado");
        }
        planificacion.setComensalPlanificador(comensal);
        return planificacion;
    }

    public void guardarPlanificacion(Planificacion planificacion) {
        planificacionDAO.crear(planificacion);
    }

    private void validarParametrosCreacion(String nombre, String hora) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre es requerido");
        }
        if (hora == null || hora.trim().isEmpty()) {
            throw new IllegalArgumentException("La hora es requerida");
        }
    }

    public Boolean agregarComensales(Planificacion planificacion, List<Comensal> comensales) {
        for (Comensal comensal : comensales) {
            if (comensal != null) {
                planificacion.addComensal(comensal);
            }
        }
        return true;
    }

    private Planificacion findPlanificacion(EntityManager em, Long planificacionId) {
        Planificacion planificacion = em.find(Planificacion.class, planificacionId);
        if (planificacion == null) {
            throw new EntityNotFoundException("Planificación no encontrada con ID: " + planificacionId);
        }
        return planificacion;
    }

    public Boolean recomendarRestaurante(Restaurante restaurante) {
        final Double PUNTAJE_MINIMO = 3.5;
        final Double DISTANCIA_MAXIMA = 5.0;
        final int TIEMPO_MAXIMO_ESPERA = 30;

        if (restaurante == null) {
            throw new IllegalArgumentException("El restaurante no puede ser nulo");
        }
        if (restaurante.getPuntajePromedio() == null || restaurante.getDistanciaUniversidad() == null
                || restaurante.getTiempoEspera() == 0) {
            throw new IllegalArgumentException("Los atributos del restaurante no pueden ser nulos");
        }

        return restaurante.getPuntajePromedio() >= PUNTAJE_MINIMO
                && restaurante.getDistanciaUniversidad() <= DISTANCIA_MAXIMA
                && restaurante.getTiempoEspera() <= TIEMPO_MAXIMO_ESPERA;
    }

    public int calcularMinutosRestantesParaVotacion(LocalDateTime ahora, LocalDateTime horaLimite) {

        return (int) Duration.between(ahora, horaLimite).toMinutes();
    }

    public Restaurante obtenerRestauranteMasVotado(Map<Restaurante, Integer> votos) {

        return votos.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse(null);

    }

    public void cancelarPlanificacion(Long planificacionId) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("UFood_PU");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            Planificacion planificacion = findPlanificacion(em, planificacionId);
            List<Comensal> comensales = planificacion.getComensales();

            for (Comensal comensal : comensales) {
                notificar(comensal, "La planificación ha sido cancelada");
            }

            em.remove(planificacion);
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive())
                tx.rollback();
            e.printStackTrace();
        } finally {
            em.close();
            emf.close();
        }
    }

    public Restaurante resolverEmpateEnVotacion(Map<Restaurante, Integer> votos) {
        int maxVotos = votos.values().stream().max(Integer::compare).orElse(0);
        List<Restaurante> empatados = votos.entrySet().stream()
                .filter(entry -> entry.getValue() == maxVotos)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());

        if (empatados.size() > 1) {
            Random random = new Random();
            return empatados.get(random.nextInt(empatados.size()));
        }
        return empatados.isEmpty() ? null : empatados.get(0);
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

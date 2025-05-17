package servicios;

import DAO.VotacionDAO;
import entidades.Calificacion;
import entidades.Comensal;
import entidades.Restaurante;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class VotacionTest {

    private VotacionDAO votacionDAO;
    private Comensal comensal1;
    private Comensal comensal2;
    private Restaurante restaurante;

    @BeforeEach
    public void setUp() {
        votacionDAO = new VotacionDAO();
        comensal1 = new Comensal("user1", "pass1", "user1@example.com", null);
        comensal2 = new Comensal("user2", "pass2", "user2@example.com", null);
        restaurante = new Restaurante("rest1", "passRest1", "rest1@example.com", "Restaurante A", "Comida Rápida");
    }

    /**
     * CA1: Registro de un voto
     */
    @Test
    public void testRegistroDeVoto() {
        Calificacion calificacion = new Calificacion(4.5, "Muy bueno", comensal1, restaurante);
        votacionDAO.guardarCalificacion(calificacion);

        assertEquals(1, votacionDAO.obtenerCalificacionesPorComensal(comensal1).size());
        assertEquals(4.5, votacionDAO.obtenerCalificacionesPorComensal(comensal1).get(0).getPuntaje());
    }

    /**
     * CA2: Resumen de votaciones
     */
    @Test
    public void testResumenDeVotaciones() {
        votacionDAO.guardarCalificacion(new Calificacion(3.5, "Bueno", comensal1, restaurante));
        votacionDAO.guardarCalificacion(new Calificacion(4.0, "Muy bueno", comensal2, restaurante));

        double promedio = votacionDAO.obtenerCalificacionesPorRestaurante(restaurante).stream()
                .mapToDouble(Calificacion::getPuntaje)
                .average()
                .orElse(0.0);

        assertEquals(3.75, promedio);
    }

    /**
     * CA3: Voto duplicado
     */
    @Test
    public void testVotoDuplicado() {
        Calificacion calificacion1 = new Calificacion(4.0, "Excelente", comensal1, restaurante);
        votacionDAO.guardarCalificacion(calificacion1);

        // Intentar votar nuevamente
        Calificacion calificacion2 = new Calificacion(2.5, "Regular", comensal1, restaurante);
        votacionDAO.guardarCalificacion(calificacion2);

        // Solo debe existir una calificación del comensal1
        assertEquals(1, votacionDAO.obtenerCalificacionesPorComensal(comensal1).size());
        assertEquals(4.0, votacionDAO.obtenerCalificacionesPorComensal(comensal1).get(0).getPuntaje());
    }
}

package servicios;

import entidades.Comensal;
import entidades.Planificacion;
import entidades.Restaurante;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class PlanificacionServiceTest {
    @Test
    public void given_name_hour_when_create_planification_then_planification_not_null() {
        String nombre = "Cena de Fin de Año";
        String hora = "20:00";

        PlanificacionService planificacionService = new PlanificacionService();
        Planificacion planificacion = planificacionService.crearPlanificacion(nombre, hora);

        assertNotNull(planificacion);
    }

    @Test
    public void given_diner_ids_when_add_to_planification_then_ok() {
        Long planificacionId = 1L;
        List<Long> comensalIds = Arrays.asList(1L, 2L);

        PlanificacionService planificacionService = new PlanificacionService();
        Boolean exito = planificacionService.agregarComensales(planificacionId, comensalIds);

        assertTrue(exito);
    }

    @Test
    public void given_duplicate_diner_when_add_to_planification_then_throw_exception() {
        Planificacion planificacion = new Planificacion("Almuerzo UTP", "12:30");
        Comensal comensal = new Comensal();
        comensal.setId(1L);

        // Agregar por primera vez (éxito)
        planificacion.addComensal(comensal);

        // Intentar agregar duplicado (debe fallar)
        try {
            planificacion.addComensal(comensal);
            fail("Debería haber lanzado IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            assertEquals("El comensal ya está en esta planificación", e.getMessage());
        }
    }

    @Test
    public void given_restaurant_when_set_to_planification_then_association_ok() {
        Planificacion planificacion = new Planificacion("Cena de equipo", "19:00");
        Restaurante restaurante = new Restaurante();
        restaurante.setId(1L);
        restaurante.setNombre("La Cevichería");

        planificacion.setRestaurante(restaurante);
        Restaurante restauranteAsociado = planificacion.getRestaurante();

        assertNotNull("El restaurante no debería ser null", planificacion.getRestaurante());
        assertEquals("La Cevichería", planificacion.getRestaurante().getNombre());
    }


}
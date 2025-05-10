package servicios;

import entidades.Planificacion;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

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
}
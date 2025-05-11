package servicios;

import entidades.Planificacion;
import entidades.Restaurante;
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

    @Test
    public void give_two_restaurants_when_resolve_empate_then_return_restaurant_randomly () {

        Restaurante restaurante1 = new Restaurante();
        restaurante1.setNombre("Restaurante 1");
        Restaurante restaurante2 = new Restaurante();
        restaurante2.setNombre("Restaurante 2");
        List<Restaurante> restaurantesEmpatados = Arrays.asList(restaurante1, restaurante2);

        PlanificacionService planificacionService = new PlanificacionService();
        Restaurante restauranteSeleccionado = planificacionService.resolverEmpateRestaurantes(restaurantesEmpatados);

        assertNotNull(restauranteSeleccionado);
        assertTrue(restaurantesEmpatados.contains(restauranteSeleccionado));
    }

    @Test
    public void given_planificacion_when_cancel_planificacion_then_ok () {

        Long planificacionId = 1L;
        PlanificacionService planificacionService = new PlanificacionService();

        boolean resultadoCancelacion = planificacionService.cancelarPlanificacionSinVotos(planificacionId);

        assertTrue(resultadoCancelacion);
    }
}
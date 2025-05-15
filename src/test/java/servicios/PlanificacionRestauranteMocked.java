package servicios;

import entidades.Comensal;
import entidades.Planificacion;
import entidades.Restaurante;
import org.mockito.Mockito;
import java.time.LocalDateTime;
import java.util.Arrays;
import static org.mockito.Mockito.*;
import org.junit.Test;

public class PlanificacionRestauranteMocked {


    @Test
    public void given_confirmed_restaurante_when_give_notification_then_expect_confirmed() {
        // Arrange
        PlanificacionService servicioSpy = spy(PlanificacionService.class);

        Comensal comensal1 = new Comensal();
        Comensal comensal2 = new Comensal();

        Planificacion planificacion = new Planificacion("Almuerzo", "13:00");
        planificacion.addComensal(comensal1);
        planificacion.addComensal(comensal2);


        String restauranteSeleccionado = "Poli Burguer";

        // Act
        doNothing().when(servicioSpy).notificar(any(Comensal.class), anyString());

        // Assert: verifica que se haya notificado a ambos usuarios
        verify(servicioSpy).notificar(comensal1, "Se ha confirmado: Poli Burguer");
        verify(servicioSpy).notificar(comensal2, "Se ha confirmado: Poli Burguer");
        verifyNoMoreInteractions(servicioSpy);
    }

    @Test
    public void given_menu_confirmed_when_restaurant_confirms_then_diners_receive_notification() {
        PlanificacionService servicio = new PlanificacionService();
        Comensal comensal1 = new Comensal();
        comensal1.setId(1L);
        Comensal comensal2 = new Comensal();
        comensal2.setId(2L);

        Planificacion planificacion = new Planificacion("Almuerzo", "13:00");
        planificacion.addComensal(comensal1);
        planificacion.addComensal(comensal2);

        String restauranteSeleccionado = "Poli Burguer";
        // Acción: confirmar restaurante
        servicio.confirmarRestauranteDelGrupo(planificacion, restauranteSeleccionado);
        // Aquí deberías comprobar el efecto esperado cuando implementes la lógica de notificación
    }

    @Test
    public void given_follower_when_restaurant_uploads_menu_then_follower_receives_notification() {
        PlanificacionService servicio = new PlanificacionService();
        Comensal seguidor = new Comensal();
        seguidor.setId(1L);
        Restaurante restaurante = new Restaurante();
        restaurante.setId(10L);
        // Acción: el restaurante sube menú
        servicio.notificar(seguidor, "El restaurante ha publicado el menú del día");
        // Aquí deberías comprobar el efecto esperado cuando implementes la lógica de notificación
    }

}

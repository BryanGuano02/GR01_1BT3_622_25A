package servicios;

import entidades.Comensal;
import entidades.Planificacion;
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


}

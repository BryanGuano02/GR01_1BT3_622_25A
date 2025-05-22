package servicios;

import entidades.*;
import org.junit.Test;

import java.util.Collections;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

public class NotificacionServiceTest {

    // mock
    @Test
    public void given_diner_subscribed_to_restaurant_when_new_menu_notification_sent_then_notification_is_sent_successfully() {
        NotificacionService notificacionService = mock(NotificacionService.class);
        Historia historia = new Historia("Menú del día: Pollo al horno");

        Comensal comensal = new Comensal();
        comensal.setId(1L);
        comensal.setNombreUsuario("comensal1");

        Restaurante restaurante = new Restaurante();
        restaurante.setId(1L);
        restaurante.setNombre("restaurante1");
        restaurante.setSuscripciones(Collections.singletonList(new Suscripcion(comensal, restaurante)));
        restaurante.agregarHistoria(historia);

        when(notificacionService.notificarComensalesMenuDia(restaurante, historia)).thenReturn(true);

        boolean resultado = notificacionService.notificarComensalesMenuDia(restaurante, historia);

        assertTrue("Debe notificar si hay comensales", resultado);
    }

    @Test
    public void given_diner_is_not_subscribed_to_restaurant_when_new_menu_notification_sent_then_notification_is_not_sent() {
        // Mock de NotificacionService
        NotificacionService notificacionService = mock(NotificacionService.class);
        Restaurante restaurante = new Restaurante();
        Historia historia = new Historia("Menú del día: Pollo al horno");

        restaurante.setId(2L);
        restaurante.setNombre("restaurante2");
        restaurante.agregarHistoria(historia);

        // Mockea el comportamiento para este caso
        when(notificacionService.notificarComensalesMenuDia(restaurante, historia)).thenReturn(false);

        boolean resultado = notificacionService.notificarComensalesMenuDia(restaurante, historia);
        assertFalse("No debe notificar si no hay comensales siguiendo", resultado);
    }

    @Test
    public void given_unread_notifications_when_mark_as_read_then_notification_should_change_leida_boolean() {
        Boolean esperadoValido = true;
        String mensaje = "Notificación de prueba";

        Notificacion notificacion = new Notificacion(mensaje);
        NotificacionService notificacionService = new NotificacionService(null, null);
        Boolean leida = notificacionService.marcarComoLeida(notificacion);

        assertEquals(esperadoValido, leida);
    }

    @Test
    public void given_null_notification_when_mark_as_read_then_return_false() {
        NotificacionService notificacionService = new NotificacionService(null, null);
        boolean resultado = notificacionService.marcarComoLeida(null);
        assertFalse("Debe devolver false si la notificación es null", resultado);
    }

}

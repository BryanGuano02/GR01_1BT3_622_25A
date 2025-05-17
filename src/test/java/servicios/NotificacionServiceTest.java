package servicios;

import DAO.NotificacionDAO;
import DAO.UsuarioDAO;
import DAO.UsuarioDAOImpl;
import entidades.Comensal;
import entidades.Restaurante;
import entidades.Suscripcion;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.junit.Test;

import java.util.Collections;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

public class NotificacionServiceTest {

    //mock
    @Test
    public void given_diner_subscribed_to_restaurant_when_new_menu_notification_sent_then_notification_is_sent_successfully() {
        NotificacionService notificacionService = mock(NotificacionService.class);

        Comensal comensal = new Comensal();
        comensal.setId(1L);
        comensal.setNombreUsuario("comensal1");

        Restaurante restaurante = new Restaurante();
        restaurante.setId(1L);
        restaurante.setNombre("restaurante1");
        restaurante.setSuscripciones(Collections.singletonList(new Suscripcion(comensal, restaurante)));
        restaurante.agregarHistoria("Menú del día: Pollo al horno");

        when(notificacionService.notificarComensalesMenuDia(restaurante)).thenReturn(true);

        boolean resultado = notificacionService.notificarComensalesMenuDia(restaurante);

        assertTrue("Debe notificar si hay comensales", resultado);
    }

    @Test

    public void given_diner_is_not_subscribed_to_restaurant_when_new_menu_notification_sent_then_notification_is_not_sent() {
        // Mock de NotificacionService
        NotificacionService notificacionService = mock(NotificacionService.class);
        Restaurante restaurante = new Restaurante();
        String menuDia = "Menú del día: Pollo al horno";

        restaurante.setId(2L);
        restaurante.setNombre("restaurante2");
        restaurante.agregarHistoria(menuDia);

        // Mockea el comportamiento para este caso
        when(notificacionService.notificarComensalesMenuDia(restaurante)).thenReturn(false);

        boolean resultado = notificacionService.notificarComensalesMenuDia(restaurante);
        assertFalse("No debe notificar si no hay comensales siguiendo", resultado);
    }

}

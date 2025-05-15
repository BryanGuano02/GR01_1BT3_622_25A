package servicios;

import entidades.Comensal;
import entidades.Restaurante;
import entidades.Suscripcion;
import org.junit.Test;

import java.util.Collections;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class NotificacionServiceTest {

    @Test
    public void given_restaurante_with_subscribers_when_create_menu_then_notification_in_notification_tray() {
        NotificacionService notificacionService = new NotificacionService();
        String menuDia = "Menú del día: Pollo al horno";
        Comensal comensal = new Comensal();
        Restaurante restaurante = new Restaurante();

        comensal.setId(1L);
        comensal.setNombreUsuario("comensal1");

        restaurante.setId(1L);
        restaurante.setNombre("restaurante1");
        restaurante.setSuscripciones(Collections.singletonList(new Suscripcion(comensal, restaurante)));
        restaurante.agregarHistoria(menuDia);


        boolean resultado = notificacionService.notificarComensalesMenuDia(restaurante);

        assertTrue("Debe notificar si hay comensales siguiendo", resultado);
    }

    @Test
    public void testNotificarComensalesMenuDia_sinComensales() {
        NotificacionService notificacionService = new NotificacionService();
        Restaurante restaurante = new Restaurante();
        String menuDia = "Menú del día: Pollo al horno";

        restaurante.setId(2L);
        restaurante.setNombre("restaurante2");
        restaurante.agregarHistoria(menuDia);

        boolean resultado = notificacionService.notificarComensalesMenuDia(restaurante);
        assertFalse("No debe notificar si no hay comensales siguiendo", resultado);
    }

}

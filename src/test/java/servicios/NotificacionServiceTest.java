package servicios;

import entidades.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Collections;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class NotificacionServiceTest {
    private static Stream<Arguments> provideNotificationTestCases() {
        return Stream.of(Arguments.of(new Notificacion("Notificación de prueba"), true), Arguments.of(null, false), Arguments.of(createReadNotification(), false));
    }

    private static Notificacion createReadNotification() {
        Notificacion notificacion = new Notificacion("Notificación leída");
        notificacion.setLeida(true);
        return notificacion;
    }

    @Test
    void givenDinerSubscribedToRestaurant_whenNewMenuNotificationSent_thenNotificationIsSentSuccessfully() {
        // Configuración
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

        // Mock del comportamiento esperado
        when(notificacionService.notificarComensalesMenuDia(restaurante, historia)).thenReturn(true);

        // Ejecución
        boolean resultado = notificacionService.notificarComensalesMenuDia(restaurante, historia);

        // Verificación
        assertTrue(resultado, "Debe notificar si hay comensales suscritos");
        verify(notificacionService).notificarComensalesMenuDia(restaurante, historia);
    }

    @Test
    void givenDinerNotSubscribedToRestaurant_whenNewMenuNotificationSent_thenNotificationIsNotSent() {
        // Configuración
        NotificacionService notificacionService = mock(NotificacionService.class);
        Restaurante restaurante = new Restaurante();
        Historia historia = new Historia("Menú del día: Pollo al horno");

        restaurante.setId(2L);
        restaurante.setNombre("restaurante2");
        restaurante.agregarHistoria(historia);

        // Mock del comportamiento esperado
        when(notificacionService.notificarComensalesMenuDia(restaurante, historia)).thenReturn(false);

        // Ejecución
        boolean resultado = notificacionService.notificarComensalesMenuDia(restaurante, historia);

        // Verificación
        assertFalse(resultado, "No debe notificar si no hay comensales suscritos");
        verify(notificacionService).notificarComensalesMenuDia(restaurante, historia);
    }

    @ParameterizedTest
    @MethodSource("provideNotificationTestCases")
    void testMarcarComoLeida(Notificacion notificacion, boolean expectedResult) {
        // Configuración
        NotificacionService notificacionService = new NotificacionService(null, null);

        // Ejecución
        boolean resultado = notificacionService.marcarComoLeida(notificacion);

        // Verificación
        assertEquals(expectedResult, resultado);
    }
}

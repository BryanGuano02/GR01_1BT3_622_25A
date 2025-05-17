package servicios;

import entidades.Notificacion;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Collection;

import DAO.NotificacionDAO;
import DAO.UsuarioDAO;
import DAO.UsuarioDAOImpl;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

@RunWith(Parameterized.class)
public class NotificacionServiceParametrizedTest {
    @Parameterized.Parameters
    public static Collection<Object[]> datosDePrueba() {
        return Arrays.asList(new Object[][] {
                // nombrePlanificacion, hora, nombreRestaurante, esperadoValido
                { "Notificacion 1", false },
                { "Notificacion 2", false },
                { "Notificacion 3", true }
        });
    }

    private String mensaje;
    private boolean esperadoValido;

    public NotificacionServiceParametrizedTest(String mensaje, boolean esperadoValido) {
        this.mensaje = mensaje;
        this.esperadoValido = esperadoValido;
    }

    @Test
    public void given_unread_notifications_when_mark_as_read_then_notification_should_change_leida_boolean() {
        // Configuración
        Notificacion notificacion = new Notificacion(mensaje);
        NotificacionService notificacionService = new NotificacionService(null, null);
        Boolean leida = notificacionService.marcarComoLeida(notificacion);
        // Ejecución y verificación
        assertEquals(esperadoValido, leida);
    }
}

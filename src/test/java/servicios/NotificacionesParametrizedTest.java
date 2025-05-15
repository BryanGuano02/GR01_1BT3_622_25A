package servicios;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import java.util.*;
import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class NotificacionesParametrizedTest {
    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
            {Arrays.asList(false, false, true), 1}, // Solo la última es no leída
            {Arrays.asList(true, true, true), 3},   // Todas no leídas
            {Arrays.asList(false, false, false), 0} // Ninguna no leída
        });
    }
    private final List<Boolean> leidas;
    private final int esperadasNoLeidas;
    public NotificacionesParametrizedTest(List<Boolean> leidas, int esperadasNoLeidas) {
        this.leidas = leidas;
        this.esperadasNoLeidas = esperadasNoLeidas;
    }
    @Test
    public void testNotificacionesNoLeidasDestacadas() {
        int noLeidas = 0;
        for (Boolean leida : leidas) {
            if (leida) noLeidas++;
        }
        assertEquals(esperadasNoLeidas, noLeidas);
    }
}

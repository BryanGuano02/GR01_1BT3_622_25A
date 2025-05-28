package servicios;

import entidades.Calificacion;
import entidades.Comensal;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class VotacionCalificacionTest {

    @Test
    public void givenComensalCalificacion_whenVotar_thenVotoRegistradoConExito() {
        // Arrange
        Long idComensal = 1L;
        Comensal comensal = new Comensal();
        comensal.setId(idComensal);

        Calificacion calificacion = new Calificacion();
        CalificacionService calificacionService = new CalificacionService(null, null, null);

        // Act
        calificacionService.votarCalificacion(comensal, calificacion);

        // Assert
        assertAll(
            () -> assertFalse(calificacion.getVotos().isEmpty(), "Debe haber votos registrados"),
            () -> assertEquals(idComensal, calificacion.getVotos().get(0).getComensal().getId(),
                "El ID del comensal debe coincidir con el voto registrado")
        );
    }

    @Test
    public void givenSameComensalSameCalificacion_whenVotar_thenDeleteVotoRegistrado() {
        // Arrange
        Long idComensal = 1L;
        Comensal comensal = new Comensal();
        comensal.setId(idComensal);

        Calificacion calificacion = new Calificacion();
        CalificacionService calificacionService = new CalificacionService(null, null, null);

        // Act
        boolean resultadoPrimerVoto = calificacionService.votarCalificacion(comensal, calificacion);
        boolean resultadoSegundoVoto = calificacionService.votarCalificacion(comensal, calificacion);

        // Assert
        assertAll(
            () -> assertTrue(resultadoPrimerVoto, "El primer voto debería registrarse exitosamente"),
            () -> assertFalse(resultadoSegundoVoto, "El segundo voto debería eliminarse"),
            () -> assertTrue(calificacion.getVotos().isEmpty(), "No debería haber votos después de deshacer")
        );
    }
}
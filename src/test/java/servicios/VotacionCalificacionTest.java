package servicios;

import org.junit.Test;

import entidades.Calificacion;
import entidades.Comensal;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class VotacionCalificacionTest {

    @Test
    public void givenComensalCalificacion_whenVotar_thenVotoRegistradoConExito() {
        Long idComensal = 1L;
        Long idCalificacion = 1L;
        Comensal comensal = new Comensal();
        Calificacion calificacion = new Calificacion();
        comensal.setId(idComensal);
        calificacion.setId(idCalificacion);

        VotacionService votacionService = new VotacionService();
        votacionService.votarCalificacion(comensal, calificacion);

        assertTrue(calificacion.getVotos().get(0).getComensal().getId().equals(idComensal));
    }

    @Test
    public void givenSameComensalSameCalificacion_whenVotar_thenDeleteVotoRegistrado() {
        Long idComensal = 1L;
        Long idCalificacion = 1L;
        Comensal comensal = new Comensal();
        Calificacion calificacion = new Calificacion();
        comensal.setId(idComensal);
        calificacion.setId(idCalificacion);

        VotacionService votacionService = new VotacionService();
        Boolean resultadoPrimerVoto = votacionService.votarCalificacion(comensal, calificacion);
        Boolean resultadoSegundoVoto = votacionService.votarCalificacion(comensal, calificacion);

        assertTrue(resultadoPrimerVoto);
        assertFalse(resultadoSegundoVoto);
    }
}

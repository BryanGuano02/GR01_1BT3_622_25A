package servicios;


import entidades.Comensal;
import entidades.Restaurante;
import org.junit.Test;

import static org.junit.Assert.*;

public class VotacionHistoriaTest {

    private VotacionHistoriaService votacionHistoriaService;
    private Restaurante restaurante;
    private Comensal comensal1;
    private Comensal comensal2;
    private String historia;


    @Test
    public void comensalPuedeDarLike() {

        votacionHistoriaService = new VotacionHistoriaService();
        restaurante = new Restaurante();
        restaurante.setId(1L);
        restaurante.setNombre("Restaurante Prueba");
        historia = "Menú del día";
        restaurante.agregarHistoria(historia);

        comensal1 = new Comensal();
        comensal1.setId(1L);

        assertTrue(votacionHistoriaService.votar(restaurante, historia, comensal1, true));
        assertEquals(1, votacionHistoriaService.getLikes(restaurante, historia));
        assertEquals(0, votacionHistoriaService.getDislikes(restaurante, historia));
    }

    @Test
    public void comensalPuedeDarDislike() {

        votacionHistoriaService = new VotacionHistoriaService();
        restaurante = new Restaurante();
        restaurante.setId(1L);
        restaurante.setNombre("Restaurante Prueba");
        historia = "Menú del día";
        restaurante.agregarHistoria(historia);

        comensal2 = new Comensal();
        comensal2.setId(2L);

        assertTrue(votacionHistoriaService.votar(restaurante, historia, comensal2, false));
        assertEquals(0, votacionHistoriaService.getLikes(restaurante, historia));
        assertEquals(1, votacionHistoriaService.getDislikes(restaurante, historia));
    }

    @Test
    public void comensalNoPuedeVotarDosVeces() {

        votacionHistoriaService = new VotacionHistoriaService();
        restaurante = new Restaurante();
        restaurante.setId(1L);
        restaurante.setNombre("Restaurante Prueba");
        historia = "Menú del día";
        restaurante.agregarHistoria(historia);

        comensal1 = new Comensal();
        comensal1.setId(1L);


        assertTrue(votacionHistoriaService.votar(restaurante, historia, comensal1, true));
        assertFalse(votacionHistoriaService.votar(restaurante, historia, comensal1, false));
        assertEquals(1, votacionHistoriaService.getLikes(restaurante, historia));
        assertEquals(0, votacionHistoriaService.getDislikes(restaurante, historia));
    }

    @Test
    public void restaurantePuedeTerminarVotacionYVerResumen() {

        votacionHistoriaService = new VotacionHistoriaService();
        restaurante = new Restaurante();
        restaurante.setId(1L);
        restaurante.setNombre("Restaurante Prueba");
        historia = "Menú del día";
        restaurante.agregarHistoria(historia);

        comensal1 = new Comensal();
        comensal1.setId(1L);
        comensal2 = new Comensal();
        comensal2.setId(2L);

        votacionHistoriaService.votar(restaurante, historia, comensal1, true);
        votacionHistoriaService.votar(restaurante, historia, comensal2, false);

        votacionHistoriaService.terminarVotacion(restaurante, historia);

        assertTrue(votacionHistoriaService.isVotacionTerminada(restaurante, historia));

        CalificacionResumen resumen = votacionHistoriaService.obtenerResumen(restaurante, historia);
        assertEquals(1, resumen.getLikes());
        assertEquals(1, resumen.getDislikes());
    }

    @Test
    public void noSePuedeVotarDespuesDeTerminarVotacion() {

        votacionHistoriaService = new VotacionHistoriaService();
        restaurante = new Restaurante();
        restaurante.setId(1L);
        restaurante.setNombre("Restaurante Prueba");
        historia = "Menú del día";
        restaurante.agregarHistoria(historia);

        comensal1 = new Comensal();
        comensal1.setId(1L);
        comensal2 = new Comensal();
        comensal2.setId(2L);

        votacionHistoriaService.votar(restaurante, historia, comensal1, true);
        votacionHistoriaService.terminarVotacion(restaurante, historia);
        assertFalse(votacionHistoriaService.votar(restaurante, historia, comensal2, false));
    }
}
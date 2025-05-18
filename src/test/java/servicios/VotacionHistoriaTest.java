package servicios;

import entidades.Comensal;
import entidades.Restaurante;
import exceptions.ServiceException;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class VotacionHistoriaTest {

    private VotoHistoriaService votoHistoriaService;
    private VotoHistoriaDAO votoHistoriaDAO;
    private VotacionHistoriaDAO votacionHistoriaDAO;
    private Restaurante restaurante;
    private Comensal comensal1;
    private Comensal comensal2;
    private String historia;

    @Before
    public void setUp() {
        votoHistoriaDAO = mock(VotoHistoriaDAO.class);
        votacionHistoriaDAO = mock(VotacionHistoriaDAO.class);
        votoHistoriaService = new VotoHistoriaService(votacionHistoriaDAO, votoHistoriaDAO);

        restaurante = new Restaurante();
        restaurante.setId(1L);
        restaurante.setNombre("Restaurante Prueba");
        historia = "Menú del día";

        comensal1 = new Comensal();
        comensal1.setId(1L);
        comensal2 = new Comensal();
        comensal2.setId(2L);
    }

    @Test
    public void comensalPuedeDarLike() throws ServiceException {
        when(votacionHistoriaDAO.estaCerrada(1L, historia)).thenReturn(false);
        when(votoHistoriaDAO.existeVoto(1L, 1L, historia)).thenReturn(false);
        when(votoHistoriaDAO.crear(any(VotoHistoria.class))).thenReturn(true);

        votoHistoriaService.votar(1L, 1L, historia, true);

        verify(votoHistoriaDAO).crear(any(VotoHistoria.class));
    }

    @Test(expected = ServiceException.class)
    public void comensalNoPuedeVotarDosVeces() throws ServiceException {
        when(votacionHistoriaDAO.estaCerrada(1L, historia)).thenReturn(false);
        when(votoHistoriaDAO.existeVoto(1L, 1L, historia)).thenReturn(true);

        votoHistoriaService.votar(1L, 1L, historia, true);
    }

    @Test(expected = ServiceException.class)
    public void noSePuedeVotarSiVotacionCerrada() throws ServiceException {
        when(votacionHistoriaDAO.estaCerrada(1L, historia)).thenReturn(true);

        votoHistoriaService.votar(1L, 1L, historia, true);
    }

    @Test
    public void contarLikesYDislikes() {
        when(votoHistoriaDAO.contarLikes(1L, historia)).thenReturn(2L);
        when(votoHistoriaDAO.contarDislikes(1L, historia)).thenReturn(1L);

        assertEquals(2, votoHistoriaService.contarLikes(1L, historia));
        assertEquals(1, votoHistoriaService.contarDislikes(1L, historia));
    }

    @Test
    public void cerrarVotacion() throws ServiceException {
        doNothing().when(votacionHistoriaDAO).cerrarVotacion(1L, historia);

        votoHistoriaService.cerrarVotacion(1L, historia);

        verify(votacionHistoriaDAO).cerrarVotacion(1L, historia);
    }
}
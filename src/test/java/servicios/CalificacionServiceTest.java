package servicios;
import DAO.CalificacionDAO;
import DAO.RestauranteDAO;
import DAO.UsuarioDAO;
import entidades.Calificacion;
import entidades.Comensal;
import entidades.Restaurante;
import exceptions.ServiceException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import servicios.CalificacionService;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.*;

public class CalificacionServiceTest {

    private CalificacionDAO calificacionDAO;
    private UsuarioDAO usuarioDAO;
    private RestauranteDAO restauranteDAO;
    private CalificacionService calificacionService;

    @BeforeEach
    public void init() {
        calificacionDAO = mock(CalificacionDAO.class);
        usuarioDAO = mock(UsuarioDAO.class);
        restauranteDAO = mock(RestauranteDAO.class);
        calificacionService = new CalificacionService(calificacionDAO, usuarioDAO, restauranteDAO);
    }

    @Test
    public void givenValidInputs_whenCalificar_thenCalificacionRegistradaConExito() throws ServiceException {

        Long idComensal = 1L;
        Long idRestaurante = 2L;
        Comensal comensal = new Comensal();
        Restaurante restaurante = new Restaurante();
        restaurante.setId(idRestaurante);

        when(usuarioDAO.obtenerComensalPorId(idComensal)).thenReturn(comensal);
        when(restauranteDAO.obtenerRestaurantePorId(idRestaurante)).thenReturn(restaurante);
        when(calificacionDAO.obtenerCalificacionPorComensalYRestaurante(idComensal, idRestaurante)).thenReturn(null);
        when(calificacionDAO.crear(any(Calificacion.class))).thenReturn(true);
        when(calificacionDAO.calcularPromedioCalificaciones(idRestaurante)).thenReturn(4.5);

        Map<String, Object> parametros = new HashMap<>();
        parametros.put("calidadComida", 5);
        parametros.put("calidadServicio", 4);
        parametros.put("limpieza", 3);
        parametros.put("ambiente", 4);
        parametros.put("tiempoEspera", 5);
        parametros.put("relacionPrecioCalidad", 4);
        parametros.put("variedadMenu", 3);
        parametros.put("accesibilidad", 5);
        parametros.put("volveria", 4);
        parametros.put("comentario", "Muy buen lugar");
        parametros.put("idComensal", idComensal);
        parametros.put("idRestaurante", idRestaurante);


        calificacionService.calificar(parametros);


        verify(calificacionDAO).crear(any(Calificacion.class));
        verify(restauranteDAO, times(2)).save(any(Restaurante.class));

    }

    @Test
    void calcularPuntajeCalificacion_debeCalcularPromedioCorrecto() {

        Calificacion calificacion = new Calificacion();
        calificacion.setCalidadComida(4);
        calificacion.setCalidadServicio(5);
        calificacion.setLimpieza(4);
        calificacion.setAmbiente(5);
        calificacion.setTiempoEspera(3);
        calificacion.setRelacionPrecioCalidad(4);
        calificacion.setVariedadMenu(4);
        calificacion.setAccesibilidad(5);
        calificacion.setVolveria(4);

        CalificacionService servicio = new CalificacionService(null, null, null);


        double puntaje = servicio.calcularPuntajeCalificacion(calificacion);


        double esperado = (4 + 5 + 4 + 5 + 3 + 4 + 4 + 5 + 4) / 9.0;
        assertEquals(esperado, puntaje, 0.0001);
        assertEquals(esperado, calificacion.getPuntaje(), 0.0001);
    }


    CalificacionDAO CalificacionDAOFalso = new CalificacionDAO() {
        @Override
        public boolean crear(Calificacion calificacion) {
            return true; // Simula que se creó con éxito
        }

    };


    RestauranteDAO RestauranteDAOFalso = new RestauranteDAO(null) {
        @Override
        public void save(Restaurante restaurante) {

        }
    };

    @Test
    public void testCrearCalificacionExitosa() {

        Calificacion calificacion = new Calificacion();
        calificacion.setCalidadComida(4);
        calificacion.setCalidadServicio(5);
        calificacion.setLimpieza(4);
        calificacion.setAmbiente(3);
        calificacion.setTiempoEspera(4);
        calificacion.setRelacionPrecioCalidad(5);
        calificacion.setVariedadMenu(3);
        calificacion.setAccesibilidad(4);
        calificacion.setVolveria(5);
        calificacion.setRestaurante(new Restaurante());



        CalificacionService servicio = new CalificacionService(CalificacionDAOFalso, null, RestauranteDAOFalso);


        assertDoesNotThrow(() -> servicio.crearCalificacion(calificacion));
        double esperado = (4 + 5 + 4 + 3 + 4 + 5 + 3 + 4 + 5) / 9.0;
        assertEquals(esperado, calificacion.getPuntaje(), 0.0001);
    }

}


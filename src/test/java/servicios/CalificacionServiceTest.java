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
import org.mockito.Mockito;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
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
    public void givenEntradasValidas_whenCalificar_thenCalificacionRegistradaConExito() throws ServiceException {

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
    void givenCalificacion_whenCalcularPuntaje_thenPuntajeCorrecto() {

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
    public void givenCalificacion_whenCrearCalificacion_thenNotThrowAndPuntajeCorrecto() throws ServiceException {

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


        @Test
        public void givenHashMapValido_whenExtraerParametrosCalificacion_thenParametrosExtraidosCorrectamente() {
            // Arrange
            CalificacionService service = new CalificacionService(null, null, null);
            HashMap<String, Object> parametros = new HashMap<>();
            parametros.put("calidadComida", 4);
            parametros.put("calidadServicio", 5);
            parametros.put("limpieza", 4);
            parametros.put("ambiente", 3);
            parametros.put("tiempoEspera", 5);
            parametros.put("relacionPrecioCalidad", 3);
            parametros.put("variedadMenu", 4);
            parametros.put("accesibilidad", 3);
            parametros.put("volveria", 1);
            parametros.put("comentario", "Buen servicio y comida rica");
            parametros.put("idComensal", 10L);
            parametros.put("idRestaurante", 1L);

            // Act
            Calificacion calificacion = service.extraerParametrosCalificacion(parametros);

            // Assert
            assertEquals(4, calificacion.getCalidadComida());
            assertEquals(5, calificacion.getCalidadServicio());
            assertEquals(4, calificacion.getLimpieza());
            assertEquals(3, calificacion.getAmbiente());
            assertEquals(5, calificacion.getTiempoEspera());
            assertEquals(3, calificacion.getRelacionPrecioCalidad());
            assertEquals(4, calificacion.getVariedadMenu());
            assertEquals(3, calificacion.getAccesibilidad());
            assertEquals(1, calificacion.getVolveria());
            assertEquals("Buen servicio y comida rica", calificacion.getComentario());
            assertEquals(Long.valueOf(10), calificacion.getComensal().getId());
            assertEquals(Long.valueOf(1), calificacion.getRestaurante().getId());

        }

        @Test
        public void givenCalificacionValida_whenActualizarCalificacion_thenVerifica() throws ServiceException {
            // Datos de entrada
            Long comensalId = 1L;
            Long restauranteId = 2L;

            HashMap<String, Object> parametros = new HashMap<>();

            UsuarioDAO usuarioDAO = Mockito.mock(UsuarioDAO.class);
            RestauranteDAO restauranteDAO = Mockito.mock(RestauranteDAO.class);
            CalificacionDAO calificacionDAO = Mockito.mock(CalificacionDAO.class);

            parametros.put("idComensal", comensalId);
            parametros.put("idRestaurante", restauranteId);
            parametros.put("calidadComida", 4);
            parametros.put("calidadServicio", 5);
            parametros.put("limpieza", 4);
            parametros.put("ambiente", 3);
            parametros.put("tiempoEspera", 4);
            parametros.put("relacionPrecioCalidad", 5);
            parametros.put("variedadMenu", 3);
            parametros.put("accesibilidad", 4);
            parametros.put("volveria", 1);
            parametros.put("comentario", "Muy bueno");

            // Objetos simulados
            Comensal comensal = new Comensal();
            comensal.setId(comensalId);

            Restaurante restaurante = new Restaurante();
            restaurante.setId(restauranteId);

            Calificacion existente = new Calificacion();
            existente.setComensal(comensal);
            existente.setRestaurante(restaurante);

            // Mocks
            when(usuarioDAO.obtenerComensalPorId(anyLong())).thenReturn(comensal);
            when(restauranteDAO.obtenerRestaurantePorId(anyLong())).thenReturn(restaurante);
            when(calificacionDAO.obtenerCalificacionPorComensalYRestaurante(comensalId, restauranteId)).thenReturn(existente);
            when(calificacionDAO.actualizar(any(Calificacion.class))).thenReturn(true);

            // Ejecutar
            CalificacionService calificacionService = new CalificacionService(calificacionDAO, usuarioDAO, restauranteDAO);
            calificacionService.calificar(parametros);

            // Verificaciones
            verify(calificacionDAO).actualizar(argThat(c ->
                    c.getCalidadComida() == 4 &&
                            c.getComentario().equals("Muy bueno")
            ));
    }

    @Test
    public void givenCalificacionExistente_whenActualizarPuntajePromedio_thenPromedioActualizadoCorrectamente() {

        CalificacionDAO calificacionDAO = mock(CalificacionDAO.class);
        RestauranteDAO restauranteDAO = mock(RestauranteDAO.class);
        CalificacionService calificacionService = new CalificacionService(calificacionDAO, null, restauranteDAO);

        // Arrange
        Restaurante restaurante = new Restaurante();
        restaurante.setId(1L);

        when(calificacionDAO.calcularPromedioCalificaciones(1L)).thenReturn(4.5);

        // Act (usando reflexión si sigue siendo private)
        try {
            Method metodo = CalificacionService.class.getDeclaredMethod("actualizarPuntajePromedio", Restaurante.class);
            metodo.setAccessible(true); // Permite invocar private
            metodo.invoke(calificacionService, restaurante);
        } catch (Exception e) {
            fail("No se pudo invocar el método: " + e.getMessage());
        }
        // Assert
        assertEquals((Double) 4.5, restaurante.getPuntajePromedio());
        verify(restauranteDAO).save(restaurante);
    }
}




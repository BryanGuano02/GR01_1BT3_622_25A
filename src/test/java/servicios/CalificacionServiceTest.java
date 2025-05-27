package servicios;
import DAO.CalificacionDAO;
import DAO.RestauranteDAO;
import DAO.UsuarioDAO;
import entidades.Calificacion;
import entidades.Comensal;
import entidades.Planificacion;
import entidades.Restaurante;
import exceptions.ServiceException;
import org.mockito.Mockito;

import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.*;
import org.junit.Test;


public class CalificacionServiceTest {


    @Test
    public void givenValidInputs_whenCalificar_thenCalificacionRegistradaConExito() {
        // Arrange
        UsuarioDAO usuarioDAOMock = Mockito.mock(UsuarioDAO.class);
        //CalificacionService service = new CalificacionService(usuarioDAOMock);

        Long idRestaurante = 1L;
        Long idComensal = 10L;
        Restaurante restaurante = new Restaurante();
        Comensal comensal = new Comensal();
        //Calificacion calificacion = new Calificacion(4, 5, 4, 3, 5, 3, 4, 3, true, "Buen servicio y comida rica");

        //Mockito.when(usuarioDAOMock.findById(idRestaurante)).thenReturn(restaurante);
        Mockito.when(usuarioDAOMock.findById(idComensal)).thenReturn(comensal);

        // Act
        //String mensaje = service.calificarRestaurante(idRestaurante, idComensal, calificacion);

        // Assert
        Mockito.verify(usuarioDAOMock).save(Mockito.any());
        //assertEquals("Restaurante calificado con éxito", mensaje);
    }

        @Test
        public void givenValidHashMap_whenExtraerParametrosCalificacion_thenParametrosExtraidosCorrectamente() {
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
        public void givenValidCalificacion_whenUpdateCalificacion_thenVerify() throws ServiceException {
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
    public void testActualizarPuntajePromedio() {

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

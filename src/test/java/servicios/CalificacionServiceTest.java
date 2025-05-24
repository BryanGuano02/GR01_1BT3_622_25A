package servicios;
import DAO.UsuarioDAO;
import entidades.Calificacion;
import entidades.Comensal;
import entidades.Planificacion;
import entidades.Restaurante;
import org.mockito.Mockito;
import java.time.LocalDateTime;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;
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


}

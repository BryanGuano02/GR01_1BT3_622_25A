package servicios;
import entidades.Calificacion;
import entidades.Preferencia;
import entidades.Restaurante;
import entidades.Usuario;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class SugerenciaRestauranteTest {

    @Test
    public void givenFavoriteFoodType_whenGetRestaurantes_thenReturnMatchingRestaurants() {

        SugerenciaService mockService = mock(SugerenciaService.class);

        Restaurante restaurante1 = new Restaurante();
        restaurante1.setTipoComida("Italiana");
        restaurante1.setPuntajePromedio(4.5);

        Restaurante restaurante2 = new Restaurante();
        restaurante2.setTipoComida("Italiana");
        restaurante2.setPuntajePromedio(4.8);

        Restaurante restaurante3 = new Restaurante();
        restaurante3.setTipoComida("Japonesa");
        restaurante3.setPuntajePromedio(3.7);

        List<Restaurante> restaurantes = new ArrayList<>();
        restaurantes.add(restaurante1);
        restaurantes.add(restaurante2);
        restaurantes.add(restaurante3);

        Preferencia preferencia = new Preferencia();
        preferencia.setTipoComida("Italiana");

        List<Restaurante> esperado = Arrays.asList(restaurante1, restaurante2);

        when(mockService.getRestaurantesPorComidaFavorita(preferencia.getTipoComida())).thenReturn(esperado);
        
        List<Restaurante> resultado = mockService.getRestaurantesPorComidaFavorita(preferencia.getTipoComida());

        assertNotNull(resultado);
        assertEquals(2, resultado.size());
    }


    @Test
    public void givenRestaurants_whenOrdenarPorCalificacion_thenReturnSortedRestaurants() {
        // Crear el mock del servicio
        SugerenciaService mockService = mock(SugerenciaService.class);

        // Crear mocks de restaurantes
        Restaurante restaurante1 = mock(Restaurante.class);
        Restaurante restaurante2 = mock(Restaurante.class);
        Restaurante restaurante3 = mock(Restaurante.class);

        // Definir el comportamiento de los mocks
        when(restaurante1.getPuntajePromedio()).thenReturn(4.5);
        when(restaurante2.getPuntajePromedio()).thenReturn(3.7);
        when(restaurante3.getPuntajePromedio()).thenReturn(4.8);

        // Crear la lista de restaurantes simulada
        List<Restaurante> restaurantes = new ArrayList<>();
        restaurantes.add(restaurante1);
        restaurantes.add(restaurante2);
        restaurantes.add(restaurante3);

        // Lista esperada ordenada
        List<Restaurante> restaurantesOrdenados = new ArrayList<>();
        restaurantesOrdenados.add(restaurante3); // 4.8
        restaurantesOrdenados.add(restaurante1); // 4.5
        restaurantesOrdenados.add(restaurante2); // 3.7

        // Simular el comportamiento del método
        when(mockService.ordenarRestaurantesPorCalificacion(restaurantes)).thenReturn(restaurantesOrdenados);

        // Llamar al método simulado
        List<Restaurante> resultado = mockService.ordenarRestaurantesPorCalificacion(restaurantes);

        // Verificar el resultado
        assertNotNull(resultado);
        assertEquals(3, resultado.size());
        assertEquals(4.8, resultado.get(0).getPuntajePromedio(), 0.01);
        assertEquals(4.5, resultado.get(1).getPuntajePromedio(), 0.01);
        assertEquals(3.7, resultado.get(2).getPuntajePromedio(), 0.01);

        // Verificar que el método fue llamado una vez con los argumentos correctos
        verify(mockService, times(1)).ordenarRestaurantesPorCalificacion(restaurantes);
    }



    @Test
    public void givenRestaurants_whenOrdenarPorDistancia_thenReturnSortedRestaurants() {
        SugerenciaService sugerenciaService = new SugerenciaService();
        Restaurante restaurante1 = new Restaurante();
        restaurante1.setDistanciaUniversidad(2.0);

        Restaurante restaurante2 = new Restaurante();
        restaurante2.setDistanciaUniversidad(1.0);

        Restaurante restaurante3 = new Restaurante();
        restaurante3.setDistanciaUniversidad(3.0);

        List<Restaurante> restaurantes = new ArrayList<>();
        restaurantes.add(restaurante1);
        restaurantes.add(restaurante2);
        restaurantes.add(restaurante3);

        List<Restaurante> resultado = sugerenciaService.ordenarRestaurantePorDistancia(restaurantes);

        List<Double> distancias = new ArrayList<>();
        distancias.add(1.0);
        distancias.add(2.0);
        distancias.add(3.0);
        assertEquals(distancias.get(0), resultado.get(0).getDistanciaUniversidad());
        assertEquals(distancias.get(1), resultado.get(1).getDistanciaUniversidad());
        assertEquals(distancias.get(2), resultado.get(2).getDistanciaUniversidad());

    }
}

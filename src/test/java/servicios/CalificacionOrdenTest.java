package servicios;

import entidades.Calificacion;
import entidades.Comensal;
import entidades.VotoCalificacion;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CalificacionOrdenTest {

    private final CalificacionService service = new CalificacionService(null, null, null);

    @Test
    public void testOrdenSimple() {
        List<Calificacion> entrada = Arrays.asList(
                crearCalificacionConVotos(1L, 3),
                crearCalificacionConVotos(2L, 1),
                crearCalificacionConVotos(3L, 2)
        );
        List<Calificacion> ordenadas = service.obtenerCalificacionesOrdenadasPorVotos(entrada);
        assertEquals(Arrays.asList(1L, 3L, 2L), ids(ordenadas));
    }

    @Test
    public void testOrdenConEmpates() {
        List<Calificacion> entrada = Arrays.asList(
                crearCalificacionConVotos(1L, 2),
                crearCalificacionConVotos(2L, 2),
                crearCalificacionConVotos(3L, 1)
        );
        List<Calificacion> ordenadas = service.obtenerCalificacionesOrdenadasPorVotos(entrada);
        assertEquals(3L, ordenadas.get(2).getId());
        assertEquals(2, ordenadas.get(0).getVotos().size());
    }

    @ParameterizedTest
    @MethodSource("proveedorEscenarios")
    public void testOrdenParametrizado(List<Calificacion> entrada, List<Long> esperado) {
        List<Calificacion> resultado = service.obtenerCalificacionesOrdenadasPorVotos(entrada);
        List<Long> idsOrdenados = resultado.stream().map(Calificacion::getId).collect(Collectors.toList());
        assertEquals(esperado, idsOrdenados);
    }

    static Stream<Arguments> proveedorEscenarios() {
        return Stream.of(
                Arguments.of(
                        Arrays.asList(crearCalificacionConVotos(1L, 0), crearCalificacionConVotos(2L, 1)),
                        Arrays.asList(2L, 1L)
                ),
                Arguments.of(
                        Arrays.asList(crearCalificacionConVotos(3L, 2), crearCalificacionConVotos(4L, 4)),
                        Arrays.asList(4L, 3L)
                ),
                Arguments.of(
                        Arrays.asList(crearCalificacionConVotos(5L, 1), crearCalificacionConVotos(6L, 1)),
                        Arrays.asList(5L, 6L)
                )
        );
    }

    private static Calificacion crearCalificacionConVotos(Long id, int cantidadVotos) {
        Calificacion c = new Calificacion();
        c.setId(id);
        List<VotoCalificacion> votos = new ArrayList<>();
        for (int i = 0; i < cantidadVotos; i++) {
            VotoCalificacion v = new VotoCalificacion();
            Comensal comensal = new Comensal();
            comensal.setId((long) i);
            v.setComensal(comensal);
            votos.add(v);
        }
        c.getVotos().addAll(votos);
        return c;
    }

    private List<Long> ids(List<Calificacion> lista) {
        return lista.stream().map(Calificacion::getId).collect(Collectors.toList());
    }
}

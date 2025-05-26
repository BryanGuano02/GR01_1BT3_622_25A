package servicios;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import entidades.Calificacion;
import entidades.Comensal;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class VotacionCalificacionParametrizedTest {

    @Parameterized.Parameters
    public static Collection<Object[]> datosDePrueba() {
        return Arrays.asList(new Object[][] {
                // listaIdsComensales, resultadoEsperado, descripcion
                { Arrays.asList(1L), 1, "1 voto" },
                { Arrays.asList(1L, 1L), 0, "0 votos" },
                { Arrays.asList(1L, 2L, 3L), 3, "3 votos" },
                { Arrays.asList(1L, 2L, 1L, 2L), 0, "0 votos" },
                { Arrays.asList(1L, 1L, 1L), 1, "1 voto" },
                { Arrays.asList(1L, 2L, 3L, 2L, 1L), 1, "1 voto" }
        });
    }

    private List<Long> listaIdsComensales;
    private int resultadoEsperado;
    private String descripcion;

    public VotacionCalificacionParametrizedTest(List<Long> listaIdsComensales,
                                         int resultadoEsperado,
                                         String descripcion) {
        this.listaIdsComensales = listaIdsComensales;
        this.resultadoEsperado = resultadoEsperado;
        this.descripcion = descripcion;
    }

    @Test
    public void testConteoVotos() {
        VotacionService votacionService = new VotacionService();
        Calificacion calificacion = new Calificacion();

        for (Long idComensal : listaIdsComensales) {
            Comensal comensal = new Comensal();
            comensal.setId(idComensal);
            votacionService.votarCalificacion(comensal, calificacion);
        }

        assertEquals(descripcion, resultadoEsperado, calificacion.getVotos().size());
    }

}

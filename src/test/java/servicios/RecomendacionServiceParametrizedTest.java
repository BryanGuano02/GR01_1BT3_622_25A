// package servicios;

// import DAO.CalificacionDAO;
// import DAO.UsuarioDAO;
// import entidades.Comensal;
// import entidades.Restaurante;
// import entidades.Usuario;
// import org.junit.Test;
// import org.junit.runner.RunWith;
// import org.junit.runners.Parameterized;
// import java.util.Arrays;
// import java.util.Collection;
// import java.util.List;
// import static org.junit.Assert.*;

// @RunWith(Parameterized.class)
// public class RecomendacionServiceParametrizedTest {

//     @Parameterized.Parameters
//     public static Collection<Object[]> datosPrueba() {
//         return Arrays.asList(new Object[][] {
//                 // Test 1: Recomendación básica
//                 {
//                         "Italiana",
//                         Arrays.asList(
//                                 crearRestaurante("Pasta", "Italiana", 4.5),
//                                 crearRestaurante("Pizza", "Italiana", 4.8),
//                                 crearRestaurante("Tacos", "Mexicana", 4.2)),
//                         Arrays.asList("Pizza", "Pasta")
//                 },

//         });
//     }

//     private final String tipoComida;
//     private final List<Restaurante> restaurantes;
//     private final List<String> nombresEsperados;

//     public RecomendacionServiceParametrizedTest(String tipoComida,
//             List<Restaurante> restaurantes,
//             List<String> nombresEsperados) {
//         this.tipoComida = tipoComida;
//         this.restaurantes = restaurantes;
//         this.nombresEsperados = nombresEsperados;
//     }

//     @Test
//     public void testObtenerRecomendaciones() {
//         Comensal comensal = new Comensal();
//         comensal.setTipoComidaFavorita(tipoComida);

//         // Crear ambas implementaciones de prueba
//         UsuarioDAOTestImpl usuarioDAOTest = new UsuarioDAOTestImpl(restaurantes);
//         CalificacionDAOTestImpl calificacionDAOTest = new CalificacionDAOTestImpl(restaurantes);

//         RecomendacionService servicio = new RecomendacionService(usuarioDAOTest, calificacionDAOTest);

//         List<Restaurante> resultado = servicio.obtenerRecomendaciones(comensal);

//         assertEquals(nombresEsperados.size(), resultado.size());
//         for (int i = 0; i < nombresEsperados.size(); i++) {
//             assertEquals(nombresEsperados.get(i), resultado.get(i).getNombre());
//             if (i > 0) {
//                 assertTrue(resultado.get(i - 1).getPuntajePromedio() >= resultado.get(i).getPuntajePromedio());
//             }
//         }
//     }

//     private static class UsuarioDAOTestImpl implements UsuarioDAO {
//         private final List<Restaurante> restaurantes;

//         public UsuarioDAOTestImpl(List<Restaurante> restaurantes) {
//             this.restaurantes = restaurantes;
//         }

//         @Override
//         public List<Restaurante> obtenerTodosRestaurantes() {
//             return restaurantes;
//         }

//         @Override
//         public Double calcularPromedioCalificaciones(Long restauranteId) {
//             return null;
//         }

//         @Override
//         public List<Restaurante> buscarRestaurantesPorTipo(String tipoComida) {
//             return null;
//         }

//         @Override
//         public List<Restaurante> buscarRestaurantes(String busqueda) {
//             return null;
//         }

//         @Override
//         public Usuario findByNombreUsuario(String nombreUsuario) {
//             return null;
//         }

//         @Override
//         public void save(Usuario usuario) {
//         }

//         @Override
//         public void insert(Usuario usuario) {
//         }

//         @Override
//         public Usuario findById(Long id) {
//             return null;
//         }

//         @Override
//         public Comensal obtenerComensalPorId(Long id) {
//             return null;
//         }

//         @Override
//         public void close() {
//         }
//     }

//     private static class CalificacionDAOTestImpl extends CalificacionDAO {
//         private final List<Restaurante> restaurantes;

//         public CalificacionDAOTestImpl(List<Restaurante> restaurantes) {
//             this.restaurantes = restaurantes;
//         }

//         @Override
//         public Double calcularPromedioCalificaciones(Long idRestaurante) {
//             return restaurantes.stream()
//                     .filter(r -> r.getId().equals(idRestaurante))
//                     .findFirst()
//                     .map(Restaurante::getPuntajePromedio)
//                     .orElse(0.0);
//         }
//     }

//     private static Restaurante crearRestaurante(String nombre, String tipoComida, double puntaje) {
//         Restaurante r = new Restaurante();
//         r.setId((long) nombre.hashCode());
//         r.setNombre(nombre);
//         r.setTipoComida(tipoComida);
//         r.setPuntajePromedio(puntaje);
//         return r;
//     }
// }

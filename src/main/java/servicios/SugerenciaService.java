package servicios;

import DAO.UsuarioDAO;
import DAO.UsuarioDAOImpl;
import entidades.Restaurante;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class SugerenciaService {
    private final UsuarioDAO usuarioDAO;

    public SugerenciaService() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("UFood_PU");
        this.usuarioDAO = new UsuarioDAOImpl(emf);
    }
    public SugerenciaService(UsuarioDAO usuarioDAO) {
        this.usuarioDAO = usuarioDAO;
    }

    public List<Restaurante> getRestaurantesPorComidaFavorita(String tipoComida) {
        RestauranteService restauranteService = new RestauranteService(usuarioDAO);
        List<Restaurante> todosRestaurantes = restauranteService.obtenerTodosRestaurantes();
        return todosRestaurantes.stream()
                .filter(restaurante -> restaurante.getTipoComida().equalsIgnoreCase(tipoComida))
                .collect(Collectors.toList());
    }

    public List<Restaurante> ordenarRestaurantesPorCalificacion(List<Restaurante> restaurantes) {
        return restaurantes.stream()
                .sorted(Comparator.comparingDouble(Restaurante::getPuntajePromedio).reversed())
                .collect(Collectors.toList());
    }

    public List<Restaurante> ordenarRestaurantePorDistancia(List<Restaurante> restaurantes) {
        return restaurantes.stream()
                .sorted(Comparator.comparingDouble(Restaurante::getDistanciaUniversidad))
                .collect(Collectors.toList());
    }
}

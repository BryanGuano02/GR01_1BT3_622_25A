package servicios;

import DAO.RestauranteDAO;
import DAO.UsuarioDAO;
import entidades.Restaurante;
import java.util.List;

public class RestauranteService {
    private final UsuarioDAO usuarioDAO;
    private final RestauranteDAO restauranteDAO;

    public RestauranteService(UsuarioDAO usuarioDAO, RestauranteDAO restauranteDAO) {
        this.usuarioDAO = usuarioDAO;
        this.restauranteDAO = restauranteDAO;
    }

    public void guardarRestaurante(Restaurante restaurante) {
        usuarioDAO.save(restaurante);
    }

    public Restaurante obtenerRestaurantePorId(Long id) {
        return (Restaurante) usuarioDAO.findById(id);
    }

    public List<Restaurante> obtenerTodosRestaurantes() {
        return usuarioDAO.obtenerTodosRestaurantes();
    }

    public List<Restaurante> buscarRestaurantes(String busqueda) {
        return usuarioDAO.buscarRestaurantes(busqueda);
    }

    public void actualizarRestaurante(Restaurante restaurante) {
        usuarioDAO.save(restaurante);
    }

    public Restaurante obtenerRestauranteConDetalles(Long id) {
        return restauranteDAO.obtenerRestauranteConDetalles(id);
    }
}
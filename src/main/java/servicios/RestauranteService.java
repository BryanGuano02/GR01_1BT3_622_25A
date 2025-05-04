package servicios;

import DAO.RestauranteDAO;
import entidades.Restaurante;
import jakarta.persistence.EntityManager;

public class RestauranteService {
    private final RestauranteDAO restauranteDAO;


    public RestauranteService() {
        this.restauranteDAO = new RestauranteDAO();
    }

    public Restaurante obtenerRestaurantePorId(Long idRestaurante) {
        return restauranteDAO.obtenerRestaurantePorId(idRestaurante);
    }



}

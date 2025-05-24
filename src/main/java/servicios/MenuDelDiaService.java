package servicios;

import DAO.RestauranteDAO;
import entidades.MenuDelDia;
import entidades.Restaurante;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class MenuDelDiaService {

    private final RestauranteDAO restauranteDAO;

    public MenuDelDiaService() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("UFood_PU");
        this.restauranteDAO = new RestauranteDAO(emf);
    }

    public MenuDelDiaService(RestauranteDAO restauranteDAO) {
        this.restauranteDAO = restauranteDAO;
    }

    public Restaurante guardarMenuDelDia(String descripcion, Long idRestaurante) {
        Restaurante restaurante = buscarRestaurante(idRestaurante);
        MenuDelDia menuDelDia = new MenuDelDia(descripcion, 0);
        restaurante.setMenuDelDia(menuDelDia);
        guardarRestaurante(restaurante);
        return restaurante;
    }

    public void sumarVoto(Long idRestaurante) {
        Restaurante restaurante = buscarRestaurante(idRestaurante);
        MenuDelDia menuDelDia = restaurante.getMenuDelDia();
        menuDelDia.setCantidadVotos(menuDelDia.getCantidadVotos() + 1);
        guardarRestaurante(restaurante);
    }

    private Restaurante buscarRestaurante(Long id) {
        return restauranteDAO.obtenerRestaurantePorId(id);
    }

    private void guardarRestaurante(Restaurante restaurante) {
        restauranteDAO.save(restaurante);
    }
}
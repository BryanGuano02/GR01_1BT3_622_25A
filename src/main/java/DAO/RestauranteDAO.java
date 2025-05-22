package DAO;

import java.util.List;

import entidades.Restaurante;

public class RestauranteDAO {
    private UsuarioDAO usuarioDAO;

    public RestauranteDAO(UsuarioDAO usuarioDAO) {
        this.usuarioDAO = usuarioDAO;
    }

    public Restaurante obtenerRestaurantePorId(Long id) {
        return (Restaurante) usuarioDAO.findById(id);
    }

    public List<Restaurante> obtenerTodosRestaurantes() {
        return usuarioDAO.obtenerTodosRestaurantes();
    }

}

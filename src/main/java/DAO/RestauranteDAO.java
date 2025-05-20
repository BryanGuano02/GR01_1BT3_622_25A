package DAO;

import entidades.Restaurante;

public class RestauranteDAO {
    private final UsuarioDAO usuarioDAO;

    public RestauranteDAO(UsuarioDAO usuarioDAO) {
        this.usuarioDAO = usuarioDAO;
    }

    public Restaurante obtenerRestaurantePorId(Long id) {
        return (Restaurante) usuarioDAO.findById(id);
    }

}
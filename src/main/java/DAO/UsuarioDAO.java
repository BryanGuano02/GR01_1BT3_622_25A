package DAO;

import entidades.Usuario;

public interface UsuarioDAO {
    Usuario findByNombreUsuario(String nombreUsuario);
    void insert(Usuario usuario);
    Usuario findById(Long id);
    void close();
}
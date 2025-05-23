package servicios;

import DAO.DueñoRestauranteDAO;
import DAO.UsuarioDAO;
import entidades.Comensal;
import entidades.DueñoRestaurante;
import entidades.Restaurante;
import entidades.Usuario;
import exceptions.ServiceException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.List;

public class AuthService {
    private final UsuarioDAO usuarioDAO;
    private final DueñoRestauranteDAO dueñoRestauranteDAO;

    public AuthService(UsuarioDAO usuarioDAO, DueñoRestauranteDAO dueñoDAO) {
        this.usuarioDAO = usuarioDAO;
        this.dueñoRestauranteDAO = dueñoDAO;
    }

    public List<Restaurante> obtenerTodosRestaurantes() {
        return usuarioDAO.obtenerTodosRestaurantes();
    }

    private String hashPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = digest.digest(password.getBytes());
            return Base64.getEncoder().encodeToString(hashBytes);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error al hashear la contraseña", e);
        }
    }

    public Usuario login(String nombreUsuario, String contrasena) throws ServiceException {
        Usuario usuario = usuarioDAO.findByNombreUsuario(nombreUsuario);

        if (usuario == null || !hashPassword(contrasena).equals(usuario.getContrasena())) {
            throw new ServiceException("Credenciales inválidas");
        }
        return usuario;
    }

    public void registrarDueñoRestaurante(DueñoRestaurante dueño) throws ServiceException {
        if (usuarioDAO.findByNombreUsuario(dueño.getNombreUsuario()) != null) {
            throw new ServiceException("El nombre de usuario ya existe");
        }
        dueñoRestauranteDAO.save(dueño);
    }

    /*public void registrarUsuarioRestaurante(Usuario usuario) throws ServiceException {
        if (usuarioDAO.findByNombreUsuario(usuario.getNombreUsuario()) != null) {
            throw new ServiceException("El nombre de usuario ya existe");
        }

        Restaurante restaurante = new Restaurante();
        restaurante.setNombreUsuario(usuario.getNombreUsuario());
        restaurante.setContrasena(hashPassword(usuario.getContrasena()));
        restaurante.setEmail(usuario.getEmail());
        restaurante.setTipoUsuario("RESTAURANTE");

        usuarioDAO.save(restaurante);
    }*/

    public void registrarComensal(Usuario usuario, String tipoComidaFavorita) throws ServiceException {
        if (usuarioDAO.findByNombreUsuario(usuario.getNombreUsuario()) != null) {
            throw new ServiceException("El nombre de usuario ya existe");
        }

        Comensal comensal = new Comensal();
        comensal.setNombreUsuario(usuario.getNombreUsuario());
        comensal.setContrasena(hashPassword(usuario.getContrasena()));
        comensal.setEmail(usuario.getEmail());
        comensal.setTipoComidaFavorita(tipoComidaFavorita); // Nuevo campo
        comensal.setTipoUsuario("COMENSAL");

        usuarioDAO.save(comensal);
    }

    public boolean usuarioExiste(String nombreUsuario) {
        return usuarioDAO.findByNombreUsuario(nombreUsuario) != null;
    }

    public Usuario findByNombreUsuario(String nombreUsuario) {
        return usuarioDAO.findByNombreUsuario(nombreUsuario);
    }
}

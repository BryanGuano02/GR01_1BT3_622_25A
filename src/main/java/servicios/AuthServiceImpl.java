package servicios;

import DAO.UsuarioDAO;
import entidades.Comensal;
import entidades.Usuario;
import entidades.UsuarioRestaurante;
import exceptions.ServiceException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class AuthServiceImpl implements AuthService {
    private final UsuarioDAO usuarioDAO;

    public AuthServiceImpl(UsuarioDAO usuarioDAO) {
        this.usuarioDAO = usuarioDAO;
    }

    // Método interno para hashear (sin clase separada)
    private String hashPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = digest.digest(password.getBytes());
            return Base64.getEncoder().encodeToString(hashBytes);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error al hashear la contraseña", e);
        }
    }

    // Método interno para verificar
    private boolean checkPassword(String inputPassword, String storedHash) {
        return hashPassword(inputPassword).equals(storedHash);
    }

    @Override
    public Usuario login(String nombreUsuario, String contrasena) throws ServiceException {
        Usuario usuario = usuarioDAO.findByNombreUsuario(nombreUsuario);

        if (usuario == null || !checkPassword(contrasena, usuario.getContrasena())) {
            throw new ServiceException("Nombre de usuario o contraseña incorrectos");
        }

        return usuario;
    }

    @Override
    public void registrarUsuarioRestaurante(Usuario usuario) throws ServiceException {
        if (usuarioDAO.findByNombreUsuario(usuario.getNombreUsuario()) != null) {
            throw new ServiceException("El nombre de usuario ya está en uso");
        }

        usuario.setContrasena(hashPassword(usuario.getContrasena()));
        usuario.setTipoUsuario("RESTAURANTE");

        UsuarioRestaurante usuarioRestaurante = new UsuarioRestaurante();
        usuarioRestaurante.setNombreUsuario(usuario.getNombreUsuario());
        usuarioRestaurante.setContrasena(usuario.getContrasena());
        usuarioRestaurante.setEmail(usuario.getEmail());
        usuarioRestaurante.setTipoUsuario(usuario.getTipoUsuario());

        usuarioDAO.insert(usuarioRestaurante);
    }

    @Override
    public void registrarComensal(Usuario usuario) throws ServiceException {
        if (usuarioDAO.findByNombreUsuario(usuario.getNombreUsuario()) != null) {
            throw new ServiceException("El nombre de usuario ya está en uso");
        }

        Comensal comensal = new Comensal();
        // Usamos los setters heredados
        comensal.setNombreUsuario(usuario.getNombreUsuario());
        comensal.setContrasena(hashPassword(usuario.getContrasena()));
        comensal.setEmail(usuario.getEmail());
        comensal.setTipoUsuario("COMENSAL");
        // Si necesitas preferencias iniciales:
        // comensal.setPreferencias(new ArrayList<>());

        usuarioDAO.insert(comensal);
    }
}
package servicios;

import DAO.UsuarioDAO;
import entidades.MenuDelDia;
import entidades.Restaurante;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class MenuDelDiaService {

    private final UsuarioDAO usuarioDAO;

    public MenuDelDiaService() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("UFood_PU");
        this.usuarioDAO = new UsuarioDAO(emf);
    }

    public MenuDelDiaService( UsuarioDAO usuarioDAO ) {
        this.usuarioDAO = usuarioDAO ;
    }

    public Restaurante guardarMenuDelDia(String descripcion, Long idRestaurante) {

        Restaurante restaurante = buscarRestaurante(idRestaurante);
        MenuDelDia menuDelDia = new MenuDelDia( descripcion, 0 );
        restaurante.setMenuDelDia(menuDelDia);
        guardarRestaurante(restaurante);
        return restaurante;

    }

    public void sumarVoto ( Long idRestaurante ) {

        Restaurante restaurante = buscarRestaurante(idRestaurante);
        MenuDelDia menuDelDia = restaurante.getMenuDelDia();
        menuDelDia.setCantidadVotos( menuDelDia.getCantidadVotos() + 1 );
        guardarRestaurante(restaurante);


    }

    private Restaurante buscarRestaurante(Long id) {
        return (Restaurante) usuarioDAO.findById(id);
    }

    private void guardarRestaurante(Restaurante restaurante) {
        usuarioDAO.save(restaurante);
    }


}

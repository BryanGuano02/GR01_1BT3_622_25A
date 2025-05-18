package servicios;

import DAO.UsuarioDAOImpl;
import entidades.MenuDelDia;
import entidades.Restaurante;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class MenuDelDiaService {

    private final UsuarioDAOImpl usuarioDAO;

    public MenuDelDiaService() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("UFood_PU");
        this.usuarioDAO = new UsuarioDAOImpl(emf);
    }

    public MenuDelDiaService( UsuarioDAOImpl usuarioDAO ) {
        this.usuarioDAO = usuarioDAO ;
    }

    public Restaurante guardarMenuDelDia(String descripcion, Long idRestaurante) {

        Restaurante restaurante = (Restaurante) usuarioDAO.findById( idRestaurante );
        MenuDelDia menuDelDia = new MenuDelDia( descripcion, 0 );
        restaurante.setMenuDelDia(menuDelDia);
        usuarioDAO.save(restaurante);
        return restaurante;


    }

    public void sumarVoto ( Long idRestaurante ) {

        Restaurante restaurante = (Restaurante) usuarioDAO.findById( idRestaurante );
        MenuDelDia menuDelDia = restaurante.getMenuDelDia();
        menuDelDia.setCantidadVotos( menuDelDia.getCantidadVotos() + 1 );
        usuarioDAO.save(restaurante);


    }

}

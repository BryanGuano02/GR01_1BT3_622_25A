package modelos;

import entidades.Comensal;
import entidades.Restaurante;
import entidades.Usuario;
import jakarta.persistence.*;

public class UsuarioDAO {
    private EntityManager entityManager;

    public Usuario autenticar(String email, String password, String tipo) {
        try {
            Class<? extends Usuario> userClass = tipo.equals("comensal") ? Comensal.class : Restaurante.class;

            String jpql = "SELECT u FROM " + userClass.getSimpleName() +
                          " u WHERE u.email = :email AND u.password = :password";

            TypedQuery<? extends Usuario> query = entityManager.createQuery(jpql, userClass);
            query.setParameter("email", email);
            query.setParameter("password", password);

            return query.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }
}

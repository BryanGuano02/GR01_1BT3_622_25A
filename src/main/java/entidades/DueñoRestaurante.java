package entidades;

import jakarta.persistence.*;

@Entity
@Table(name = "duenos_restaurante")
@PrimaryKeyJoinColumn(name = "usuario_id")
public class DueñoRestaurante extends Usuario {

    @OneToOne(mappedBy = "dueño", cascade = CascadeType.ALL)
    private Restaurante restaurante;

    public DueñoRestaurante() {
        this.setTipoUsuario("DUENO_RESTAURANTE");
    }

    public DueñoRestaurante(String nombreUsuario, String contrasena, String email) {
        this.setNombreUsuario(nombreUsuario);
        this.setContrasena(contrasena);
        this.setEmail(email);
        this.setTipoUsuario("DUENO_RESTAURANTE");
    }

    // Getters y setters
    public Restaurante getRestaurante() {
        return restaurante;
    }

    public void setRestaurante(Restaurante restaurante) {
        this.restaurante = restaurante;
        if (restaurante != null) {
            restaurante.setDueño(this);
        }
    }
}
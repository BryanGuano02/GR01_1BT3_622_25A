package entidades;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "comensales")
@PrimaryKeyJoinColumn(name = "usuario_id")
public class Comensal extends Usuario {

    @OneToMany(mappedBy = "comensal", cascade = CascadeType.ALL)
    private List<Preferencia> preferencias;

    public Comensal() {
        this.setTipoUsuario("COMENSAL");
    }

    public Comensal(String nombreUsuario, String contrasena, String email, List<Preferencia> preferencias) {
        this.setNombreUsuario(nombreUsuario);
        this.setContrasena(contrasena);
        this.setEmail(email);
        this.setTipoUsuario("COMENSAL");
        this.preferencias = preferencias;

        // Establece la relación bidireccional
        if (preferencias != null) {
            preferencias.forEach(p -> p.setComensal(this));
        }
    }

    public List<Preferencia> getPreferencias() {
        return preferencias;
    }

    public void setPreferencias(List<Preferencia> preferencias) {
        this.preferencias = preferencias;

        // Establece la relación bidireccional
        if (preferencias != null) {
            preferencias.forEach(p -> p.setComensal(this));
        }
    }

    // Método de conveniencia para manejar la relación
    public void agregarPreferencia(Preferencia preferencia) {
        preferencias.add(preferencia);
        preferencia.setComensal(this);
    }

    public void removerPreferencia(Preferencia preferencia) {
        preferencias.remove(preferencia);
        preferencia.setComensal(null);
    }
}
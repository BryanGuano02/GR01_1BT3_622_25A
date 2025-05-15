package entidades;

import jakarta.persistence.*;

import java.util.List;
import java.util.ArrayList;

@Entity
@Table(name = "comensales")
@PrimaryKeyJoinColumn(name = "usuario_id")
public class Comensal extends Usuario {

    @ManyToMany(mappedBy = "comensales")
    private List<Planificacion> planificaciones;
    @OneToMany(mappedBy = "comensal", cascade = CascadeType.ALL)
    private List<Preferencia> preferencias;

    @ManyToMany
    @JoinTable(
        name = "suscripcion_restaurante",
        joinColumns = @JoinColumn(name = "comensal_id"),
        inverseJoinColumns = @JoinColumn(name = "restaurante_id")
    )
    private List<Restaurante> restaurantesSuscritos = new ArrayList<>();

    public Comensal() {
        this.setTipoUsuario("COMENSAL");
    }

    public Comensal(String nombreUsuario, String contrasena, String email, List<Preferencia> preferencias) {
        this.setNombreUsuario(nombreUsuario);
        this.setContrasena(contrasena);
        this.setEmail(email);
        this.setTipoUsuario("COMENSAL");
        this.preferencias = preferencias;
        this.planificaciones = null;

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

    public List<Planificacion> getPlanificaciones() {
        return planificaciones;
    }

    public void setPlanificaciones(List<Planificacion> planificaciones) {
        this.planificaciones = planificaciones;
    }

    public List<Restaurante> getRestaurantesSuscritos() {
        return restaurantesSuscritos;
    }

    public void setRestaurantesSuscritos(List<Restaurante> restaurantesSuscritos) {
        this.restaurantesSuscritos = restaurantesSuscritos;
    }

    public void suscribirseARestaurante(Restaurante restaurante) {
        if (!restaurantesSuscritos.contains(restaurante)) {
            restaurantesSuscritos.add(restaurante);
            if (restaurante.getSuscriptores() != null && !restaurante.getSuscriptores().contains(this)) {
                restaurante.getSuscriptores().add(this);
            }
        }
    }

    public void desuscribirseDeRestaurante(Restaurante restaurante) {
        restaurantesSuscritos.remove(restaurante);
        if (restaurante.getSuscriptores() != null) {
            restaurante.getSuscriptores().remove(this);
        }
    }
}

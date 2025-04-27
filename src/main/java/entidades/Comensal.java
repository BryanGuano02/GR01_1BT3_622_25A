package entidades;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Comensal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private String correo;
    @OneToMany
    @JoinColumn(name = "idComensal")
    private List<Preferencia> preferencias;

    public Comensal() {
    }

    public Comensal(Long id, String nombre, String correo, List<Preferencia> preferencias) {
        this.id = id;
        this.nombre = nombre;
        this.correo = correo;
        this.preferencias = preferencias;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public List<Preferencia> getPreferencias() {
        return preferencias;
    }

    public void setPreferencias(List<Preferencia> preferencias) {
        this.preferencias = preferencias;
    }

}

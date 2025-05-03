package entidades;

import jakarta.persistence.*;

import java.util.List;

//@Entity
//@Table(name = "comensal")
//@PrimaryKeyJoinColumn(name = "usuario_id")
//@DiscriminatorValue("COMENSAL")
@Entity
@DiscriminatorValue("COMENSAL")
public class Comensal extends Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String nombre;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "comensal_id")
    private List<Preferencia> preferencias;

    public Comensal() {
        super();
    }

    public Comensal(String email, String password, String nombre, List<Preferencia> preferencias) {
        super(email, password);
        this.nombre = nombre;
        this.preferencias = preferencias;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public List<Preferencia> getPreferencias() {
        return preferencias;
    }

    public void setPreferencias(List<Preferencia> preferencias) {
        this.preferencias = preferencias;
    }

}

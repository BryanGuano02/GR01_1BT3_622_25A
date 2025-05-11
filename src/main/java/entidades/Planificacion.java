package entidades;


import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Planificacion {
    public String nombre;
    public String hora;
    public String estado;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToMany
    @JoinTable(
            name = "planificacion_comensal",
            joinColumns = @JoinColumn(name = "planificacion_id"),
            inverseJoinColumns = @JoinColumn(name = "comensal_id")
    )
    private List<Comensal> comensales = new ArrayList<>();

    public Planificacion() {
    }

    public Planificacion(String hora, String nombre) {
        this.hora = hora;
        this.nombre = nombre;
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

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public List<Comensal> getComensales() {
        return comensales;
    }

    public void setComensales(List<Comensal> comensales) {
        this.comensales = comensales;
    }
    public void addComensal(Comensal comensal) {
        this.comensales.add(comensal);
    }
    public void setEstado(String estado) {}
    public String getEstado() { return estado; }
}

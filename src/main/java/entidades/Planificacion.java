package entidades;


import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Planificacion {
    public String nombre;
    public String hora;
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

    @ManyToOne
    @JoinColumn(name = "restaurante_id")
    private Restaurante restaurante;

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

    public Restaurante getRestaurante() {
        return restaurante;
    }

    public void setRestaurante(Restaurante restaurante) {
        if (restaurante == null || restaurante.getNombre() == null || restaurante.getNombre().trim().isEmpty()) {
            throw new IllegalArgumentException("Restaurante no válido");
        }
        this.restaurante = restaurante;
    }

    public void setComensales(List<Comensal> comensales) {
        this.comensales = comensales;
    }

    public void addComensal(Comensal comensal) {
        if (this.comensales == null) {
            this.comensales = new ArrayList<>();
        }

        // Verifica si el comensal ya existe
        if (this.comensales.stream().anyMatch(c -> c.getId().equals(comensal.getId()))) {
            throw new IllegalArgumentException("El comensal ya está en esta planificación");
        }

        this.comensales.add(comensal);
    }

}

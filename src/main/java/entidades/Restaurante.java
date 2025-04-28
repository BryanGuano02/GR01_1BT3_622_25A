package entidades;

import jakarta.persistence.*;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Restaurante {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private String descripcion;
    private String tipoComida;
    private LocalTime horaApertura;
    private LocalTime horaCierre;

    @OneToMany(
            mappedBy = "restaurante",                 // Asume que Historia tiene @ManyToOne restaurante
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY
    )
    @OrderBy("fechaPublicacion DESC")
    private List<Historia> historias = new ArrayList<>();

    public Restaurante() {}

    public Restaurante(String nombre, String tipoComida, LocalTime horaApertura, LocalTime horaCierre) {
        this.nombre = nombre;
        this.tipoComida = tipoComida;
        this.horaApertura = horaApertura;
        this.horaCierre = horaCierre;
    }

    // --- Getters y setters para los campos básicos ---

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

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getTipoComida() {
        return tipoComida;
    }

    public void setTipoComida(String tipoComida) {
        this.tipoComida = tipoComida;
    }

    public LocalTime getHoraApertura() {
        return horaApertura;
    }

    public void setHoraApertura(LocalTime horaApertura) {
        this.horaApertura = horaApertura;
    }

    public LocalTime getHoraCierre() {
        return horaCierre;
    }

    public void setHoraCierre(LocalTime horaCierre) {
        this.horaCierre = horaCierre;
    }

    // --- Getter y setter de historias ---

    /**
     * Devuelve la lista de historias asociadas a este restaurante.
     */
    public List<Historia> getHistorias() {
        return historias;
    }

    /**
     * Reemplaza la lista de historias de este restaurante.
     */
    public void setHistorias(List<Historia> historias) {
        this.historias = historias;
    }

    // --- Método de conveniencia para añadir historias ---

    public Boolean crearHistoria(String contenido, String imagenUrl) {
        if (contenido == null || contenido.trim().isEmpty()) {
            return false;
        }
        Historia nuevaHistoria = new Historia(contenido, imagenUrl, this);
        this.historias.add(nuevaHistoria);
        return true;
    }

    @Override
    public String toString() {
        return "Restaurante{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", tipoComida='" + tipoComida + '\'' +
                ", horaApertura=" + horaApertura +
                ", horaCierre=" + horaCierre +
                '}';
    }
}

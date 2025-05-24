package entidades;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
public class Calificacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double puntaje;
    private String comentario;

    @Column(name = "fecha_creacion")
    private LocalDateTime fechaCreacion;

    @ManyToOne
    @JoinColumn(name = "idComensal")
    private Comensal comensal;
    @ManyToOne
    @JoinColumn(name = "idRestaurante")
    private Restaurante restaurante;

    public Calificacion() {
        // Establecer la fecha de creación automáticamente al crear una nueva instancia
        this.fechaCreacion = LocalDateTime.now();
    }

    public Calificacion(Double puntaje, String comentario, Comensal comensal, Restaurante restaurante) {
        this.puntaje = puntaje;
        this.comentario = comentario;
        this.comensal = comensal;
        this.restaurante = restaurante;
        this.fechaCreacion = LocalDateTime.now(); // Establecer la fecha automáticamente
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getPuntaje() {
        return puntaje;
    }

    public void setPuntaje(Double puntaje) {
        this.puntaje = puntaje;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public Comensal getComensal() {
        return comensal;
    }

    public void setComensal(Comensal comensal) {
        this.comensal = comensal;
    }

    public Restaurante getRestaurante() {
        return restaurante;
    }

    public void setRestaurante(Restaurante restaurante) {
        this.restaurante = restaurante;
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public String getFechaFormateada() {
        return this.fechaCreacion.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
    }

    @Override
    public String toString() {
        return "Calificacion{" + "id=" + id + ", puntaje=" + puntaje + ", comentario='" + comentario + '\'' + ", fechaCreacion=" + fechaCreacion + ", comensal=" + comensal + ", restaurante=" + restaurante + '}';
    }
}

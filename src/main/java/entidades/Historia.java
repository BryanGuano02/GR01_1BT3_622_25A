package entidades;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Historia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String contenido;
    private String imagenUrl;
    private LocalDateTime fechaPublicacion;

    @ManyToOne
    @JoinColumn(name = "idRestaurante")
    private Restaurante restaurante;

    public Historia() {}

    public Historia(String contenido, String imagenUrl, Restaurante restaurante) {
        this.contenido = contenido;
        this.imagenUrl = imagenUrl;
        this.fechaPublicacion = LocalDateTime.now();
        this.restaurante = restaurante;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public String getImagenUrl() {
        return imagenUrl;
    }

    public void setImagenUrl(String imagenUrl) {
        this.imagenUrl = imagenUrl;
    }

    public LocalDateTime getFechaPublicacion() {
        return fechaPublicacion;
    }

    public void setFechaPublicacion(LocalDateTime fechaPublicacion) {
        this.fechaPublicacion = fechaPublicacion;
    }

    public Restaurante getRestaurante() {
        return restaurante;
    }

    public void setRestaurante(Restaurante restaurante) {
        this.restaurante = restaurante;
    }

    // getters y setters
}
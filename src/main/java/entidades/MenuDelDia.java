package entidades;

import jakarta.persistence.*;

@Entity
public class MenuDelDia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int cantidadVotos;
    private String descripcion;

    public MenuDelDia(String descripcion, int cantidadVotos) {
        this.cantidadVotos = cantidadVotos;
        this.descripcion = descripcion;
    }

    public MenuDelDia() {}

    // Getters y setters
    public int getCantidadVotos() {
        return cantidadVotos;
    }

    public void setCantidadVotos(int cantidadVotos) {
        this.cantidadVotos = cantidadVotos;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}

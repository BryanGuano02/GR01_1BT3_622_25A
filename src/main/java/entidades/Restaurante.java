package entidades;

import jakarta.persistence.*;

import java.time.LocalTime;
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
    private Double puntajePromedio;

    public Restaurante() {
    }

    public Restaurante(String nombre, String tipoComida, LocalTime horaApertura, LocalTime horaCierre) {
        this.nombre = nombre;
        this.tipoComida = tipoComida;
        this.horaApertura = horaApertura;
        this.horaCierre = horaCierre;
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTipoComida() {
        return tipoComida;
    }

    public void setTipoComida(String tipoComida) {
        this.tipoComida = tipoComida;
    }

    public Double getPuntajePromedio() {
        return puntajePromedio;
    }

    public void setPuntajePromedio(Double puntajePromedio) {
        this.puntajePromedio = puntajePromedio;
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

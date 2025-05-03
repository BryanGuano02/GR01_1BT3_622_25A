package entidades;

import jakarta.persistence.*;

import java.time.LocalTime;

//@Entity
//@Table(name = "restaurante")
//@PrimaryKeyJoinColumn(name = "usuario_id")
//@DiscriminatorValue("RESTAURANTE")
@Entity
@DiscriminatorValue("RESTAURANTE")
public class Restaurante extends Usuario {

    private String nombre;
    private String descripcion;
    private String tipoComida;
    private LocalTime horaApertura;
    private LocalTime horaCierre;

    public Restaurante() {
        super();
    }

    public Restaurante(String email, String password, String nombre, String tipoComida, LocalTime horaApertura, LocalTime horaCierre) {
        super(email, password);
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

    @Override
    public String toString() {
        return "Restaurante{" +
                ", nombre='" + nombre + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", tipoComida='" + tipoComida + '\'' +
                ", horaApertura=" + horaApertura +
                ", horaCierre=" + horaCierre +
                '}';
    }
}

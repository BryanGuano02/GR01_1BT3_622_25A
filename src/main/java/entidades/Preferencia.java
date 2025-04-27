package entidades;

import jakarta.persistence.*;
import java.time.LocalTime;

@Entity
public class Preferencia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String tipoComida;
    private LocalTime horaApertura;
    private LocalTime horaCierre;
    private Double distanciaUniversidad;

    public Preferencia() {
    }

    public Preferencia(Long id, String tipoComida, LocalTime horaApertura, LocalTime horaCierre, Double distanciaUniversidad) {
        this.id = id;
        this.tipoComida = tipoComida;
        this.horaApertura = horaApertura;
        this.horaCierre = horaCierre;
        this.distanciaUniversidad = distanciaUniversidad;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Double getDistanciaUniversidad() {
        return distanciaUniversidad;
    }

    public void setDistanciaUniversidad(Double distanciaUniversidad) {
        this.distanciaUniversidad = distanciaUniversidad;
    }
}

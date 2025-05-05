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
    private Double puntajePromedio;
    private int precio;
    private Double distanciaUniversidad;
    private int calidad;
    private int tiempoEspera;


    // Nuevo atributo para las historias (menú del día)
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(
            name = "restaurante_historias",
            joinColumns = @JoinColumn(name = "restaurante_id"),
            foreignKey = @ForeignKey(name = "fk_restaurante_historias")
    )
    @Column(name = "historia", length = 1000, nullable = false)
    private List<String> historias = new ArrayList<>();

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


    public int getPrecio() {
        return precio;
    }

    public void setPrecio(int precio) {
        this.precio = precio;
    }


    public Double getDistanciaUniversidad() {
        return distanciaUniversidad;
    }

    public void setDistanciaUniversidad(Double distanciaUniversidad) {
        this.distanciaUniversidad = distanciaUniversidad;
    }

    public int getCalidad() {
        return calidad;
    }

    public void setCalidad(int calidad) {
        this.calidad = calidad;
    }

    public int getTiempoEspera() {
        return tiempoEspera;
    }

    public void setTiempoEspera(int tiempoEspera) {
        this.tiempoEspera = tiempoEspera;
    }

    public List<String> getHistorias() {
        if (this.historias == null) {
            this.historias = new ArrayList<>();
        }
        return this.historias;
    }

    public void setHistorias(List<String> historias) {
        this.historias = historias;
    }

    public void agregarHistoria(String historia) {
        this.historias.add(historia);
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
                ", historias=" + historias +
                '}';
    }
}
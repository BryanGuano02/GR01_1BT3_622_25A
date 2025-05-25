package entidades;

import jakarta.persistence.*;

@Entity
public class Calificacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double puntaje;
    private String comentario;
    @ManyToOne
    @JoinColumn(name = "idComensal")
    private Comensal comensal;
    @ManyToOne
    @JoinColumn(name = "idRestaurante")
    private Restaurante restaurante;

    private int calidadComida;
    private int calidadServicio;
    private int limpieza;
    private int ambiente;
    private int tiempoEspera;
    private int relacionPrecioCalidad;
    private int variedadMenu;
    private int accesibilidad;
    private int volveria;









    public Calificacion() {
    }

//    public Calificacion(Double puntaje, String comentario, Comensal comensal, Restaurante restaurante) {
//        this.puntaje = puntaje;
//        this.comentario = comentario;
//        this.comensal = comensal;
//        this.restaurante = restaurante;
//    }


    public Calificacion(String comentario, Comensal comensal, Restaurante restaurante, int calidadComida, int calidadServicio, int limpieza, int ambiente, int tiempoEspera, int relacionPrecioCalidad, int variedadMenu, int accesibilidad, int volveria) {
        this.comentario = comentario;
        this.comensal = comensal;
        this.restaurante = restaurante;
        this.calidadComida = calidadComida;
        this.calidadServicio = calidadServicio;
        this.limpieza = limpieza;
        this.ambiente = ambiente;
        this.tiempoEspera = tiempoEspera;
        this.relacionPrecioCalidad = relacionPrecioCalidad;
        this.variedadMenu = variedadMenu;
        this.accesibilidad = accesibilidad;
        this.volveria = volveria;
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

    public int getCalidadComida() {
        return calidadComida;
    }

    public void setCalidadComida(int calidadComida) {
        this.calidadComida = calidadComida;
    }

    public int getCalidadServicio() {
        return calidadServicio;
    }

    public void setCalidadServicio(int calidadServicio) {
        this.calidadServicio = calidadServicio;
    }

    public int getLimpieza() {
        return limpieza;
    }

    public void setLimpieza(int limpieza) {
        this.limpieza = limpieza;
    }

    public int getAmbiente() {
        return ambiente;
    }

    public void setAmbiente(int ambiente) {
        this.ambiente = ambiente;
    }

    public int getTiempoEspera() {
        return tiempoEspera;
    }

    public void setTiempoEspera(int tiempoEspera) {
        this.tiempoEspera = tiempoEspera;
    }

    public int getRelacionPrecioCalidad() {
        return relacionPrecioCalidad;
    }

    public void setRelacionPrecioCalidad(int relacionPrecioCalidad) {
        this.relacionPrecioCalidad = relacionPrecioCalidad;
    }

    public int getVariedadMenu() {
        return variedadMenu;
    }

    public void setVariedadMenu(int variedadMenu) {
        this.variedadMenu = variedadMenu;
    }

    public int getAccesibilidad() {
        return accesibilidad;
    }

    public void setAccesibilidad(int accesibilidad) {
        this.accesibilidad = accesibilidad;
    }

    public int getVolveria() {
        return volveria;
    }

    public void setVolveria(int volveria) {
        this.volveria = volveria;
    }

    @Override
    public String toString() {
        return "Calificacion{" +
                "id=" + id +
                ", puntaje=" + puntaje +
                ", comentario='" + comentario + '\'' +
                ", comensal=" + comensal +
                ", restaurante=" + restaurante +
                '}';
    }
}

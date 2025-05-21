package entidades;

import jakarta.persistence.*;

@Entity
@Table(name = "historia")
public class Historia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "contenido", length = 1000)
    private String contenido;

    @ManyToOne
    @JoinColumn(name = "restaurante_id", foreignKey = @ForeignKey(name = "fk_historia_restaurante"))
    private Restaurante restaurante;

    public Historia() {
    }

    public Historia(String contenido) {
        this.contenido = contenido;
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

    public Restaurante getRestaurante() {
        return restaurante;
    }

    public void setRestaurante(Restaurante restaurante) {
        this.restaurante = restaurante;
    }

    @Override
    public String toString() {
        return "Historia{" +
                "id=" + id +
                ", contenido='" + contenido + '\'' +
                '}';
    }
}

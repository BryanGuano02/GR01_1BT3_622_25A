package entidades;

import jakarta.persistence.*;

@Entity
@Table(name = "votos_historia", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"comensal_id", "restaurante_id", "historia"})
})
public class VotoHistoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // El comensal que vota
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "comensal_id", nullable = false)
    private Comensal comensal;

    // El restaurante dueño de la historia
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurante_id", nullable = false)
    private Restaurante restaurante;

    // El nombre o identificador de la historia (menú)
    @Column(name = "historia", nullable = false)
    private String historia;

    // true = like, false = dislike
    @Column(name = "like", nullable = false)
    private boolean like;

    // Getters y setters

    public Long getId() {
        return id;
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

    public String getHistoria() {
        return historia;
    }

    public void setHistoria(String historia) {
        this.historia = historia;
    }

    public boolean isLike() {
        return like;
    }

    public void setLike(boolean like) {
        this.like = like;
    }
}

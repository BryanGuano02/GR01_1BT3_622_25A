package entidades;

import jakarta.persistence.*;

@Entity
@Table(name = "votaciones_historia", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"restaurante_id", "historia"})
})
public class VotacionHistoria {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurante_id", nullable = false)
    private Restaurante restaurante;

    @Column(name = "historia", nullable = false)
    private String historia;

    @Column(name = "cerrada", nullable = false)
    private boolean cerrada = false;

    // Getters y setters...
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
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
    public boolean isCerrada() {
        return cerrada;
    }
    public void setCerrada(boolean cerrada) {
        this.cerrada = cerrada;
    }
    
}
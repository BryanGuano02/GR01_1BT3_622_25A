package entidades;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.*;
import jakarta.persistence.Table;
import java.util.List;

@Entity
@Table(name = "usuarios_restaurante")
public class UsuarioRestaurante extends Usuario {
    @OneToMany(mappedBy = "usuarioRestaurante", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Restaurante> restaurantes;

    public List<Restaurante> getRestaurantes() { return restaurantes; }
    public void setRestaurantes(List<Restaurante> restaurantes) { this.restaurantes = restaurantes; }
}
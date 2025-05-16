package servicios;

import entidades.Comensal;
import entidades.Restaurante;
import DAO.UsuarioDAO;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class RecomendacionService {
    private final UsuarioDAO usuarioDAO;

    public RecomendacionService(UsuarioDAO usuarioDAO) {
        this.usuarioDAO = usuarioDAO;
    }

    public List<Restaurante> obtenerRecomendaciones(Comensal comensal) {
        if (comensal == null || comensal.getTipoComidaFavorita() == null) {
            return Collections.emptyList();
        }

        List<Restaurante> todosRestaurantes = usuarioDAO.obtenerTodosRestaurantes();

        return todosRestaurantes.stream()
                .filter(r -> r.getTipoComida() != null &&
                        r.getTipoComida().equalsIgnoreCase(comensal.getTipoComidaFavorita()))
                .sorted(Comparator.comparingDouble(Restaurante::getPuntajePromedio).reversed())
                .collect(Collectors.toList());
    }
}
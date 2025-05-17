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
        // Validación más robusta
        if (comensal == null || comensal.getTipoComidaFavorita() == null
                || comensal.getTipoComidaFavorita().trim().isEmpty()) {
            System.out.println("DEBUG - No se pueden generar recomendaciones: comensal o tipo comida favorita inválido");
            return Collections.emptyList();
        }

        String tipoBuscado = comensal.getTipoComidaFavorita().trim().toLowerCase();
        System.out.println("DEBUG - Buscando restaurantes de tipo: " + tipoBuscado);

        return usuarioDAO.obtenerTodosRestaurantes().stream()
                .filter(r -> r.getTipoComida() != null && !r.getTipoComida().trim().isEmpty())
                .peek(r -> System.out.println("DEBUG - Evaluando restaurante: " + r.getNombre()
                        + " (" + r.getTipoComida() + ")"))
                .filter(r -> r.getTipoComida().trim().toLowerCase().equals(tipoBuscado))
                .sorted(Comparator.comparingDouble(Restaurante::getPuntajePromedio).reversed())
                .peek(r -> System.out.println("DEBUG - Restaurante seleccionado: " + r.getNombre()
                        + " con puntaje " + r.getPuntajePromedio()))
                .collect(Collectors.toList());
    }
}
package servicios;

import DAO.CalificacionDAO;
import entidades.Comensal;
import entidades.Restaurante;
import DAO.UsuarioDAO;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class RecomendacionService {
    private final UsuarioDAO usuarioDAO;
    private final CalificacionDAO calificacionDAO;

    public RecomendacionService(UsuarioDAO usuarioDAO, CalificacionDAO calificacionDAO) {
        this.usuarioDAO = usuarioDAO;
        this.calificacionDAO = calificacionDAO;
    }

    public List<Restaurante> obtenerRecomendaciones(Comensal comensal) {
        if (!esComensalValido(comensal)) {
            return Collections.emptyList();
        }

        List<Restaurante> todosRestaurantes = usuarioDAO.obtenerTodosRestaurantes();

        // Calcular y asignar promedios actualizados
        todosRestaurantes.forEach(r -> {
            Double promedio = calificacionDAO.calcularPromedioCalificaciones(r.getId());
            r.setPuntajePromedio(promedio != null ? promedio : 0.0);
        });

        String tipoBuscado = normalizarTipoComida(comensal.getTipoComidaFavorita());

        return todosRestaurantes.stream()
                .filter(r -> r.getPuntajePromedio() != null)
                .filter(this::tieneTipoComidaValido)
                .filter(r -> coincideTipoComida(r, tipoBuscado))
                .sorted(Comparator.comparingDouble(Restaurante::getPuntajePromedio).reversed())
                .collect(Collectors.toList());
    }

    private boolean esComensalValido(Comensal comensal) {
        return comensal != null &&
                comensal.getTipoComidaFavorita() != null &&
                !comensal.getTipoComidaFavorita().trim().isEmpty();
    }

    private String normalizarTipoComida(String tipoComida) {
        return tipoComida.trim().toLowerCase();
    }

    private List<Restaurante> filtrarYOrdenarRestaurantes(List<Restaurante> restaurantes, String tipoBuscado) {
        return restaurantes.stream()
                .filter(this::tieneTipoComidaValido)
                .filter(r -> coincideTipoComida(r, tipoBuscado))
                .sorted(comparadorPorPuntajeDescendente())
                .collect(Collectors.toList());
    }

    private boolean tieneTipoComidaValido(Restaurante restaurante) {
        return restaurante.getTipoComida() != null &&
                !restaurante.getTipoComida().trim().isEmpty();
    }

    private boolean coincideTipoComida(Restaurante restaurante, String tipoBuscado) {
        return normalizarTipoComida(restaurante.getTipoComida()).equals(tipoBuscado);
    }

    private Comparator<Restaurante> comparadorPorPuntajeDescendente() {
        return Comparator.comparingDouble(Restaurante::getPuntajePromedio).reversed();
    }
}
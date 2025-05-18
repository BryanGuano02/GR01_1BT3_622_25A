package servicios;

import entidades.Comensal;
import entidades.Restaurante;

import java.util.*;

public class VotacionHistoriaService {
    // Map<RestauranteId, Map<Historia, Map<ComensalId, Boolean>>>
    private final Map<Long, Map<String, Map<Long, Boolean>>> votos = new HashMap<>();
    private final Map<Long, Set<String>> votacionesTerminadas = new HashMap<>();

    public boolean votar(Restaurante restaurante, String historia, Comensal comensal, boolean like) {
        if (isVotacionTerminada(restaurante, historia)) {
            return false; // No se puede votar si la votación ha terminado
        }
        votos.putIfAbsent(restaurante.getId(), new HashMap<>());
        Map<String, Map<Long, Boolean>> historias = votos.get(restaurante.getId());
        historias.putIfAbsent(historia, new HashMap<>());
        Map<Long, Boolean> votosHistoria = historias.get(historia);
        if (votosHistoria.containsKey(comensal.getId())) {
            return false; // Ya votó este comensal por esta historia
        }
        votosHistoria.put(comensal.getId(), like);
        return true;
    }

    public int getLikes(Restaurante restaurante, String historia) {
        return (int) votos.getOrDefault(restaurante.getId(), Collections.emptyMap())
                .getOrDefault(historia, Collections.emptyMap())
                .values().stream().filter(v -> v).count();
    }

    public int getDislikes(Restaurante restaurante, String historia) {
        return (int) votos.getOrDefault(restaurante.getId(), Collections.emptyMap())
                .getOrDefault(historia, Collections.emptyMap())
                .values().stream().filter(v -> !v).count();
    }

    public void terminarVotacion(Restaurante restaurante, String historia) {
        votacionesTerminadas.putIfAbsent(restaurante.getId(), new HashSet<>());
        votacionesTerminadas.get(restaurante.getId()).add(historia);
    }

    public boolean isVotacionTerminada(Restaurante restaurante, String historia) {
        return votacionesTerminadas.getOrDefault(restaurante.getId(), Collections.emptySet()).contains(historia);
    }

    public CalificacionResumen obtenerResumen(Restaurante restaurante, String historia) {
        return new CalificacionResumen(getLikes(restaurante, historia), getDislikes(restaurante, historia));
    }
}

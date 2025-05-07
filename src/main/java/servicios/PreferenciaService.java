package servicios;

import DAO.CalificacionDAO;
import DAO.ComensalDAO;
import DAO.RestauranteDAO;
import entidades.Calificacion;
import entidades.Comensal;
import entidades.Preferencia;
import entidades.Restaurante;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class PreferenciaService {
    private final CalificacionDAO calificacionDAO;
    private final RestauranteDAO restauranteDAO;
    private final ComensalDAO comensalDAO;

    public PreferenciaService() {
        this.calificacionDAO = new CalificacionDAO();
        this.restauranteDAO = new RestauranteDAO();
        this.comensalDAO = new ComensalDAO();
    }

    public List<Restaurante> aplicarPreferencia(Map<String, Object> parametrosPreferencia) {

        String tipoComida = (String) parametrosPreferencia.get("tipoComida");
        LocalTime horaApertura = (LocalTime) parametrosPreferencia.get("horaApertura");
        LocalTime horaCierre = (LocalTime) parametrosPreferencia.get("horaCierre");
        Double distancia = (Double) parametrosPreferencia.get("distancia");

        List<Restaurante> restaurantes = restauranteDAO.obtenerTodosLosRestaurantes();


        return restaurantes.stream()
                .filter(r -> r.getTipoComida().equalsIgnoreCase(tipoComida))
                .filter(r -> r.getHoraApertura().isBefore(horaApertura))
                .filter(r -> r.getHoraCierre().isAfter(horaCierre))
                .filter(r -> r.getDistanciaUniversidad() <= distancia)
                .sorted(Comparator.comparingDouble(this::calcularPuntajeRanking).reversed())
                .collect(Collectors.toList());

    }

    private double calcularPuntajeRanking(Restaurante restaurante) {

        return (1 / (1 + restaurante.getDistanciaUniversidad())) * 0.3 +
                restaurante.getPuntajePromedio() * 0.5;

    }

    public void crearPreferencia(String tipoComida, LocalTime horaApertura, LocalTime horaCierre,
                                 Double distanciaUniversidad, Long idComensal) {
        Comensal comensal = comensalDAO.obtenerComensalPorId(idComensal);
        if (comensal != null) {
            Preferencia preferencia = new Preferencia(tipoComida, horaApertura, horaCierre,
                    distanciaUniversidad, comensal);
            comensal.getPreferencias().add(preferencia);
        }
    }
}

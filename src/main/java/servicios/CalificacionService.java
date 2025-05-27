package servicios;

import DAO.CalificacionDAO;
import DAO.RestauranteDAO;
import DAO.UsuarioDAO;
import entidades.Calificacion;
import entidades.Comensal;
import entidades.Restaurante;
import entidades.VotoCalificacion;
import exceptions.ServiceException;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public class CalificacionService {
    private final CalificacionDAO calificacionDAO;
    private final UsuarioDAO usuarioDAO;
    private final RestauranteDAO restauranteDAO;
    private final EntityManagerFactory emf;

    public CalificacionService(CalificacionDAO calificacionDAO, UsuarioDAO usuarioDAO, RestauranteDAO restauranteDAO) {
        if (usuarioDAO == null && calificacionDAO == null && restauranteDAO == null) {
            this.emf = null;
        } else {
            this.emf = Persistence.createEntityManagerFactory("UFood_PU");
        }
        this.calificacionDAO = calificacionDAO;
        this.usuarioDAO = usuarioDAO;
        this.restauranteDAO = restauranteDAO;
    }

    public void crearCalificacion(Calificacion calificacion) throws ServiceException {
        calcularPuntajeCalificacion(calificacion);
        try {
            if (!calificacionDAO.crear(calificacion)) {
                throw new ServiceException("No se pudo crear la calificación");
            }
            actualizarPuntajePromedio(calificacion.getRestaurante());
        } catch (Exception e) {
            throw new ServiceException("Error al crear calificación: " + e.getMessage(), e);
        }
    }

    public List<Calificacion> obtenerCalificacionesPorRestaurante(Long restauranteId) {
        try {
            return calificacionDAO.obtenerCalificacionesPorRestaurante(restauranteId);
        } catch (Exception e) {
            throw new RuntimeException("Error al obtener calificaciones", e);
        }
    }

    public void calificar(Map<String, Object> parametrosCalificacion) throws ServiceException {
        try {
            // Double puntaje = (Double) parametrosCalificacion.get("puntaje");
            int calidadComida = (Integer) parametrosCalificacion.get("calidadComida");
            int calidadServicio = (Integer) parametrosCalificacion.get("calidadServicio");
            int limpieza = (Integer) parametrosCalificacion.get("limpieza");
            int ambiente = (Integer) parametrosCalificacion.get("ambiente");
            int tiempoEspera = (Integer) parametrosCalificacion.get("tiempoEspera");
            int relacionPrecioCalidad = (Integer) parametrosCalificacion.get("relacionPrecioCalidad");
            int variedadMenu = (Integer) parametrosCalificacion.get("variedadMenu");
            int accesibilidad = (Integer) parametrosCalificacion.get("accesibilidad");
            int volveria = (Integer) parametrosCalificacion.get("volveria");
            String comentario = (String) parametrosCalificacion.get("comentario");
            Long idComensal = (Long) parametrosCalificacion.get("idComensal");
            Long idRestaurante = (Long) parametrosCalificacion.get("idRestaurante");

            Comensal comensal = usuarioDAO.obtenerComensalPorId(idComensal);
            Restaurante restaurante = restauranteDAO.obtenerRestaurantePorId(idRestaurante);

            if (comensal == null || restaurante == null) {
                throw new ServiceException("Comensal o restaurante no encontrado");
            }

            // Buscar si ya existe una calificación previa
            Calificacion calificacionExistente = calificacionDAO.obtenerCalificacionPorComensalYRestaurante(idComensal,
                    idRestaurante);

            if (calificacionExistente != null) {
                // Actualizar la calificación existente
                // calificacionExistente.setPuntaje(puntaje);
                calificacionExistente.setCalidadComida(calidadComida);
                calificacionExistente.setCalidadServicio(calidadServicio);
                calificacionExistente.setLimpieza(limpieza);
                calificacionExistente.setAmbiente(ambiente);
                calificacionExistente.setTiempoEspera(tiempoEspera);
                calificacionExistente.setRelacionPrecioCalidad(relacionPrecioCalidad);
                calificacionExistente.setVariedadMenu(variedadMenu);
                calificacionExistente.setAccesibilidad(accesibilidad);
                calificacionExistente.setVolveria(volveria);
                calificacionExistente.setComentario(comentario);
                calcularPuntajeCalificacion(calificacionExistente);

                if (!calificacionDAO.actualizar(calificacionExistente)) {
                    throw new ServiceException("No se pudo actualizar la calificación existente");
                }
            } else {
                // Crear una nueva calificación
                Calificacion nuevaCalificacion = new Calificacion(comentario, comensal, restaurante, calidadComida,
                        calidadServicio, limpieza, ambiente, tiempoEspera, relacionPrecioCalidad, variedadMenu,
                        accesibilidad, volveria);
                // TODO Mandar por constructor
                // nuevaCalificacion.setPuntaje(puntaje);
                // nuevaCalificacion.setComentario(comentario);
                // nuevaCalificacion.setComensal(comensal);
                // nuevaCalificacion.setRestaurante(restaurante);
                crearCalificacion(nuevaCalificacion);
            }

            // Actualizar el promedio del restaurante
            actualizarPuntajePromedio(restaurante);
        } catch (Exception e) {
            throw new ServiceException("Error al procesar la calificación: " + e.getMessage(), e);
        }
    }

    public double calcularPuntajeCalificacion(Calificacion calificacion) {
        int suma = 0;
        int cantidad = 0;

        suma += calificacion.getCalidadComida();
        cantidad++;
        suma += calificacion.getCalidadServicio();
        cantidad++;
        suma += calificacion.getLimpieza();
        cantidad++;
        suma += calificacion.getAmbiente();
        cantidad++;
        suma += calificacion.getTiempoEspera();
        cantidad++;
        suma += calificacion.getRelacionPrecioCalidad();
        cantidad++;
        suma += calificacion.getVariedadMenu();
        cantidad++;
        suma += calificacion.getAccesibilidad();
        cantidad++;
        suma += calificacion.getVolveria();
        cantidad++;

        calificacion.setPuntaje((double) suma / cantidad);
        return (double) suma / cantidad;
    }

    private void actualizarPuntajePromedio(Restaurante restaurante) {
        try {
            Double nuevoPromedio = calificacionDAO.calcularPromedioCalificaciones(restaurante.getId());
            restaurante.setPuntajePromedio(nuevoPromedio);
            restauranteDAO.save(restaurante); // Usar RestauranteDAO para guardar
        } catch (Exception e) {
            throw new RuntimeException("Error al actualizar promedio", e);
        }
    }

    // 1. Move method (de Votacionservice a CalificacionService)
    public Boolean votarCalificacion(Comensal comensal, Calificacion calificacion) {
        List<VotoCalificacion> votos = calificacion.getVotos();
        // 3. Extract method
        Optional<VotoCalificacion> votoExistente = encontrarVotoExistente(votos, comensal);

        // 2. Replace Nested Conditional with Guard Clauses
        if (votoExistente.isPresent()) {
            votos.remove(votoExistente.get());
            return false;
        }

        // 3. Extract method
        agregarNuevoVoto(votos, comensal, calificacion);
        return true;
    }

    // 3. Extract method
    private Optional<VotoCalificacion> encontrarVotoExistente(List<VotoCalificacion> votos, Comensal comensal) {
        return votos.stream().filter(v -> v.getComensal().getId().equals(comensal.getId())).findFirst();
    }

    // 3. Extract method
    private void agregarNuevoVoto(List<VotoCalificacion> votos, Comensal comensal, Calificacion calificacion) {
        VotoCalificacion nuevoVoto = new VotoCalificacion();
        nuevoVoto.setCalificacion(calificacion);
        nuevoVoto.setComensal(comensal);
        votos.add(nuevoVoto);
    }
}

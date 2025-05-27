package servicios;

import DAO.CalificacionDAO;
import DAO.RestauranteDAO;
import DAO.UsuarioDAO;
import entidades.Calificacion;
import entidades.Comensal;
import entidades.Restaurante;
import exceptions.ServiceException;

import java.util.List;
import java.util.Map;

public class CalificacionService {
    private final CalificacionDAO calificacionDAO;
    private final UsuarioDAO usuarioDAO;
    private final RestauranteDAO restauranteDAO;

    public CalificacionService(CalificacionDAO calificacionDAO, UsuarioDAO usuarioDAO, RestauranteDAO restauranteDAO) {
        this.calificacionDAO = calificacionDAO;
        this.usuarioDAO = usuarioDAO;
        this.restauranteDAO = restauranteDAO;
    }

    public void crearCalificacion(Calificacion calificacion) throws ServiceException {
        try {
            if (!calificacionDAO.crear(calificacion)) {
                throw new ServiceException("No se pudo crear la calificación");
            }
            calcularPuntajeCalificacion(calificacion);
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
            // Extraer la calificación desde el mapa usando el método centralizado
            Calificacion calificacion = extraerParametrosCalificacion(parametrosCalificacion);

            // Obtener comensal y restaurante reales desde la base de datos
            Comensal comensal = usuarioDAO.obtenerComensalPorId(calificacion.getComensal().getId());
            Restaurante restaurante = restauranteDAO.obtenerRestaurantePorId(calificacion.getRestaurante().getId());

            if (comensal == null || restaurante == null) {
                throw new ServiceException("Comensal o restaurante no encontrado");
            }

            // Actualizar las referencias por las reales
            calificacion.setComensal(comensal);
            calificacion.setRestaurante(restaurante);

            // Buscar si ya existe una calificación previa
             Calificacion calificacionExistente = calificacionDAO.obtenerCalificacionPorComensalYRestaurante(
                     comensal.getId(), restaurante.getId());

            if (calificacionExistente != null) {
                // Actualizar la calificación existente con los nuevos valores
                calificacionExistente.setCalidadComida(calificacion.getCalidadComida());
                calificacionExistente.setCalidadServicio(calificacion.getCalidadServicio());
                calificacionExistente.setLimpieza(calificacion.getLimpieza());
                calificacionExistente.setAmbiente(calificacion.getAmbiente());
                calificacionExistente.setTiempoEspera(calificacion.getTiempoEspera());
                calificacionExistente.setRelacionPrecioCalidad(calificacion.getRelacionPrecioCalidad());
                calificacionExistente.setVariedadMenu(calificacion.getVariedadMenu());
                calificacionExistente.setAccesibilidad(calificacion.getAccesibilidad());
                calificacionExistente.setVolveria(calificacion.getVolveria());
                calificacionExistente.setComentario(calificacion.getComentario());
                calcularPuntajeCalificacion(calificacionExistente);

                if (!calificacionDAO.actualizar(calificacionExistente)) {
                    throw new ServiceException("No se pudo actualizar la calificación existente");
                }
            } else {
                // Crear una nueva calificación
                crearCalificacion(calificacion);
            }

            // Actualizar el promedio del restaurante
            actualizarPuntajePromedio(restaurante);
        } catch (Exception e) {
            throw new ServiceException("Error al procesar la calificación: " + e.getMessage(), e);
        }
    }

    private double calcularPuntajeCalificacion(Calificacion calificacion) {
        int suma = 0;
        int cantidad = 0;

        suma += calificacion.getCalidadComida(); cantidad++;
        suma += calificacion.getCalidadServicio(); cantidad++;
        suma += calificacion.getLimpieza(); cantidad++;
        suma += calificacion.getAmbiente(); cantidad++;
        suma += calificacion.getTiempoEspera(); cantidad++;
        suma += calificacion.getRelacionPrecioCalidad(); cantidad++;
        suma += calificacion.getVariedadMenu(); cantidad++;
        suma += calificacion.getAccesibilidad(); cantidad++;
        suma += calificacion.getVolveria(); cantidad++;

        calificacion.setPuntaje( (double) suma / cantidad );
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

        public Calificacion extraerParametrosCalificacion(Map<String, Object> parametros) {
            String comentario = (String) parametros.get("comentario");
            Long idComensal = (Long) parametros.get("idComensal");
            Long idRestaurante = (Long) parametros.get("idRestaurante");
            int calidadComida = (Integer) parametros.get("calidadComida");
            int calidadServicio = (Integer) parametros.get("calidadServicio");
            int limpieza = (Integer) parametros.get("limpieza");
            int ambiente = (Integer) parametros.get("ambiente");
            int tiempoEspera = (Integer) parametros.get("tiempoEspera");
            int relacionPrecioCalidad = (Integer) parametros.get("relacionPrecioCalidad");
            int variedadMenu = (Integer) parametros.get("variedadMenu");
            int accesibilidad = (Integer) parametros.get("accesibilidad");
            int volveria = (Integer) parametros.get("volveria");

            Comensal comensal = new Comensal();
            comensal.setId(idComensal);

            Restaurante restaurante = new Restaurante();
            restaurante.setId(idRestaurante);

            return new Calificacion(
                comentario,
                comensal,
                restaurante,
                calidadComida,
                calidadServicio,
                limpieza,
                ambiente,
                tiempoEspera,
                relacionPrecioCalidad,
                variedadMenu,
                accesibilidad,
                volveria
            );
        }

    
}
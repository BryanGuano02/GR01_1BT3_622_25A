package servlets;

import DAO.CalificacionDAO;
import DAO.RestauranteDAO;
import DAO.SuscripcionDAO;
import DAO.UsuarioDAO;
import DTO.RestauranteDTO;
import entidades.Calificacion;
import entidades.Comensal;
import entidades.Restaurante;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "SvDetalleRestaurante", value = "/detalleRestaurante")
public class SvDetalleRestaurante extends HttpServlet {
    private EntityManagerFactory emf;
    private RestauranteDAO restauranteDAO;
    private CalificacionDAO calificacionDAO;

    @Override
    public void init() {
        emf = Persistence.createEntityManagerFactory("UFood_PU");
        restauranteDAO = new RestauranteDAO(emf);
        
        // Implementar CalificacionDAO igual que en SvCalificacion
        calificacionDAO = new CalificacionDAO() {
            @Override
            public boolean crear(Calificacion calificacion) {
                jakarta.persistence.EntityManager em = emf.createEntityManager();
                try {
                    em.getTransaction().begin();
                    em.persist(calificacion);
                    em.getTransaction().commit();
                    return true;
                } catch (Exception e) {
                    if (em.getTransaction().isActive()) {
                        em.getTransaction().rollback();
                    }
                    e.printStackTrace();
                    return false;
                } finally {
                    em.close();
                }
            }

            @Override
            public List<Calificacion> obtenerCalificacionesPorRestaurante(Long idRestaurante) {
                jakarta.persistence.EntityManager em = emf.createEntityManager();
                try {
                    return em.createQuery(
                                    "SELECT c FROM Calificacion c WHERE c.restaurante.id = :idRestaurante ORDER BY c.fechaCreacion DESC",
                                    Calificacion.class)
                            .setParameter("idRestaurante", idRestaurante)
                            .getResultList();
                } finally {
                    em.close();
                }
            }

            @Override
            public Double calcularPromedioCalificaciones(Long idRestaurante) {
                jakarta.persistence.EntityManager em = emf.createEntityManager();
                try {
                    return em.createQuery(
                                    "SELECT AVG(c.puntaje) FROM Calificacion c WHERE c.restaurante.id = :idRestaurante",
                                    Double.class)
                            .setParameter("idRestaurante", idRestaurante)
                            .getSingleResult();
                } catch (Exception e) {
                    return 0.0;
                } finally {
                    em.close();
                }
            }
            
            @Override
            public Calificacion obtenerCalificacionPorComensalYRestaurante(Long idComensal, Long idRestaurante) {
                EntityManager em = emf.createEntityManager();
                try {
                    List<Calificacion> resultados = em.createQuery(
                            "SELECT c FROM Calificacion c WHERE c.comensal.id = :idComensal AND c.restaurante.id = :idRestaurante",
                            Calificacion.class)
                            .setParameter("idComensal", idComensal)
                            .setParameter("idRestaurante", idRestaurante)
                            .getResultList();
                    return resultados.isEmpty() ? null : resultados.get(0);
                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                } finally {
                    em.close();
                }
            }
        };
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            // Obtener el ID del restaurante de la URL
            String idRestauranteStr = req.getParameter("id");
            if (idRestauranteStr == null || idRestauranteStr.isEmpty()) {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "ID de restaurante no especificado");
                return;
            }

            Long idRestaurante = Long.parseLong(idRestauranteStr);
            Restaurante restaurante = restauranteDAO.obtenerRestaurantePorId(idRestaurante);

            if (restaurante == null) {
                resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Restaurante no encontrado");
                return;
            }

            // Verificar si el usuario está suscrito
            HttpSession session = req.getSession(false);
            boolean estaSuscrito = false;

            if (session != null && session.getAttribute("usuario") instanceof Comensal) {
                Comensal comensal = (Comensal) session.getAttribute("usuario");
                SuscripcionDAO suscripcionDAO = new SuscripcionDAO();
                estaSuscrito = suscripcionDAO.existeSuscripcion(comensal.getId(), restaurante.getId());
                
                // Crear DTO con información de suscripción
                RestauranteDTO restauranteDTO = new RestauranteDTO(restaurante, estaSuscrito);
                req.setAttribute("restaurante", restauranteDTO);
            } else {
                // Si no hay usuario logueado, usar objeto Restaurante directamente
                req.setAttribute("restaurante", restaurante);
            }
            
            // Obtener calificaciones del restaurante
            List<Calificacion> calificaciones = calificacionDAO.obtenerCalificacionesPorRestauranteOrdenadoPorVotos(idRestaurante);
            req.setAttribute("calificaciones", calificaciones);

            // Redirigir a la página de detalle
            req.getRequestDispatcher("/detalleRestaurante.jsp").forward(req, resp);

        } catch (NumberFormatException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "ID de restaurante inválido");
        } catch (Exception e) {
            e.printStackTrace();
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error al procesar la solicitud");
        }
    }

    @Override
    public void destroy() {
        if (emf != null && emf.isOpen()) {
            emf.close();
        }
    }
}

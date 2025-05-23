package servlets;

import DAO.RestauranteDAO;
import DAO.UsuarioDAOImpl;
import entidades.Restaurante;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.NoResultException;
import jakarta.persistence.Persistence;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import servicios.RestauranteService;

import java.io.IOException;

@WebServlet("/detallesRestaurante")
public class SvDetallesRestaurante extends HttpServlet {
    private EntityManagerFactory emf;
    private RestauranteService restauranteService;

    @Override
    public void init() {
        emf = Persistence.createEntityManagerFactory("UFood_PU");
        restauranteService = new RestauranteService(
                new UsuarioDAOImpl(emf),
                new RestauranteDAO(emf.createEntityManager())
        );
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        EntityManager em = null;
        try {
            String idParam = request.getParameter("id");
            System.out.println("ID recibido: " + idParam); // Log para depuración

            if (idParam == null || idParam.trim().isEmpty()) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "ID de restaurante requerido");
                return;
            }

            long id = Long.parseLong(idParam.trim());
            em = emf.createEntityManager();

            Restaurante restaurante = em.createQuery(
                            "SELECT r FROM Restaurante r LEFT JOIN FETCH r.menuDelDia WHERE r.id = :id",
                            Restaurante.class)
                    .setParameter("id", id)
                    .getSingleResult();

            System.out.println("Restaurante encontrado: " + (restaurante != null)); // Log para depuración

            request.setAttribute("restaurante", restaurante);

            // Forzar no cache para desarrollo
            response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
            response.setHeader("Pragma", "no-cache");
            response.setHeader("Expires", "0");

            request.getRequestDispatcher("/WEB-INF/fragmentos/detallesRestauranteModal.jsp").forward(request, response);

        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "ID debe ser numérico");
        } catch (NoResultException e) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Restaurante no encontrado");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error interno: " + e.getMessage());
        } finally {
            if (em != null) em.close();
        }
    }

    @Override
    public void destroy() {
        if (emf != null && emf.isOpen()) {
            emf.close();
        }
    }
}
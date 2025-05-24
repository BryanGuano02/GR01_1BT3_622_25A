package servlets;

import DAO.RestauranteDAO;
import DAO.SuscripcionDAO;
import DAO.UsuarioDAO;
import DTO.RestauranteDTO;
import entidades.Restaurante;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import servicios.SuscripcionService;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet(name = "suscribirse", value = "/suscribirse")
public class SvSuscripcion extends HttpServlet {
    private EntityManagerFactory emf;
    private UsuarioDAO usuarioDAO;
    private RestauranteDAO restauranteDAO;
    private SuscripcionService suscripcionService;
    private SuscripcionDAO suscripcionDAO;

    @Override
    public void init() {
        emf = Persistence.createEntityManagerFactory("UFood_PU");
        usuarioDAO = new UsuarioDAO(emf);
        restauranteDAO = new RestauranteDAO(emf);
        suscripcionDAO = new SuscripcionDAO();
        suscripcionService = new SuscripcionService(usuarioDAO, restauranteDAO, suscripcionDAO);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String action = req.getParameter("action");

        if ("desuscribir".equals(action)) {
            Long idComensal = Long.parseLong(req.getParameter("idComensal"));
            Long idRestaurante = Long.parseLong(req.getParameter("idRestaurante"));

            try {
                suscripcionService.desuscribir(idComensal, idRestaurante);
                req.getSession().setAttribute("mensaje", "Has cancelado tu suscripción correctamente");
                resp.sendRedirect(req.getContextPath() + "/inicio?success=desuscrito");
            } catch (Exception e) {
                req.getSession().setAttribute("error", "Error al cancelar suscripción: " + e.getMessage());
                resp.sendRedirect(req.getContextPath() + "/inicio?error=" +
                        URLEncoder.encode("Error al procesar la desuscripción: " + e.getMessage(), "UTF-8"));
            }
        } else {
            resp.sendRedirect(req.getContextPath() + "/inicio");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        Long idComensal = Long.parseLong(req.getParameter("idComensal"));
        Long idRestaurante = Long.parseLong(req.getParameter("idRestaurante"));

        try {
            suscripcionService.suscribir(idComensal, idRestaurante);
            resp.sendRedirect(req.getContextPath() + "/inicio?success=suscrito");
        } catch (Exception e) {
            req.getSession().setAttribute("error", "Error al suscribirte: " + e.getMessage());
            resp.sendRedirect(req.getContextPath() + "/inicio?error=" +
                    URLEncoder.encode("Error al procesar la suscripción: " + e.getMessage(), "UTF-8"));
        }
    }

    private Long obtenerIdComensal(HttpServletRequest req) {
        String idComensalString = req.getParameter("idComensal");
        if (idComensalString == null || idComensalString.isEmpty()) {
            return null;
        }
        try {
            return Long.parseLong(idComensalString);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    public List<RestauranteDTO> getRestaurantesConSuscripcion(Long comensalId) {
        List<Restaurante> restaurantes = restauranteDAO.obtenerTodosRestaurantes();
        return restaurantes.stream().map(restaurante -> {
            boolean estaSuscrito = suscripcionDAO.existeSuscripcion(comensalId, restaurante.getId());
            return new RestauranteDTO(restaurante, estaSuscrito);
        }).collect(Collectors.toList());
    }

    @Override
    public void destroy() {
        if (usuarioDAO != null)
            usuarioDAO.close();
        if (emf != null)
            emf.close();
    }
}
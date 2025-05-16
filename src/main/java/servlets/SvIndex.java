package servlets;

import DAO.CalificacionDAO;
import DAO.UsuarioDAO;
import DAO.UsuarioDAOImpl;
import entidades.Comensal;
import entidades.Restaurante;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import servicios.RecomendacionService;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "SvIndex", value = "/inicio")
public class SvIndex extends HttpServlet {
    private EntityManagerFactory emf;
    private UsuarioDAO usuarioDAO;
    private CalificacionDAO calificacionDAO;
    private RecomendacionService recomendacionService;

    @Override
    public void init() {
        emf = Persistence.createEntityManagerFactory("UFood_PU");
        usuarioDAO = new UsuarioDAOImpl(emf);
        calificacionDAO = new CalificacionDAO(emf);
        recomendacionService = new RecomendacionService(usuarioDAO);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        try {
            HttpSession session = req.getSession();
            Comensal comensal = (Comensal) session.getAttribute("usuario");

            String busqueda = req.getParameter("busqueda");
            boolean isAjax = "XMLHttpRequest".equals(req.getHeader("X-Requested-With"));

            List<Restaurante> restaurantes;
            if (busqueda != null && !busqueda.isEmpty()) {
                restaurantes = usuarioDAO.buscarRestaurantes(busqueda);
            } else {
                restaurantes = usuarioDAO.obtenerTodosRestaurantes();
            }

            // Calcular promedios (descomentar si se necesita)
            /*
            Map<Long, Double> promedios = new HashMap<>();
            for (Restaurante r : restaurantes) {
                Double promedio = calificacionDAO.calcularPromedioCalificaciones(r.getId());
                promedios.put(r.getId(), promedio);
                r.setPuntajePromedio(promedio);
            }
            req.setAttribute("promedios", promedios);
            */

            // Obtener recomendaciones solo si es comensal
            if (comensal != null && comensal.getTipoComidaFavorita() != null) {
                List<Restaurante> recomendados = recomendacionService.obtenerRecomendaciones(comensal);
                req.setAttribute("restaurantesSugeridos", recomendados);
            }

            req.setAttribute("restaurantes", restaurantes);
            req.getRequestDispatcher("/index.jsp").forward(req, resp);

        } catch (Exception e) {
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error al cargar restaurantes");
        }
    }

    @Override
    public void destroy() {
        if (emf != null && emf.isOpen()) {
            emf.close();
        }
    }
}
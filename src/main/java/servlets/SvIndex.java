package servlets;

import DAO.CalificacionDAO;
import DAO.RestauranteDAO;
import entidades.Calificacion;
import entidades.Restaurante;
import entidades.Usuario;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet(name = "SvIndex", value = "/inicio")
public class SvIndex extends HttpServlet {
    private EntityManagerFactory emf;
    private RestauranteDAO restauranteDAO;
    private CalificacionDAO calificacionDAO;

    @Override
    public void init() {
        emf = Persistence.createEntityManagerFactory("UFood_PU");
        restauranteDAO = new RestauranteDAO(emf);
        calificacionDAO = new CalificacionDAO(emf);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        try {
            String busqueda = req.getParameter("busqueda");
            boolean isAjax = "XMLHttpRequest".equals(req.getHeader("X-Requested-With"));

            List<Restaurante> restaurantes;
            if (busqueda != null && !busqueda.isEmpty()) {
                restaurantes = restauranteDAO.buscarRestaurantes(busqueda);
            } else {
                restaurantes = restauranteDAO.obtenerTodosLosRestaurantes();
            }

            // Calcular promedios
            Map<Long, Double> promedios = new HashMap<>();
            for (Restaurante r : restaurantes) {
                Double promedio = calificacionDAO.calcularPromedioCalificaciones(r.getId());
                promedios.put(r.getId(), promedio);
                r.setPuntajePromedio(promedio);
            }

            req.setAttribute("restaurantes", restaurantes);
            req.setAttribute("promedios", promedios);

            if (isAjax) {
                req.getRequestDispatcher("/index.jsp").forward(req, resp);
            } else {
                req.getRequestDispatcher("/index.jsp").forward(req, resp);
            }
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
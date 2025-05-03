package servlets;

import Servicios.CalificacionService;
import Servicios.RestauranteService;
import entidades.Restaurante;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name = "calificar", value = "/calificar")
public class SvCalificacion extends HttpServlet {
    private EntityManagerFactory emf;

    @Override
    public void init() {
        emf = Persistence.createEntityManagerFactory("UFood_PU");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long idRestaurante = obtenerIdRestaurante(req);

        RestauranteService restaurante = new RestauranteService();
        Restaurante restauranteAPresentar = restaurante.obtenerRestaurantePorId(idRestaurante);

        req.setAttribute("restaurante", restauranteAPresentar);
        req.getRequestDispatcher("calificarRestaurante.jsp").forward(req, resp);
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Map<String, Object> parametrosCalificacion = extraerParametrosCalificacion(req);

        CalificacionService calificacion = new CalificacionService();
        calificacion.calificar(parametrosCalificacion);

        resp.sendRedirect(req.getContextPath() + "/inicio?success=true");
    }

    private Long obtenerIdRestaurante(HttpServletRequest req) {
        String idRestauranteString = req.getParameter("idRestaurante");
        if (idRestauranteString == null || idRestauranteString.isEmpty()) {
            return null;
        }
        try {
            return Long.parseLong(idRestauranteString);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private Map<String, Object> extraerParametrosCalificacion(HttpServletRequest req) {
        Map<String, Object> parametrosCalificacion = new HashMap<>();
        parametrosCalificacion.put("puntaje", Double.parseDouble(req.getParameter("puntaje")));
        parametrosCalificacion.put("comentario", req.getParameter("comentario"));
        parametrosCalificacion.put("idComensal", Long.parseLong(req.getParameter("idComensal")));
        parametrosCalificacion.put("idRestaurante", Long.parseLong(req.getParameter("idRestaurante")));
        return parametrosCalificacion;
    }

    @Override
    public void destroy() {
        if (emf != null) {
            emf.close();
        }
    }
}

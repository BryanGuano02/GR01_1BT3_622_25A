package servlet;

import DAO.VotacionDAO;
import entidades.Calificacion;
import entidades.Comensal;
import entidades.Restaurante;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet("/votacion")
public class VotacionServlet extends HttpServlet {

    private VotacionDAO votacionDAO;

    @Override
    public void init() {
        votacionDAO = new VotacionDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("usuario") == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        Restaurante restaurante = (Restaurante) session.getAttribute("restaurante");
        if (restaurante == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        List<String> menuDelDia = restaurante.getHistorias();
        request.setAttribute("menuDelDia", menuDelDia);
        request.setAttribute("votacionActiva", restaurante.isVotacionActiva());

        request.getRequestDispatcher("votacion.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("usuario") == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        Comensal comensal = (Comensal) session.getAttribute("usuario");
        Restaurante restaurante = (Restaurante) session.getAttribute("restaurante");

        if (!restaurante.isVotacionActiva()) {
            request.setAttribute("error", "La votación está cerrada.");
            request.getRequestDispatcher("votacion.jsp").forward(request, response);
            return;
        }

        double puntaje = Double.parseDouble(request.getParameter("puntaje"));
        String comentario = request.getParameter("comentario");

        // Verificar si ya votó
        List<Calificacion> calificaciones = votacionDAO.obtenerCalificacionesPorComensal(comensal);
        boolean yaVoto = calificaciones.stream()
                .anyMatch(c -> c.getRestaurante().getId().equals(restaurante.getId()));

        if (yaVoto) {
            request.setAttribute("error", "Ya has votado para este restaurante.");
        } else {
            Calificacion calificacion = new Calificacion(puntaje, comentario, comensal, restaurante);
            votacionDAO.guardarCalificacion(calificacion);
        }

        response.sendRedirect("votacion");
    }
}

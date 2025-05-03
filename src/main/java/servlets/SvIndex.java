package servlets;

import entidades.Calificacion;
import entidades.Restaurante;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import DAO.CalificacionDAO;
import DAO.RestauranteDAO;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "/inicio", value = "/inicio")
public class SvIndex extends HttpServlet {
    private EntityManagerFactory emf;
    private RestauranteDAO restauranteDAO;
    private CalificacionDAO calificacionesDAO;

    @Override
    public void init() {
        emf = Persistence.createEntityManagerFactory("UFood_PU");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        EntityManager em = emf.createEntityManager();
        restauranteDAO = new RestauranteDAO();
        calificacionesDAO = new CalificacionDAO();

        List<Restaurante> restaurantes = restauranteDAO.obtenerTodosLosRestaurantes();
        List<Calificacion> calificacions = calificacionesDAO.obtenerTodosLosCalificaciones();

        req.setAttribute("restaurantes", restaurantes);
        req.setAttribute("calificaciones", calificacions);
        req.getRequestDispatcher("/index.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }
}

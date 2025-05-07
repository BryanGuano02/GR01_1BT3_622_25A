package servlets;

import DAO.CalificacionDAO;
import DAO.RestauranteDAO;
import entidades.Calificacion;
import entidades.Restaurante;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "SvIndex", value = "/inicio")
public class SvIndex extends HttpServlet {
    private EntityManagerFactory emf;
    private RestauranteDAO restauranteDAO;
    private CalificacionDAO calificacionDAO;

    @Override
    public void init() {
        emf = Persistence.createEntityManagerFactory("UFood_PU");
        restauranteDAO = new RestauranteDAO();
        calificacionDAO = new CalificacionDAO();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        // Inicializar listas vacías por defecto
        List<Restaurante> restaurantes = new ArrayList<>();
        List<Calificacion> calificaciones = new ArrayList<>();

        try {
            // Obtener datos de la base de datos
            restaurantes = restauranteDAO.obtenerTodosLosRestaurantes();
            calificaciones = calificacionDAO.obtenerTodosLosCalificaciones();

            // Si por alguna razón son null, mantener las listas vacías
            if (restaurantes == null) {
                restaurantes = new ArrayList<>();
            }
            if (calificaciones == null) {
                calificaciones = new ArrayList<>();
            }

        } catch (Exception e) {
            // Loggear el error pero continuar con listas vacías
            System.err.println("Error al obtener datos: " + e.getMessage());
        }

        // Establecer atributos con valores no nulos
        req.setAttribute("restaurantes", restaurantes);
        req.setAttribute("calificaciones", calificaciones);

        req.getRequestDispatcher("/index.jsp").forward(req, resp);
    }

    @Override
    public void destroy() {
        if (restauranteDAO != null) restauranteDAO.cerrar();
        if (calificacionDAO != null) calificacionDAO.cerrar();
        if (emf != null) emf.close();
    }
}
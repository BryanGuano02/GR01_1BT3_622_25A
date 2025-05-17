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
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

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
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            HttpSession session = req.getSession(false);
            if (session != null) {
                Comensal comensal = (Comensal) session.getAttribute("usuario");

                if (comensal != null && comensal.getTipoComidaFavorita() == null) {
                    // Cargar tipo de comida favorita si no está en sesión
                    Comensal comensalBD = usuarioDAO.obtenerComensalPorId(comensal.getId());
                    comensal.setTipoComidaFavorita(comensalBD.getTipoComidaFavorita());
                    session.setAttribute("usuario", comensal); // Actualizar sesión
                }

                // 1. Obtener todos los restaurantes
                List<Restaurante> restaurantes = usuarioDAO.obtenerTodosRestaurantes();

                // 2. Calcular promedios
                for (Restaurante r : restaurantes) {
                    Double promedio = calificacionDAO.calcularPromedioCalificaciones(r.getId());
                    r.setPuntajePromedio(promedio != null ? promedio : 0.0);
                }

                // 3. Cargar recomendados SIEMPRE
                if (comensal != null) {
                    List<Restaurante> recomendados = recomendacionService.obtenerRecomendaciones(comensal);

                    // Debug crucial
                    System.out.println("DEBUG - Usuario: " + comensal.getNombreUsuario());
                    System.out.println("DEBUG - Tipo comida: " + comensal.getTipoComidaFavorita());
                    System.out.println("DEBUG - Restaurantes recomendados: " + recomendados.size());

                    req.setAttribute("restaurantesRecomendados", recomendados);
                }

                req.setAttribute("restaurantes", restaurantes);
            }

            // Importante: deshabilitar caché
            resp.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
            resp.setHeader("Pragma", "no-cache");
            resp.setDateHeader("Expires", 0);

            req.getRequestDispatcher("/index.jsp").forward(req, resp);

        } catch (Exception e) {
            e.printStackTrace();
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
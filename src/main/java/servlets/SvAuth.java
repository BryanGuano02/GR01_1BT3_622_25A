package servlets;

import DAO.CalificacionDAO;
import DAO.UsuarioDAOImpl;
import entidades.Comensal;
import entidades.Restaurante;
import entidades.Usuario;
import exceptions.ServiceException;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import servicios.AuthServiceImpl;
import servicios.RecomendacionService;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "SvAuth", urlPatterns = {"/login", "/registro-restaurante", "/registro-comensal"})
public class SvAuth extends HttpServlet {
    private AuthServiceImpl authService;
    private static EntityManagerFactory emf;
    private RecomendacionService recomendacionService;
    private CalificacionDAO calificacionDAO;

    @Override
    public void init() throws ServletException {
        try {
            if (emf == null) {
                emf = Persistence.createEntityManagerFactory("UFood_PU");
            }
            UsuarioDAOImpl usuarioDAO = new UsuarioDAOImpl(emf);
            this.authService = new AuthServiceImpl(usuarioDAO);
            this.recomendacionService = new RecomendacionService(usuarioDAO);
            this.calificacionDAO = new CalificacionDAO(emf);
        } catch (Exception e) {
            throw new ServletException("Error al inicializar JPA", e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String path = request.getServletPath();

        if ("/login".equals(path)) {
            handleLogin(request, response);
        } else if ("/registro-restaurante".equals(path)) {
            handleRestauranteRegistration(request, response);
        } else if ("/registro-comensal".equals(path)) {
            handleComensalRegistration(request, response);
        }
    }

    private void handleLogin(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String nombreUsuario = request.getParameter("nombreUsuario");
        String contrasena = request.getParameter("contrasena");

        try {
            Usuario usuario = authService.login(nombreUsuario, contrasena);
            HttpSession session = request.getSession();
            session.setAttribute("usuario", usuario);

            if (usuario instanceof Comensal) {
                handleComensalLogin((Comensal) usuario, session, request, response);
            } else if ("RESTAURANTE".equals(usuario.getTipoUsuario())) {
                response.sendRedirect("crearRestaurante.jsp");
            }
        } catch (ServiceException e) {
            request.setAttribute("error", e.getMessage());
            request.getRequestDispatcher("/login.jsp").forward(request, response);
        }
    }

    private void handleComensalLogin(Comensal comensal, HttpSession session,
                                     HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        // 1. Cargar notificaciones
        session.setAttribute("notificaciones", comensal.getNotificaciones());

        // 2. Verificar tipoComidaFavorita
        if (comensal.getTipoComidaFavorita() == null || comensal.getTipoComidaFavorita().trim().isEmpty()) {
            System.out.println("DEBUG: Comensal sin tipoComidaFavorita configurado");
        }

        // 3. Obtener todos los restaurantes con sus puntajes
        List<Restaurante> restaurantes = authService.obtenerTodosRestaurantes();
        for (Restaurante r : restaurantes) {
            Double promedio = calificacionDAO.calcularPromedioCalificaciones(r.getId());
            r.setPuntajePromedio(promedio != null ? promedio : 0.0);
        }

        // 4. Obtener recomendaciones
        List<Restaurante> recomendados = recomendacionService.obtenerRecomendaciones(comensal);
        System.out.println("DEBUG: Número de restaurantes recomendados: " + recomendados.size());

        // 5. Guardar en sesión
        session.setAttribute("restaurantes", restaurantes);
        session.setAttribute("restaurantesRecomendados", recomendados);

        // 6. Redirigir a SvIndex para manejo consistente
        response.sendRedirect("inicio");
    }

    private void handleRestauranteRegistration(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Restaurante restaurante = new Restaurante(
                request.getParameter("nombreUsuario"),
                request.getParameter("contrasena"),
                request.getParameter("email"),
                request.getParameter("nombreComercial"),
                request.getParameter("tipoComida")
        );

        try {
            authService.registrarUsuarioRestaurante(restaurante);
            response.sendRedirect("login.jsp?registroExitoso=true");
        } catch (ServiceException e) {
            request.setAttribute("error", e.getMessage());
            request.getRequestDispatcher("/registro-restaurante.jsp").forward(request, response);
        }
    }

    private void handleComensalRegistration(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            Comensal comensal = new Comensal();
            comensal.setNombreUsuario(request.getParameter("nombreUsuario"));
            comensal.setContrasena(request.getParameter("contrasena"));
            comensal.setEmail(request.getParameter("email"));

            // Asegurar que el tipoComidaFavorita tenga un valor
            String tipoComidaFavorita = request.getParameter("tipoComidaFavorita");
            if (tipoComidaFavorita == null || tipoComidaFavorita.trim().isEmpty()) {
                tipoComidaFavorita = "General"; // Valor por defecto
            }

            authService.registrarComensal(comensal, tipoComidaFavorita);
            response.sendRedirect("login.jsp?registroExitoso=true");
        } catch (ServiceException e) {
            request.setAttribute("error", e.getMessage());
            request.getRequestDispatcher("/registro.jsp").forward(request, response);
        }
    }

    @Override
    public void destroy() {
        if (emf != null && emf.isOpen()) {
            emf.close();
        }
    }
}
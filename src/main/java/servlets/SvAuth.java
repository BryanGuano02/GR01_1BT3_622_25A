package servlets;

import DAO.UsuarioDAOImpl;
import entidades.Comensal;
import entidades.Restaurante;
import entidades.Usuario;  // Importación añadida
import exceptions.ServiceException;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import servicios.AuthServiceImpl;

import java.io.IOException;

@WebServlet(name = "SvAuth", urlPatterns = {"/login", "/registro-restaurante", "/registro-comensal"})
public class SvAuth extends HttpServlet {
    private AuthServiceImpl authService;
    private static EntityManagerFactory emf;

    @Override
    public void init() throws ServletException {
        try {
            if (emf == null) {
                emf = Persistence.createEntityManagerFactory("UFood_PU");
            }
            this.authService = new AuthServiceImpl(new UsuarioDAOImpl(emf));
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
            String nombreUsuario = request.getParameter("nombreUsuario");
            String contrasena = request.getParameter("contrasena");

            try {
                Usuario usuario = authService.login(nombreUsuario, contrasena);
                HttpSession session = request.getSession();
                session.setAttribute("usuario", usuario);

                if ("RESTAURANTE".equals(usuario.getTipoUsuario())) {
                    response.sendRedirect("crearRestaurante.jsp");
                } else {
                    response.sendRedirect("index.jsp");
                }
            } catch (ServiceException e) {
                request.setAttribute("error", e.getMessage());
                request.getRequestDispatcher("/login.jsp").forward(request, response);
            }
        }
        else if ("/registro-restaurante".equals(path)) {
            Restaurante restaurante = new Restaurante(
                    request.getParameter("nombreUsuario"),
                    request.getParameter("contrasena"),
                    request.getParameter("email"),
                    request.getParameter("nombreComercial"),
                    request.getParameter("tipoComida")
            );

            try {
                authService.registrarUsuarioRestaurante(restaurante);  // Método actualizado
                response.sendRedirect("login.jsp?registroExitoso=true");
            } catch (ServiceException e) {
                request.setAttribute("error", e.getMessage());
                request.getRequestDispatcher("/registro-restaurante.jsp").forward(request, response);
            }
        }
        else if ("/registro-comensal".equals(path)) {
            try {
                Comensal comensal = new Comensal();
                comensal.setNombreUsuario(request.getParameter("nombreUsuario"));
                comensal.setContrasena(request.getParameter("contrasena"));
                comensal.setEmail(request.getParameter("email"));

                authService.registrarComensal(comensal);
                response.sendRedirect("login.jsp?registroExitoso=true");
            } catch (ServiceException e) {
                request.setAttribute("error", e.getMessage());
                request.getRequestDispatcher("/registro.jsp").forward(request, response);
            }
        }
    }
}
package servlets;

import DAO.UsuarioDAOImpl;
import entidades.Usuario;
import exceptions.ServiceException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import servicios.AuthServiceImpl;

import java.io.IOException;

@WebServlet(name = "SvAuth", urlPatterns = {"/login", "/registro"})
public class SvAuth extends HttpServlet {
    private AuthServiceImpl authService;

    @Override
    public void init() throws ServletException {
        super.init();
        this.authService = new AuthServiceImpl(new UsuarioDAOImpl());
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/login.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = request.getServletPath();

        if ("/login".equals(path)) {
            String nombreUsuario = request.getParameter("nombreUsuario");
            String contrasena = request.getParameter("contrasena");

            try {
                Usuario usuario = authService.login(nombreUsuario, contrasena);
                HttpSession session = request.getSession();
                session.setAttribute("usuario", usuario);

                if ("RESTAURANTE".equals(usuario.getTipoUsuario())) {
                    response.sendRedirect(request.getContextPath() + "/restaurante");
                } else {
                    response.sendRedirect(request.getContextPath() + "/index.jsp");
                }
            } catch (ServiceException e) {
                request.setAttribute("error", e.getMessage());
                request.getRequestDispatcher("/login.jsp").forward(request, response);
            }
        } else if ("/registro".equals(path)) {
            String tipoUsuario = request.getParameter("tipoUsuario");
            String nombreUsuario = request.getParameter("nombreUsuario");
            String contrasena = request.getParameter("contrasena");
            String email = request.getParameter("email");

            Usuario usuario = new Usuario();
            usuario.setNombreUsuario(nombreUsuario);
            usuario.setContrasena(contrasena);
            usuario.setEmail(email);

            try {
                if ("RESTAURANTE".equals(tipoUsuario)) {
                    authService.registrarUsuarioRestaurante(usuario);
                } else {
                    authService.registrarComensal(usuario);
                }

                response.sendRedirect(request.getContextPath() + "/login.jsp?registroExitoso=true");
            } catch (ServiceException e) {
                request.setAttribute("error", e.getMessage());
                request.getRequestDispatcher("/registro.jsp").forward(request, response);
            }
        }
    }
}
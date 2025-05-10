package servlets;

import DAO.UsuarioDAO;
import DAO.UsuarioDAOImpl;
import entidades.Restaurante;
import entidades.Usuario;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.IOException;
import java.time.LocalTime;
import java.util.List;

@WebServlet(name = "SvRestaurante", value = "/restaurante")
public class SvRestaurante extends HttpServlet {
    private EntityManagerFactory emf;
    private UsuarioDAO usuarioDAO;

    @Override
    public void init() {
        emf = Persistence.createEntityManagerFactory("UFood_PU");
        usuarioDAO = new UsuarioDAOImpl(emf);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        HttpSession session = req.getSession(false);

        // Validar sesión
        if (session == null || session.getAttribute("usuario") == null) {
            resp.sendRedirect(req.getContextPath() + "/login.jsp");
            return;
        }

        Usuario usuario = (Usuario) session.getAttribute("usuario");

        // Redirigir según tipo de usuario
        if ("RESTAURANTE".equals(usuario.getTipoUsuario())) {
            mostrarPanelRestaurante(req, resp, (Restaurante) usuario);
        } else {
            resp.sendRedirect(req.getContextPath() + "/inicio");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        HttpSession session = req.getSession(false);

        if (session == null || session.getAttribute("usuario") == null ||
                !"RESTAURANTE".equals(((Usuario)session.getAttribute("usuario")).getTipoUsuario())) {
            resp.sendRedirect(req.getContextPath() + "/login.jsp");
            return;
        }

        Restaurante restauranteUsuario = (Restaurante) session.getAttribute("usuario");
        String accion = req.getParameter("accion");

        if ("guardar".equals(accion)) {
            procesarGuardarRestaurante(req, resp, restauranteUsuario);
        } else if ("agregarHistoria".equals(accion)) {
            procesarAgregarMenu(req, resp, restauranteUsuario);
        }
    }

    private void mostrarPanelRestaurante(HttpServletRequest req, HttpServletResponse resp,
                                         Restaurante restauranteUsuario) throws ServletException, IOException {
        try {
            // Obtener el restaurante del usuario actual
            Restaurante restaurante = (Restaurante) usuarioDAO.findById(restauranteUsuario.getId());

            req.setAttribute("restaurante", restaurante);
            req.getRequestDispatcher("crearRestaurante.jsp").forward(req, resp);
        } catch (Exception e) {
            req.setAttribute("error", "Error al cargar datos del restaurante: " + e.getMessage());
            req.getRequestDispatcher("crearRestaurante.jsp").forward(req, resp);
        }
    }

    private void procesarGuardarRestaurante(HttpServletRequest req, HttpServletResponse resp,
                                            Restaurante restauranteUsuario) throws IOException {
        try {
            // Actualizar los datos del restaurante (que es el usuario)
            restauranteUsuario.setNombre(req.getParameter("nombre"));
            restauranteUsuario.setDescripcion(req.getParameter("descripcion"));
            restauranteUsuario.setTipoComida(req.getParameter("tipoComida"));
            restauranteUsuario.setHoraApertura(LocalTime.parse(req.getParameter("horaApertura")));
            restauranteUsuario.setHoraCierre(LocalTime.parse(req.getParameter("horaCierre")));

            usuarioDAO.save(restauranteUsuario);
            resp.sendRedirect(req.getContextPath() + "/restaurante?success=Restaurante actualizado exitosamente");
        } catch (Exception e) {
            resp.sendRedirect(req.getContextPath() + "/restaurante?error=Error al guardar: " + e.getMessage());
        }
    }


    private void procesarAgregarMenu(HttpServletRequest req, HttpServletResponse resp,
                                     Restaurante restauranteUsuario) throws IOException {
        try {
            String menu = req.getParameter("historia");

            if (menu == null || menu.trim().isEmpty()) {
                resp.sendRedirect(req.getContextPath() + "/restaurante?error=El menú no puede estar vacío");
                return;
            }

            // Obtener el restaurante actualizado
            Restaurante restaurante = (Restaurante) usuarioDAO.findById(restauranteUsuario.getId());
            restaurante.agregarHistoria(menu);

            usuarioDAO.save(restaurante);
            resp.sendRedirect(req.getContextPath() + "/restaurante?success=Menú agregado");
        } catch (Exception e) {
            resp.sendRedirect(req.getContextPath() + "/restaurante?error=" + e.getMessage());
        }
    }

    @Override
    public void destroy() {
        if (usuarioDAO != null) usuarioDAO.close();
        if (emf != null) emf.close();
    }
}
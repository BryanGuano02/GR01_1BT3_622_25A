package servlets;

import DAO.RestauranteDAO;
import entidades.Restaurante;
import entidades.Usuario;
import entidades.UsuarioRestaurante;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.time.LocalTime;
import java.util.List;

@WebServlet(name = "SvRestaurante", value = "/restaurante")
public class SvRestaurante extends HttpServlet {
    private EntityManagerFactory emf;
    private RestauranteDAO restauranteDAO;

    @Override
    public void init() {
        emf = Persistence.createEntityManagerFactory("UFood_PU");
        restauranteDAO = new RestauranteDAO();
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
            mostrarPanelRestaurante(req, resp, (UsuarioRestaurante) usuario);
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

        UsuarioRestaurante usuarioRestaurante = (UsuarioRestaurante) session.getAttribute("usuario");
        String accion = req.getParameter("accion");

        if ("guardar".equals(accion)) {
            procesarGuardarRestaurante(req, resp, usuarioRestaurante);
        } else if ("agregarHistoria".equals(accion)) {
            procesarAgregarMenu(req, resp, usuarioRestaurante);
        }
    }

    private void mostrarPanelRestaurante(HttpServletRequest req, HttpServletResponse resp,
                                         UsuarioRestaurante usuarioRestaurante) throws ServletException, IOException {

        EntityManager em = emf.createEntityManager();
        try {
            // Obtener solo los restaurantes del usuario actual
            List<Restaurante> restaurantes = em.createQuery(
                            "SELECT r FROM Restaurante r WHERE r.usuarioRestaurante.id = :userId",
                            Restaurante.class)
                    .setParameter("userId", usuarioRestaurante.getId())
                    .getResultList();

            req.setAttribute("restaurantes", restaurantes);
            req.getRequestDispatcher("/WEB-INF/restaurante/panelAdministrativo.jsp").forward(req, resp);
        } finally {
            em.close();
        }
    }

    private void procesarGuardarRestaurante(HttpServletRequest req, HttpServletResponse resp,
                                            UsuarioRestaurante usuarioRestaurante) throws IOException {
        try {
            Restaurante restaurante = new Restaurante();
            restaurante.setNombre(req.getParameter("nombre"));
            restaurante.setDescripcion(req.getParameter("descripcion"));
            restaurante.setTipoComida(req.getParameter("tipoComida"));
            restaurante.setHoraApertura(LocalTime.parse(req.getParameter("horaApertura")));
            restaurante.setHoraCierre(LocalTime.parse(req.getParameter("horaCierre")));
            restaurante.setUsuarioRestaurante(usuarioRestaurante);

            restauranteDAO.guardarRestaurante(restaurante);
            resp.sendRedirect(req.getContextPath() + "/restaurante?success=Restaurante creado exitosamente");
        } catch (Exception e) {
            resp.sendRedirect(req.getContextPath() + "/restaurante?error=Error al guardar: " + e.getMessage());
        }
    }

    private void procesarAgregarMenu(HttpServletRequest req, HttpServletResponse resp,
                                     UsuarioRestaurante usuarioRestaurante) throws IOException {
        EntityManager em = emf.createEntityManager();
        try {
            Long restauranteId = Long.parseLong(req.getParameter("restauranteId"));
            String menu = req.getParameter("historia");

            if (menu == null || menu.trim().isEmpty()) {
                resp.sendRedirect(req.getContextPath() + "/restaurante?error=El menú no puede estar vacío");
                return;
            }

            em.getTransaction().begin();
            Restaurante restaurante = em.find(Restaurante.class, restauranteId);

            // Verificar propiedad del restaurante
            if (restaurante != null && restaurante.getUsuarioRestaurante().getId().equals(usuarioRestaurante.getId())) {
                restaurante.agregarHistoria(menu);
                em.merge(restaurante);
                em.getTransaction().commit();
                resp.sendRedirect(req.getContextPath() + "/restaurante?success=Menú agregado");
            } else {
                em.getTransaction().rollback();
                resp.sendRedirect(req.getContextPath() + "/restaurante?error=No autorizado");
            }
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            resp.sendRedirect(req.getContextPath() + "/restaurante?error=" + e.getMessage());
        } finally {
            em.close();
        }
    }

    @Override
    public void destroy() {
        if (restauranteDAO != null) restauranteDAO.cerrar();
        if (emf != null) emf.close();
    }
}
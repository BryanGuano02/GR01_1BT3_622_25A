package servlets;

import DAO.RestauranteDAO;
import DAO.SuscripcionDAO;
import DAO.UsuarioDAO;
import DTO.RestauranteDTO;
import entidades.Comensal;
import entidades.DuenioRestaurante;
import entidades.Historia;
import entidades.Restaurante;
import entidades.Usuario;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import servicios.MenuDelDiaService;
import servicios.NotificacionService;

import java.io.IOException;
import java.net.URLEncoder;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet(name = "SvRestaurante", value = "/restaurante")
public class SvRestaurante extends HttpServlet {
    private EntityManagerFactory emf;
    private UsuarioDAO usuarioDAO = new UsuarioDAO(Persistence.createEntityManagerFactory("UFood_PU"));
    private RestauranteDAO restauranteDAO = new RestauranteDAO(Persistence.createEntityManagerFactory("UFood_PU"));

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
        if ("DUENO_RESTAURANTE".equals(usuario.getTipoUsuario())) {
            DuenioRestaurante duenio = (DuenioRestaurante) usuario;
            mostrarPanelRestaurante(req, resp, duenio.getRestaurante());
        } else {
            resp.sendRedirect(req.getContextPath() + "/inicio");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        HttpSession session = req.getSession(false);

        if (session == null || session.getAttribute("usuario") == null ||
                !"DUENO_RESTAURANTE".equals(((Usuario) session.getAttribute("usuario")).getTipoUsuario())) {
            resp.sendRedirect(req.getContextPath() + "/login.jsp");
            return;
        }

        DuenioRestaurante duenio = (DuenioRestaurante) session.getAttribute("usuario");
        Restaurante restauranteUsuario = duenio.getRestaurante();
        String accion = req.getParameter("accion");

        if ("guardar".equals(accion)) {
            procesarGuardarRestaurante(req, resp, restauranteUsuario);
        } else if ("agregarHistoria".equals(accion)) {
            Historia historia = new Historia(req.getParameter("historia"));
            procesarAgregarMenu(req, resp, restauranteUsuario, historia);
            NotificacionService notificacionService = new NotificacionService(usuarioDAO, null);
            notificacionService.notificarComensalesMenuDia(restauranteUsuario, historia);
        } else if ("actualizar".equals(accion)) {
            procesarActualizarRestaurante(req, resp, restauranteUsuario);
        } else if ("subscribirse".equals(accion)) {
            procesarSubscribirse(req, resp, restauranteUsuario);
        } else {
            resp.sendRedirect(req.getContextPath() + "/crearRestaurante.jsp");
        }

        if ("agregarMenuDelDia".equals(accion)) {
            String descripcionMenu = req.getParameter("historia");
            System.out.println(restauranteUsuario.getId());
            if (descripcionMenu != null && !descripcionMenu.trim().isEmpty()) {
                MenuDelDiaService menuDelDiaService = new MenuDelDiaService();
                Restaurante restaurante = menuDelDiaService.guardarMenuDelDia(descripcionMenu,
                        restauranteUsuario.getId());
                duenio.setRestaurante(restaurante);
                usuarioDAO.save(duenio);
                session.setAttribute("usuario", duenio);
            }
        }
    }

    private void procesarSubscribirse(HttpServletRequest req, HttpServletResponse resp,
                                      Restaurante restauranteUsuario) throws IOException {
        try {
            Long idComensal = Long.parseLong(req.getParameter("idComensal"));
            Comensal comensal = usuarioDAO.obtenerComensalPorId(idComensal);
            if (comensal == null) {
                resp.sendRedirect(req.getContextPath() + "/restaurantesFiltrados.jsp?error=Comensal+no+encontrado");
                return;
            }

            String idRestauranteStr = req.getParameter("idRestaurante");
            if (idRestauranteStr == null) {
                resp.sendRedirect(
                        req.getContextPath() + "/restaurantesFiltrados.jsp?error=Restaurante+no+especificado");
                return;
            }
            Long idRestaurante = Long.parseLong(idRestauranteStr);

            Restaurante restaurante = restauranteDAO.obtenerRestaurantePorId(idRestaurante);
            usuarioDAO.save(comensal);
            req.getSession().setAttribute("usuario", comensal);
        } catch (Exception e) {
            // Manejar error
        }
    }

    private void mostrarPanelRestaurante(HttpServletRequest req, HttpServletResponse resp,
                                         Restaurante restauranteUsuario) throws ServletException, IOException {
        try {
            String success = req.getParameter("success");
            String error = req.getParameter("error");

            if (success != null) {
                req.setAttribute("successMessage", success);
            }
            if (error != null) {
                req.setAttribute("errorMessage", error);
            }

            Restaurante restaurante = restauranteDAO.obtenerRestaurantePorId(restauranteUsuario.getId());
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
            restauranteUsuario.setNombre(req.getParameter("nombre"));
            restauranteUsuario.setDescripcion(req.getParameter("descripcion"));
            restauranteUsuario.setTipoComida(req.getParameter("tipoComida"));
            restauranteUsuario.setHoraApertura(LocalTime.parse(req.getParameter("horaApertura")));
            restauranteUsuario.setHoraCierre(LocalTime.parse(req.getParameter("horaCierre")));
            restauranteUsuario.setDistanciaUniversidad(
                    Double.parseDouble(req.getParameter("distanciaUniversidad")));
            restauranteUsuario.setPrecio(Integer.parseInt(req.getParameter("precio")));

            try {
                restauranteUsuario.setTiempoEspera(Integer.parseInt(req.getParameter("tiempoEspera")));
                restauranteUsuario.setCalidad(Integer.parseInt(req.getParameter("calidad")));
                restauranteUsuario.setPrecio(Integer.parseInt(req.getParameter("precio")));
                restauranteUsuario
                        .setDistanciaUniversidad(Double.parseDouble(req.getParameter("distanciaUniversidad")));
            } catch (NumberFormatException e) {
                resp.sendRedirect(req.getContextPath() + "/restaurante?error=Formato+inválido+en+campos+numéricos");
                return;
            }

            restauranteDAO.save(restauranteUsuario);

            HttpSession session = req.getSession();
            DuenioRestaurante duenio = (DuenioRestaurante) session.getAttribute("usuario");
            duenio.setRestaurante(restauranteUsuario);
            session.setAttribute("usuario", duenio);

            resp.sendRedirect(req.getContextPath() + "/restaurante?success=Restaurante+actualizado+exitosamente");
        } catch (Exception e) {
            resp.sendRedirect(
                    req.getContextPath() + "/restaurante?error=" + URLEncoder.encode(e.getMessage(), "UTF-8"));
        }
    }

    private void procesarAgregarMenu(HttpServletRequest req, HttpServletResponse resp,
                                     Restaurante restauranteUsuario, Historia historia) throws IOException, ServletException {
        try {
            if (historia == null) {
                resp.sendRedirect(req.getContextPath() + "/restaurante?error=El+menú+no+puede+estar+vacío");
                return;
            }

            Restaurante restaurante = restauranteDAO.obtenerRestaurantePorId(restauranteUsuario.getId());
            restaurante.agregarHistoria(historia);

            restauranteDAO.save(restaurante);
            req.getSession().setAttribute("usuario", ((DuenioRestaurante) req.getSession().getAttribute("usuario")));

            resp.sendRedirect(req.getContextPath() + "/restaurante?success=Menú+agregado");
        } catch (Exception e) {
            resp.sendRedirect(
                    req.getContextPath() + "/restaurante?error=" + URLEncoder.encode(e.getMessage(), "UTF-8"));
        }
    }

    private void procesarActualizarRestaurante(HttpServletRequest req, HttpServletResponse resp,
                                               Restaurante restauranteUsuario) throws IOException {
        try {
            String nombre = req.getParameter("nombre");
            String descripcion = req.getParameter("descripcion");
            String tipoComida = req.getParameter("tipoComida");
            String horaApertura = req.getParameter("horaApertura");
            String horaCierre = req.getParameter("horaCierre");
            Double distanciaUniversidad = Double.parseDouble(req.getParameter("distanciaUniversidad"));
            int precio = Integer.parseInt(req.getParameter("precio"));

            if (nombre != null)
                restauranteUsuario.setNombre(nombre);
            if (descripcion != null)
                restauranteUsuario.setDescripcion(descripcion);
            if (tipoComida != null)
                restauranteUsuario.setTipoComida(tipoComida);
            if (horaApertura != null)
                restauranteUsuario.setHoraApertura(LocalTime.parse(horaApertura));
            if (horaCierre != null)
                restauranteUsuario.setHoraCierre(LocalTime.parse(horaCierre));
            if (distanciaUniversidad != null)
                restauranteUsuario.setDistanciaUniversidad(distanciaUniversidad);
            if (precio > 0)
                restauranteUsuario.setPrecio(precio);

            restauranteDAO.save(restauranteUsuario);

            HttpSession session = req.getSession();
            DuenioRestaurante duenio = (DuenioRestaurante) session.getAttribute("usuario");
            duenio.setRestaurante(restauranteUsuario);
            session.setAttribute("usuario", duenio);

            resp.sendRedirect(req.getContextPath() + "/restaurante?success=Restaurante+actualizado+correctamente");
        } catch (Exception e) {
            resp.sendRedirect(
                    req.getContextPath() + "/restaurante?error=" + URLEncoder.encode(e.getMessage(), "UTF-8"));
        }
    }

    public List<RestauranteDTO> getRestaurantesConSuscripcion(Long comensalId) {
        SuscripcionDAO suscripcionDAO = new SuscripcionDAO();
        List<Restaurante> restaurantes = restauranteDAO.obtenerTodosRestaurantes();
        return restaurantes.stream().map(restaurante -> {
            boolean estaSuscrito = suscripcionDAO.existeSuscripcion(comensalId, restaurante.getId());
            return new RestauranteDTO(restaurante, estaSuscrito);
        }).collect(Collectors.toList());
    }

    @Override
    public void destroy() {
        if (usuarioDAO != null)
            usuarioDAO.close();
        if (emf != null)
            emf.close();
    }
}

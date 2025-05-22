package servlets;

import DAO.UsuarioDAO;
import entidades.Comensal;
import entidades.Restaurante;
import entidades.Usuario;
import exceptions.ServiceException;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import servicios.AuthService;

import java.io.IOException;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;

@WebServlet(name = "SvAuth", urlPatterns = { "/login", "/registro-restaurante", "/registro-comensal" })
public class SvAuth extends HttpServlet {
    private AuthService authService;
    private static EntityManagerFactory emf;
    UsuarioDAO usuarioDAO;

    @Override
    public void init() throws ServletException {
        try {
            if (emf == null) {
                emf = Persistence.createEntityManagerFactory("UFood_PU");
            }
            this.usuarioDAO = new UsuarioDAO(emf);
            this.authService = new AuthService(usuarioDAO);
        } catch (Exception e) {
            throw new ServletException("Error al inicializar JPA", e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Crear usuarios de prueba solo cuando se inicia la aplicación
        crearUsuarios();
        // Llenar los restaurantes con datos
        request.getRequestDispatcher("/login.jsp").forward(request, response);
    }

    private void crearUsuarios() {
        try {
            int numeroComensales = 3;
            int numeroRestaurantes = 6;

            for (int i = 1; i <= numeroComensales; i++) {
                String nombreUsuario = "c" + i;
                String contrasena = "c" + i;
                String email = "c" + i + "@c" + i + ".com";
                String tipoComidaFavorita = "Comida Rápida";

                // Verificar si ya existe el usuario
                if (authService.usuarioExiste(nombreUsuario)) {
                    continue; // Si ya existe, pasar al siguiente
                }

                // Crear nuevo comensal
                Comensal comensal = new Comensal();
                comensal.setNombreUsuario(nombreUsuario);
                comensal.setContrasena(contrasena);
                comensal.setEmail(email);
                comensal.setTipoComidaFavorita(tipoComidaFavorita);
                // comensal.setTipoUsuario("COMENSAL");

                authService.registrarComensal(comensal, tipoComidaFavorita);
                System.out.println("Usuario comensal creado: " + nombreUsuario);
            }

            for (int i = 1; i <= numeroRestaurantes; i++) {
                String nombreUsuario = "r" + i;
                String contrasena = "r" + i;
                String email = "r" + i + "@r" + i + ".com";

                // Verificar si ya existe el usuario
                if (authService.usuarioExiste(nombreUsuario)) {
                    continue; // Si ya existe, pasar al siguiente
                }

                // Crear nuevo restaurante
                Restaurante restaurante = new Restaurante();
                restaurante.setNombreUsuario(nombreUsuario);
                restaurante.setContrasena(contrasena);
                restaurante.setEmail(email);
                // restaurante.setTipoUsuario("RESTAURANTE");

                authService.registrarUsuarioRestaurante(restaurante);
                System.out.println("Usuario restaurante creado: " + nombreUsuario);
            }

            registrarRestaurantesQuemados(numeroRestaurantes);
        } catch (ServiceException e) {
            System.out.println("Error al crear usuarios: " + e.getMessage());
        }
    }

    private void registrarRestaurantesQuemados(int numeroRestaurantes) {
        List<String> nombres = Arrays.asList(
                "Burger Place", "Comida Casera Doña Marta", "Pescados y Mariscos del Pacífico",
                "Restaurante Gourmet La Mesa", "Pizza Rápida", "El Buen Sabor");

        List<String> descripciones = Arrays.asList(
                "Las mejores hamburguesas de la ciudad", "Comida casera como la de mamá",
                "Los mejores mariscos frescos", "Ambiente elegante y platos selectos",
                "Pizza rápida y deliciosa", "Sabores tradicionales");

        List<String> tiposComida = Arrays.asList(
                "Comida Rápida", "Comida Casera", "Comida Costeña",
                "Platos a la Carta", "Comida Rápida", "Comida Casera");

        List<String> horasApertura = Arrays.asList(
                "10:00", "08:00", "11:00", "12:00", "11:00", "07:30");

        List<String> horasCierre = Arrays.asList(
                "22:00", "18:00", "23:00", "00:00", "22:30", "17:00");

        List<Double> distanciasUniversidad = Arrays.asList(
                0.5, 1.2, 2.0, 3.5, 0.8, 1.7);

        List<Integer> precios = Arrays.asList(
                2, 2, 4, 5, 1, 2);

        List<Integer> tiemposEspera = Arrays.asList(
                10, 25, 30, 40, 15, 20);

        List<Integer> calidades = Arrays.asList(
                3, 4, 4, 5, 3, 4);

        // Recuperar los usuarios restaurante ya creados
        for (int i = 0; i < 6; i++) {
            try {
                String nombreUsuario = "r" + (i + 1);

                // Verificar si el usuario existe
                if (!authService.usuarioExiste(nombreUsuario)) {
                    continue; // Si no existe, pasar al siguiente
                }

                // Recuperar el restaurante de la base de datos
                Usuario usuario = authService.findByNombreUsuario(nombreUsuario);
                if (!(usuario instanceof Restaurante)) {
                    continue;
                }

                Restaurante restaurante = (Restaurante) usuario;

                // Establecer los datos del restaurante
                restaurante.setNombre(nombres.get(i));
                restaurante.setDescripcion(descripciones.get(i));
                restaurante.setTipoComida(tiposComida.get(i));
                restaurante.setHoraApertura(LocalTime.parse(horasApertura.get(i)));
                restaurante.setHoraCierre(LocalTime.parse(horasCierre.get(i)));
                restaurante.setDistanciaUniversidad(distanciasUniversidad.get(i));
                restaurante.setPrecio(precios.get(i));
                restaurante.setTiempoEspera(tiemposEspera.get(i));
                restaurante.setCalidad(calidades.get(i));

                // Guardar el restaurante actualizado
                usuarioDAO.save(restaurante);
                System.out.println("Restaurante actualizado: " + nombreUsuario + " - " + nombres.get(i));

            } catch (Exception e) {
                System.out.println("Error al actualizar restaurante: " + e.getMessage());
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String path = request.getServletPath();

        switch (path) {
            case "/login":
                handleLogin(request, response);
                break;
            case "/registro-restaurante":
                handleRestauranteRegistration(request, response);
                break;
            case "/registro-comensal":
                handleComensalRegistration(request, response);
                break;
            default:
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
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

            // Redirección según tipo de usuario
            if (usuario instanceof Comensal) {
                Comensal comensal = (Comensal) usuario;
                session.setAttribute("notificaciones", comensal.getNotificaciones());
                response.sendRedirect("inicio"); // La lógica de recomendaciones ahora está en SvIndex
            } else if ("RESTAURANTE".equals(usuario.getTipoUsuario())) {
                response.sendRedirect("crearRestaurante.jsp");
            }
        } catch (ServiceException e) {
            request.setAttribute("error", e.getMessage());
            request.getRequestDispatcher("/login.jsp").forward(request, response);
        }
    }

    private void handleRestauranteRegistration(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            // Crear instancia de Restaurante en lugar de Usuario
            Restaurante restaurante = new Restaurante();
            restaurante.setNombreUsuario(request.getParameter("nombreUsuario"));
            restaurante.setContrasena(request.getParameter("contrasena"));
            restaurante.setEmail(request.getParameter("email"));
            restaurante.setTipoUsuario("RESTAURANTE");

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
            String tipoComidaFavorita = request.getParameter("tipoComidaFavorita");
            if (tipoComidaFavorita == null || tipoComidaFavorita.trim().isEmpty()) {
                tipoComidaFavorita = "General";
            }

            // Crear instancia de Comensal en lugar de Usuario
            Comensal comensal = new Comensal();
            comensal.setNombreUsuario(request.getParameter("nombreUsuario"));
            comensal.setContrasena(request.getParameter("contrasena"));
            comensal.setEmail(request.getParameter("email"));
            comensal.setTipoComidaFavorita(tipoComidaFavorita);
            comensal.setTipoUsuario("COMENSAL");

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

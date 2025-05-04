package servlets;

import entidades.Restaurante;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import modelos.RestauranteDAO;

import java.io.IOException;
import java.time.LocalTime;

@WebServlet(name = "restauranteCrear", value = "/restaurante")
public class SvRestaurante extends HttpServlet {
    private EntityManagerFactory emf;

    @Override
    public void init() {
        emf = Persistence.createEntityManagerFactory("UFood_PU");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
//        String accion = req.getParameter("accion");
//
//        if ("listar".equals(accion)) {
//            // lógica para listar
//        } else if ("eliminar".equals(accion)) {
//            // lógica para eliminar
//        }

        req.getRequestDispatcher("/crearRestaurante.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        EntityManager em = emf.createEntityManager();
        String accion = req.getParameter("accion");

        if ("guardar".equals(accion)) {
            try {
                Restaurante restaurante = crearRestauranteDesdeRequest(req);
                RestauranteDAO dao = new RestauranteDAO();
                dao.guardarRestaurante(restaurante);
                dao.cerrar();

                resp.sendRedirect(req.getContextPath() + "/inicio?success=true");

            } catch (Exception e) {
                // Manejo de errores
                em.getTransaction().rollback();
                req.getSession().setAttribute("error", "Error al guardar el restaurante");
                resp.sendRedirect(req.getContextPath() + "/inicio");
            } finally {
                em.close();
            }
        } else if ("agregarHistoria".equals(accion)) {
            try {
                Long restauranteId = Long.parseLong(req.getParameter("restauranteId"));
                String historia = req.getParameter("historia");

                em.getTransaction().begin();
                Restaurante restaurante = em.find(Restaurante.class, restauranteId);
                if (restaurante != null) {
                    restaurante.agregarHistoria(historia);
                    em.merge(restaurante);
                }
                em.getTransaction().commit();

                resp.sendRedirect(req.getContextPath() + "/restaurante?success=Historia agregada");
            } catch (Exception e) {
                em.getTransaction().rollback();
                resp.sendRedirect(req.getContextPath() + "/restaurante?error=" + e.getMessage());
            } finally {
                em.close();
            }
        }
    }

    private Restaurante crearRestauranteDesdeRequest(HttpServletRequest req) {
        String nombre = req.getParameter("nombre");
        String descripcion = req.getParameter("descripcion");
        String tipoComida = req.getParameter("tipoComida");
        LocalTime horaApertura = LocalTime.parse(req.getParameter("horaApertura"));
        LocalTime horaCierre = LocalTime.parse(req.getParameter("horaCierre"));

        Restaurante restaurante = new Restaurante();
        restaurante.setNombre(nombre);
        restaurante.setDescripcion(descripcion);
        restaurante.setTipoComida(tipoComida);
        restaurante.setHoraApertura(horaApertura);
        restaurante.setHoraCierre(horaCierre);

        return restaurante;
    }

    @Override
    public void destroy() {
        if (emf != null) {
            emf.close();
        }
    }
}
package servlets;

import entidades.Calificacion;
import entidades.Comensal;
import entidades.Restaurante;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name = "calificar", value = "/calificar")
public class SvCalificacion extends HttpServlet {
    private EntityManagerFactory emf;

    @Override
    public void init() {
        emf = Persistence.createEntityManagerFactory("UFood_PU");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        EntityManager em = emf.createEntityManager();
        String idRestauranteString = req.getParameter("idRestaurante");

        if (idRestauranteString == null || idRestauranteString.isEmpty()) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Parámetro idRestaurante requerido");
            return;
        }
        int idRestaurante;
        try {
            idRestaurante = Integer.parseInt(idRestauranteString);
        } catch (NumberFormatException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "ID de restaurante debe ser un número entero");
            return;
        }
        Restaurante restaurante = em.find(Restaurante.class, idRestaurante);
        req.setAttribute("restaurante", restaurante);
        req.getRequestDispatcher("calificarRestaurante.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        EntityManager em = emf.createEntityManager();

        try {
            // Obtener parámetros del formulario
            int puntaje = Integer.parseInt(req.getParameter("puntaje"));
            String comentario = req.getParameter("comentario");
            Long idComensal = Long.parseLong(req.getParameter("idComensal"));
            Long idRestaurante = Long.parseLong(req.getParameter("idRestaurante"));

            Calificacion nuevaCalificacion = new Calificacion();
            em.getTransaction().begin();
            Comensal comensal = em.find(Comensal.class, idComensal);
            Restaurante restaurante = em.find(Restaurante.class, idRestaurante);

            if (comensal == null || restaurante == null) {
                throw new IllegalArgumentException("Comensal o Restaurante no encontrado");
            }

            nuevaCalificacion.setPuntaje(puntaje);
            nuevaCalificacion.setComentario(comentario);
            boolean exito = nuevaCalificacion.calificar(comensal, restaurante);

            if (exito) {
                em.persist(nuevaCalificacion);
                em.getTransaction().commit();
            } else {
                em.getTransaction().rollback();
            }
            resp.sendRedirect(req.getContextPath() + "/inicio?success=true");

        } catch (Exception e) {
            em.getTransaction().rollback();
            resp.sendRedirect(req.getContextPath() + "/inicio?error=" + e.getMessage());
        } finally {
            em.close();
        }
    }

    @Override
    public void destroy() {
        if (emf != null) {
            emf.close();
        }
    }
}

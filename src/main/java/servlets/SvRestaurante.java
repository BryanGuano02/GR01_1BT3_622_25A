package servlets;

import entidades.Restaurante;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.persistence.*;
import jakarta.servlet.annotation.WebServlet;

import java.io.IOException;

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
        String accion = req.getParameter("accion");

        if ("listar".equals(accion)) {
            // lógica para listar
        } else if ("eliminar".equals(accion)) {
            // lógica para eliminar
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        EntityManager em = emf.createEntityManager();
        String accion = req.getParameter("accion");

        resp.getWriter().println("Entró a SvRestaurante");
        if ("guardar".equals(accion)) {
            String nombre = req.getParameter("nombre");
            String descripcion = req.getParameter("descripcion");
            String horarioAtencion = req.getParameter("horarioAtencion");
            Restaurante restaurante = new Restaurante(nombre, descripcion, horarioAtencion);

            em.getTransaction().begin();
            em.persist(restaurante);
            em.getTransaction().commit();

            em.close();

            resp.getWriter().println("Restaurante guardado con éxito: " + restaurante.getNombre());
            resp.sendRedirect(req.getContextPath() + "/index.jsp");
        } else if ("actualizar".equals(accion)) {
            // lógica para actualizar
        }
    }
}

package servlets;

import entidades.Restaurante;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.persistence.*;
import jakarta.servlet.annotation.WebServlet;
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
        String accion = req.getParameter("accion");

        if ("listar".equals(accion)) {
            // lógica para listar
        } else if ("eliminar".equals(accion)) {
            // lógica para eliminar
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        EntityManager em = emf.createEntityManager();
        String accion = req.getParameter("accion");

        if ("guardar".equals(accion)) {
            try {
                // Obtener parámetros del formulario
                String nombre = req.getParameter("nombre");
                String tipoComida = req.getParameter("tipoCocina");

                // Convertir los horarios a LocalTime
                LocalTime horaApertura = LocalTime.parse(req.getParameter("horaApertura"));
                LocalTime horaCierre = LocalTime.parse(req.getParameter("horaCierre"));

                // Crear y persistir el restaurante
                Restaurante restaurante = new Restaurante();
                restaurante.setNombre(nombre);
                restaurante.setTipoComida(tipoComida);
                restaurante.setHoraApertura(horaApertura);
                restaurante.setHoraCierre(horaCierre);

                em.getTransaction().begin();
                em.persist(restaurante);
                em.getTransaction().commit();

                resp.sendRedirect(req.getContextPath() + "/index.jsp?success=true");

            } catch (Exception e) {
                // Manejo de errores
                em.getTransaction().rollback();
                resp.sendRedirect(req.getContextPath() + "/index.jsp?error=" + e.getMessage());
            } finally {
                em.close();
            }
        } else if ("actualizar".equals(accion)) {
            // lógica para actualizar
        }
    }

    @Override
    public void destroy() {
        if (emf != null) {
            emf.close();
        }
    }
}
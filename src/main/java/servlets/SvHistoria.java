package servlets;

import entidades.Comensal;
import entidades.Restaurante;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

import java.io.IOException;

@WebServlet(name = "SvHistoria", value = "/restaurantes/historias")
@MultipartConfig
public class SvHistoria extends HttpServlet {
    private EntityManagerFactory emf;

    @Override
    public void init() {
        emf = Persistence.createEntityManagerFactory("UFood_PU");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        EntityManager em = emf.createEntityManager();

        try {
            Long restauranteId = Long.parseLong(req.getParameter("idRestaurante"));
            String contenido = req.getParameter("contenido");
            Part imagen = req.getPart("imagen"); // Para manejar archivos subidos

            if (contenido == null || contenido.trim().isEmpty() || imagen == null) {
                resp.sendRedirect(req.getContextPath() + "/inicio?error=Datos incompletos");
                return;
            }

            // Aquí iría la lógica para subir la imagen a tu storage y obtener la URL
            String imagenUrl = "URL_GENERADA"; // <-- Aquí debes llamar a tu storageService

            em.getTransaction().begin();
            Restaurante restaurante = em.find(Restaurante.class, restauranteId);
            restaurante.crearHistoria(contenido, imagenUrl);
            em.merge(restaurante);
            em.getTransaction().commit();

            resp.sendRedirect(req.getContextPath() + "/restaurantes/" + restauranteId);

        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
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

package servlets;

import entidades.Calificacion;
import entidades.Comensal;
import entidades.Preferencia;
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
import java.time.LocalTime;
import java.util.List;

@WebServlet(name = "SvPreferencia", value = "/SvPreferencia")
public class SvPreferencia extends HttpServlet {
    private EntityManagerFactory emf;

    @Override
    public void init() {
        emf = Persistence.createEntityManagerFactory("UFood_PU");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String tipoComida = req.getParameter("tipoComida");
        String horaAperturaStr = req.getParameter("horaApertura");
        String horaCierreStr = req.getParameter("horaCierre");
        String distanciaStr = req.getParameter("distancia");

        LocalTime horaApertura = (horaAperturaStr != null && !horaAperturaStr.isEmpty()) ? LocalTime.parse(horaAperturaStr) : null;
        LocalTime horaCierre = (horaCierreStr != null && !horaCierreStr.isEmpty()) ? LocalTime.parse(horaCierreStr) : null;
        Double distanciaMax = (distanciaStr != null && !distanciaStr.isEmpty()) ? Double.parseDouble(distanciaStr) : null;

        Preferencia preferencia = new Preferencia( tipoComida, horaApertura, horaCierre, distanciaMax );

        EntityManager em = emf.createEntityManager();
        List<Restaurante> restaurantes = em.createQuery("SELECT r FROM Restaurante r").getResultList();
        List<Restaurante> restaurantesFiltrados =  preferencia.aplicarPreferencia(restaurantes);

        req.setAttribute("restaurantesFiltrados", restaurantesFiltrados);
        req.getRequestDispatcher("restaurantesFiltrados.jsp").forward(req, resp);


    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        EntityManager em = emf.createEntityManager();

        try {

            Long idComensal = Long.parseLong(req.getParameter("idComensal"));
            String tipoComida = req.getParameter("tipoComida");
            String horaAperturaStr = req.getParameter("horaApertura");
            String horaCierreStr = req.getParameter("horaCierre");
            String distanciaStr = req.getParameter("distancia");

            LocalTime horaApertura = (horaAperturaStr != null && !horaAperturaStr.isEmpty()) ? LocalTime.parse(horaAperturaStr) : null;
            LocalTime horaCierre = (horaCierreStr != null && !horaCierreStr.isEmpty()) ? LocalTime.parse(horaCierreStr) : null;
            Double distanciaMax = (distanciaStr != null && !distanciaStr.isEmpty()) ? Double.parseDouble(distanciaStr) : null;

            Preferencia nuevaPreferencia = new Preferencia();
            em.getTransaction().begin();
            Comensal comensal = em.find(Comensal.class, idComensal);

            if ( comensal == null ) {
                throw new IllegalArgumentException("Comensal no encontrado");
            }

            nuevaPreferencia.setTipoComida(tipoComida);
            nuevaPreferencia.setHoraApertura(horaApertura);
            nuevaPreferencia.setHoraCierre(horaCierre);
            nuevaPreferencia.setDistanciaUniversidad(distanciaMax);

            boolean exito = comensal.guardarPreferencia(nuevaPreferencia);

            if (exito) {
                em.persist(nuevaPreferencia);
                em.getTransaction().commit();
            } else {
                em.getTransaction().rollback();
                // Manejar el caso de fallo en la calificación
            }
            resp.sendRedirect(req.getContextPath() + "/index.jsp?success=true");

        } catch (Exception e) {
            // Manejo de errores
            em.getTransaction().rollback();
            resp.sendRedirect(req.getContextPath() + "/index.jsp?error=" + e.getMessage());
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

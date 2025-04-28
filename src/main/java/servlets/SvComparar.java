package servlets;

import entidades.Restaurante;
import entidades.Calificacion;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.persistence.*;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "SvComparar", urlPatterns = {"/SvComparar"})
public class SvComparar extends HttpServlet {
    private EntityManagerFactory emf;

    @Override
    public void init() {
        emf = Persistence.createEntityManagerFactory("UFood_PU");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        EntityManager em = emf.createEntityManager();
        String accion = request.getParameter("accion");

        try {
            if (accion == null || accion.equals("listar")) {
                List<Restaurante> restaurantes = em.createQuery(
                        "SELECT r FROM Restaurante r ORDER BY r.id",
                        Restaurante.class)
                        .getResultList();
            
                request.setAttribute("restaurantes", restaurantes);
                request.getRequestDispatcher("/compararRestaurantes.jsp")
                  .forward(request, response);
        
        } else if (accion.equals("comparar")) {
            Long id1 = Long.parseLong(request.getParameter("restaurante1"));
            Long id2 = Long.parseLong(request.getParameter("restaurante2"));

            if (id1.equals(id2)) {
                request.setAttribute("error", "Por favor seleccione dos restaurantes diferentes");
                    doGet(request, response);
                return;
            }

            // Obtener restaurantes con sus primeras calificaciones
            Restaurante rest1 = obtenerRestauranteConCalificacion(em, id1);
            Restaurante rest2 = obtenerRestauranteConCalificacion(em, id2);

            if (rest1 == null || rest2 == null) {
                request.setAttribute("error", "Uno o ambos restaurantes no fueron encontrados");
                    doGet(request, response);
                return;
            }

            // Obtener la primera calificación de cada restaurante
            Calificacion cal1 = obtenerPrimeraCalificacion(em, id1);
            Calificacion cal2 = obtenerPrimeraCalificacion(em, id2);
                System.out.println("prueba de que este funcionando 3");
            // Guardamos los datos en la sesión en lugar de request
request.getSession().setAttribute("restaurante1", rest1);
request.getSession().setAttribute("restaurante2", rest2);
request.getSession().setAttribute("calificacion1", cal1);
request.getSession().setAttribute("calificacion2", cal2);

// Redirigimos
response.sendRedirect(request.getContextPath() + "/resultadoComparacion.jsp");

        }
    } catch (Exception e) {
        // Agregamos manejo de error
        request.setAttribute("error", "Ha ocurrido un error en el proceso");
        List<Restaurante> restaurantes = em.createQuery(
                "SELECT r FROM Restaurante r ORDER BY r.id",
                Restaurante.class)
                .getResultList();
        request.setAttribute("restaurantes", restaurantes);
        request.getRequestDispatcher("/compararRestaurantes.jsp")
              .forward(request, response);
    } finally {
        em.close();
    }
}

    private Restaurante obtenerRestauranteConCalificacion(EntityManager em, Long id) {
        return em.find(Restaurante.class, id);
    }

    private Calificacion obtenerPrimeraCalificacion(EntityManager em, Long restauranteId) {
        try {
            return em.createQuery(
                    "SELECT c FROM Calificacion c " +
                    "WHERE c.restaurante.id = :restauranteId " +
                    "ORDER BY c.id", // Agregamos ORDER BY para asegurar obtener la primera
                    Calificacion.class)
                    .setParameter("restauranteId", restauranteId)
                    .setMaxResults(1)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

    @Override
    public void destroy() {
        if (emf != null && emf.isOpen()) {
            emf.close();
        }
    }
}
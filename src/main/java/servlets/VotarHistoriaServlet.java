package servlets;

import DAO.VotacionHistoriaDAO;
import DAO.VotoHistoriaDAO;
import exceptions.ServiceException;
import jakarta.persistence.EntityManagerFactory;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import servicios.VotoHistoriaService;

import java.io.IOException;

@WebServlet("/votarHistoria")
public class VotarHistoriaServlet extends HttpServlet {
    private EntityManagerFactory emf;

    @Override
    public void init() {
        emf = (EntityManagerFactory) getServletContext().getAttribute("emf");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long idComensal = Long.valueOf(req.getParameter("idComensal"));
        Long idRestaurante = Long.valueOf(req.getParameter("idRestaurante"));
        String historia = req.getParameter("historia");
        boolean like = Boolean.parseBoolean(req.getParameter("like"));

        VotoHistoriaDAO votoHistoriaDAO = new VotoHistoriaDAO(emf);
        VotacionHistoriaDAO votacionHistoriaDAO = new VotacionHistoriaDAO(emf);
        VotoHistoriaService service = new VotoHistoriaService(votacionHistoriaDAO, votoHistoriaDAO);

        try {
            service.votar(idComensal, idRestaurante, historia, like);
            resp.sendRedirect("inicio?mensaje=¡Tu voto ha sido registrado!");
        } catch (ServiceException e) {
            resp.sendRedirect("inicio?error=" + java.net.URLEncoder.encode(e.getMessage(), "UTF-8"));
        }
    }
}

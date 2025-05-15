package servlets;

import entidades.Comensal;
import entidades.Notificacion;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import servicios.NotificacionService;

@WebServlet("/notificaciones/leida")
public class SvNotificacionLeida extends HttpServlet {
    private NotificacionService notificacionService = new NotificacionService();
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idStr = request.getParameter("id");
        HttpSession session = request.getSession(false);
        if (idStr != null && session != null && session.getAttribute("usuario") instanceof Comensal) {
            try {
                Long id = Long.parseLong(idStr);
                Comensal comensal = (Comensal) session.getAttribute("usuario");
                List<Notificacion> notificaciones = comensal.getNotificaciones();
                for (Notificacion n : notificaciones) {
                    if (n.getId() != null && n.getId().equals(id)) {
                        n.setLeida(true);
                        // Persistir usando el objeto completo
                        notificacionService.marcarNotificacionComoLeida(n);
                        break;
                    }
                }
            } catch (NumberFormatException ignored) {}
        }
        response.setStatus(HttpServletResponse.SC_OK);
    }
}

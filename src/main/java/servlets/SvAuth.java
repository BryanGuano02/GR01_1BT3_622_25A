package servlets;

import entidades.Usuario;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import modelos.UsuarioDAO;

import java.io.IOException;

@WebServlet("/login")
public class SvAuth extends HttpServlet {
    private UsuarioDAO usuarioDao;

    @Override
    public void init() {
        usuarioDao = new UsuarioDAO();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        resp.sendRedirect("login.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        String tipoUsuario = req.getParameter("tipoUsuario");

        Usuario usuario = usuarioDao.autenticar(email, password, tipoUsuario);

        if (usuario != null) {
            HttpSession session = req.getSession();
            session.setAttribute("usuario", usuario);

            if ("comensal".equals(tipoUsuario)) {
                resp.sendRedirect("index.jsp");
            } else if ("restaurante".equals(tipoUsuario)) {
                resp.sendRedirect("crearRestaurante.jsp");
            }
        } else {
            req.setAttribute("error", "Credenciales inválidas");
            req.getRequestDispatcher("login.jsp").forward(req, resp);
        }
    }
}

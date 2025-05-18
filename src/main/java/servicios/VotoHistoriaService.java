package servicios;

import DAO.VotoHistoriaDAO;
import DAO.VotacionHistoriaDAO;
import entidades.Comensal;
import entidades.Restaurante;
import entidades.VotoHistoria;
import exceptions.ServiceException;

public class VotoHistoriaService {
    private final VotoHistoriaDAO votoHistoriaDAO;
    private final VotacionHistoriaDAO votacionHistoriaDAO;

    public VotoHistoriaService(VotacionHistoriaDAO votacionHistoriaDAO, VotoHistoriaDAO votoHistoriaDAO) {
        this.votacionHistoriaDAO = votacionHistoriaDAO;
        this.votoHistoriaDAO = votoHistoriaDAO;
    }

    public void votar(Long idComensal, Long idRestaurante, String historia, boolean like) throws ServiceException {
        if (votacionHistoriaDAO.estaCerrada(idRestaurante, historia)) {
            throw new ServiceException("La votación para esta historia ya está cerrada.");
        }
        if (votoHistoriaDAO.existeVoto(idComensal, idRestaurante, historia)) {
            throw new ServiceException("El comensal ya ha votado por esta historia.");
        }
        VotoHistoria voto = new VotoHistoria();
        Comensal comensal = new Comensal();
        comensal.setId(idComensal);
        Restaurante restaurante = new Restaurante();
        restaurante.setId(idRestaurante);
        voto.setComensal(comensal);
        voto.setRestaurante(restaurante);
        voto.setHistoria(historia);
        voto.setLike(like);

        if (!votoHistoriaDAO.crear(voto)) {
            throw new ServiceException("No se pudo registrar el voto.");
        }
    }

    public void cerrarVotacion(Long idRestaurante, String historia) throws ServiceException {
        votacionHistoriaDAO.cerrarVotacion(idRestaurante, historia);
    }

    public boolean estaVotacionCerrada(Long idRestaurante, String historia) {
        return votacionHistoriaDAO.estaCerrada(idRestaurante, historia);
    }

    public long contarLikes(Long idRestaurante, String historia) {
        return votoHistoriaDAO.contarLikes(idRestaurante, historia);
    }

    public long contarDislikes(Long idRestaurante, String historia) {
        return votoHistoriaDAO.contarDislikes(idRestaurante, historia);
    }
}
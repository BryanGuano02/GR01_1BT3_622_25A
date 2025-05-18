package DAO;

import entidades.VotacionHistoria;
import jakarta.persistence.*;

public class VotacionHistoriaDAO {
    private final EntityManagerFactory emf;

    public VotacionHistoriaDAO(EntityManagerFactory emf) {
        this.emf = emf;
    }

    // Sobrecarga: obtener usando un EntityManager existente
    public VotacionHistoria obtener(EntityManager em, Long idRestaurante, String historia) {
        TypedQuery<VotacionHistoria> query = em.createQuery(
            "SELECT v FROM VotacionHistoria v WHERE v.restaurante.id = :idRestaurante AND v.historia = :historia",
            VotacionHistoria.class
        );
        query.setParameter("idRestaurante", idRestaurante);
        query.setParameter("historia", historia);
        return query.getResultStream().findFirst().orElse(null);
    }

    // Mantén el método original para consultas fuera de transacción
    public VotacionHistoria obtener(Long idRestaurante, String historia) {
        EntityManager em = emf.createEntityManager();
        try {
            return obtener(em, idRestaurante, historia);
        } finally {
            em.close();
        }
    }

    public void cerrarVotacion(Long idRestaurante, String historia) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            VotacionHistoria votacion = obtener(em, idRestaurante, historia); // Usa el mismo EM
            if (votacion != null) {
                votacion.setCerrada(true);
                em.merge(votacion);
            }
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    public boolean estaCerrada(Long idRestaurante, String historia) {
        VotacionHistoria votacion = obtener(idRestaurante, historia);
        return votacion != null && votacion.isCerrada();
    }
}
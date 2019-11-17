/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.eventos.persistence;

import co.edu.uniandes.csw.eventos.entities.MemoriaEntity;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * @author Alberic Despres
 */
@Stateless
public class MemoriaPersistence {

    private static final Logger LOGGER = Logger.getLogger(MemoriaPersistence.class.getName());

    @PersistenceContext(unitName = "eventosPU")
    protected EntityManager em;

    public MemoriaEntity create(MemoriaEntity memoria) {
        LOGGER.log(Level.INFO, "Creando una nueva memoria del evento");
        em.persist(memoria);
        LOGGER.log(Level.INFO, "Memoria del Evento creada");
        return memoria;
    }

    public MemoriaEntity find(Long eventosId, Long memoriaId) {
        LOGGER.log(Level.INFO, "Consultando la memoria del evento con id={0}", memoriaId);
        TypedQuery<MemoriaEntity> q = em.createQuery("select p from MemoriaEntity p where (p.evento.id = :eventoid) and (p.id = :memoriasId)", MemoriaEntity.class);
        q.setParameter("eventoid", eventosId);
        q.setParameter("memoriasId", memoriaId);
        List<MemoriaEntity> results = q.getResultList();
        MemoriaEntity memoria = null;
        if (results != null && !results.isEmpty()) {
            memoria = results.get(0);
        }
        LOGGER.log(Level.INFO, "Saliendo de consultar la memoria del evento con id = {0}", memoriaId);
        return memoria;
    }

    public MemoriaEntity update(MemoriaEntity memoria) {
        LOGGER.log(Level.INFO, "Actualizando la memoria del evento con id={0}", memoria.getId());
        return em.merge(memoria);
    }

    public void delete(Long memoriaId) {
        LOGGER.log(Level.INFO, "Borrando la memoria del evento con id={0}", memoriaId);
        MemoriaEntity entity = em.find(MemoriaEntity.class, memoriaId);
        em.remove(entity);
        LOGGER.log(Level.INFO, "Saliendo de borrar la memoria con id = {0}", memoriaId);
    }
}

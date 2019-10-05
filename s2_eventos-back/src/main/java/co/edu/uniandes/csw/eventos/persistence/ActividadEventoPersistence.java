/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.eventos.persistence;

import co.edu.uniandes.csw.eventos.entities.ActividadEventoEntity;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * @author Germán David Martínez Solano
 */
@Stateless
public class ActividadEventoPersistence {

    private static final Logger LOGGER = Logger.getLogger(ActividadEventoPersistence.class.getName());

    @PersistenceContext(unitName = "eventosPU")
    protected EntityManager em;

    public ActividadEventoEntity create(ActividadEventoEntity actividadEvento) {
        LOGGER.log(Level.INFO, "Creando una nueva actividad del evento");
        em.persist(actividadEvento);
        LOGGER.log(Level.INFO, "Cctividad del Evento creada");
        return actividadEvento;
    }

    public ActividadEventoEntity find(Long eventosId, Long actividadEventoEventosId) {
        LOGGER.log(Level.INFO, "Consultando la actividad del evento con id={0} del evento con id = " + eventosId, actividadEventoEventosId);
        TypedQuery<ActividadEventoEntity> q = em.createQuery("select p from ActividadEventoEntity p where (p.evento.id = :eventoid) and (p.id = :actividadesEventoId)", ActividadEventoEntity.class);
        q.setParameter("eventoid", eventosId);
        q.setParameter("actividadesEventoId", actividadEventoEventosId);
        List<ActividadEventoEntity> results = q.getResultList();
        ActividadEventoEntity actividad = null;
        if (results == null) {
            actividad = null;
        } else if (results.isEmpty()) {
            actividad = null;
        } else if (results.size() >= 1) {
            actividad = results.get(0);
        }
        LOGGER.log(Level.INFO, "Saliendo de consultar la actividad del evento con id = {0} del evento con id =" + eventosId, actividadEventoEventosId);
        return actividad;
    }

    public List<ActividadEventoEntity> findAllOfAnEvent(Long eventosId) {
        LOGGER.log(Level.INFO, "Consultando todas las actividades del evento");
        TypedQuery<ActividadEventoEntity> q = em.createQuery("select p from ActividadEventoEntity p where (p.evento.id = :eventoid)", ActividadEventoEntity.class);
        q.setParameter("eventoid", eventosId);
        return q.getResultList();
    }

    public ActividadEventoEntity update(ActividadEventoEntity actividadEvento) {
        LOGGER.log(Level.INFO, "Actualizando la actividad del evento con id={0}", actividadEvento.getId());
        return em.merge(actividadEvento);
    }

    public void delete(Long actividadEventoId) {
        LOGGER.log(Level.INFO, "Borrando la actividad del evento con id={0}", actividadEventoId);
        ActividadEventoEntity entity = em.find(ActividadEventoEntity.class, actividadEventoId);
        em.remove(entity);
        LOGGER.log(Level.INFO, "Saliendo de borrar la actididad con id = {0}", actividadEventoId);
    }
}

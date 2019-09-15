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

    public ActividadEventoEntity find(Long actividadEventoEventosId) {
        LOGGER.log(Level.INFO, "Consultando la actividad del evento con id={0}", actividadEventoEventosId);
        return em.find(ActividadEventoEntity.class, actividadEventoEventosId);
    }

    public List<ActividadEventoEntity> findAll() {
        LOGGER.log(Level.INFO, "Consultando todas las actividades del evento");
        TypedQuery<ActividadEventoEntity> query = em.createQuery("select u from ActividadEventoEntity u", ActividadEventoEntity.class);
        return query.getResultList();
    }

    public ActividadEventoEntity update(ActividadEventoEntity actividadEvento) {
        LOGGER.log(Level.INFO, "Actualizando la actividad del evento con id={0}", actividadEvento.getId());
        return em.merge(actividadEvento);
    }

    public void delete(Long actividadEventoId) {
        LOGGER.log(Level.INFO, "Borrando la actividad del evento con id={0}", actividadEventoId);
        ActividadEventoEntity entity = em.find(ActividadEventoEntity.class, actividadEventoId);
        em.remove(entity);
    }

    public ActividadEventoEntity findByName(String name) {
        LOGGER.log(Level.INFO, "Consultando actividades de evento por nombre ", name);
        TypedQuery query = em.createQuery("select u from ActividadEventoEntity u where u.nombre = :name", ActividadEventoEntity.class);
        query = query.setParameter("name", name);
        List<ActividadEventoEntity> sameName = query.getResultList();
        ActividadEventoEntity result;
        if (sameName == null) {
            result = null;
        } else if (sameName.isEmpty()) {
            result = null;
        } else {
            result = sameName.get(0);
        }

        LOGGER.log(Level.INFO, "Saliendo de consultar actividades de evento por nombre ", name);
        return result;
    }
}

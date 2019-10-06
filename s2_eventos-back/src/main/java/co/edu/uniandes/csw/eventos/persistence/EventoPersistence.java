/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.eventos.persistence;

import co.edu.uniandes.csw.eventos.entities.EventoEntity;
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
public class EventoPersistence {

    private static final Logger LOGGER = Logger.getLogger(EventoPersistence.class.getName());

    @PersistenceContext(unitName = "eventosPU")
    protected EntityManager em;

    public EventoEntity create(EventoEntity evento) {
        LOGGER.log(Level.INFO, "Creando un evento nuevo");
        em.persist(evento);
        LOGGER.log(Level.INFO, "Evento creado");
        return evento;
    }

    public EventoEntity find(Long eventosId) {
        LOGGER.log(Level.INFO, "Consultando el evento con id={0}", eventosId);
        TypedQuery<EventoEntity> query = em.createQuery("select u from EventoEntity u left join FETCH u.responsable p where u.id =:id", EventoEntity.class);
        query = query.setParameter("id", eventosId);
        List<EventoEntity> eventos = query.getResultList();
        EventoEntity result = null;
        if (!(eventos == null || eventos.isEmpty())) {
            result = eventos.get(0);
        }
        return result;
    }

    public List<EventoEntity> findAll() {
        LOGGER.log(Level.INFO, "Consultando todos los eventos");
        TypedQuery<EventoEntity> query = em.createQuery("select u from EventoEntity u left join FETCH u.responsable p", EventoEntity.class);
        return query.getResultList();
    }

    public EventoEntity update(EventoEntity evento) {
        LOGGER.log(Level.INFO, "Actualizando el evento con id={0}", evento.getId());
        return em.merge(evento);
    }

    public void delete(Long eventoId) {
        LOGGER.log(Level.INFO, "Borrando el evento con id={0}", eventoId);
        TypedQuery<EventoEntity> query = em.createQuery("select u from EventoEntity u left join FETCH u.responsable p where u.id =:id", EventoEntity.class);
        query = query.setParameter("id", eventoId);
        EventoEntity eventoEntity = query.getSingleResult();
        em.remove(eventoEntity);
    }

    public EventoEntity findByName(String name) {
        LOGGER.log(Level.INFO, "Consultando eventos por nombre ", name);
        TypedQuery query = em.createQuery("select u from EventoEntity u where u.nombre = :name", EventoEntity.class);
        query = query.setParameter("name", name);
        List<EventoEntity> sameName = query.getResultList();
        EventoEntity result;
        if (sameName == null) {
            result = null;
        } else if (sameName.isEmpty()) {
            result = null;
        } else {
            result = sameName.get(0);
        }
        LOGGER.log(Level.INFO, "Saliendo de consultar eventos por nombre ", name);
        return result;
    }
}

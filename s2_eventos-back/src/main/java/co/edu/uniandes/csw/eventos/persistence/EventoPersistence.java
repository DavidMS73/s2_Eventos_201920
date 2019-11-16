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
import javax.persistence.Query;
import javax.persistence.TypedQuery;

/**
 * Clase que maneja la persistencia para Evento. Se conecta a través Entity
 * Manager de javax.persistance con la base de datos SQL.
 *
 * @author Germán David Martínez Solano
 */
@Stateless
public class EventoPersistence {

    /**
     * El logger de la persistencia
     */
    private static final Logger LOGGER = Logger.getLogger(EventoPersistence.class.getName());

    /**
     * Contexto de persistencia
     */
    @PersistenceContext(unitName = "eventosPU")
    protected EntityManager em;

    /**
     * Método para persisitir la entidad en la base de datos.
     *
     * @param evento objeto evento que se creará en la base de datos
     * @return devuelve la entidad creada con un id dado por la base de datos.
     */
    public EventoEntity create(EventoEntity evento) {
        LOGGER.log(Level.INFO, "Creando un evento nuevo");
        em.persist(evento);
        LOGGER.log(Level.INFO, "Evento creado");
        return evento;
    }

    /**
     * Busca si hay algún evento con el id que se envía de argumento
     *
     * @param eventosId: id correspondiente al evento buscado.
     * @return un evento.
     */
    public EventoEntity find(Long eventosId) {
        LOGGER.log(Level.INFO, "Consultando el evento con id={0}", eventosId);
        return em.find(EventoEntity.class, eventosId);
    }

    /**
     * Devuelve todos los eventos de la base de datos.
     *
     * @return una lista con todos los eventos que encuentre en la base de datos
     */
    public List<EventoEntity> findAll() {
        LOGGER.log(Level.INFO, "Consultando todos los eventos");
        Query q = em.createQuery("select u from EventoEntity u");
        return q.getResultList();
    }

    /**
     * Actualiza un evento.
     *
     * @param evento: el evento que viene con los nuevos cambios.
     * @return un evento con los cambios aplicados.
     */
    public EventoEntity update(EventoEntity evento) {
        LOGGER.log(Level.INFO, "Actualizando el evento con id={0}", evento.getId());
        return em.merge(evento);
    }

    /**
     * Borra una evento de la base de datos recibiendo como argumento el id de
     * la evento
     *
     * @param eventosId: id correspondiente al evento a borrar.
     */
    public void delete(Long eventosId) {
        LOGGER.log(Level.INFO, "Borrando el evento con id={0}", eventosId);
        EventoEntity eventoEntity = em.find(EventoEntity.class, eventosId);
        em.remove(eventoEntity);
    }

    /**
     * Busca si hay algún evento con el nombre que se envía de argumento
     *
     * @param name: Nombre del evento que se está buscando
     * @return null si no existe ningún evento con el nombre del argumento. Si
     * existe alguna devuelve la primera.
     */
    public EventoEntity findByName(String name) {
        LOGGER.log(Level.INFO, "Consultando eventos por nombre {0}", name);
        TypedQuery query = em.createQuery("select u from EventoEntity u where u.nombre = :name", EventoEntity.class);
        query = query.setParameter("name", name);
        List<EventoEntity> sameName = query.getResultList();
        EventoEntity result = null;
        if (sameName != null && !sameName.isEmpty()) {
            result = sameName.get(0);
        }
        LOGGER.log(Level.INFO, "Saliendo de consultar eventos por nombre {0}", name);
        return result;
    }
}

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
 * Clase que maneja la persistencia para ActividadEvento. Se conecta a través
 * del Entity Manager de javax.persistance con la base de datos SQL.
 *
 * @author Germán David Martínez Solano
 */
@Stateless
public class ActividadEventoPersistence {

    /**
     * Logger de la persistencia de la actividad
     */
    private static final Logger LOGGER = Logger.getLogger(ActividadEventoPersistence.class.getName());

    /**
     * Contexto de persistencia
     */
    @PersistenceContext(unitName = "eventosPU")
    protected EntityManager em;

    /**
     * Método para persisitir la entidad en la base de datos.
     *
     * @param actividadEvento objeto actividad que se creará en la base de datos
     * @return devuelve la entidad creada con un id dado por la base de datos.
     */
    public ActividadEventoEntity create(ActividadEventoEntity actividadEvento) {
        LOGGER.log(Level.INFO, "Creando una nueva actividad del evento");
        em.persist(actividadEvento);
        LOGGER.log(Level.INFO, "Actividad del Evento creada");
        return actividadEvento;
    }

    /**
     * Buscar una actividad
     *
     * Busca si hay alguna actividad asociada a un evento y con un ID específico
     *
     * @param eventosId El ID del evento con respecto al cual se busca
     * @param actividadEventoEventosId El ID de la actividad buscada
     * @return La actividad encontrada o null. Nota: Si existe una o más
     * actividades devuelve siempre la primera que encuentra
     */
    public ActividadEventoEntity find(Long eventosId, Long actividadEventoEventosId) {
        LOGGER.log(Level.INFO, "Consultando la actividad del evento con id={0}", actividadEventoEventosId);
        TypedQuery<ActividadEventoEntity> q = em.createQuery("select p from ActividadEventoEntity p where (p.evento.id = :eventoid) and (p.id = :actividadesEventoId)", ActividadEventoEntity.class);
        q.setParameter("eventoid", eventosId);
        q.setParameter("actividadesEventoId", actividadEventoEventosId);
        List<ActividadEventoEntity> results = q.getResultList();
        ActividadEventoEntity actividad = null;
        if (results != null && !results.isEmpty()) {
            actividad = results.get(0);
        }
        LOGGER.log(Level.INFO, "Saliendo de consultar la actividad del evento con id = {0}", actividadEventoEventosId);
        return actividad;
    }

    /**
     * Actualiza una actividad.
     *
     * @param actividadEvento: la actividad que viene con los nuevos cambios.
     * @return una actividad con los cambios aplicados.
     */
    public ActividadEventoEntity update(ActividadEventoEntity actividadEvento) {
        LOGGER.log(Level.INFO, "Actualizando la actividad del evento con id={0}", actividadEvento.getId());
        return em.merge(actividadEvento);
    }

    /**
     * Borra una actividad de la base de datos recibiendo como argumento el id
     * de la actividad
     *
     * @param actividadEventoId: id correspondiente a la actividad a borrar.
     */
    public void delete(Long actividadEventoId) {
        LOGGER.log(Level.INFO, "Borrando la actividad del evento con id={0}", actividadEventoId);
        ActividadEventoEntity entity = em.find(ActividadEventoEntity.class, actividadEventoId);
        em.remove(entity);
        LOGGER.log(Level.INFO, "Saliendo de borrar la actididad con id = {0}", actividadEventoId);
    }
}
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.eventos.ejb;

import co.edu.uniandes.csw.eventos.entities.ActividadEventoEntity;
import co.edu.uniandes.csw.eventos.entities.EventoEntity;
import co.edu.uniandes.csw.eventos.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.eventos.persistence.ActividadEventoPersistence;
import co.edu.uniandes.csw.eventos.persistence.EventoPersistence;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author Germán David Martínez
 */
@Stateless
public class ActividadEventoLogic {

    private static final Logger LOGGER = Logger.getLogger(EventoLogic.class.getName());

    @Inject
    private ActividadEventoPersistence persistence;

    @Inject
    private EventoPersistence eventoPersistence;

    public ActividadEventoEntity createActividadEvento(Long eventosId, ActividadEventoEntity actividad) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de crear actividad del evento");

        if (actividad.getNombre() == null) {
            throw new BusinessLogicException("El nombre de la actividad está vacío");
        }
        if (actividad.getDescripcion() == null) {
            throw new BusinessLogicException("Debe escribir una descripción");
        }
        if (actividad.getFecha() == null) {
            throw new BusinessLogicException("La fecha no puede ser vacía");
        }
        if (actividad.getHoraInicio() == null) {
            throw new BusinessLogicException("La hora inicial no puede ser vacía");
        }
        if (actividad.getHoraFin() == null) {
            throw new BusinessLogicException("La hora final no puede ser vacía");
        }

        EventoEntity evento = eventoPersistence.find(eventosId);
        actividad.setEvento(evento);

        LOGGER.log(Level.INFO, "Termina proceso de creación de la actividad del evento");

        actividad = persistence.create(actividad);
        return actividad;
    }

    public List<ActividadEventoEntity> getActividadesEvento(Long eventosId) {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar todas las actividades asociados al evento con id = {0}", eventosId);
        EventoEntity eventoEntity = eventoPersistence.find(eventosId);
        LOGGER.log(Level.INFO, "Termina proceso de consultar todas las actividades asociados al evento con id = {0}", eventosId);
        return eventoEntity.getActividadesEvento();
    }

    public ActividadEventoEntity getActividadEvento(Long eventosId, Long actividadesEventoId) {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar la actividad del evento con id = {0} del evento con id = " + eventosId, actividadesEventoId);
        return persistence.find(eventosId, actividadesEventoId);
    }

    public ActividadEventoEntity updateActividadEvento(Long eventosId, ActividadEventoEntity actividadEventoEntity) {
        LOGGER.log(Level.INFO, "Inicia proceso de actualizar la editorial con id = {0} del evento con id = " + eventosId, actividadEventoEntity.getId());

        EventoEntity eventoEntity = eventoPersistence.find(eventosId);
        actividadEventoEntity.setEvento(eventoEntity);
        persistence.update(actividadEventoEntity);
        LOGGER.log(Level.INFO, "Termina proceso de actualizar la editorial con id = {0} del evento con id = " + eventosId, actividadEventoEntity.getId());
        return actividadEventoEntity;
    }

    public void deleteActividadEvento(Long eventosId, Long actividadesEventosId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de borrar la editorial con id = {0} del evento con id = " + eventosId, actividadesEventosId);
        ActividadEventoEntity old = getActividadEvento(eventosId, actividadesEventosId);
        if (old == null) {
            throw new BusinessLogicException("La actividad con id = " + actividadesEventosId + " no esta asociado al evento con id = " + eventosId);
        }
        persistence.delete(old.getId());
        LOGGER.log(Level.INFO, "Termina proceso de borrar la editorial con id = {0} del evento con id = " + eventosId, actividadesEventosId);
    }
}

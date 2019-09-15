/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.eventos.ejb;

import co.edu.uniandes.csw.eventos.entities.ActividadEventoEntity;
import co.edu.uniandes.csw.eventos.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.eventos.persistence.ActividadEventoPersistence;
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

    public ActividadEventoEntity createActividadEvento(ActividadEventoEntity actividad) throws BusinessLogicException {
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

        actividad = persistence.create(actividad);
        return actividad;
    }

    public List<ActividadEventoEntity> getEventos() {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar todas las actividades");
        List<ActividadEventoEntity> actividadesEventos = persistence.findAll();
        LOGGER.log(Level.INFO, "Termina proceso de consultar todas las actividades");
        return actividadesEventos;
    }

    public ActividadEventoEntity getEvento(Long eventosId) {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar el evento con id = {0}", eventosId);
        ActividadEventoEntity eventoEntity = persistence.find(eventosId);
        if (eventoEntity == null) {
            LOGGER.log(Level.SEVERE, "El evento con el id = {0} no existe", eventosId);
        }
        LOGGER.log(Level.INFO, "Termina proceso de consultar la editorial con id = {0}", eventosId);
        return eventoEntity;
    }

    public ActividadEventoEntity updateEvento(Long eventosId, ActividadEventoEntity actividadEventoEntity) {
        LOGGER.log(Level.INFO, "Inicia proceso de actualizar la editorial con id = {0}", eventosId);
        ActividadEventoEntity newEntity = persistence.update(actividadEventoEntity);
        LOGGER.log(Level.INFO, "Termina proceso de actualizar la editorial con id = {0}", actividadEventoEntity.getId());
        return newEntity;
    }

    public void deleteEvento(Long actividadesEventosId) {
        LOGGER.log(Level.INFO, "Inicia proceso de borrar la editorial con id = {0}", actividadesEventosId);
        persistence.delete(actividadesEventosId);
        LOGGER.log(Level.INFO, "Termina proceso de borrar la editorial con id = {0}", actividadesEventosId);
    }
}

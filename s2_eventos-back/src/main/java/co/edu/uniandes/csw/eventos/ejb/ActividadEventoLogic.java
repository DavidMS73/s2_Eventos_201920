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
 * Clase que implementa la conexión con las persistencia para la entidad
 * ActividadesEvento
 *
 * @author Germán David Martínez
 */
@Stateless
public class ActividadEventoLogic {

    /**
     * Logger de la actividad del evento
     */
    private static final Logger LOGGER = Logger.getLogger(EventoLogic.class.getName());

    /**
     * Persistencia de la actividad
     */
    @Inject
    private ActividadEventoPersistence persistence;

    /**
     * Persistencia del evento
     */
    @Inject
    private EventoPersistence eventoPersistence;

    /**
     * Crea un evento en la persistencia
     *
     * @param eventosId id del evento donde pertenece la actividad
     * @param actividad entidad a persistir
     * @return entidad de la actividad luego de persistirla
     * @throws BusinessLogicException si no cumple las reglas de negocio
     */
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

        if (actividad.getFecha().before(evento.getFechaInicio())) {
            throw new BusinessLogicException("La fecha de la actividad no puede estar por fuera de las fechas del evento");
        }
        if (actividad.getFecha().after(evento.getFechaFin())) {
            throw new BusinessLogicException("La fecha de la actividad no puede estar por fuera de las fechas del evento");
        }

        LOGGER.log(Level.INFO, "Termina proceso de creación de la actividad del evento");

        actividad = persistence.create(actividad);
        return actividad;
    }

    /**
     * Obtiene la lista de los registros de ActividadEvento que pertenecen a un
     * Evento.
     *
     * @param eventosId id del evento el cual es padre de las actividades.
     * @return Colección de objetos de ActividadEventoEntity.
     */
    public List<ActividadEventoEntity> getActividadesEvento(Long eventosId) {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar las actividad asociadas al evento con id = {0}", eventosId);
        EventoEntity eventoEntity = eventoPersistence.find(eventosId);
        LOGGER.log(Level.INFO, "Termina proceso de consultar las actividad asociadas al evento con id = {0}", eventosId);
        return eventoEntity.getActividadesEvento();
    }

    /**
     * Obtiene los datos de una instancia de ActividadEvento a partir de su ID.
     * La existencia del elemento padre Evento se debe garantizar.
     *
     * @param eventosId El id del evento buscado
     * @param actividadesId Identificador de la actividad a consultar
     * @return Instancia de ActividadEventoEntity con los datos de la actividad
     * consultada.
     *
     */
    public ActividadEventoEntity getActividadEvento(Long eventosId, Long actividadesId) {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar la actividad con id = {0} del evento con id = " + eventosId, actividadesId);
        return persistence.find(eventosId, actividadesId);
    }

    /**
     * Actualizar una actividad por Id
     *
     * @param eventosId: id del evento
     * @param actividadEventoEntity entidad de la actividad con los cambios
     * deseados
     * @return actividad con los cambios actualizados en la base de datos
     */
    public ActividadEventoEntity updateActividadEvento(Long eventosId, ActividadEventoEntity actividadEventoEntity) {
        LOGGER.log(Level.INFO, "Inicia proceso de actualizar la editorial con id = {0} del evento con id = " + eventosId, actividadEventoEntity.getId());

        EventoEntity eventoEntity = eventoPersistence.find(eventosId);
        actividadEventoEntity.setEvento(eventoEntity);
        persistence.update(actividadEventoEntity);
        LOGGER.log(Level.INFO, "Termina proceso de actualizar la editorial con id = {0} del evento con id = " + eventosId, actividadEventoEntity.getId());
        return actividadEventoEntity;
    }

    /**
     * Elimina una instancia de Actividad de la base de datos.
     *
     * @param actividadesId Identificador de la instancia a eliminar.
     * @param eventosId id del Evento el cual es padre de la actividad.
     * @throws BusinessLogicException Si la actividad no está asociada al
     * evento.
     *
     */
    public void deleteActividadEvento(Long eventosId, Long actividadesId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de borrar la actividad con id = {0} del evento con id = " + eventosId, actividadesId);
        ActividadEventoEntity old = getActividadEvento(eventosId, actividadesId);
        if (old == null) {
            throw new BusinessLogicException("La actividad con id = " + actividadesId + " no esta asociado al evento con id = " + eventosId);
        }
        persistence.delete(old.getId());
        LOGGER.log(Level.INFO, "Termina proceso de borrar la actividad con id = {0} del evento con id = " + eventosId, actividadesId);
    }
}

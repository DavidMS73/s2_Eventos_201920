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
import co.edu.uniandes.csw.eventos.persistence.MultimediaPersistence;
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
     * Persistencia del multimedia
     */
    @Inject
    private MultimediaPersistence multimediaPersistence;
    
    /**
     * Persistencia del evento
     */
    @Inject
    private EventoPersistence eventoPersistence;

    /**
     * Crea una actividad en la persistencia
     *
     * @param actividad entidad a persistir
     * @return entidad de la actividad luego de persistirla
     * @throws BusinessLogicException si no cumple las reglas de negocio
     */
    public ActividadEventoEntity createActividadEvento(ActividadEventoEntity actividad) throws BusinessLogicException {
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
        if (actividad.getEvento() == null || eventoPersistence.find(actividad.getEvento().getId()) == null) {
            throw new BusinessLogicException("El evento es inválido");
        }
       /* if (actividad.getMultimedia() == null || multimediaPersistence.find(actividad.getMultimedia().getId()) == null) {
            throw new BusinessLogicException("El multimedia es inválido");
        }
        */
        actividad = persistence.create(actividad);
        
        LOGGER.log(Level.INFO, "Termina proceso de creación de la actividad");

        return actividad;
    }

    /**
     * Obtiene la lista de los registros de ActividadEvento.
     *
     * @return Colección de objetos de ActividadEventoEntity.
     */
    public List<ActividadEventoEntity> getActividadesEvento() {
        LOGGER.log(Level.INFO, "Se empieza el proceso de buscar actividades.");
        List<ActividadEventoEntity> actividades = persistence.findAll();
        LOGGER.log(Level.INFO, "Se termina el proceso de buscar actividades");
        return actividades;
    }

    /**
     * Obtiene los datos de una instancia de ActividadEvento a partir de su ID.
     *
     * @param actividadesId Identificador de la actividad a consultar
     * @return Instancia de ActividadEventoEntity con los datos de la actividad
     * consultada.
     *
     */
    public ActividadEventoEntity getActividadEvento(Long actividadesId) {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar la actividad con id = {0}", actividadesId);
        return persistence.find(actividadesId);
    }

    /**
     * Actualizar una actividad por Id
     *
     * @param actividadEventoEntity entidad de la actividad con los cambios
     * deseados
     * @return actividad con los cambios actualizados en la base de datos
     */
    public ActividadEventoEntity updateActividadEvento(ActividadEventoEntity actividadEventoEntity) {
        LOGGER.log(Level.INFO, "Inicia proceso de actualizar la actividad con id = {0} ", actividadEventoEntity.getId());

        persistence.update(actividadEventoEntity);
        LOGGER.log(Level.INFO, "Termina proceso de actualizar la actividad con id = {0} ", actividadEventoEntity.getId());
        return actividadEventoEntity;
    }

    /**
     * Elimina una instancia de Actividad de la base de datos.
     *
     * @param actividadesId Identificador de la instancia a eliminar.
     * @throws BusinessLogicException Si la actividad no está asociada al
     * evento.
     *
     */
    public void deleteActividadEvento(Long actividadesId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de borrar la actividad con id = {0}", actividadesId);
        ActividadEventoEntity old = getActividadEvento(actividadesId);
        persistence.delete(old.getId());
        LOGGER.log(Level.INFO, "Termina proceso de borrar la actividad con id = {0} ", actividadesId);
    }
}

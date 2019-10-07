/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.eventos.ejb;

import co.edu.uniandes.csw.eventos.entities.ActividadEventoEntity;
import co.edu.uniandes.csw.eventos.entities.EventoEntity;
import co.edu.uniandes.csw.eventos.persistence.ActividadEventoPersistence;
import co.edu.uniandes.csw.eventos.persistence.EventoPersistence;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;


/**
 * Clase que implementa la conexion con la persistencia para la relación entre
 * la entidad de Envento y ActividadEvento.
 *
 * @author Albéric Despres
 */
@Stateless
public class EventoActividadesEventoLogic {

    private static final Logger LOGGER = Logger.getLogger(EventoActividadesEventoLogic.class.getName());

    @Inject
    private ActividadEventoPersistence actividadPersistence;

    @Inject
    private EventoPersistence eventoPersistence;

    /**
     * Agregar una actividad al evento
     *
     * @param actividadesId El id de la actividad a guardar
     * @param eventosId El id del evento en la cual se va a guardar la actividad.
     * @return la actividad creado.
     */
    public ActividadEventoEntity addActividad(Long actividadesId, Long eventosId) {
        LOGGER.log(Level.INFO, "Inicia proceso de agregarle una actividad al evento con id = {0}", eventosId);
        EventoEntity eventoEntity = eventoPersistence.find(eventosId);
        ActividadEventoEntity actividadEntity = actividadPersistence.find(eventosId, actividadesId);
        actividadEntity.setEvento(eventoEntity);
        LOGGER.log(Level.INFO, "Termina proceso de agregarle una actividad al evento con id = {0}", eventosId);
        return actividadEntity;
    }
    
    /**
     * Desasocia una actividad existente de un evento existente
     *
     * @param eventosId Identificador de la instancia de Evento
     * @param actividadesId Identificador de la instancia de ActividadEvento
     */
    public void removeActividad(Long eventosId, Long actividadesId) {
        LOGGER.log(Level.INFO, "Inicia proceso de borrar una actividad del evento con id = {0}", eventosId);
        EventoEntity eventoEntity = eventoPersistence.find(eventosId);
        ActividadEventoEntity actividadEntity = actividadPersistence.find(eventosId, actividadesId);
        eventoEntity.getActividadesEvento().remove(actividadEntity);
        LOGGER.log(Level.INFO, "Termina proceso de borrar una actividad del evento con id = {0}", eventosId);
    }
}

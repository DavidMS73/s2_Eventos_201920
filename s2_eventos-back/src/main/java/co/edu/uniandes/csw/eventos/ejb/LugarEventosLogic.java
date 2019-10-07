/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.eventos.ejb;

import co.edu.uniandes.csw.eventos.entities.EventoEntity;
import co.edu.uniandes.csw.eventos.entities.LugarEntity;
import co.edu.uniandes.csw.eventos.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.eventos.persistence.EventoPersistence;
import co.edu.uniandes.csw.eventos.persistence.LugarPersistence;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 * Clase que implementa la conexion con la persistencia para la relación entre
 * la entidad de Lugar y Evento.
 *
 * @author Germán David Martínez Solano
 *
 */
@Stateless
public class LugarEventosLogic {

    private static final Logger LOGGER = Logger.getLogger(LugarEventosLogic.class.getName());

    @Inject
    private EventoPersistence eventoPersistence;

    @Inject
    private LugarPersistence lugarPersistence;

    public EventoEntity addEvento(Long lugaresId, Long eventosId) {
        LOGGER.log(Level.INFO, "Inicia proceso de asociarle un evento al lugar con id = {0}", lugaresId);
        LugarEntity lugarEntity = lugarPersistence.find(lugaresId);
        EventoEntity eventoEntity = eventoPersistence.find(eventosId);
        eventoEntity.getLugares().add(lugarEntity);
        LOGGER.log(Level.INFO, "Termina proceso de asociarle un evento al lugar con id = {0}", lugaresId);
        return eventoPersistence.find(eventosId);
    }

    public List<EventoEntity> getEventos(Long lugaresId) {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar todos los eventos del lugar con id = {0}", lugaresId);
        return lugarPersistence.find(lugaresId).getEventos();
    }

    public EventoEntity getEvento(Long lugaresId, Long eventosId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar el evento con id = {0} del lugar con id = " + lugaresId, eventosId);
        List<EventoEntity> eventos = lugarPersistence.find(lugaresId).getEventos();
        EventoEntity eventoEntity = eventoPersistence.find(eventosId);
        int index = eventos.indexOf(eventoEntity);
        LOGGER.log(Level.INFO, "Termina proceso de consultar el evento con id = {0} del lugar con id = " + lugaresId, eventosId);
        if (index >= 0) {
            return eventos.get(index);
        }
        throw new BusinessLogicException("El evento no está asociado al lugar");
    }

    public List<EventoEntity> replaceEventos(Long lugarId, List<EventoEntity> eventos) {
        LOGGER.log(Level.INFO, "Inicia proceso de reemplazar los eventos asociados al lugar con id = {0}", lugarId);
        LugarEntity lugarEntity = lugarPersistence.find(lugarId);
        List<EventoEntity> eventoList = eventoPersistence.findAll();
        for (EventoEntity evento : eventoList) {
            if (eventos.contains(evento)) {
                if (!evento.getLugares().contains(lugarEntity)) {
                    evento.getLugares().add(lugarEntity);
                }
            } else {
                evento.getLugares().remove(lugarEntity);
            }
        }
        lugarEntity.setEventos(eventos);
        LOGGER.log(Level.INFO, "Termina proceso de reemplazar los eventos asociados al lugar con id = {0}", lugarId);
        return lugarEntity.getEventos();
    }

    public void removeEvento(Long lugaresId, Long eventosId) {
        LOGGER.log(Level.INFO, "Inicia proceso de borrar un evento del lugar con id = {0}", lugaresId);
        LugarEntity authorEntity = lugarPersistence.find(lugaresId);
        EventoEntity bookEntity = eventoPersistence.find(eventosId);
        bookEntity.getLugares().remove(authorEntity);
        LOGGER.log(Level.INFO, "Termina proceso de borrar un evento del lugar con id = {0}", lugaresId);
    }
}

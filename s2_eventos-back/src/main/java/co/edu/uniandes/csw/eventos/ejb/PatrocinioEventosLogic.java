/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.eventos.ejb;

import co.edu.uniandes.csw.eventos.entities.EventoEntity;
import co.edu.uniandes.csw.eventos.entities.PatrocinioEntity;
import co.edu.uniandes.csw.eventos.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.eventos.persistence.EventoPersistence;
import co.edu.uniandes.csw.eventos.persistence.PatrocinioPersistence;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author Daniel Betancurth Dorado
 */
@Stateless
public class PatrocinioEventosLogic {
    private static final Logger LOGGER = Logger.getLogger(PatrocinioEventosLogic.class.getName());

    @Inject
    private EventoPersistence eventoPersistence;

    @Inject
    private PatrocinioPersistence patrocinioPersistence;
    
    public EventoEntity addEvento(Long patrociniosId, Long eventosId) {
        LOGGER.log(Level.INFO, "Inicia proceso de asociarle un evento al patrocinio con id = {0}", patrociniosId);
        PatrocinioEntity patrocinioEntity = patrocinioPersistence.find(patrociniosId);
        EventoEntity eventoEntity = eventoPersistence.find(eventosId);
        eventoEntity.getPatrocinios().add(patrocinioEntity);
        LOGGER.log(Level.INFO, "Termina proceso de asociarle un evento al patrocinio con id = {0}", patrociniosId);
        return eventoPersistence.find(eventosId);
    }
    
     public List<EventoEntity> getEventos(Long patrociniosId) {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar todos los eventos del patrocinador con id = {0}", patrociniosId);
        return patrocinioPersistence.find(patrociniosId).getEventos();
    }
     public EventoEntity getEvento(Long patrociniosId, Long eventosId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar el evento con id = {0}", eventosId);
        List<EventoEntity> eventos = patrocinioPersistence.find(patrociniosId).getEventos();
        EventoEntity eventoEntity = eventoPersistence.find(eventosId);
        int index = eventos.indexOf(eventoEntity);
        LOGGER.log(Level.INFO, "Termina proceso de consultar el evento con id = {0}", eventosId);
        if (index >= 0) {
            return eventos.get(index);
        }
        throw new BusinessLogicException("El evento no está asociado al patrocinio");
    }
     
     public List<EventoEntity> replaceEventos(Long patrociniosId, List<EventoEntity> eventos) {
        LOGGER.log(Level.INFO, "Inicia proceso de reemplazar los eventos asociados al patrocinio con id = {0}", patrociniosId);
        PatrocinioEntity patrocinioEntity = patrocinioPersistence.find(patrociniosId);
        List<EventoEntity> eventoList = eventoPersistence.findAll();
        for (EventoEntity evento : eventoList) {
            if (eventos.contains(evento)) {
                if (!evento.getPatrocinios().contains(patrocinioEntity)) {
                    evento.getPatrocinios().add(patrocinioEntity);
                }
            } else {
                evento.getPatrocinios().remove(patrocinioEntity);
            }
        }
        patrocinioEntity.setEventos(eventos);
        LOGGER.log(Level.INFO, "Termina proceso de reemplazar los eventos asociados al patrocinio con id = {0}", patrociniosId);
        return patrocinioEntity.getEventos();
    }

    public void removeEvento(Long patrociniosId, Long eventosId) {
        LOGGER.log(Level.INFO, "Inicia proceso de borrar un evento del patrocinio con id = {0}", patrociniosId);
        PatrocinioEntity patrocinioEntity = patrocinioPersistence.find(patrociniosId);
        EventoEntity bookEntity = eventoPersistence.find(eventosId);
        bookEntity.getPatrocinios().remove(patrocinioEntity);
        LOGGER.log(Level.INFO, "Termina proceso de borrar un evento del patrocinio con id = {0}", patrociniosId);
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.eventos.ejb;

import co.edu.uniandes.csw.eventos.entities.EventoEntity;
import co.edu.uniandes.csw.eventos.entities.PatrocinioEntity;
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
public class EventoPatrociniosLogic 
{
    private static final Logger LOGGER = Logger.getLogger(EventoPatrociniosLogic.class.getName());

    @Inject
    private EventoPersistence eventoPersistence;

    @Inject
    private PatrocinioPersistence patrocinioPersistence;
    
     public PatrocinioEntity addPatrocinio(Long eventosId, Long patrociniosId) {
        LOGGER.log(Level.INFO, "Inicia proceso de agregarle un patrocinio al evento con id = {0}", eventosId);
        PatrocinioEntity patrocinioEntity = patrocinioPersistence.find(patrociniosId);
        EventoEntity eventoEntity = eventoPersistence.find(eventosId);
        eventoEntity.getPatrocinios().add(patrocinioEntity);
        LOGGER.log(Level.INFO, "Termina proceso de agregarle un patrocinio al evento con id = {0}", eventosId);
        return patrocinioPersistence.find(patrociniosId);
    }
     public List<PatrocinioEntity> getPatrocinios(Long eventosId) {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar los patrocinadores asociados al evento con id = {0}", eventosId);
        return eventoPersistence.find(eventosId).getPatrocinios();
    }
     public PatrocinioEntity getPatrocinio(Long eventosId, Long patrociniosId) {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar un patrocinio del evento con id = {0}", eventosId);
        List<PatrocinioEntity> patrocinios = eventoPersistence.find(eventosId).getPatrocinios();
        PatrocinioEntity lugarEntity = patrocinioPersistence.find(patrociniosId);
        int index = patrocinios.indexOf(lugarEntity);
        LOGGER.log(Level.INFO, "Termina proceso de consultar un patrocinio del evento con id = {0}", eventosId);
        if (index >= 0) {
            return patrocinios.get(index);
        }
        return null;
    }
      public List<PatrocinioEntity> replacePatrocinios(Long eventosId, List<PatrocinioEntity> list) {
        LOGGER.log(Level.INFO, "Inicia proceso de reemplazar los lugares del evento con id = {0}", eventosId);
        EventoEntity eventoEntity = eventoPersistence.find(eventosId);
        eventoEntity.setPatrocinios(list);
        LOGGER.log(Level.INFO, "Termina proceso de reemplazar los lugares del evento con id = {0}", eventosId);
        return eventoPersistence.find(eventosId).getPatrocinios();
    }

    public void removePatrocinio(Long eventosId, Long lugaresId) {
        LOGGER.log(Level.INFO, "Inicia proceso de borrar un lugar del evento con id = {0}", eventosId);
        PatrocinioEntity patrocinioEntity = patrocinioPersistence.find(lugaresId);
        EventoEntity eventoEntity = eventoPersistence.find(eventosId);
        eventoEntity.getPatrocinios().remove(patrocinioEntity);
        LOGGER.log(Level.INFO, "Termina proceso de borrar un lugar del evento con id = {0}", eventosId);
    }
}

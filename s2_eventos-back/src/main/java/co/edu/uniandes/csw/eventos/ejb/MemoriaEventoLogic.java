/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.eventos.ejb;

import co.edu.uniandes.csw.eventos.entities.EventoEntity;
import co.edu.uniandes.csw.eventos.entities.MemoriaEntity;
import co.edu.uniandes.csw.eventos.persistence.EventoPersistence;
import co.edu.uniandes.csw.eventos.persistence.MemoriaPersistence;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;

/**
 *
 * @author Daniel Betancurth Dorado
 */
public class MemoriaEventoLogic 
{
    private static final Logger LOGGER = Logger.getLogger(MemoriaEventoLogic.class.getName());

    @Inject
    private MemoriaPersistence memoriaPersistence;

    @Inject
    private EventoPersistence eventoPersistence;
    
    public MemoriaEntity replaceEvento(Long memoriasId, Long eventosId) {
        LOGGER.log(Level.INFO, "Inicia proceso de actualizar la memoria con id = {0}", memoriasId);
        EventoEntity eventoEntity = eventoPersistence.find(eventosId);
        MemoriaEntity memoriaEntity = memoriaPersistence.find(memoriasId);
        memoriaEntity.setEvento(eventoEntity);
        memoriaPersistence.update(memoriaEntity);
        LOGGER.log(Level.INFO, "Termina proceso de actualizar la memoria con id = {0}", memoriaEntity.getId());
        return memoriaEntity;
    }
    
     public void removeEvento(Long memoriasId) {
        LOGGER.log(Level.INFO, "Inicia proceso de borrar el evento de la memoria con id = {0}", memoriasId);
        MemoriaEntity memoria = memoriaPersistence.find(memoriasId);
        EventoEntity evento = eventoPersistence.find(memoria.getEvento().getId());
        memoria.setEvento(null);
        memoriaPersistence.update(memoria);
        LOGGER.log(Level.INFO, "Termina procea.getEvento().getId()so de borrar el evento de la memoria con id = {0}", memoria.getId());
    }
}

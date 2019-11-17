/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.eventos.ejb;

import co.edu.uniandes.csw.eventos.entities.EventoEntity;
import co.edu.uniandes.csw.eventos.entities.MemoriaEntity;
import co.edu.uniandes.csw.eventos.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.eventos.persistence.EventoPersistence;
import co.edu.uniandes.csw.eventos.persistence.MemoriaPersistence;
import java.util.ArrayList;
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
public class EventoMemoriasLogic {

    private static final Logger LOGGER = Logger.getLogger(EventoMemoriasLogic.class.getName());

    @Inject
    private MemoriaPersistence memoriaPersistence;

    @Inject
    private EventoPersistence eventoPersistence;

    public MemoriaEntity addMemoria(Long memoriasId, Long eventosId) {
        LOGGER.log(Level.INFO, "Inicia proceso de agregarle una memoria al evento con id = {0}", eventosId);
        EventoEntity eventoEntity = eventoPersistence.find(eventosId);
        MemoriaEntity memoriaEntity = memoriaPersistence.find(memoriasId);
        memoriaEntity.setEvento(eventoEntity);
        LOGGER.log(Level.INFO, "Termina proceso de agregarle una memoria al evento con id = {0}", eventosId);
        return memoriaEntity;
    }

    public List<MemoriaEntity> getMemorias(Long eventosId) {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar las memorias asociados al evento con id = {0}", eventosId);
        return eventoPersistence.find(eventosId).getMemorias();
    }

    public MemoriaEntity getMemoria(Long eventosId, Long memoriasId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar la memoria con id = {0}", memoriasId);
        List<MemoriaEntity> memorias = eventoPersistence.find(eventosId).getMemorias();
        MemoriaEntity memoriaEntity = memoriaPersistence.find(memoriasId);
        int index = memorias.indexOf(memoriaEntity);
        LOGGER.log(Level.INFO, "Termina proceso de consultar la memoria con id = {0}", memoriasId);
        if (index >= 0) {
            return memorias.get(index);
        }
        throw new BusinessLogicException("La memoria no está asociada al evento");
    }

    public List<MemoriaEntity> replaceMemorias(Long eventosId, List<MemoriaEntity> memorias) {

        LOGGER.log(Level.INFO, "Inicia proceso de actualizar el evento con id = {0}", eventosId);
        EventoEntity eventoEntity = eventoPersistence.find(eventosId);
        
        for (MemoriaEntity m : eventoEntity.getMemorias()) {
            m.setEvento(null);
            memoriaPersistence.update(m);
        }
        eventoEntity.setMemorias(new ArrayList<>());

        for (MemoriaEntity memoria : memorias) {
            memoria.setEvento(eventoEntity);
            eventoEntity.addMemorias(memoria);
            memoriaPersistence.update(memoria);
        }

        LOGGER.log(Level.INFO, "Termina proceso de actualizar el eveento con id = {0}", eventosId);
        return memorias;

    }
}

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
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author Alberic Despres
 */
@Stateless
public class MemoriaLogic {

    private static final Logger LOGGER = Logger.getLogger(MemoriaLogic.class.getName());

    @Inject
    private MemoriaPersistence persistence;

    @Inject
    private EventoPersistence eventoPersistence;

    public MemoriaEntity createMemoria(Long eventosId, MemoriaEntity memoria) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de crear memoria del evento");

        if (memoria.getLugar() == null) {
            throw new BusinessLogicException("El lugar no puede estar vacio");
        }
        if (memoria.getFecha() == null) {
            throw new BusinessLogicException("La fecha de la memoria no puede estar vacia");
        }
        if (memoria.getImagen() == null) {
            throw new BusinessLogicException("La imagen de la memoria no puede estar vacia");
        }
        EventoEntity evento = eventoPersistence.find(eventosId);
        memoria.setEvento(evento);
        LOGGER.log(Level.INFO, "Termina proceso de creación de la actividad del evento");
        memoria = persistence.create(memoria);
        return memoria;
    }

    public List<MemoriaEntity> getMemorias(Long eventosId) {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar las memorias asociadas al evento con id = {0}", eventosId);
        EventoEntity eventoEntity = eventoPersistence.find(eventosId);
        LOGGER.log(Level.INFO, "Termina proceso de consultar las memorias asociadas al evento con id = {0}", eventosId);
        return eventoEntity.getMemorias();
    }

    public MemoriaEntity getMemoria(Long eventosId, Long memoriaId) {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar la memoria con id = {0}", memoriaId);
        return persistence.find(eventosId, memoriaId);
    }

    public MemoriaEntity updateMemoria(Long eventosId, MemoriaEntity memoriaEntity) {
        LOGGER.log(Level.INFO, "Inicia proceso de actualizar la memoria con id = {0}", memoriaEntity.getId());
        EventoEntity eventoEntity = eventoPersistence.find(eventosId);
        memoriaEntity.setEvento(eventoEntity);
        persistence.update(memoriaEntity);
        LOGGER.log(Level.INFO, "Termina proceso de actualizar la memoria con id = {0}", memoriaEntity.getId());
        return memoriaEntity;
    }

    public void deleteMemoria(Long eventosId, Long memoriasId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de borrar la memoria con id = {0}", memoriasId);
        MemoriaEntity old = getMemoria(eventosId, memoriasId);
        if (old == null) {
            throw new BusinessLogicException("La memoria con id = " + memoriasId + " no está asociada al evento con id = " + eventosId);
        }
        persistence.delete(old.getId());
        LOGGER.log(Level.INFO, "Termina proceso de borrar la memoria con id = {0}", memoriasId);
    }
}

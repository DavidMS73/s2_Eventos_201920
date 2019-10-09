/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.eventos.ejb;

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

    public MemoriaEntity createMemoria(MemoriaEntity memoria) throws BusinessLogicException {

        if (memoria.getLugar() == null || memoria.getFecha() == null) {
            throw new BusinessLogicException("El lugar o la fecha de memoria esta vacio");
        }
        if (memoria.getEvento() == null || eventoPersistence.find(memoria.getEvento().getId()) == null) {
            throw new BusinessLogicException("El evento inválido");
        }
        memoria = persistence.create(memoria);
        return memoria;
    }

    public List<MemoriaEntity> getMemorias() {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar todos las memorias");
        List<MemoriaEntity> memorias = persistence.findAll();
        LOGGER.log(Level.INFO, "Termina proceso de consultar todos las memorias");
        return memorias;
    }

    public MemoriaEntity getMemoria(Long memoriaId) {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar la memoria con id = {0}", memoriaId);
        MemoriaEntity memoria = persistence.find(memoriaId);
        if (memoria == null) {
            LOGGER.log(Level.SEVERE, "La memoria con el id = {0} no existe", memoriaId);
        }
        LOGGER.log(Level.INFO, "Termina proceso de consultar la memoria con id = {0}", memoriaId);
        return memoria;
    }

    public MemoriaEntity updateMemoria(Long memoriaId, MemoriaEntity memoria) {
        LOGGER.log(Level.INFO, "Inicia proceso de actualizar la memoria con id = {0}", memoriaId);
        MemoriaEntity newEntity = persistence.update(memoria);
        LOGGER.log(Level.INFO, "Termina proceso de actualizar la memoria con id = {0}", memoria.getId());
        return newEntity;
    }

    public void deleteMemoria(Long memoriaId) {
        LOGGER.log(Level.INFO, "Inicia proceso de borrar la memoria con id = {0}", memoriaId);
        persistence.delete(memoriaId);
        LOGGER.log(Level.INFO, "Termina proceso de borrar la memoria con id = {0}", memoriaId);
    }
}

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
public class PatrocinioLogic {

    private static final Logger LOGGER = Logger.getLogger(PatrocinioLogic.class.getName());

    @Inject
    private PatrocinioPersistence persistence;
    @Inject
    private EventoPersistence eventoPersistence;

    public PatrocinioEntity createPatrocinio(PatrocinioEntity patrocinio) throws BusinessLogicException {
        if (patrocinio.getEmpresa() == null) {
            throw new BusinessLogicException("El patrocinio no tiene empresa");
        }
        if(patrocinio.getTipo()==null){
            throw new BusinessLogicException("El patrocinio no tiene su tipo");
        }
        if(persistence.findByName(patrocinio.getEmpresa())!=null)
        {
           throw new BusinessLogicException("Esa empresa ya existe");

        }
        patrocinio = persistence.create(patrocinio);
        return patrocinio;
    }
    public List<PatrocinioEntity> getPatrocinios()
    {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar todos los patrocinios");
        List<PatrocinioEntity> patrocinios = persistence.findAll();
        LOGGER.log(Level.INFO, "Termina proceso de consultar todos los patrocinios");
        return patrocinios;
    }
    
    public PatrocinioEntity getPatrocinio(Long patrociniosId)
    {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar el patrocinio con id = {0}", patrociniosId);
        PatrocinioEntity patrocinioEntity = persistence.find(patrociniosId);
        if(patrocinioEntity == null)
        {
            LOGGER.log(Level.INFO, "El patrocinio con el id = {0} no existe", patrociniosId);
        }
        LOGGER.log(Level.INFO, "Termina proceso de consultar el patrocinio con id = {0}", patrociniosId);
        return patrocinioEntity;
    }
    
    public PatrocinioEntity updatePatrocinio(Long patrociniosId, PatrocinioEntity patrocinioEntity)
    {
        LOGGER.log(Level.INFO, "Inicia proceso de actualizar el patrocinio con id = {0}", patrociniosId);
        PatrocinioEntity newEntity = persistence.update(patrocinioEntity);
        LOGGER.log(Level.INFO, "Termina proceso de actualizar el patrocinio con id = {0}", patrocinioEntity.getId());
        return newEntity;
    }
    
    public void deletePatrocinio(Long patrociniosId) throws BusinessLogicException
    {
        LOGGER.log(Level.INFO, "Inicia proceso de borrar el patrocinio con id = {0}", patrociniosId);
        List<EventoEntity> eventos = getPatrocinio(patrociniosId).getEventos();
        if (eventos != null && !eventos.isEmpty()) {
            throw new BusinessLogicException("No se puede borrar el patrocinio con id = " + patrociniosId + " porque tiene eventos asociados");
        }
        persistence.delete(patrociniosId);
        LOGGER.log(Level.INFO, "Termina proceso de borrer el patrocinio con id = {0}", patrociniosId);
    }


}

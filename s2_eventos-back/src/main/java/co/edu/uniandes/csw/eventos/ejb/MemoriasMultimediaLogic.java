/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.eventos.ejb;

import co.edu.uniandes.csw.eventos.entities.MemoriaEntity;
import co.edu.uniandes.csw.eventos.entities.MultimediaEntity;
import co.edu.uniandes.csw.eventos.persistence.MemoriaPersistence;
import co.edu.uniandes.csw.eventos.persistence.MultimediaPersistence;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author Gabriel Jose Gonzalez Pereira
 */
public class MemoriasMultimediaLogic 
{
    private static final Logger LOGGER = Logger.getLogger(MemoriasMultimediaLogic.class.getName());
    
    @Inject
    public MemoriaPersistence memoriaPersistence;
    
    @Inject
    public MultimediaPersistence multimediaPersistence;
    
    public MultimediaEntity addMultimedia(Long multimediaId, Long memoriasId)
    {
        LOGGER.log(Level.INFO, "Inicia proceso de agregarle una multimedia a la memoria con id = {0}");
        MultimediaEntity multimediaEntity = multimediaPersistence.find(multimediaId);
        MemoriaEntity memoriaEntity = memoriaPersistence.find(memoriasId);
        multimediaEntity.setMemoria(memoriaEntity);
        LOGGER.log(Level.INFO, "Termina proceso de agregarle una multimedia a la memoria con id = {0}");
        return multimediaEntity; 
    }
    
    public MultimediaEntity getMultimedia(Long multimediasId)
    {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar la multimedia asociada a la memoria con id = {0}", multimediasId);
        return memoriaPersistence.find(multimediasId).getMultimedia();
    }
    
    public MultimediaEntity replaceMultimedia(Long memoriasId, MultimediaEntity multimedia)
    {
       LOGGER.log(Level.INFO, "Inicia proceso de actualizar la memoria con id = {0}", memoriasId);
       MemoriaEntity memoriaEntity = memoriaPersistence.find(memoriasId);
       
       MultimediaEntity m = memoriaEntity.getMultimedia();
       m.setMemoria(null);
       multimediaPersistence.update(m);
       
       MultimediaEntity m1 = multimedia;
       m1.setMemoria(memoriaEntity);
       memoriaEntity.setMultimedia(m1);
       multimediaPersistence.update(m1);
       
       LOGGER.log(Level.INFO, "Termina proceso de actualizar la memoria con id = {0}", memoriasId);
       return m1;
    }
 
}
